package com.example.kabar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kabar.DB.Menu
import kotlinx.android.synthetic.main.daftarmenu.*


class daftar_menu : AppCompatActivity() {

    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }

    private lateinit var noteViewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daftarmenu)

        buttonAddNote.setOnClickListener {
            startActivityForResult(
                    Intent(this, AddEditMenu::class.java),
                    ADD_NOTE_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val adapter = MenuAdapter()
        recycler_view.adapter = adapter

        noteViewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        noteViewModel.getAllNotes().observe(this, Observer<List<Menu>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Catatan dihapus!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : MenuAdapter.OnItemClickListener {
            override fun onItemClick(menu: Menu) {
                val intent = Intent(baseContext, AddEditMenu::class.java)
                intent.putExtra(AddEditMenu.EXTRA_ID, menu.id)
                intent.putExtra(AddEditMenu.EXTRA_JUDUL, menu.nama)
                intent.putExtra(AddEditMenu.EXTRA_HARGA, menu.harga)
                intent.putExtra(AddEditMenu.EXTRA_DESKRIPSI, menu.deskripsi)

                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.delete_allMenu -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "Semua sudah dihapus!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val newNote = Menu(
                    data!!.getStringExtra(AddEditMenu.EXTRA_JUDUL).toString(),
                    data.getStringExtra(AddEditMenu.EXTRA_HARGA).toString(),
                    data.getStringExtra(AddEditMenu.EXTRA_DESKRIPSI).toString()
            )
            noteViewModel.insert(newNote)
            Toast.makeText(this, "Catatan disimpan!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditMenu.EXTRA_ID, 1)

            if (id == -1) {
                Toast.makeText(this, "Pembaharuan gagal!", Toast.LENGTH_SHORT).show()
            }

            val updateNote = Menu(
                    data!!.getStringExtra(AddEditMenu.EXTRA_JUDUL).toString(),
                    data.getStringExtra(AddEditMenu.EXTRA_HARGA).toString(),
                    data.getStringExtra(AddEditMenu.EXTRA_DESKRIPSI).toString()
            )
            updateNote.id = data.getIntExtra(AddEditMenu.EXTRA_ID, -1)
            noteViewModel.update(updateNote)
        } else {
            Toast.makeText(this, "Catatan tidak disimpan!", Toast.LENGTH_SHORT).show()
        }
    }
}
