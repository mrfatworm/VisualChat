package yuntech.b10517012.visualchat

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomizeColorFragment : Fragment() {

    private lateinit var tvColor:TextView
    private lateinit var btnColor1:Button
    private lateinit var btnColor2:Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var customizeViewModel: CustomizeViewModel;

    fun setViewModel(customizeViewModel: CustomizeViewModel){
        this.customizeViewModel = customizeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_customize_color, container, false)

        //colorViewModel = ViewModelProviders.of(this).get(ColorViewModel::class.java)
        tvColor = root.findViewById(R.id.tv_color)
        btnColor1 = root.findViewById(R.id.btn_color1)
        btnColor2 = root.findViewById(R.id.btn_color2)
        recyclerView = root.findViewById(R.id.recyclerview)

        btnColor1.setOnClickListener(btnClick())
        btnColor2.setOnClickListener(btnClick())
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        val adapter = ColorAdapter(sampleColorData(), customizeViewModel)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        return root
    }

    private fun btnClick() =View.OnClickListener { view ->
        if(view.id == R.id.btn_color1){
            tvColor.setTextColor(resources.getColor(R.color.black))
            customizeViewModel.setColor(resources.getColor(R.color.black))
        }else{
            tvColor.setTextColor(resources.getColor(R.color.gray))

            customizeViewModel.setColor(resources.getColor(R.color.gray))
        }

    }

    private fun sampleColorData():Array<ColorModel>{
        var data = ColorModel("",0,0)
        data.apply {
            name= ""
            textColor = Color.GREEN
            bgColor = Color.BLACK
        }
        var dataArray = Array<ColorModel>(10){data}

        return dataArray
    }

}