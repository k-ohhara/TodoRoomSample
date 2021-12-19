package com.websarva.wings.android.todoroomsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * ListAdapter<A: Object, B: RecyclerView.ViewHolder>(C: DiffUtils.ItemCallback<A>) を継承します
 * 今回はTodoを表示するので、Todoを表示するための View を保持した ViewHolder とTodoの差分を比較するための ItemCallback をセットします
 *
 * - A:Object には表示するデータを保持するクラスをセットする
 * - B:RecyclerView.ViewHolder には表示する View を保持する ViewHolder をセットする
 * - C:DiffUtils.ItemCallback<A> には A:Object の差分確認方法を実装した ItemCallback をセットする
 */
class TodoListAdapter : ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(TODO_COMPARATOR) {

    // ここで View を生成し、生成した View は ViewHolder に格納して、戻り値として返す
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.create(parent)
    }

    // その位置のTodoを取得し、ViewHolder を通じて View にTodo情報をセットする
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.taskName)
    }

    /**
     * 生成したViewを保持します
     * bindでTodoをViewに反映させます
     *
     * @param itemView 表示するアイテムのビュー
     */
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val todoItemView: TextView = itemView.findViewById(R.id.todo_title)

        fun bind(text: String?) {
            todoItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): TodoViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return TodoViewHolder(view)
            }
        }
    }

    companion object {
        /**
         * Todoの差分を確認します
         */
        private val TODO_COMPARATOR = object : DiffUtil.ItemCallback<Todo>() {
            // 渡されたデータが同じ値であるか確認をする
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem === newItem
            }

            // 渡されたデータが同じ項目であるか確認する
            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.taskName == newItem.taskName
            }
        }
    }
}
