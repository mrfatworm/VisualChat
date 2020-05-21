package yuntech.b10517012.visualchat

import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.TextViewCompat
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
    private lateinit var edtInput: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customizeViewModel = ViewModelProviders.of(this).get(CustomizeViewModel::class.java)
        setupView()
        customizeViewModel.currentColor.observe(this, Observer {
            tvPreview.setTextColor(it)
        })
        customizeViewModel.currentFont.observe(this, Observer {
            tvPreview.setTextSize(TypedValue.COMPLEX_UNIT_SP, it)
        })
        customizeViewModel.currentAutoFont.observe(this, Observer {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(it){
                    tvPreview.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
                }else{
                    tvPreview.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE)
                }
            }
        })
        customizeViewModel.currentBold.observe(this, Observer {
            if (it){
                tvPreview.setTypeface(null, Typeface.BOLD)
            }else{
                tvPreview.setTypeface(null, Typeface.NORMAL)
            }
        })
        edtInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) { }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tvPreview.text = p0.toString()
            }

        })

    }

    private fun setupView(){
        tvPreview = findViewById(R.id.tv_main_preview)
        tabLayout = findViewById(R.id.tab_main_setting)
        viewPager = findViewById(R.id.vp_main_setting)
        edtInput = findViewById(R.id.edt_main_input)
        adapter = ViewPager2Adapter(this)
        adapter.setViewModel(customizeViewModel)

        //val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            tab.text = (when(position){
                0 -> "色彩配置"
                1 -> "字型大小"
                else -> "方向設置"
            })
        }.attach()



    }
}
