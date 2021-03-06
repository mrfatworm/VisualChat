package yuntech.b10517012.visualchat.model

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import yuntech.b10517012.visualchat.R

class ColorModel() {

    class ColorText(var textColor: Int, var bgColor: Int)
    
    public fun sampleColorData(context: Context): Array<ColorText> {
        val dataArray = Array(17){ ColorText(0,0) }
        dataArray[0].apply {
            textColor = Color.WHITE
            bgColor = ContextCompat.getColor(context, R.color.blackboard)}
        dataArray[1].apply {
            textColor = Color.WHITE
            bgColor = ContextCompat.getColor(context, R.color.darkYellow) }
        dataArray[2].apply {
            textColor = Color.WHITE
            bgColor = ContextCompat.getColor(context, R.color.darkRed) }
        dataArray[3].apply {
            textColor = Color.WHITE
            bgColor = ContextCompat.getColor(context, R.color.navy) }
        dataArray[4].apply {
            textColor = Color.WHITE
            bgColor = Color.BLACK }
        dataArray[5].apply {
            textColor = ContextCompat.getColor(context, R.color.pttYellow)
            bgColor = Color.BLACK }
        dataArray[6].apply {
            textColor = Color.GREEN
            bgColor = Color.BLACK }
        dataArray[7].apply {
            textColor = ContextCompat.getColor(context, R.color.skin)
            bgColor = ContextCompat.getColor(context, R.color.black) }
        dataArray[8].apply {
            textColor = ContextCompat.getColor(context, R.color.skin)
            bgColor = ContextCompat.getColor(context, R.color.navy) }
        dataArray[9].apply {
            textColor = ContextCompat.getColor(context, R.color.pttYellow)
            bgColor = ContextCompat.getColor(context, R.color.navy) }
        dataArray[10].apply {
            textColor = ContextCompat.getColor(context, R.color.black)
            bgColor = ContextCompat.getColor(context, R.color.lightYellow) }
        dataArray[11].apply {
            textColor = ContextCompat.getColor(context, R.color.navy)
            bgColor = ContextCompat.getColor(context, R.color.lightYellow) }
        dataArray[12].apply {
            textColor = ContextCompat.getColor(context, R.color.pink)
            bgColor = ContextCompat.getColor(context, R.color.lightYellow) }
        dataArray[13].apply {
            textColor = Color.WHITE
            bgColor = ContextCompat.getColor(context, R.color.pink) }
        dataArray[14].apply {
            textColor = Color.BLACK
            bgColor = ContextCompat.getColor(context, R.color.pttYellow) }
        dataArray[15].apply {
            textColor = ContextCompat.getColor(context, R.color.navy)
            bgColor = ContextCompat.getColor(context, R.color.pttYellow) }
        dataArray[16].apply {
            textColor = ContextCompat.getColor(context, R.color.white)
            bgColor = ContextCompat.getColor(context, R.color.colorPrimaryDark) }

        return dataArray
    }
}