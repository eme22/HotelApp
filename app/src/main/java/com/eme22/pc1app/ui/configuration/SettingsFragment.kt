package com.eme22.pc1app.ui.configuration

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.eme22.pc1app.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}