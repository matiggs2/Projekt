package com.example.projekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "notes_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "note";

    public DatabaseHelper(Context context) {
        super(context,TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //metoda dodająca rekord do bazy
    public boolean addData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1) {//jeśli dane są błędne program zwraca -1
            return false;
        } else {
            return true;
        }
    }
    //zwraca dane z bazy
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    //zwraca jedynie ID które pasuje do notki
    public Cursor getItemID(String note){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + note + "'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    //aktualizuje notatkę
    public void updateNote(String newNote, int id, String oldNote){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 + " = '" + newNote + "' Where " + COL1 + " = '" + id + "'" + " AND " + COL2 + " = '" + oldNote + "'";
        Log.d(TAG, "updateNote: query: " + query);
        Log.d(TAG, "updateNote: Setting note to " + newNote);
        db.execSQL(query);
    }
    //usunięcie z bazy
    public void deleteNote(int id, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + id + "'" + " AND " + COL2 + " = '" + note + "'";
        Log.d(TAG, "deleteNote: query: " + query);
        Log.d(TAG, "deleteNote: Deleting " + note + " from database.");
        db.execSQL(query);
    }
}
