package yuntech.b10517012.visualchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var tvPreview:TextView
    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPager2Adapter
    private lateinit var customizeViewModel: CustomizeViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customizeViewModel = ViewModelProviders.of(this).get(CustomizeViewModel::class.java)
        setupView()
        customizeViewModel.currentColor.observe(this, Observer {
            tvPreview.setTextColor(it)
        })
    }

    private fun setupView(){
        tvPreview = findViewById(R.id.tv_main_preview)
        tabLayout = findViewById(R.id.tab_main_setting)
        viewPager = findViewById(R.id.vp_main_setting)
        adapter = ViewPager2Adapter(this)
        adapter.setViewModel(customizeViewModel)

        //val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        viewPager.setAdapter(adapter)
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            tab.text = (when(position){
                0 -> "色彩配置"
                1 -> "字型大小"
                else -> "方向設置"
            })
        }.attach()



    }
}
