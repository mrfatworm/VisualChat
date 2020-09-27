package yuntech.b10517012.visualchat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import yuntech.b10517012.visualchat.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}