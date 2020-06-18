package com.app.plan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    private static final String[] columns = {
            DBHelper.ID,
            DBHelper.CONTENT,
            DBHelper.MODE
    };

    public DBManager(Context context) {
        dbHandler = new DBHelper(context);
    }

    public void open(){
        db = dbHandler.getWritableDatabase();
    }

    public void close(){
        dbHandler.close();
    }

    public Note addNote(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.CONTENT, note.getContent());
        long insertId = db.insert(DBHelper.TABLE_NAME, null, contentValues);
        note.setId(insertId);
        return note;
    }

    public List<Note> getAllNotes(){
        Cursor cursor = db.query(DBHelper.TABLE_NAME,columns,null,null,null, null, null);

        List<Note> notes = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.ID)));
                note.setContent(cursor.getString(cursor.getColumnIndex(DBHelper.CONTENT)));
                notes.add(note);
            }
        }
        return notes;
    }

    public void removeNote(Note note) {
        db.delete(DBHelper.TABLE_NAME, DBHelper.ID + "=?",new String[] { String.valueOf(note.getId())});
    }

}
