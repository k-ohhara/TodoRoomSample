package com.websarva.wings.android.todoroomsample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 1列のデータベーステーブルの行であるエンティティを表す基本クラス
 *
 * @ Entity - クラスにエンティティとして注釈を付け、クラス名でない場合はテーブル名を指定する必要があります
 * @ PrimaryKey - 主キーを識別する必要があります
 * @ ColumnInfo - 変数名と異なる場合は、列名を指定する必要があります
 *
 * @param taskId タスクID
 * @param taskName タスク名
 * @param createdAt 登録日
 * @param updatedAt 完了日
 * @param expired 期限
 * @param description メモ
 * @param status ステータス 0:未完了 1:完了
 */
@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey @ColumnInfo(name = "task_id") val taskId: Int,
    @ColumnInfo(name = "task_name") val taskName: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String,
    @ColumnInfo(name = "expired") val expired: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "status") val status: Int
)
