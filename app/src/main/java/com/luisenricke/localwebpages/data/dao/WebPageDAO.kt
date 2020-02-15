package com.luisenricke.localwebpages.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.luisenricke.localwebpages.data.entity.WebPage
import com.luisenricke.localwebpages.data.entity.WebPage.SCHEMA

@Suppress("unused", "SpellCheckingInspection")
@Dao
abstract class WebPageDAO : Base<WebPage>,
    Base.UpdateDAO<WebPage>,
    Base.DeleteDAO<WebPage>,
    Base.PrimaryKeyDAO<WebPage> {

    @Query("SELECT COUNT(*) FROM ${SCHEMA.TABLE}")
    abstract override fun count(): Long

    @Query("SELECT * FROM ${SCHEMA.TABLE}")
    abstract override fun get(): List<WebPage>

    @Query("DELETE FROM ${SCHEMA.TABLE}")
    abstract override fun drop()

    @Query("SELECT * FROM ${SCHEMA.TABLE} WHERE ${SCHEMA.ID} = :id")
    abstract override fun get(id: Long): WebPage

    @Query("SELECT * FROM ${SCHEMA.TABLE} WHERE ${SCHEMA.ID} IN(:ids)")
    abstract override fun get(ids: LongArray): List<WebPage>

    @Query("DELETE FROM ${SCHEMA.TABLE} WHERE ${SCHEMA.ID} = :id")
    abstract override fun delete(id: Long): Int

    @Query("DELETE FROM ${SCHEMA.TABLE} WHERE ${SCHEMA.ID} IN(:ids)")
    abstract override fun deletes(ids: LongArray): Int

    @Query("SELECT * FROM ${SCHEMA.TABLE} WHERE ${SCHEMA.TITLE} LIKE :descripcion  AND ACTIVE = 1 ORDER BY ${SCHEMA.ID} ASC LIMIT 1")
    abstract fun getByTitle(descripcion: String): WebPage

    @Query("SELECT ${SCHEMA.TITLE} FROM ${SCHEMA.TABLE} WHERE ACTIVE = 1")
    abstract fun getTitles(): List<String>
}