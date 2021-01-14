package com.example.kabar


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var menu = findViewById<ImageButton>(R.id.Menu)
        var profile = findViewById<ImageButton>(R.id.profile)
        var kontak = findViewById<ImageView>(R.id.kontak)


        menu.setOnClickListener {
            startActivity(Intent(this@MainActivity, daftar_menu::class.java))
        }

        profile.setOnClickListener {
            startActivity(Intent(this@MainActivity, ProfileAkun::class.java)
            )
        }
        kontak.setOnClickListener {
            startActivity(Intent(this@MainActivity, KontakSaya::class.java)
            )
        }
    }
}