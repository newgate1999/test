package com.example.phamtuananhkiemtra2bai2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HocOnline.db";
    public static final String TABLE_NAME = "CourseTable";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_MAJOR = "major";
    public static final String COLUMN_ACTIVE = "active";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table CourseTable " +
                        "(id integer primary key, name text,date text,major text, active text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS CourseTable");
        onCreate(db);
    }

    public boolean insertKhoaHoc(KhoaHoc khoaHoc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, khoaHoc.getName());
        contentValues.put(COLUMN_DATE, khoaHoc.getDate());
        contentValues.put(COLUMN_MAJOR, khoaHoc.getMajor());
        contentValues.put(COLUMN_ACTIVE, khoaHoc.getActive());

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from CourseTable where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateKhoaHoc(KhoaHoc khoaHoc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, khoaHoc.getName());
        contentValues.put(COLUMN_DATE, khoaHoc.getDate());
        contentValues.put(COLUMN_MAJOR, khoaHoc.getMajor());
        contentValues.put(COLUMN_ACTIVE, khoaHoc.getActive());

        db.update(TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(Integer.parseInt(khoaHoc.getId()))});
        return true;
    }

    public Integer deleteCourse(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<KhoaHoc> getAllKhoaHoc() {
        ArrayList<KhoaHoc> array_list = new ArrayList<KhoaHoc>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from CourseTable", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(
                    new KhoaHoc(
                            res.getString(res.getColumnIndex(COLUMN_ID)),
                            res.getString(res.getColumnIndex(COLUMN_NAME)),
                            res.getString(res.getColumnIndex(COLUMN_DATE)),
                            res.getString(res.getColumnIndex(COLUMN_MAJOR)),
                            res.getString(res.getColumnIndex(COLUMN_ACTIVE)))
            );
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<KhoaHoc> searchKhoaHoc(String search) {
        ArrayList<KhoaHoc> array_list = new ArrayList<KhoaHoc>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;

        res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE name " + " LIKE  ?", new String[]{"%" + search + "%"});

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(
                    new KhoaHoc(
                            res.getString(res.getColumnIndex(COLUMN_ID)),
                            res.getString(res.getColumnIndex(COLUMN_NAME)),
                            res.getString(res.getColumnIndex(COLUMN_DATE)),
                            res.getString(res.getColumnIndex(COLUMN_MAJOR)),
                            res.getString(res.getColumnIndex(COLUMN_ACTIVE)))
            );
            res.moveToNext();
        }
        return array_list;
    }
}