package yuntech.b10517012.visualchat.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomizeViewModel : ViewModel() {

    private val _color = MutableLiveData<Int>()
    private val _bgColor = MutableLiveData<Int>()
    private val _font = MutableLiveData<Float>()
    private val _autoFont = MutableLiveData<Boolean>()
    private val _bold = MutableLiveData<Boolean>()
    private val _word = MutableLiveData<String>()
    private val _flash = MutableLiveData<Boolean>()
    private var _marquee = MutableLiveData<Boolean>()

    val currentColor: LiveData<Int> = _color
    val currentBGColor: LiveData<Int> = _bgColor
    val currentFont: LiveData<Float> = _font
    val currentAutoFont: LiveData<Boolean> = _autoFont
    val currentBold: LiveData<Boolean> = _bold
    val currentWord: LiveData<String> = _word
    val currentFlash: LiveData<Boolean> = _flash
    val currentMarquee: LiveData<Boolean> = _marquee

    fun setColor(value: Int) {
        _color.value = value
    }

    fun setBGColor(value: Int) {
        _bgColor.value = value
    }

    fun setFont(value: Float){
        _font.value = value
    }

    fun setAutoFont(value: Boolean){
        _autoFont.value = value
    }

    fun setBold(value: Boolean){
        _bold.value = value
    }

    fun setWord(value: String){
        _word.value = value
    }

    fun setFlash(value: Boolean){
        _flash.value = value
    }

    fun setMarquee(value: Boolean){
        _marquee.value = value
    }

    //    val color: LiveData<String> = Transformations.map(_color) {
//        "Hello world from section: $it"
//    }
}