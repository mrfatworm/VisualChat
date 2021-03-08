package yuntech.b10517012.visualchat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.adapter.WordAdapter
import yuntech.b10517012.visualchat.model.CustomizeViewModel
import yuntech.b10517012.visualchat.model.WordModel
import yuntech.b10517012.visualchat.sqlite.MyWordDAO
import java.util.*

class MyWordFragment : Fragment(){

    private lateinit var customizeViewModel: CustomizeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var wordAdapter: WordAdapter
    private val wordList: MutableList<WordModel> = ArrayList()
    private lateinit var myWordDAO: MyWordDAO
    private lateinit var tvNothing: TextView

    fun setViewModel(customizeViewModel: CustomizeViewModel){
        this.customizeViewModel = customizeViewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_my_word, container, false)

        initView(root)
        initData()
        initRecyclerView()

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        customizeViewModel.newWord.observe(viewLifecycleOwner, Observer {
            if (it){
                updateData()
            }
             })
    }

    private fun initData() {
        myWordDAO = MyWordDAO(context)
        wordList.addAll(myWordDAO.getAll()!!)
        checkEmpty()
    }

    fun updateData(){
        wordList.clear()
        wordList.addAll(myWordDAO.getAll()!!)
        checkEmpty()
        wordList.reverse()
        wordAdapter.notifyDataSetChanged()
    }

    private fun checkEmpty() {
        if (wordList.isEmpty()) {
            tvNothing.visibility = View.VISIBLE
        } else {
            tvNothing.visibility = View.INVISIBLE
        }
    }

    private fun initView(root: View) {
        recyclerView = root.findViewById(R.id.recyclerview)
        tvNothing = root.findViewById(R.id.tv_nothing)
    }

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        wordList.reverse()
        wordAdapter = WordAdapter(wordList, customizeViewModel)
        recyclerView.adapter = wordAdapter
    }
}