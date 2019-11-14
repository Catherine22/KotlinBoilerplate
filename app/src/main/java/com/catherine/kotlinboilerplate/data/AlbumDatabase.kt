package com.catherine.kotlinboilerplate.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [Album::class],
    version = AlbumDatabase.DB_VERSION,
    exportSchema = false
)
@TypeConverters(AlbumConverters::class)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "album_database"
        @Volatile
        private var sInstance: AlbumDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AlbumDatabase? {
            if (sInstance == null) {
                synchronized(AlbumDatabase::class.java) {
                    if (sInstance == null) {
                        // persist data in local storage
//                        sInstance = Room.databaseBuilder(
//                            context.applicationContext,
//                            AlbumDatabase::class.java, DB_NAME
//                        )
//                            // Wipes and rebuilds instead of migrating
//                            // if no Migration object.
//                            // Migration is not part of this practical.
//                            .fallbackToDestructiveMigration()
//                            .build()

                        // Creates a RoomDatabase.Builder for an in memory database. Information stored in an in memory database disappears when the process is killed.
                        sInstance = Room.inMemoryDatabaseBuilder(
                            context.applicationContext,
                            AlbumDatabase::class.java
                        ).build()
                    }

                }


            }
            return sInstance
        }
    }
}