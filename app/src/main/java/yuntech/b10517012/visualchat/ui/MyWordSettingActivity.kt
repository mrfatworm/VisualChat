package yuntech.b10517012.visualchat.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.adapter.WordSettingAdapter
import yuntech.b10517012.visualchat.model.WordModel
import yuntech.b10517012.visualchat.sqlite.MyWordDAO
import java.util.*
import kotlin.collections.ArrayList


class MyWordSettingActivity : AppCompatActivity(), IEditWord {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WordSettingAdapter
    private lateinit var btnAdd: FloatingActionButton
    private var wordList: MutableList<WordModel> = ArrayList()
    private lateinit var myWordDAO: MyWordDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_word_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()
        initRecyclerView()

        btnAdd.setOnClickListener {
            alertEditWord(0,"")
        }
    }

    private fun updateData() {
        wordList.clear()
        wordList.addAll(myWordDAO.getAll()!!)
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerview)
        btnAdd = findViewById(R.id.fab_add_word)
        myWordDAO = MyWordDAO(this)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true
        recyclerView.layoutManager = layoutManager
        wordList.addAll(myWordDAO.getAll()!!)
        adapter = WordSettingAdapter(wordList, this)
        recyclerView.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                myWordDAO.update(WordModel(wordList[fromPos].id, wordList[fromPos].sentence, wordList[toPos].order))
                myWordDAO.update(WordModel(wordList[toPos].id, wordList[toPos].sentence, wordList[fromPos].order))
                updateData()
                adapter.notifyItemMoved(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removePos = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.RIGHT){
                    myWordDAO.delete(wordList[removePos].id)
                    updateData()
                    adapter.notifyItemRemoved(removePos)
                }
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun alertEditWord(index: Long, word: String) {
        Log.v("ListMon", "Click =  $index")
        val itemView = LayoutInflater.from(this).inflate(R.layout.alert_add_word, null)
        val edtAdd = itemView.findViewById<EditText>(R.id.edt_add_input)
        edtAdd.setText(word)
        val title: String
        title = if (edtAdd.text.toString() == ""){
            getString(R.string.new_word)
        }else{
            getString(R.string.mod_word)
        }
        edtAdd.setText(word)
        AlertDialog.Builder(this)
            .setTitle(title + getString(R.string.word))
            .setView(itemView)
            .setPositiveButton(title){ _, _ ->
                if (edtAdd.text.toString() == ""){}
                else if (title == getString(R.string.new_word)){
                    myWordDAO.insert(WordModel(0, edtAdd.text.toString(), myWordDAO.getLargestOrder()+1))
                    updateData()
                    adapter.notifyDataSetChanged()
                }else if(title == getString(R.string.mod_word)){
                    myWordDAO.update(WordModel(index, edtAdd.text.toString(), 0))
                    updateData()
                    adapter.notifyDataSetChanged()
                }
            }.show()
    }
}