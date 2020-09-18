package yuntech.b10517012.visualchat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.adapter.WordAdapter
import yuntech.b10517012.visualchat.model.CustomizeViewModel
import yuntech.b10517012.visualchat.model.WordModel
import java.util.*

class MyWordFragment : Fragment() {

    private lateinit var customizeViewModel: CustomizeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var wordAdapter: WordAdapter
    private val wordList: MutableList<WordModel> = ArrayList()

    fun setViewModel(customizeViewModel: CustomizeViewModel){
        this.customizeViewModel = customizeViewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_my_word, container, false)

        initView(root)
        initData()
        val layoutManager = GridLayoutManager(context,2)
        recyclerView.layoutManager = layoutManager
        wordAdapter = WordAdapter(wordList, customizeViewModel)
        recyclerView.adapter = wordAdapter

        return root
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

    private fun initView(root: View) {
        recyclerView = root.findViewById(R.id.recyclerview)
    }
}