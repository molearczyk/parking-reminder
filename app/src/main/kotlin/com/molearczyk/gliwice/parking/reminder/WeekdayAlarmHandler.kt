package com.molearczyk.gliwice.parking.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import org.threeten.bp.*

class WeekdayAlarmHandler(context: Context) {

    private val appContext: Context = context.applicationContext
    private val alarmManager: AlarmManager = context.getSystemService(AlarmManager::class.java)!!

    fun cancelAlarm(alarmDay: DayOfWeek) {
        alarmManager.cancel(createIntent(alarmDay))
    }

    fun enableAlarm(alarmDay: DayOfWeek) {
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, computeAlarm(OffsetDateTime.now(), alarmDay).toEpochSecond() * 1000, createIntent(alarmDay))
    }

    private fun computeAlarm(todayDateTime: OffsetDateTime, alarmDayFor: DayOfWeek): OffsetDateTime =
            if (todayDateTime.dayOfWeek.value > alarmDayFor.value || (todayDateTime.dayOfWeek.value == alarmDayFor.value && todayDateTime.atZoneSimilarLocal(GLIWICE_ZONE_ID).hour > PAYMENT_START_TIME.hour)) {
                OffsetDateTime.of(LocalDate.now().plusDays(7L - (todayDateTime.dayOfWeek.value - alarmDayFor.value)), PAYMENT_START_TIME, todayDateTime.offset)
            } else {
                OffsetDateTime.of(LocalDate.now().plusDays((alarmDayFor.value - LocalDate.now().dayOfWeek.value).toLong()), PAYMENT_START_TIME, todayDateTime.offset)
            }

    private fun createIntent(alarmDay: DayOfWeek): PendingIntent {
        val dayIndex = alarmDay.value
        return PendingIntent.getBroadcast(appContext, 999 - dayIndex, Intent(appContext, AlarmReceiver::class.java).apply {
            type = "alarm/day$dayIndex"
        }, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    companion object {
        private val GLIWICE_ZONE_ID: ZoneId = ZoneId.of("GMT+2")

        private val PAYMENT_START_TIME: LocalTime
            get() = LocalTime.of(10, 0)

    }
}