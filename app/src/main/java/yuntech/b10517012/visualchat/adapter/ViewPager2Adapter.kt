package yuntech.b10517012.visualchat.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import yuntech.b10517012.visualchat.model.CustomizeViewModel
import yuntech.b10517012.visualchat.ui.AdvanceFunctionFragment
import yuntech.b10517012.visualchat.ui.CustomizeStyleFragment
import yuntech.b10517012.visualchat.ui.MyWordFragment

class ViewPager2Adapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private lateinit var customizeViewModel: CustomizeViewModel;

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CustomizeStyleFragment().apply { setViewModel(customizeViewModel) }
            1 -> MyWordFragment().apply { setViewModel(customizeViewModel) }
            else -> AdvanceFunctionFragment().apply { setViewModel(customizeViewModel) }
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