package com.example.kabar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_menu.*

class AddEditMenu : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.example.kabar.EXTRA_ID"
        const val EXTRA_JUDUL = "com.example.kabar.EXTRA_JUDUL"
        const val EXTRA_HARGA = "com.example.kabar.EXTRA_HARGA"
        const val EXTRA_DESKRIPSI = "com.example.kabar.EXTRA_DESKRIPSI"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_menu)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_launcher_background)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Catatan"
            masakan.setText(intent.getStringExtra(EXTRA_JUDUL))
            harga.setText(intent.getStringExtra(EXTRA_HARGA))
            deskripsi.setText(intent.getStringExtra(EXTRA_DESKRIPSI))

        } else {
            title = "Tambah Catatan"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        if (masakan.text.toString().trim().isBlank() || deskripsi.text.toString().trim().isBlank() || harga.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Catatan kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_JUDUL, masakan.text.toString())
            putExtra(EXTRA_HARGA, harga.text.toString())
            putExtra(EXTRA_DESKRIPSI, deskripsi.text.toString())
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}