package com.websarva.wings.android.todoroomsample

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * アプリケーションクラス
 *
 * アプリに含めるデータベースとリポジトリのインスタンスは、それぞれ1つのみにする必要があります。
 * そのための簡単な方法は、これらを Application クラスのメンバーとして作成することです。
 * こうすれば、必要な際にはそのたびに作成する代わりに、アプリから取得するだけで済みます。
 */
class TodoApplication : Application() {
    /**
     * コルーチンスコープ
     * このスコープはプロセスによって破棄されるため、キャンセルする必要はありません
     */
    val applicationScope = CoroutineScope(SupervisorJob())

    /**
     * データベース
     * アプリケーションの起動時ではなく、必要なときにのみ作成されます
     */
    val database by lazy { TodoRoomDatabase.getDatabase(this, applicationScope) }

    /**
     * リポジトリ
     * アプリケーションの起動時ではなく、必要なときにのみ作成されます
     */
    val repository by lazy { TodoRepository(database.todoDao()) }
}
