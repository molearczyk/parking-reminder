package com.molearczyk.gliwice.parking.reminder

import org.threeten.bp.DayOfWeek

interface MainActivityView {

    fun markAsEnabledFor(dayOfWeek: DayOfWeek)

}