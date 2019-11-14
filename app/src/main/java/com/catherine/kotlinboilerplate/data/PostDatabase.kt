package com.catherine.kotlinboilerplate.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [Post::class],
    version = PostDatabase.DB_VERSION,
    exportSchema = false
)
@TypeConverters(PostConverters::class)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "post_database"
        @Volatile
        private var sInstance: PostDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PostDatabase? {
            if (sInstance == null) {
                synchronized(PostDatabase::class.java) {
                    if (sInstance == null) {
                        // persist data in local storage
//                        sInstance = Room.databaseBuilder(
//                            context.applicationContext,
//                            PostDatabase::class.java, DB_NAME
//                        )
//                            // Wipes and rebuilds instead of migrating
//                            // if no Migration object.
//                            // Migration is not part of this practical.
//                            .fallbackToDestructiveMigration()
//                            .build()

                        // Creates a RoomDatabase.Builder for an in memory database. Information stored in an in memory database disappears when the process is killed.
                        sInstance = Room.inMemoryDatabaseBuilder(
                            context.applicationContext,
                            PostDatabase::class.java
                        ).build()
                    }

                }


            }
            return sInstance
        }
    }
}