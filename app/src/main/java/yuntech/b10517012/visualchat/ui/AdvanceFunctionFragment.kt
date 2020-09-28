package yuntech.b10517012.visualchat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import yuntech.b10517012.visualchat.model.CustomizeViewModel
import yuntech.b10517012.visualchat.R

class AdvanceFunctionFragment : Fragment() {

    private lateinit var customizeViewModel: CustomizeViewModel;
    private lateinit var swFlash: Switch
    private lateinit var swMarquee: Switch

    fun setViewModel(customizeViewModel: CustomizeViewModel){
        this.customizeViewModel = customizeViewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_advance_function, container, false)

        initView(root)

        swFlash.setOnCheckedChangeListener { compoundButton, b ->
            customizeViewModel.setFlash(b)
        }

        swMarquee.setOnCheckedChangeListener { compoundButton, b ->
            customizeViewModel.setMarquee(b)
        }

        return root
    }

    private fun initView(root: View) {
        swFlash = root.findViewById(R.id.sw_flash)
        swMarquee = root.findViewById(R.id.sw_marquee)
    }
}