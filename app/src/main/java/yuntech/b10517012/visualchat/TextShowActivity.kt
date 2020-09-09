package yuntech.b10517012.visualchat

import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding

class TextShowActivity : AppCompatActivity() {

    private lateinit var background: ConstraintLayout
    private lateinit var tvTextShow: TextView
    private lateinit var btnClose: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_text_show)

        background = findViewById(R.id.constraintLayout_text_show)
        tvTextShow = findViewById(R.id.tv_text_show)
        btnClose = findViewById(R.id.btn_close_show)

        val bundle = intent.getBundleExtra("format")
        val height = resources.displayMetrics.heightPixels
        val width = resources.displayMetrics.widthPixels
        tvTextShow.apply {
            text = bundle.getString("text")
            setTextSize(TypedValue.COMPLEX_UNIT_SP, bundle.getFloat("size")* width/height)
            setTextColor(bundle.getInt("color"))
        }
        background.setBackgroundColor(bundle.getInt("bg"))

        if(bundle.getBoolean("auto") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            tvTextShow.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        }
        if(bundle.getBoolean("bold")){
            tvTextShow.setTypeface(null, Typeface.BOLD)
        }

        // Close TextShowActivity
        btnClose.setOnClickListener {
            finish()
        }
    }
}
