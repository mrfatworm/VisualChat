package yuntech.b10517012.visualchat

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomizeColorFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var customizeViewModel: CustomizeViewModel;

    fun setViewModel(customizeViewModel: CustomizeViewModel){
        this.customizeViewModel = customizeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_customize_color, container, false)
        recyclerView = root.findViewById(R.id.recyclerview)

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        val adapter = ColorAdapter(sampleColorData(), customizeViewModel)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        return root
    }

    private fun sampleColorData(): Array<ColorModel> {
        val dataArray = Array(12){ColorModel(0,0)}
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