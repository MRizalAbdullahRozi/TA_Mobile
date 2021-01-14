package com.example.kabar.DB

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class MenuRepository (application: Application) {
    private var MenuDao: MenuDao
    private var allNotes: LiveData<List<Menu>>

    init {
        val database: MenuDatabase = MenuDatabase.getInstance(
            application.applicationContext
        )!!
        MenuDao = database.MenuDao()
        allNotes = MenuDao.getAllNotes()
    }

    fun insert(Menu: Menu) {
        val insertNoteAsyncTask = InsertNoteAsyncTask(MenuDao).execute(Menu)
    }

    fun update(Menu: Menu) {
        val updateNoteAsyncTask = UpdateNoteAsyncTask(MenuDao).execute(Menu)
    }

    fun delete(Menu: Menu) {
        val deleteNoteAsyncTask = DeleteNoteAsyncTask(MenuDao).execute(Menu)
    }

    fun deleteAllNotes() {
        val deleteAllNotesAsyncTask = DeleteAllNotesAsyncTask(
            MenuDao
        ).execute()
    }

    fun getAllNotes(): LiveData<List<Menu>> {
        return allNotes
    }

    companion object {
        private class InsertNoteAsyncTask(MenuDao: MenuDao) : AsyncTask<Menu, Unit, Unit>() {
            val MenuDao = MenuDao

            override fun doInBackground(vararg p0: Menu?) {
                MenuDao.insert(p0[0]!!)
            }
        }

        private class UpdateNoteAsyncTask(MenuDao: MenuDao) : AsyncTask<Menu, Unit, Unit>() {
            val MenuDao = MenuDao

            override fun doInBackground(vararg p0: Menu?) {
                MenuDao.update(p0[0]!!)
            }
        }

        private class DeleteNoteAsyncTask(MenuDao: MenuDao) : AsyncTask<Menu, Unit, Unit>() {
            val MenuDao = MenuDao

            override fun doInBackground(vararg p0: Menu?) {
                MenuDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllNotesAsyncTask(MenuDao: MenuDao) : AsyncTask<Unit, Unit, Unit>() {
            val MenuDao = MenuDao

            override fun doInBackground(vararg p0: Unit?) {
                MenuDao.deleteAllNotes()
            }
        }
    }
}