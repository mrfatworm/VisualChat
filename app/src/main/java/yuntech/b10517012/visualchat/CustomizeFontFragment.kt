package yuntech.b10517012.visualchat

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class CustomizeFontFragment : Fragment() {

    private lateinit var customizeViewModel: CustomizeViewModel;
    private lateinit var swAuto: Switch
    private lateinit var seekBar: SeekBar
    private lateinit var cbBold: CheckBox

    fun setViewModel(customizeViewModel: CustomizeViewModel){
        this.customizeViewModel = customizeViewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_customize_font, container, false)

        swAuto = root.findViewById(R.id.sw_font_auto)
        seekBar = root.findViewById(R.id.seekBar_font)
        cbBold = root.findViewById(R.id.cb_font_bold)

        swAuto.setOnCheckedChangeListener { compoundButton, b ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                seekBar.isEnabled = !b
                customizeViewModel.setAutoFont(b)
            }else if(b){
                Toast.makeText(context, getString(R.string.auto_font_warning), Toast.LENGTH_SHORT).show()
            }

        }

        cbBold.setOnCheckedChangeListener { compoundButton, b ->
            customizeViewModel.setBold(b)
        }

        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                customizeViewModel.setFont((p1*10).toFloat())
            }
            override fun onStartTrackingTouch(p0: SeekBar?) { }
            override fun onStopTrackingTouch(p0: SeekBar?) { }
        })

        return root
    }
}