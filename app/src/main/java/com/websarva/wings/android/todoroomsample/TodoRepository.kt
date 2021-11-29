package com.websarva.wings.android.todoroomsample

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

/**
 * Todoリポジトリクラス
 *
 * @param todoDao DAO
 */
class TodoRepository(private val todoDao: TodoDao) {

    /**
     * 全Todoリスト
     *
     * Roomは、すべてのクエリを別のスレッドで実行します。
     * Observed Flowは、データが変更されたときにオブザーバーに通知します
     */
    val allTodo: Flow<List<Todo>> = todoDao.getAlphabetizedWords()

    /**
     * メインスレッド以外でTodoを挿入する処理を呼び出す
     *
     * デフォルトでは、Roomはメインスレッドからサスペンドクエリを実行するため、
     * メインスレッドから長時間実行されるデータベース作業を行わないようにするために、
     * 他に何も実装する必要はありません。
     *
     * @param todo Todo
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }
}
