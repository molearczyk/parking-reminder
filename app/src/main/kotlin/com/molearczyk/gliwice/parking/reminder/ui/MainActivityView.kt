package com.molearczyk.gliwice.parking.reminder.ui

import org.threeten.bp.DayOfWeek

interface MainActivityView {

    fun markAsEnabledFor(dayOfWeek: DayOfWeek)

}