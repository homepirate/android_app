package com.example.lab1

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import kotlinx.coroutines.launch
import java.io.File

class SettingsFragment : PreferenceFragmentCompat() {


    private val fileName = "loginov.txt"
    private lateinit var internalStorageFile: File

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        internalStorageFile = File(requireContext().filesDir, fileName)

        checkFileStatus() // Check file status when the preferences are created


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

        findPreference<Preference>("delete_file")?.setOnPreferenceClickListener {
            deleteFile()
            true
        }

        findPreference<Preference>("restore_file")?.setOnPreferenceClickListener {
            restoreFile()
            true
        }
    }

    private fun checkFileStatus() {
        val externalFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName)

        val fileStatusPreference: Preference? = findPreference("file_status")
        fileStatusPreference?.summary = if (externalFile.exists()) {
            "File exists in external storage."
        } else {
            "File does not exist in external storage."
        }
    }

    private fun deleteFile() {
        val externalFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName)
        if (externalFile.exists()) {
            backupFileToInternalStorage(externalFile)
            externalFile.delete()
            Toast.makeText(requireContext(), "File deleted from external storage.", Toast.LENGTH_SHORT).show()
            checkFileStatus()
        } else {
            Toast.makeText(requireContext(), "File not found in external storage.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun backupFileToInternalStorage(externalFile: File) {
        if (externalFile.exists()) {
            externalFile.copyTo(internalStorageFile, overwrite = true)
            Toast.makeText(requireContext(), "File backed up to internal storage.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun restoreFile() {
        if (internalStorageFile.exists()) {
            val externalFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName)
            internalStorageFile.copyTo(externalFile, overwrite = true)
            Toast.makeText(requireContext(), "File restored to external storage.", Toast.LENGTH_SHORT).show()
            checkFileStatus()
        } else {
            Toast.makeText(requireContext(), "No backup found in internal storage.", Toast.LENGTH_SHORT).show()
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