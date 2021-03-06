package yuntech.b10517012.visualchat.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about.*
import yuntech.b10517012.visualchat.BuildConfig
import yuntech.b10517012.visualchat.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //val versionCodes = BuildConfig.VERSION_CODE
        val versionName = BuildConfig.VERSION_NAME

        tv_version.text = "V $versionName"
    }
}