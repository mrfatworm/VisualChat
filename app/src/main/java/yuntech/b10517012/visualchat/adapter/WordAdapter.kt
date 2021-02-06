package yuntech.b10517012.visualchat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.model.CustomizeViewModel
import yuntech.b10517012.visualchat.model.WordModel

class WordAdapter(
    private var data: List<WordModel>,
    private var customizeViewModel: CustomizeViewModel) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var textView: TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cell = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_word, parent, false)
        val viewHolder = ViewHolder(cell)
        viewHolder.textView = cell.findViewById(R.id.tv_word)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]
        holder.textView.text = model.sentence

        holder.textView.setOnClickListener{
            customizeViewModel.setWord(model.sentence)
        }
    }
}