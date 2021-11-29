package com.websarva.wings.android.todoroomsample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Todoルームデータベース
 *
 * Todoクラスのテーブル（エンティティ）を持つルームデータベースになるようにクラスに注釈を付けます
 */
@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoRoomDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        // シングルトンは、データベースが同時に開く複数のインスタンスを防ぎます
        @Volatile
        private var INSTANCE: TodoRoomDatabase? = null

        /**
         * データベースを取得する
         *
         * @param context コンテキスト
         */
        fun getDatabase(context: Context): TodoRoomDatabase {
            // INSTANCEがnullでない場合はnullを返し、nullの場合はデータベースを作成します
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoRoomDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
