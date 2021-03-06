package yuntech.b10517012.visualchat.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomizeViewModel : ViewModel() {

    val currentColor = MutableLiveData<Int>()
    val bgColor = MutableLiveData<Int>()
    val font = MutableLiveData<Float>()
    val autoFont = MutableLiveData<Boolean>()
    val bold = MutableLiveData<Boolean>()
    val word = MutableLiveData<String>()
    val blink = MutableLiveData<Boolean>()
    val marquee = MutableLiveData<Boolean>()
    val table = MutableLiveData<Boolean>()
    var newWord = MutableLiveData<Boolean>()

    fun setColor(value: Int) {
        currentColor.value = value
    }

    fun setBGColor(value: Int) {
        bgColor.value = value
    }

    fun setFont(value: Float){
        font.value = value
    }

    fun setAutoFont(value: Boolean){
        autoFont.value = value
    }

    fun setBold(value: Boolean){
        bold.value = value
    }

    fun setWord(value: String){
        word.value = value
    }

    fun setBlink(value: Boolean){
        blink.value = value
    }

    fun setMarquee(value: Boolean){
        marquee.value = value
    }

    fun setTable(value: Boolean){
        table.value = value
    }

    fun setNewWord(value: Boolean){
        newWord.value = value
    }

    //    val color: LiveData<String> = Transformations.map(_color) {
//        "Hello world from section: $it"
//    }
}