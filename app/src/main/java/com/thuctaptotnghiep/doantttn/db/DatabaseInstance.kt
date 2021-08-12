package com.thuctaptotnghiep.doantttn.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thuctaptotnghiep.doantttn.db.model.Cart

@Database(entities = [Cart::class], version = 1)
abstract class DatabaseInstance:RoomDatabase() {

    abstract fun getDbDao():DbDao

    companion object{
        private var instance:DatabaseInstance? = null

        fun getDbInstance(context: Context):DatabaseInstance{
            if(instance == null){
                instance = Room.databaseBuilder<DatabaseInstance>(
                    context.applicationContext,
                    DatabaseInstance::class.java,
                    "CartDb"
                ).build()
            }
            return instance!!
        }
    }
}