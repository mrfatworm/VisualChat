package yuntech.b10517012.visualchat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomizeViewModel : ViewModel() {

    private val _color = MutableLiveData<Int>()
    private val _font = MutableLiveData<Int>()
    private val _bold = MutableLiveData<Boolean>()
    private val _layout = MutableLiveData<Int>()

    val currentColor: LiveData<Int> = _color
    val currentFont: LiveData<Int> = _font
    val currentLayout: LiveData<Int> = _layout

    fun setColor(value: Int) {
        _color.value = value
    }

    fun setFont(value: Int){
        _font.value = value
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