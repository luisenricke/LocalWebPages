package com.luisenricke.localwebpages.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Suppress("unused")
interface Base<X> {
    /**
     * Insert a row in the table.
     *
     * @param row: the object to be inserted.
     * @return id of the row inserted.
     */
    @Insert
    fun insert(row: X): Long

    /**
     * Insert a list of objects with vararg to the table.
     *
     * @param rows: the objects to be inserted.
     * @return the list of id's of the rows inserted.
     */
    @Insert
    fun inserts(vararg rows: X): List<Long>

    /**
     * Insert a list of objects to the table.
     *
     * @param rows: the objects to be inserted.
     * @return the list of id's of the rows inserted.
     */
    @Insert
    fun inserts(rows: List<X>): List<Long>

    /**
     * Count rows from the table.
     *
     * ``@Query("SELECT COUNT(*) FROM ${SCHEMA.TABLE}")``
     *
     * @return the total number of rows.
     */
    fun count(): Long

    /**
     * Get a list of objects existing in the table.
     *
     * ``@Query("SELECT * FROM ${SCHEMA.TABLE}")``
     *
     * @return list of objects requested.
     */
    fun get(): List<X>

    /**
     * Drop all rows existing in the table.
     *
     * ``@Query("DELETE FROM ${SCHEMA.TABLE}")``
     */
    fun drop()

    interface UpdateDAO<Y> {
        /**
         * Update a row with an existing ID in the table and change the data.
         *
         * @param row: the object to be updated.
         * @return the total number of rows changed.
         */
        @Update
        fun update(row: Y): Int

        /**
         * Update a list of objects with vararg which contains existing ID in the table and change the data.
         *
         * @param rows: the objects to be updated.
         * @return the total number of rows changed.
         */
        @Update
        fun updates(vararg rows: Y): Int

        /**
         * Update a list of rows with an existing ID in the table and change the data.
         *
         * @param rows: the objects to be updated.
         * @return the total number of rows changed.
         */
        @Update
        fun updates(rows: List<Y>): Int
    }

    interface DeleteDAO<Y> {
        /**
         * Delete a row from the table.
         *
         * @param row: the object to be deleted.
         * @return the total number of rows dropped.
         */
        @Delete
        fun delete(row: Y): Int

        /**
         * Delete a list of rows with vararg from the table.
         *
         * @param rows: the objects to be deleted.
         * @return the total number of rows dropped.
         */
        @Delete
        fun deletes(vararg rows: Y): Int

        /**
         * Delete a list of rows from the table.
         *
         * @param rows: the objects to be deleted.
         * @return the total number of rows dropped.
         */
        @Delete
        fun deletes(rows: List<Y>): Int
    }

    interface PrimaryKeyDAO<Y> {

        /**
         * Get a object existing in the table by ID.
         *
         * ``@Query("SELECT * FROM ${SCHEMA.TABLE} WHERE id = :id")``
         *
         * @param id: the id of the searched object.
         * @return the object requested.
         */
        fun get(id: Long): Y

        /**
         * Get a list of object existing in the table by ID's.
         *
         *  ``@Query("SELECT * FROM ${SCHEMA.TABLE} WHERE id IN(:ids)")``
         *
         * @param ids: the list id's of the searched objects.
         * @return the list objects requested.
         *
         */
        fun get(ids: LongArray): List<Y>

        /**
         * Delete a row existing in the table by ID.
         *
         * ``@Query("DELETE FROM ${SCHEMA.TABLE} WHERE id = :id")``
         *
         * @param id: the id of the searched object.
         * @return the total number of rows dropped.
         */
        fun delete(id: Long): Int

        /**
         * Delete a list of object existing in the table by ID's.
         *
         *  ``@Query("DELETE FROM ${SCHEMA.TABLE} WHERE id IN(:ids)")``
         *
         * @param ids: the list id's of the searched objects.
         * @return the total number of rows dropped.
         *
         */
        fun deletes(ids: LongArray): Int
    }

