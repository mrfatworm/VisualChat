package yuntech.b10517012.visualchat.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import yuntech.b10517012.visualchat.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val prefWordSetting = findPreference<Preference>("word_setting")
            val prefAboutApp = findPreference<Preference>("about_app")

            prefWordSetting?.setOnPreferenceClickListener {
                val intent = Intent(context, MyWordSettingActivity::class.java)
                startActivity(intent)
                true
            }

            prefAboutApp?.setOnPreferenceClickListener {
                val intent = Intent(context, AboutActivity::class.java)
                startActivity(intent)
                true
            }

        }
    }
}