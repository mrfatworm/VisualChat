package yuntech.b10517012.visualchat.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(
    context: Context?, name: String?, factory: CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(MyWordDAO.CREATE_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS " + MyWordDAO.TABLE_NAME)
        onCreate(db)
    }

    companion object {
        var db: SQLiteDatabase? = null
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "word.db"

        // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
        fun getDatabase(context: Context?): SQLiteDatabase? {
            if (db == null || !db!!.isOpen) {
                db = SQLiteHelper(
                    context,
                    DATABASE_NAME,
                    null,
                    DATABASE_VERSION
                ).writableDatabase
            }
            return db
        }
    }
}