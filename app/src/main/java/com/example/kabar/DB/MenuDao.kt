package com.example.kabar.DB

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MenuDao {
    @Insert
    fun insert(Menu: Menu)

    @Update
    fun update(Menu: Menu)

    @Delete
    fun delete(Menu: Menu)

    @Query("DELETE FROM menu_table")
    fun deleteAllNotes()

    @Query("SELECT * from menu_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Menu>>
}