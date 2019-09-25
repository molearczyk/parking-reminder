package com.molearczyk.gliwice.parking.reminder.alarms

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId

class AlarmCalculator {

    fun computeTriggerTime(todayDateTime: OffsetDateTime, alarmDayFor: DayOfWeek): OffsetDateTime {
        val todayDate = todayDateTime.toLocalDate()
        return if (todayDateTime.isWeekDayAfter(alarmDayFor) || todayDateTime.isTodayPastStartTime(alarmDayFor)) {
            OffsetDateTime.of(todayDate.plusDays(7L - (todayDateTime.dayOfWeek.value - alarmDayFor.value)), PAYMENT_START_TIME, todayDateTime.offset)
        } else {
            OffsetDateTime.of(todayDate.plusDays((alarmDayFor.value - todayDate.dayOfWeek.value).toLong()), PAYMENT_START_TIME, todayDateTime.offset)
        }
    }

    private fun OffsetDateTime.isTodayPastStartTime(alarmDayFor: DayOfWeek) =
            dayOfWeek.value == alarmDayFor.value
                    && atZoneSimilarLocal(GLIWICE_ZONE_ID).hour >= PAYMENT_START_TIME.hour && atZoneSimilarLocal(GLIWICE_ZONE_ID).minute > PAYMENT_START_TIME.minute

    private fun OffsetDateTime.isWeekDayAfter(alarmDayFor: DayOfWeek) =
            dayOfWeek.value > alarmDayFor.value

    companion object {
        private val GLIWICE_ZONE_ID: ZoneId = ZoneId.of("GMT+2")

        private val PAYMENT_START_TIME: LocalTime
            get() = LocalTime.of(10, 0)

    }

}