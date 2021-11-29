package com.websarva.wings.android.todoroomsample

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAOインターフェース
 *
 * Room Magicはこのファイルにあり、メソッド呼び出しをSQLクエリにマップします
 * Dateなどの複雑なデータ型を使用している場合は、型コンバーターも提供する必要があります
 */
@Dao
interface TodoDao {

    /**
     * すべてのTodoをアルファベット順に並べる
     *
     * フローは常に最新バージョンのデータを保持/キャッシュします
     * データが変更されたときにオブザーバーに通知します
     */
    @Query("SELECT * FROM todo_table ORDER BY task_name ASC")
    fun getAlphabetizedWords(): Flow<List<Todo>>

    /**
     * Todoを挿入する
     *
     * @param todo Todo
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    /**
     * すべての単語を削除する
     */
    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()
}
