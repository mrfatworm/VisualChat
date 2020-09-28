package yuntech.b10517012.visualchat.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import yuntech.b10517012.visualchat.model.WordModel
import java.util.*

class MyWordDAO// 建構子，一般的應用都不需要修改
    (context: Context?) {

    companion object{
        // 表格名稱
        const val TABLE_NAME = "users"

        // 編號表格欄位名稱，固定不變
        const val KEY_ID = "_id"

        // 其它表格欄位名稱
        const val SENTENCE_COLUMN = "sentence"
        const val VISIBLE_COLUMN = "visible"
        const val ORDER_COLUMN = "morder"


        // 使用上面宣告的變數建立表格的SQL指令
        const val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SENTENCE_COLUMN + " TEXT NOT NULL, " +
                VISIBLE_COLUMN + " INTEGER, " +
                ORDER_COLUMN + " INTEGER)"
    }

    // 資料庫物件
    private var db: SQLiteDatabase? = null

    init {
        db = SQLiteHelper.getDatabase(context)
    }

    // 關閉資料庫，一般的應用都不需要修改
    fun close() {
        db!!.close()
    }

    // 新增參數指定的物件
    fun insert(item: WordModel): WordModel? {
        // 建立準備新增資料的ContentValues物件
        val cv = ContentValues()

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(SENTENCE_COLUMN, item.sentence)
        cv.put(VISIBLE_COLUMN, item.visible)
        cv.put(ORDER_COLUMN, item.order)

        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        val id = db!!.insert(TABLE_NAME, null, cv)

        // 設定編號
        item.id = id
        Log.v("SQLMon", "ADD = $id")
        // 回傳結果
        return item
    }

    // 修改參數指定的物件
    fun update(item: WordModel): Boolean {
        // 建立準備修改資料的ContentValues物件
        val cv = ContentValues()

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(SENTENCE_COLUMN, item.sentence)
        cv.put(VISIBLE_COLUMN, item.visible)
        if (item.order != 0){
            cv.put(ORDER_COLUMN, item.order)
        }


        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        val where = KEY_ID + "=" + item.id

        // 執行修改資料並回傳修改的資料數量是否成功
        return db!!.update(TABLE_NAME, cv, where, null) > 0
    }

    // 刪除參數指定編號的資料
    fun delete(id: Long): Boolean {
        // 設定條件為編號，格式為「欄位名稱=資料」
        val where = "$KEY_ID=$id"
        // 刪除指定編號資料並回傳刪除是否成功
        return db!!.delete(TABLE_NAME, where, null) > 0
    }

    // 讀取所有記事資料
    fun getAll(): MutableList<WordModel>? {
        val result: MutableList<WordModel> = ArrayList()
        val cursor = db!!.query(
            TABLE_NAME,
            null,
            null,
            null,
            ORDER_COLUMN,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            result.add(getRecord(cursor))
        }
        cursor.close()
        return result
    }


    fun getLargestOrder(): Int{
            var result = 0
            val cursor =
                db!!.rawQuery("SELECT MAX($ORDER_COLUMN) FROM $TABLE_NAME", null)
            if (cursor.moveToNext()) {
                result = cursor.getInt(0)
            }
            return result
    }

    // 取得指定編號的資料物件
    operator fun get(id: Long): WordModel? {
        // 準備回傳結果用的物件
        var item: WordModel? = null
        // 使用編號為查詢條件
        val where = "$KEY_ID=$id"
        // 執行查詢
        val result = db!!.query(
            TABLE_NAME, null, where, null, null, null, null, null
        )

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(result)
        }

        // 關閉Cursor物件
        result.close()
        // 回傳結果
        return item
    }

    // 把Cursor目前的資料包裝為物件
    fun getRecord(cursor: Cursor): WordModel {
        // 準備回傳結果用的物件
        val result = WordModel()
        result.id = cursor.getLong(0)
        result.sentence = cursor.getString(1)
        result.visible =  cursor.getInt(2)
        result.order = cursor.getInt(3)

        // 回傳結果
        return result
    }

    // 取得資料數量
    val count: Int
        get() {
            var result = 0
            val cursor =
                db!!.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
            if (cursor.moveToNext()) {
                result = cursor.getInt(0)
            }
            return result
        }
}