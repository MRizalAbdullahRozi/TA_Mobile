package com.example.kabar


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.kabar.DB.Menu
import com.example.kabar.DB.MenuRepository

class MenuViewModel (application: Application) : AndroidViewModel(application)
{
    private var repository: MenuRepository =
        MenuRepository(application)
    private var allNotes: LiveData<List<Menu>> = repository.getAllNotes()

    fun insert(Menu: Menu) {
        repository.insert(Menu)
    }

    fun update(note: Menu) {
        repository.update(note)
    }

    fun delete(note: Menu) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Menu>> {
        return allNotes
    }
}