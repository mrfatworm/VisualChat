package yuntech.b10517012.visualchat.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.model.CustomizeViewModel

class AdvanceFunctionFragment : PreferenceFragmentCompat() {

    private lateinit var customizeViewModel: CustomizeViewModel

    fun setViewModel(customizeViewModel: CustomizeViewModel){
        this.customizeViewModel = customizeViewModel
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.advance_preferences, rootKey)

        val swBlink = findPreference<SwitchPreference>("blink")
        val swMarquee = findPreference<SwitchPreference>("marquee")
        val swTable = findPreference<SwitchPreference>("table_mode")

        swBlink?.isChecked = false
        swMarquee?.isChecked = false
        swTable?.isChecked = false

        swBlink?.setOnPreferenceClickListener {
            customizeViewModel.setBlink(swBlink.isChecked)
            true
        }

        swMarquee?.setOnPreferenceClickListener {
            customizeViewModel.setMarquee(swMarquee.isChecked)
            true
        }
        swTable?.setOnPreferenceClickListener {
            customizeViewModel.setTable(swTable.isChecked)
            true
        }
    }
}