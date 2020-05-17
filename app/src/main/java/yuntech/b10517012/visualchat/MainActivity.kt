package yuntech.b10517012.visualchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tvPreview:TextView
    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: CustomizeAdapter
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
        adapter = CustomizeAdapter(supportFragmentManager)
        adapter.setViewModel(customizeViewModel)

        //val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }
}
