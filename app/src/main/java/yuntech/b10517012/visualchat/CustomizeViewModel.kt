package yuntech.b10517012.visualchat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomizeViewModel : ViewModel() {

    private val _color = MutableLiveData<Int>()
    private val _font = MutableLiveData<Float>()
    private val _autoFont = MutableLiveData<Boolean>()
    private val _bold = MutableLiveData<Boolean>()
    private val _layout = MutableLiveData<Int>()

    val currentColor: LiveData<Int> = _color
    val currentFont: LiveData<Float> = _font
    val currentAutoFont: LiveData<Boolean> = _autoFont
    val currentBold: LiveData<Boolean> = _bold
    val currentLayout: LiveData<Int> = _layout

    fun setColor(value: Int) {
        _color.value = value
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

    fun setLayout(value: Int){
        _layout.value = value
    }

    //    val color: LiveData<String> = Transformations.map(_color) {
//        "Hello world from section: $it"
//    }
}