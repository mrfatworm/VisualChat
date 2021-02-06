package yuntech.b10517012.visualchat.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import yuntech.b10517012.visualchat.BuildConfig
import yuntech.b10517012.visualchat.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tvVersion = findViewById<TextView>(R.id.tv_version)

        //val versionCodes = BuildConfig.VERSION_CODE
        val versionName = BuildConfig.VERSION_NAME

        tvVersion.text = "V $versionName"
    }
}