package com.websarva.wings.android.todoroomsample

import androidx.lifecycle.*
import kotlinx.coroutines.launch

/**
 * Todoリポジトリへの参照とすべてのTodoの最新リストを保持するViewModel
 *
 * @param repository Todoリポジトリ
 */
class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    /** Todoリストをキャッシュに保存するためのLiveData */
    val allTodo: LiveData<List<Todo>> = repository.allTodo.asLiveData()

    /**
     * 新しいコルーチンを開始し、リポジトリの insert を呼び出す
     *
     * @param todo Todo
     */
    fun insert(todo: Todo) = viewModelScope.launch {
        repository.insert(todo)
    }
}

/**
 * TodoViewModel作成クラス
 *
 * @param repository Todoリポジトリ
 */
class TodoViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
    /**
     * 指定されたクラスの新しいインスタンスを作成します
     *
     * @param modelClass インスタンスが要求されたクラス
     * @return 新しく作成されたViewModel
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
