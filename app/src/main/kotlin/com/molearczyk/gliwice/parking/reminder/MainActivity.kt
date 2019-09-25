package com.molearczyk.gliwice.parking.reminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.threeten.bp.DayOfWeek


class MainActivity : AppCompatActivity(), MainActivityView {

    private val dayButtons by lazy {
        arrayOf(day1Button, day2Button, day3Button, day4Button, day5Button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainActivityPresenter(this, this).let { presenter ->
            presenter.init()
            dayButtons.forEachIndexed { index, button ->
                button.addOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        presenter.onAlarmEnabled(DayOfWeek.values()[index])
                    } else {
                        presenter.onAlarmDisabled(DayOfWeek.values()[index])
                    }
                }
            }
        }
    }

    override fun markAsEnabledFor(dayOfWeek: DayOfWeek) {
        dayButtons[dayOfWeek.ordinal].isChecked = true
    }

}


