package yuntech.b10517012.visualchat.ui

import android.os.Bundle
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
import java.util.*
import kotlin.collections.ArrayList


class MyWordSettingActivity : AppCompatActivity(), IEditWord {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WordSettingAdapter
    private lateinit var btnAdd: FloatingActionButton
    private var wordList: MutableList<WordModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_word_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()
        initData()
        initRecyclerView()

        btnAdd.setOnClickListener {
            alertEditWord(0,"")
        }
    }


    private fun initView() {
        recyclerView = findViewById(R.id.recyclerview)
        btnAdd = findViewById(R.id.fab_add_word)
    }

    private fun initData() {
        wordList.add(WordModel(1, "謝謝你"))
        wordList.add(WordModel(2, "了解"))
        wordList.add(WordModel(3, "好的"))
        wordList.add(WordModel(4, "瞧你那啥逼樣，信不信老子打死你"))
        wordList.add(WordModel(5, "珍珠紅茶拿鐵，全糖去冰，幹嘛？拎北臺南人啦"))
        wordList.add(WordModel(6, "這邊請"))
        wordList.add(WordModel(7, "早安"))
        wordList.add(WordModel(8, "+1"))
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true
        recyclerView.layoutManager = layoutManager
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
                Collections.swap(wordList, fromPos, toPos)
                adapter.notifyItemMoved(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removePos = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.RIGHT){
                    wordList.removeAt(removePos)
                    adapter.notifyItemRemoved(removePos)
                }
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun alertEditWord(index: Int, word: String) {
        val itemView = LayoutInflater.from(this).inflate(R.layout.alert_add_word, null)
        val edtAdd = itemView.findViewById<EditText>(R.id.edt_add_input)
        edtAdd.setText(word)
        var title: String
        if (edtAdd.text.toString() == ""){
            title = "新增"
        }else{
            title = "修改"
        }
        edtAdd.setText(word)
        AlertDialog.Builder(this)
            .setTitle("$title 詞句")
            .setView(itemView)
            .setPositiveButton(title){ _, _ ->
                if (edtAdd.text.toString() == ""){}
                else if (title =="新增"){
                    wordList.add(wordList.lastIndex + 1 ,WordModel(0, edtAdd.text.toString()))
                    adapter.notifyItemInserted(wordList.lastIndex + 1)
                }else if(title =="修改"){
                    wordList[index] = WordModel(index, edtAdd.text.toString())
                    adapter.notifyItemChanged(index)
                }
            }.show()
    }
}