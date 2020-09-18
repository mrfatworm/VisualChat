package yuntech.b10517012.visualchat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import yuntech.b10517012.visualchat.model.ColorModel
import yuntech.b10517012.visualchat.model.CustomizeViewModel
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.model.WordModel

class WordAdapter : RecyclerView.Adapter<WordAdapter.ViewHolder>{
    private var data: List<WordModel>
    private var customizeViewModel: CustomizeViewModel

    constructor(data: List<WordModel>, customizeViewModel: CustomizeViewModel): super(){
        this.data = data
        this.customizeViewModel = customizeViewModel
    }

    class ViewHolder:RecyclerView.ViewHolder{
        lateinit var textView: TextView
        constructor(itemView: View): super(itemView)
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