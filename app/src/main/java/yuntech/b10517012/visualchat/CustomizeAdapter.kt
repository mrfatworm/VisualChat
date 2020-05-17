package yuntech.b10517012.visualchat

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CustomizeAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager){

    private lateinit var customizeViewModel: CustomizeViewModel;

    fun setViewModel(customizeViewModel: CustomizeViewModel){
        this.customizeViewModel = customizeViewModel
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> CustomizeColorFragment().apply { setViewModel(customizeViewModel) }
            1 -> CustomizeFontFragment().apply { setViewModel(customizeViewModel) }
            else -> CustomizeLayoutFragment().apply { setViewModel(customizeViewModel) }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "色彩配置"
            1 -> "字型大小"
            else -> "方向設置"

        }
    }
}