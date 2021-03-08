package yuntech.b10517012.visualchat.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.adapter.ColorAdapter
import yuntech.b10517012.visualchat.model.ColorModel
import yuntech.b10517012.visualchat.model.CustomizeViewModel

class CustomizeStyleFragment : Fragment() {

    private lateinit var customizeViewModel: CustomizeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swAuto: SwitchMaterial
    private lateinit var seekBar: SeekBar
    private lateinit var cbBold: CheckBox
    private lateinit var pref: SharedPreferences

    fun setViewModel(customizeViewModel: CustomizeViewModel){
        this.customizeViewModel = customizeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_customize_style, container, false)
        initView(root)

        pref = this.requireContext().getSharedPreferences("favor", Context.MODE_PRIVATE)

        initRecyclerview()

        // On/off Auto size function
        swAuto.setOnCheckedChangeListener { _, b ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                seekBar.isEnabled = !b
                customizeViewModel.setAutoFont(b)
                pref.edit().putBoolean("Auto", b).apply()
            }else if(b){
                Toast.makeText(context, getString(R.string.auto_font_warning), Toast.LENGTH_SHORT).show()
            }
        }

        // Font bold editor
        cbBold.setOnCheckedChangeListener { _, b ->
            customizeViewModel.setBold(b)
            pref.edit().putBoolean("Bold", b).apply()
        }

        // Font size editor
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                customizeViewModel.setFont(((p1 + 2)*5).toFloat())
                pref.edit().putInt("FontSize", p1).apply()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) { }
            override fun onStopTrackingTouch(p0: SeekBar?) { }
        })

        loadFavor()

        return root
    }

    private fun initView(root: View) {
        recyclerView = root.findViewById(R.id.recyclerview)
        swAuto = root.findViewById(R.id.sw_font_auto)
        seekBar = root.findViewById(R.id.seekBar_font)
        cbBold = root.findViewById(R.id.cb_font_bold)
    }

    private fun initRecyclerview() {
        val linearLayoutManager = LinearLayoutManager(context)
        val colorModel = ColorModel()
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        val adapter = ColorAdapter( colorModel.sampleColorData(requireContext()), customizeViewModel, pref)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    // Loading history setting
    private fun loadFavor() {
        if (pref.getInt("TextColor", 0) != 0){
            customizeViewModel.setColor(pref.getInt("TextColor", Color.WHITE))
            customizeViewModel.setBGColor(pref.getInt("BgColor", R.color.colorPrimaryDark))
        }
        if (pref.getBoolean("Auto", false)) {
            swAuto.isChecked = true
        }
        if (pref.getBoolean("Bold", false)) {
            cbBold.isChecked = true
        }
        seekBar.progress = pref.getInt("FontSize", 2)
    }

}