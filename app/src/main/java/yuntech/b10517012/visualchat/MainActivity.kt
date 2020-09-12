package yuntech.b10517012.visualchat

import android.R.attr.button
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private lateinit var tvPreview:TextView
    private lateinit var imgBG: ImageView
    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPager2Adapter
    private lateinit var customizeViewModel: CustomizeViewModel
    private lateinit var edtInput: EditText
    private lateinit var btnShow: ImageButton
    private var tvSize: Float = 48F
    private var isAuto: Boolean = false
    private var isBold: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        customizeViewModel = ViewModelProviders.of(this).get(CustomizeViewModel::class.java)
        setupView()

        /** Text color listener */
        customizeViewModel.currentColor.observe(this, Observer {
            tvPreview.setTextColor(it)
        })

        /** Text color listener */
        customizeViewModel.currentBGColor.observe(this, Observer {
            imgBG.setBackgroundColor(it)
            this.window.statusBarColor = it
        })

        /** Text size listener */
        customizeViewModel.currentFont.observe(this, Observer {
            tvPreview.setTextSize(TypedValue.COMPLEX_UNIT_SP, it)
            tvSize = it
        })

        /** Auto text size listener */
        customizeViewModel.currentAutoFont.observe(this, Observer {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(it){
                    tvPreview.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
                }else{
                    tvPreview.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE)
                }
            }
            isAuto = it
        })

        /** Bold text listener */
        customizeViewModel.currentBold.observe(this, Observer {
            if (it){
                tvPreview.setTypeface(null, Typeface.BOLD)
            }else{
                tvPreview.setTypeface(null, Typeface.NORMAL)
            }
            isBold = it
        })

        /** Text input listener */
        edtInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) { }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tvPreview.text = p0.toString()
                if(p0?.length == 0){
                    tvPreview.text = getString(R.string.text_preview)
                }
            }

        })

        /** GO to fullscreen listener */
        btnShow.setOnClickListener {
            var intent = Intent(this, TextShowActivity::class.java)
            var bundle = Bundle()
            val bgColor = imgBG.background as ColorDrawable

            bundle.apply {
                putString("text", tvPreview.text.toString())
                putFloat("size", tvSize)
                putInt("color", tvPreview.currentTextColor)
                putInt("bg", bgColor.color)
                putBoolean("auto", isAuto)
                putBoolean("bold", isBold )
            }
            intent.putExtra("format", bundle)
            startActivity(intent)
        }

    }

    private fun setupView(){
        tvPreview = findViewById(R.id.tv_main_preview)
        imgBG = findViewById(R.id.img_main_preview_bg)
        tabLayout = findViewById(R.id.tab_main_setting)
        viewPager = findViewById(R.id.vp_main_setting)
        edtInput = findViewById(R.id.edt_main_input)
        btnShow = findViewById(R.id.btn_main_show)

        val height = resources.displayMetrics.heightPixels
        val width = resources.displayMetrics.widthPixels
        imgBG.layoutParams.height = width* width/height

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
