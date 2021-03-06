package yuntech.b10517012.visualchat.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.adapter.ViewPager2Adapter
import yuntech.b10517012.visualchat.model.CustomizeViewModel
import yuntech.b10517012.visualchat.model.WordModel
import yuntech.b10517012.visualchat.sqlite.MyWordDAO

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPager2Adapter
    private lateinit var customizeViewModel: CustomizeViewModel
    private lateinit var myWordDAO: MyWordDAO
    private var tvSize: Float = 48F
    private var isAuto: Boolean = false
    private var isBold: Boolean = false
    private var isFlash: Boolean = false
    private var isMarquee: Boolean = false
    lateinit var pref: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        customizeViewModel = ViewModelProvider(this).get(CustomizeViewModel::class.java)
        initView()

        /** Text color listener */
        customizeViewModel.currentColor.observe(this, Observer { tv_main_preview.setTextColor(it)})

        /** Text color listener */
        customizeViewModel.bgColor.observe(this, Observer {
            img_main_preview_bg.setBackgroundColor(it)
            this.window.statusBarColor = it
        })

        /** Text size listener */
        customizeViewModel.font.observe(this, Observer {
            tv_main_preview.setTextSize(TypedValue.COMPLEX_UNIT_SP, it)
            tvSize = it
        })

        /** Auto text size listener */
        customizeViewModel.autoFont.observe(this, Observer {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(it){
                    tv_main_preview.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
                }else{
                    tv_main_preview.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE)
                }
            }
            isAuto = it
        })

        /** Bold text listener */
        customizeViewModel.bold.observe(this, Observer {
            if (it){
                tv_main_preview.setTypeface(null, Typeface.BOLD)
            }else{
                tv_main_preview.setTypeface(null, Typeface.NORMAL)
            }
            isBold = it
        })

        /** Select My Word listener */
        customizeViewModel.word.observe(this, Observer { edt_main_input.setText(it) })

        /** Advance function : Table Mode listener */
        customizeViewModel.blink.observe(this, Observer {
            if (it){
                val anim: Animation = AlphaAnimation(0.0f, 1.0f)
                anim.duration = 500 //You can manage the blinking time with this parameter
                anim.startOffset = 0
                anim.repeatMode = Animation.REVERSE
                anim.repeatCount = Animation.INFINITE
                tv_main_preview.startAnimation(anim)
            }else{
                tv_main_preview.clearAnimation()
            }
            isFlash = it
        })
        /** Advance function : Marquee Mode listener */
        customizeViewModel.marquee.observe(this, Observer {
            if(it){
                tv_main_preview.apply {
                    isSelected = true
                    ellipsize = TextUtils.TruncateAt.MARQUEE
                    marqueeRepeatLimit = -1
                    isSingleLine = true
                    setHorizontallyScrolling(true)
                }
            }else {
                tv_main_preview.apply {
                    isSelected = false
                    ellipsize = TextUtils.TruncateAt.END
                    marqueeRepeatLimit = 0
                    isSingleLine = false
                    setHorizontallyScrolling(false)
                }
            }
            isMarquee = it
        })
        /** Advance function : Table Mode listener */
        customizeViewModel.table.observe(this, Observer {
            if (it){
                tv_main_preview.rotation = 180F
            }else{
                tv_main_preview.rotation = 0F
            }
        })

        /** Text input listener */
        edt_main_input.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) { }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tv_main_preview.text = p0.toString()
                if(p0?.length == 0){
                    tv_main_preview.text = getString(R.string.text_preview)
                }
                pref.edit().putString("Text", p0.toString()).apply()
            }

        })
        /** Clear edit text */
        btn_clear.setOnClickListener {
            edt_main_input.text.clear()
            Toast.makeText(this, getString(R.string.clear), Toast.LENGTH_SHORT).show()
        }

        /** Add edit text to Sentences */
        btn_new_word.setOnClickListener {
            val itemView = LayoutInflater.from(this).inflate(R.layout.alert_add_word, null)
            val edtAdd = itemView.findViewById<EditText>(R.id.edt_add_input)
            edtAdd.text = edt_main_input.text
            AlertDialog.Builder(this)
                .setTitle(R.string.new_word)
                .setView(itemView)
                .setPositiveButton(R.string.add){ _, _ ->
                    if (!edtAdd.text.toString().equals("")){
                        myWordDAO.insert(WordModel(0, edtAdd.text.toString(), myWordDAO.getLargestOrder()+1))
                        adapter.notifyDataSetChanged()
                        customizeViewModel.setNewWord(true)
                        customizeViewModel.setNewWord(false)
                    }else{
                        edtAdd.hint = "Type something"
                    }
                }.show()
        }

        /** GO to fullscreen listener */
        btn_main_show.setOnClickListener {
            val intent = Intent(this, TextShowActivity::class.java)
            val bundle = Bundle()
            val bgColor = img_main_preview_bg.background as ColorDrawable

            bundle.apply {
                putString("text", tv_main_preview.text.toString())
                putFloat("size", tvSize)
                putInt("color", tv_main_preview.currentTextColor)
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
        btn_main_setting.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        if (pref.getString("Text", "")!= ""){
            edt_main_input.setText(pref.getString("Text",""))
        }

    }

    private fun initView(){
        myWordDAO = MyWordDAO(this)
        viewPager = findViewById(R.id.vp_main_setting)
        tabLayout = findViewById(R.id.tab_main_setting)

        pref = getSharedPreferences("favor", Context.MODE_PRIVATE)
        if(pref.getBoolean("first", true)){
            val myWordDAO = MyWordDAO(this)
            val sample = arrayOf(
                getString(R.string.sample_01),
                getString(R.string.sample_02),
                getString(R.string.sample_03),
                getString(R.string.sample_04),
                getString(R.string.sample_05),
                getString(R.string.sample_06),
                getString(R.string.sample_07),
                getString(R.string.sample_08))
            for(i in sample.indices){
                myWordDAO.insert(WordModel(0, sample[i], i+1))
            }
            pref.edit().putBoolean("first", false).apply()
        }


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
