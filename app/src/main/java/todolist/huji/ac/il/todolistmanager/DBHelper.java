package todolist.huji.ac.il.todolistmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "todo_db", null, 1);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table todo (_id integer primary key autoincrement,title string,due string);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertContact(String title, String due)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("due", due);
        db.insert("todo", null, contentValues);
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from todo", null );
        return res;
    }
    public String getLastElement(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from todo", null);
        res.moveToLast();
        return res.getString(res.getColumnIndex("_id"));
    }
    public void deleteById(String IdToDelete){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("todo","_id=?",new String[]{IdToDelete});
    }
}