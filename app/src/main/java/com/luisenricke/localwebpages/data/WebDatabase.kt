package com.luisenricke.localwebpages.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.luisenricke.localwebpages.Constants.DATABASE_FOLDER
import com.luisenricke.localwebpages.data.convert.DateConverter
import com.luisenricke.localwebpages.data.dao.WebPageDAO
import com.luisenricke.localwebpages.data.entity.WebPage
import timber.log.Timber

@Suppress("SpellCheckingInspection")
@Database(
    entities = [WebPage::class],
    version = 1, exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class WebDatabase : RoomDatabase() {

    abstract fun webPage(): WebPageDAO

    companion object {
        private var NAME = "${WebDatabase::class.java.simpleName}.db"

        @Volatile
        private var INSTANCE: WebDatabase? = null

        fun getInstance(context: Context): WebDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context)
                    .also { INSTANCE = it }
            }
        }

        private fun build(context: Context): WebDatabase {
            return Room.databaseBuilder(context, WebDatabase::class.java, NAME)
                .createFromAsset("$DATABASE_FOLDER/WebDatabaseExternal.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Timber.v("Creating database")
                        ioThread {
                            Timber.i("Start migration")

                            db.execSQL(MIGRATE_WEB_PAGE)
                            db.execSQL("DROP TABLE WebPage_")

                            Timber.i("Finish migration")
                        }
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Timber.v("Open database")
                    }
                })
                .allowMainThreadQueries()
                .build()
        }

        val MIGRATE_WEB_PAGE =
            """
                INSERT INTO ${WebPage.SCHEMA.TABLE} ( 
                    ${WebPage.SCHEMA.ID}, 
                    ${WebPage.SCHEMA.TITLE}, 
                    ${WebPage.SCHEMA.BODY}, 
                    ${WebPage.SCHEMA.DESCRIPTION}, 
                    ${WebPage.SCHEMA.ACTIVE}) 
                SELECT 
                    id_, 
                    title_, 
                    body_, 
                    description_, 
                    active_ 
                FROM WebPage_
            """.trimIndent()
    }
}