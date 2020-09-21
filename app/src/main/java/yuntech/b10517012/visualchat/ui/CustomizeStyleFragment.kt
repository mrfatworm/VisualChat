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
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import yuntech.b10517012.visualchat.model.ColorModel
import yuntech.b10517012.visualchat.model.CustomizeViewModel
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.adapter.ColorAdapter

class CustomizeStyleFragment : Fragment() {

    private lateinit var customizeViewModel: CustomizeViewModel;
    private lateinit var recyclerView: RecyclerView
    private lateinit var swAuto: Switch
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

        pref = this.context!!.getSharedPreferences("favor", Context.MODE_PRIVATE)

        initRecyclerview()

        swAuto.setOnCheckedChangeListener { compoundButton, b ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                seekBar.isEnabled = !b
                customizeViewModel.setAutoFont(b)
                pref.edit().putBoolean("Auto", b).apply()
            }else if(b){
                Toast.makeText(context, getString(R.string.auto_font_warning), Toast.LENGTH_SHORT).show()
            }
        }

        cbBold.setOnCheckedChangeListener { compoundButton, b ->
            customizeViewModel.setBold(b)
            pref.edit().putBoolean("Bold", b).apply()
        }

        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                customizeViewModel.setFont((p1*10).toFloat())
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
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        val adapter = ColorAdapter(sampleColorData(), customizeViewModel, pref)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

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
        seekBar.setProgress(pref.getInt("FontSize", 2))
    }

    private fun sampleColorData(): Array<ColorModel> {
        val dataArray = Array(12){ ColorModel(0,0) }
        dataArray[0].apply {
            textColor = Color.WHITE
            bgColor = resources.getColor(R.color.blackboard) }
        dataArray[1].apply {
            textColor = Color.WHITE
            bgColor = resources.getColor(R.color.darkYellow) }
        dataArray[2].apply {
            textColor = Color.WHITE
            bgColor = resources.getColor(R.color.darkRed) }
        dataArray[3].apply {
            textColor = Color.WHITE
            bgColor = resources.getColor(R.color.navy) }
        dataArray[4].apply {
            textColor = Color.WHITE
            bgColor = Color.BLACK }
        dataArray[5].apply {
            textColor = resources.getColor(R.color.pttYellow)
            bgColor = Color.BLACK }
        dataArray[6].apply {
            textColor = Color.GREEN
            bgColor = Color.BLACK }
        dataArray[7].apply {
            textColor = resources.getColor(R.color.skin)
            bgColor = resources.getColor(R.color.navy) }
        dataArray[8].apply {
            textColor = resources.getColor(R.color.navy)
            bgColor = resources.getColor(R.color.lightYellow) }
        dataArray[9].apply {
            textColor = resources.getColor(R.color.pink)
            bgColor = resources.getColor(R.color.lightYellow) }
        dataArray[10].apply {
            textColor = Color.WHITE
            bgColor = resources.getColor(R.color.pink) }
        dataArray[11].apply {
            textColor = Color.BLACK
            bgColor = resources.getColor(R.color.pttYellow) }



        return dataArray
    }

}