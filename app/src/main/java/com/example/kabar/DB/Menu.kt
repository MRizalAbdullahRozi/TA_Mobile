package com.example.kabar.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_table")
data class Menu(
    var nama: String,
    var harga: String,
    var deskripsi: String
    )
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}