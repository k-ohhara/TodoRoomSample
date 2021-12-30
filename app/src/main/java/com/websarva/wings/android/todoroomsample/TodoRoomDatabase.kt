package com.websarva.wings.android.todoroomsample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
         * @param scope コルーチンスコープ
         */
        fun getDatabase(context: Context, scope: CoroutineScope): TodoRoomDatabase {
            // INSTANCEがnullでない場合はnullを返し、nullの場合はデータベースを作成します
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoRoomDatabase::class.java,
                    "todo_database"
                )
                    .addCallback(TodoDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        /**
         * アプリが作成されるたびにデータベースのコンテンツをすべて削除して再入力します
         *
         * @param scope コルーチンスコープ
         */
        private class TodoDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * onCreateメソッドをオーバーライドして、データベースにデータを入力します
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // アプリを再起動してもデータを保持したい場合は、次の行をコメントアウトしてください
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.todoDao())
                    }
                }
            }
        }

        /**
         * データベースに新しいコルーチンを入力します
         *
         * @param todoDao DAO
         */
        suspend fun populateDatabase(todoDao: TodoDao) {
            // 毎回クリーンなデータベースでアプリを起動します
            // 作成時にのみ入力する場合は必要ありません
            todoDao.deleteAll()

            var todo = Todo("大原テスト")
            todoDao.insert(todo)
            todo = Todo("OhharaTest")
            todoDao.insert(todo)
        }
    }
}
