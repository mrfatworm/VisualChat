package yuntech.b10517012.visualchat

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private lateinit var customizeViewModel: CustomizeViewModel;

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CustomizeColorFragment().apply { setViewModel(customizeViewModel) }
            1 -> CustomizeFontFragment().apply { setViewModel(customizeViewModel) }
            else -> CustomizeLayoutFragment().apply { setViewModel(customizeViewModel) }
        }
    }

    fun setViewModel(customizeViewModel: CustomizeViewModel){
        this.customizeViewModel = customizeViewModel
    }

    override fun getItemCount(): Int {
        return CARD_ITEM_SIZE
    }

    companion object {
        private const val CARD_ITEM_SIZE = 3
    }
}