package com.molearczyk.gliwice.parking.reminder.alarms

import org.junit.Test
import org.threeten.bp.*

class AlarmCalculatorTest {

    @Test
    fun `alarm calculator computes forward date of the week correctly`() {
        val mondayDateTime = OffsetDateTime.of(LocalDate.of(2019, 9, 23), LocalTime.of(9, 0), ZoneOffset.ofHours(2))

        val expectedTuesDayTriggerDate = OffsetDateTime.of(LocalDate.of(2019, 9, 24), LocalTime.of(10, 0), ZoneOffset.ofHours(2))

        assert(AlarmCalculator().computeTriggerTime(mondayDateTime, DayOfWeek.TUESDAY).isEqual(expectedTuesDayTriggerDate))
    }

    @Test
    fun `alarm calculator computes backward date of the week correctly`() {
        val fridayDateTime = OffsetDateTime.of(LocalDate.of(2019, 9, 27), LocalTime.of(9, 0), ZoneOffset.ofHours(2))

        val expectedNextTuesDayTriggerDate = OffsetDateTime.of(LocalDate.of(2019, 9, 24).plusWeeks(1), LocalTime.of(10, 0), ZoneOffset.ofHours(2))

        assert(AlarmCalculator().computeTriggerTime(fridayDateTime, DayOfWeek.TUESDAY).isEqual(expectedNextTuesDayTriggerDate))
    }

    @Test
    fun `alarm calculator for a date past parking time computes next week day correctly`() {
        val tuesDayDateTime = OffsetDateTime.of(LocalDate.of(2019, 9, 24), LocalTime.of(10, 20), ZoneOffset.ofHours(2))

        val expectedNextTuesDayTriggerDate = OffsetDateTime.of(LocalDate.of(2019, 9, 24).plusWeeks(1), LocalTime.of(10, 0), ZoneOffset.ofHours(2))

        assert(AlarmCalculator().computeTriggerTime(tuesDayDateTime, DayOfWeek.TUESDAY).isEqual(expectedNextTuesDayTriggerDate))
    }

}

