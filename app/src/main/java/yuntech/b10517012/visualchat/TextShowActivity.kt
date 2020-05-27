package yuntech.b10517012.visualchat

import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class TextShowActivity : AppCompatActivity() {

    private lateinit var background: ConstraintLayout
    private lateinit var tvTextShow: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_show)


        background = findViewById(R.id.constraintLayout_text_show)
        tvTextShow = findViewById(R.id.tv_text_show)

        val bundle = intent.getBundleExtra("format")
        tvTextShow.text = bundle.getString("text")
        tvTextShow.setTextSize(TypedValue.COMPLEX_UNIT_SP, bundle.getFloat("size"))
        Toast.makeText(this, bundle.getFloat("size").toString() + "  " + tvTextShow.textSize.toString(), Toast.LENGTH_SHORT).show()
        tvTextShow.setTextColor(bundle.getInt("color"))
        background.setBackgroundColor(bundle.getInt("bg"))

        if(bundle.getBoolean("auto") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            tvTextShow.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        }
        if(bundle.getBoolean("bold")){
            tvTextShow.setTypeface(null, Typeface.BOLD)
        }
    }
}
