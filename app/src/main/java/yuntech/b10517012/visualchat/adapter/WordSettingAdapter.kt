package yuntech.b10517012.visualchat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.model.WordModel
import yuntech.b10517012.visualchat.ui.IEditWord

class WordSettingAdapter(private var data: List<WordModel>, private val iView: IEditWord) :
    RecyclerView.Adapter<WordSettingAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var textView: TextView
        lateinit var imageButton: ImageButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cell = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_word_setting, parent, false)
        val viewHolder = ViewHolder(cell)
        viewHolder.textView = cell.findViewById(R.id.tv1)
        viewHolder.imageButton = cell.findViewById(R.id.imageButton)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]
        holder.textView.text = model.sentence

        holder.textView.setOnClickListener{
            iView.alertEditWord(model.id,model.sentence)
        }
    }
}