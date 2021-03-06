package yuntech.b10517012.visualchat.adapter

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.model.ColorModel
import yuntech.b10517012.visualchat.model.CustomizeViewModel

class ColorAdapter(
    private var data: Array<ColorModel.ColorText>,
    private var customizeViewModel: CustomizeViewModel,
    private var pref: SharedPreferences) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var textView: TextView
        lateinit var cardView: CardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cell = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_color, parent, false)
        val viewHolder = ViewHolder(cell)
        viewHolder.textView = cell.findViewById(R.id.tv1)
        viewHolder.cardView = cell.findViewById(R.id.card_view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]
        holder.textView.setTextColor(model.textColor)
        holder.cardView.setCardBackgroundColor(model.bgColor)

        holder.cardView.setOnClickListener{
            customizeViewModel.setColor(model.textColor)
            customizeViewModel.setBGColor(model.bgColor)
            pref.edit()
                .putInt("TextColor", model.textColor)
                .putInt("BgColor", model.bgColor)
                .apply()
        }
    }
}