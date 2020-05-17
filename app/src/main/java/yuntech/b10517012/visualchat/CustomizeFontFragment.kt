package yuntech.b10517012.visualchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsSeekBar
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Switch
import androidx.fragment.app.Fragment

class CustomizeFontFragment : Fragment() {

    private lateinit var customizeViewModel: CustomizeViewModel;
    private lateinit var swAuto: Switch
    private lateinit var seekBar: SeekBar
    private lateinit var btnBold: ImageButton

    fun setViewModel(customizeViewModel: CustomizeViewModel){
        this.customizeViewModel = customizeViewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_customize_font, container, false)

        return root
    }
}