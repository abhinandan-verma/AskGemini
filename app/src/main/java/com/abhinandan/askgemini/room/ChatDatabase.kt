package com.abhinandan.askgemini.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Chat::class],
    version = 3,
    exportSchema = false
)
abstract class ChatDatabase: RoomDatabase() {

    abstract fun chatDao(): ChatDao

    companion object {
        /*The value of a volatile variable will never be cached,
        and all writes and reads will be done to and from the main memory.
       This helps make sure the value of INSTANCE is always up-to-date
       and the same for all execution threads.
       It means that changes made by one thread to INSTANCE
       are visible to all other threads immediately.*/
        @Volatile
        private var INSTANCE: ChatDatabase? = null

        fun getDatabase(context: Context): ChatDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "chat_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}