package com.molearczyk.gliwice.parking.reminder

import io.realm.RealmObject
import org.threeten.bp.DayOfWeek

open class AlarmStorageModel() : RealmObject() {

    constructor(value: DayOfWeek) : this() {
        dayOfWeek = value
    }


    private var _dayOfWeek: Int = -1

    var dayOfWeek: DayOfWeek
        get() = DayOfWeek.of(_dayOfWeek)
        set(value) {
            _dayOfWeek = value.value
        }

}