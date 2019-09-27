package com.molearczyk.gliwice.parking.reminder.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.molearczyk.gliwice.parking.reminder.R
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

            if (ContextCompat.checkSelfPermission(
                            this, Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
            trackingCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    presenter.onGeofenceEnabled()
                } else {
                    presenter.onGeofenceDisabled()
                }
            }

        }
    }

    override fun markAsEnabledFor(dayOfWeek: DayOfWeek) {
        dayButtons[dayOfWeek.ordinal].isChecked = true
    }

    override fun geofencingActionFailed() {
        Snackbar.make(window.decorView, R.string.geofence_error, Snackbar.LENGTH_SHORT).show()
    }

}


