package com.example.lab1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import kotlinx.coroutines.launch

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val sharedPreferences = requireActivity().getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE)

        val emailPreference = findPreference<EditTextPreference>("example_text")
        emailPreference?.setOnPreferenceChangeListener { _, newValue ->
            val editor = sharedPreferences?.edit()
            editor?.putString("user_email", newValue as String)
            editor?.apply()
            true
        }

        val passwordPreference = findPreference<EditTextPreference>("example_password")
        passwordPreference?.setOnPreferenceChangeListener { _, newValue ->
            val editor = sharedPreferences?.edit()
            editor?.putString("user_password", newValue as String)  // Сохранение пароля
            editor?.apply()
            true
        }

        val switchPreference = findPreference<SwitchPreference>("example_switch")
        switchPreference?.setOnPreferenceChangeListener { _, newValue ->
            lifecycleScope.launch {
                saveTheme(newValue as Boolean)
            }
            true
        }

        val fontSizePreference = findPreference<EditTextPreference>("font_size")
        fontSizePreference?.setOnPreferenceChangeListener { _, newValue ->
            lifecycleScope.launch {
                val fontSize = (newValue as String).toIntOrNull() ?: 14
                saveFont(fontSize)
            }
            true
        }
    }



    private suspend fun saveTheme(isDarkMode: Boolean) {
        requireContext().dataStore.edit { settings ->
            settings[SettingsDataStore.PreferencesKeys.IS_DARK_MODE] = isDarkMode
        }
    }
    private suspend fun saveFont(fontSize: Int){
        requireContext().dataStore.edit { settings ->
            settings[SettingsDataStore.PreferencesKeys.FONT_SIZE] = fontSize
        }
    }
}