    @Deprecated("This interfaces its just a reference query.")
    interface ForeignKeyDAO<Y, Z> {
        /**
         * Count rows from the table Y in Z.
         *
         *  ``@Query(
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; """
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; SELECT COUNT(*) FROM  ${SCHEMA.TABLE}  AS CHILD
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; INNER JOIN  ${Z.SCHEMA.TABLE}  AS PARENT
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ON CHILD.parent_id = PARENT.id
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; """
         *
         *  )``
         *
         * @return the total number of rows.
         */
        fun countJoinByParent(): Long

        /**
         * Count rows from the table by ID of the reference.
         *
         * ``@Query("SELECT COUNT(*) FROM ${SCHEMA.TABLE} WHERE parent_id = :fk")``
         *
         * @param fk: the id of the reference table.
         * @return the total number of rows.
         */
        fun countByParent(fk: Int): Long

        /**
         * Count rows from the table Y in Z filtering with foreign key.
         *
         *  ``@Query(
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; """
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; SELECT COUNT(*) FROM  ${SCHEMA.TABLE}  AS CHILD
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; INNER JOIN  ${Z.SCHEMA.TABLE}  AS PARENT
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ON CHILD.parent_id = PARENT.id
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; WHERE parent_id = :fk
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; """
         *
         *  )``
         *
         * @param fk: the id of the reference table.
         * @return the total number of rows.
         */
        fun countJoinByParent(fk: Int): Long

        /**
         * Get a list of objects existing in the table Y in Z.
         *
         *  ``@Query(
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; """
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; SELECT CHILD.* FROM  ${SCHEMA.TABLE}  AS CHILD
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; INNER JOIN  ${Z.SCHEMA.TABLE}  AS PARENT
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ON CHILD.parent_id = PARENT.id
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; """
         *
         *  )``
         *
         * @return the list of objects requested.
         */
        fun getJoinByParent(): List<Y>

        /**
         * Get a list of objects existing in the table by ID of the reference.
         *
         * ``@Query("SELECT * FROM ${SCHEMA.TABLE} WHERE parent_id = :fk")``
         *
         * @param fk: the id of the reference table.
         * @return the list of objects requested.
         */
        fun getByParent(fk: Long): List<Y>

        /**
         * Count rows from the table Y in Z filtering with foreign key
         *
         *  ``@Query(
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; """
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; SELECT CHILD.* FROM  ${SCHEMA.TABLE}  AS CHILD
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; INNER JOIN  ${Z.SCHEMA.TABLE}  AS PARENT
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ON CHILD.parent_id = PARENT.id
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; WHERE parent_id = :fk
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; """
         *
         *  )``
         *
         * @param fk: the id of the reference table.
         * @return the list of objects requested.
         */
        fun getJoinByParent(fk: Long): List<Y>

        /**
         * Drop all rows existing in the table by ID of the reference.
         *
         * ``@Query("DELETE FROM " + SCHEMA.TABLE + " WHERE parent_id = :fk")``
         *
         * @param fk: the id of the reference table.
         */
        fun dropByParent(fk: Long)
    }

    @Deprecated("This interfaces its just a reference query.")
    interface InnerJoinDAO<A, B> {
        /**
         * Get a list of objects in the left table existing in the right table.
         *
         *  ``@Query(
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; """
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; SELECT * FROM  TABLEA
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; INNER JOIN ${SCHEMA.TABLE}
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ON TABLEA.id = ${SCHEMA.TABLE}.TABLEA_id
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; WHERE ${SCHEMA.TABLE}.TABLEB_id = :idLeft
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; """
         *
         *  )``
         *
         * @param idRight: the id of the right table.
         * @return the list of objects matches.
         */
        fun getJoinLeft(idRight: Long): List<A>

        /**
         * Get a list of objects in the right table existing in the left table.
         *
         *  ``@Query(
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; """
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; SELECT * FROM  TABLEB
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; INNER JOIN ${SCHEMA.TABLE}
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ON TABLEB.id = ${SCHEMA.TABLE}.TABLEB_id
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; WHERE ${SCHEMA.TABLE}.TABLEA_id = :idLeft
         *
         *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; """
         *
         *  )``
         *
         * @param idLeft: the id of the right table.
         * @return the list of objects matches.
         */
        fun getJoinRight(idLeft: Long): List<B>
    }
}