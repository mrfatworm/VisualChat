package yuntech.b10517012.visualchat.ui

import android.content.*
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.TypedValue
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.adapter.ViewPager2Adapter
import yuntech.b10517012.visualchat.model.CustomizeViewModel
import yuntech.b10517012.visualchat.model.WordModel
import yuntech.b10517012.visualchat.sqlite.MyWordDAO


class MainActivity : AppCompatActivity() {

    private lateinit var tvPreview:TextView
    private lateinit var imgBG: ImageView
    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPager2Adapter
    private lateinit var customizeViewModel: CustomizeViewModel
    private lateinit var edtInput: EditText
    private lateinit var btnShow: ImageButton
    private lateinit var btnSetting: ImageButton
    private lateinit var btnClear: ImageButton
    private lateinit var btnPaste: ImageButton
    private var tvSize: Float = 48F
    private var isAuto: Boolean = false
    private var isBold: Boolean = false
    private var isFlash: Boolean = false
    private var isMarquee: Boolean = false
    lateinit var pref: SharedPreferences
    lateinit var clipboard: ClipboardManager
    lateinit var clipData: ClipData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        customizeViewModel = ViewModelProviders.of(this).get(CustomizeViewModel::class.java)
        initView()

        /** Text color listener */
        customizeViewModel.currentColor.observe(this, Observer { tvPreview.setTextColor(it)})

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

        /** My word listener */
        customizeViewModel.currentWord.observe(this, Observer { edtInput.setText(it) })

        customizeViewModel.currentFlash.observe(this, Observer {
            if (it){
                val anim: Animation = AlphaAnimation(0.0f, 1.0f)
                anim.duration = 500 //You can manage the blinking time with this parameter
                anim.startOffset = 0
                anim.repeatMode = Animation.REVERSE
                anim.repeatCount = Animation.INFINITE
                tvPreview.startAnimation(anim)
            }else{
                tvPreview.clearAnimation()
            }
            isFlash = it
        })

        customizeViewModel.currentMarquee.observe(this, Observer {
            if(it){
                tvPreview.apply {
                    isSelected = true
                    ellipsize = TextUtils.TruncateAt.MARQUEE
                    marqueeRepeatLimit = -1
                    isSingleLine = true
                    setHorizontallyScrolling(true)
                }
            }else {
                tvPreview.apply {
                    isSelected = false
                    ellipsize = TextUtils.TruncateAt.END
                    marqueeRepeatLimit = 0
                    isSingleLine = false
                    setHorizontallyScrolling(false)
                }
            }
            isMarquee = it
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
                pref.edit().putString("Text", p0.toString()).apply()
            }

        })

        btnClear.setOnClickListener {
            edtInput.text.clear()
            Toast.makeText(this, getString(R.string.clear), Toast.LENGTH_SHORT).show()
        }

        btnPaste.setOnClickListener {
            clipData = clipboard.primaryClip as ClipData
            edtInput.setText(edtInput.text.toString() + clipData.getItemAt(0).text)
            edtInput.setSelection(edtInput.text.length)
            Toast.makeText(this, getString(R.string.paste), Toast.LENGTH_SHORT).show()
        }

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
                putBoolean("flash", isFlash)
                putBoolean("marquee", isMarquee)
            }
            intent.putExtra("format", bundle)
            startActivity(intent)
        }

        /** GO to setting page listener */
        btnSetting.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        if (pref.getString("Text", "")!= ""){
            edtInput.setText(pref.getString("Text",""))
        }

    }

    private fun initView(){
        tvPreview = findViewById(R.id.tv_main_preview)
        imgBG = findViewById(R.id.img_main_preview_bg)
        tabLayout = findViewById(R.id.tab_main_setting)
        viewPager = findViewById(R.id.vp_main_setting)
        edtInput = findViewById(R.id.edt_main_input)
        btnShow = findViewById(R.id.btn_main_show)
        btnSetting = findViewById(R.id.btn_main_setting)
        btnClear = findViewById(R.id.btn_clear)
        btnPaste = findViewById(R.id.btn_paste)

        pref = getSharedPreferences("favor", Context.MODE_PRIVATE)
        if(pref.getBoolean("first", true)){
            val myWordDAO = MyWordDAO(this)
            val sample = arrayOf<String>(
                getString(R.string.sample_01),
                getString(R.string.sample_02),
                getString(R.string.sample_03),
                getString(R.string.sample_04),
                getString(R.string.sample_05),
                getString(R.string.sample_06),
                getString(R.string.sample_07),
                getString(R.string.sample_08))
            for(i in sample.indices){
                myWordDAO.insert(WordModel(0, sample[i],i))
            }
            pref.edit().putBoolean("first", false).apply()
        }
        clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val height = resources.displayMetrics.heightPixels
        val width = resources.displayMetrics.widthPixels
        imgBG.layoutParams.height = width* width/height

        adapter = ViewPager2Adapter(this)
        adapter.setViewModel(customizeViewModel)
        //val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            tab.text = (when(position){
                0 -> getString(R.string.tab_customize_style)
                1 -> getString(R.string.tab_my_word)
                else -> getString(R.string.tab_advance_function)
            })
        }.attach()
    }
}
