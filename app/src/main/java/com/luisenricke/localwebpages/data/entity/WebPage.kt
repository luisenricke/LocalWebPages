package com.luisenricke.localwebpages.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Suppress("SpellCheckingInspection")
@Entity(tableName = WebPage.SCHEMA.TABLE)
data class WebPage(
    @ColumnInfo(name = SCHEMA.TITLE)
    val title: String?,
    @ColumnInfo(name = SCHEMA.BODY)
    val body: String?,
    @ColumnInfo(name = SCHEMA.DESCRIPTION)
    val description: String?,
    @ColumnInfo(name = SCHEMA.ACTIVE)
    val active: Int?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SCHEMA.ID)
    val id: Long = 0
) {
    object SCHEMA {
        const val TABLE = "WebPage"
        const val ID = "id"
        const val TITLE = "title"
        const val BODY = "body"
        const val DESCRIPTION = "description"
        const val ACTIVE = "active"
    }
}