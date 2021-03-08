package yuntech.b10517012.visualchat.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_my_word_setting.*
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.adapter.WordSettingAdapter
import yuntech.b10517012.visualchat.model.WordModel
import yuntech.b10517012.visualchat.sqlite.MyWordDAO
import yuntech.b10517012.visualchat.utils.AlertEditWord


class MyWordSettingActivity : AppCompatActivity(), IEditWord {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WordSettingAdapter
    private var wordList: MutableList<WordModel> = ArrayList()
    private lateinit var myWordDAO: MyWordDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_word_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()
        initRecyclerView()

        fab_add_word.setOnClickListener {
            myWordDialog(0, "")
        }

        btn_nothing.setOnClickListener {
            myWordDialog(0, "")
        }
    }

    private fun updateData() {
        wordList.clear()
        wordList.addAll(myWordDAO.getAll()!!)
        checkEmpty()
    }

    private fun checkEmpty() {
        if (wordList.isEmpty()){
            tv_nothing.visibility = View.VISIBLE
            btn_nothing.visibility = View.VISIBLE
        }else{
            tv_nothing.visibility = View.INVISIBLE
            btn_nothing.visibility = View.INVISIBLE
        }
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerview)
        myWordDAO = MyWordDAO(this)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true
        recyclerView.layoutManager = layoutManager
        wordList.addAll(myWordDAO.getAll()!!)
        checkEmpty()
        adapter = WordSettingAdapter(wordList, this)
        recyclerView.adapter = adapter

        /** Long click to sort, right swipe to delete */
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

    override fun myWordDialog(index: Long, word: String) {
        val alertEditWord = AlertEditWord()
        alertEditWord.addAndMod(index, word, this, myWordDAO)
    }

    override fun updateAsDialogFinish() {
        updateData()
        adapter.notifyDataSetChanged()
    }
}