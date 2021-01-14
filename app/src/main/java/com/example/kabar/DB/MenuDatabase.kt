package com.example.kabar.DB


import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Menu::class], version = 1)
abstract class MenuDatabase : RoomDatabase() {
    abstract fun MenuDao(): MenuDao

    companion object {
        private var instance: MenuDatabase? = null

        fun getInstance(context: Context): MenuDatabase? {
            if (instance == null) {
                synchronized(MenuDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MenuDatabase::class.java, "Menu_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: MenuDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val MenuDao = db?.MenuDao()

        override fun doInBackground(vararg p0: Unit?) {
            MenuDao?.insert(Menu("Coba 1", "Deskripsi 1","harga"))
        }
    }
}