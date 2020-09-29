package yuntech.b10517012.visualchat.ui

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import yuntech.b10517012.visualchat.model.CustomizeViewModel
import yuntech.b10517012.visualchat.R

class AdvanceFunctionFragment : PreferenceFragmentCompat() {

    private lateinit var customizeViewModel: CustomizeViewModel

    fun setViewModel(customizeViewModel: CustomizeViewModel){
        this.customizeViewModel = customizeViewModel
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.advance_preferences, rootKey)

        val swBlink = findPreference<SwitchPreference>("blink")
        val swMarquee = findPreference<SwitchPreference>("marquee")

        swBlink?.setOnPreferenceClickListener {
            customizeViewModel.setBlink(swBlink.isChecked)
            true
        }

        swMarquee?.setOnPreferenceClickListener {
            customizeViewModel.setMarquee(swMarquee.isChecked)
            true
        }
    }
}