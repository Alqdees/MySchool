package com.school.myschool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class databaseSqlite extends SQLiteOpenHelper {
    public static final int VIRION = 1;
    public static final String DB_NAME = "School.db";
    public static final String TB_NAME = "teachers";
    public static final String C_NAME = "name";
    public static final String C_ID = "ID";
    public static final String C_picture = "picture";
    public static final String C_identification = "identification";
    public static final String C_residence = "residence";
    public static final String C_tom= "tom";
    public static final String C_certificate= "certificate";
    public static final String C_document= "document";

    public databaseSqlite(@Nullable Context context) {
        super(context, DB_NAME, null, VIRION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TB_NAME +
                        "(" + C_ID + " Integer PRIMARY KEY AUTOINCREMENT, " + C_NAME + " TEXT   UNIQUE, "
        + C_picture + " BLOB, " + C_identification + " BLOB, " + C_residence + " BLOB, " + C_tom + " BLOB, "
                        + C_certificate + " BLOB, " + C_document + " BLOB" + ")"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(sqLiteDatabase);

    }

    public long insertData(String name ,byte[] pic,byte[] identification,byte[] residence,byte[] tom,byte[] certificate,byte[] document){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(C_NAME,name);
            cv.put(C_picture,pic);
            cv.put(C_identification,identification);
            cv.put(C_residence,residence);
            cv.put(C_tom,tom);
            cv.put(C_certificate,certificate);
            cv.put(C_document,document);
            long result = db.insert(TB_NAME,null,cv);
            db.close();
        return result;
    }
    public ArrayList<Model> getAllRecords( String orderBy){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Model> models = new ArrayList<>();
        String query = "SELECT * FROM " + TB_NAME + " ORDER BY " + orderBy;

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
              Model model = new Model(
                      cursor.getString(0),
                      cursor.getString(1),
                      cursor.getBlob(2),
                      cursor.getBlob(3),
                      cursor.getBlob(4),
                      cursor.getBlob(5),
                      cursor.getBlob(6),
                      cursor.getBlob(7));
              models.add(model);
            }while (cursor.moveToNext());
        }
        db.close();
        return models;
    }
    public void deleteItem(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_NAME,C_ID+ " = ?",new String[]{id});
        db.close();
    }

    public void updateData(String name ,String id ,byte[] pic,byte[] identification,byte[] residence,byte[] tom,byte[] certificate,byte[] document){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_NAME,name);
        cv.put(C_picture,pic);
        cv.put(C_identification,identification);
        cv.put(C_residence,residence);
        cv.put(C_tom,tom);
        cv.put(C_certificate,certificate);
        cv.put(C_document,document);
        db.update(TB_NAME,cv,C_ID + " = ?",new String[]{id});
        db.close();
    }
    public ArrayList<Model> searchRecord(String query){
        ArrayList<Model> recordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TB_NAME + " WHERE " + C_NAME + " LIKE '%" + query + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                Model model = new Model(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getBlob(2),
                        cursor.getBlob(3),
                        cursor.getBlob(4),
                        cursor.getBlob(5),
                        cursor.getBlob(6),
                        cursor.getBlob(7));
                recordList.add(model);
            }while (cursor.moveToNext());
        }
        db.close();
        return recordList;
    }

}
