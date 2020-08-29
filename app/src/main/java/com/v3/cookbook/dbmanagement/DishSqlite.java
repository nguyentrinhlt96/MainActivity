package com.v3.cookbook.dbmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;

import com.v3.cookbook.moduls.Categories;
import com.v3.cookbook.moduls.Disher;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DishSqlite extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static String DB_NAME = "foodver2.db";
    public String DB_PATH = "data/data/com.v3.cookbook/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DishSqlite(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (VERSION.SDK_INT >= 16) {
            db.disableWriteAheadLogging();
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void openDatabase() {
        String dbPath = this.mContext.getDatabasePath(DB_NAME).getAbsolutePath();
        SQLiteDatabase sQLiteDatabase = this.mDatabase;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            this.mDatabase = SQLiteDatabase.openDatabase(dbPath, null, 0);
        } else {
            this.mDatabase.close();
        }
    }

    public void closeDatabase() {
        SQLiteDatabase sQLiteDatabase = this.mDatabase;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
    }

    public List<Categories> getListCategories() {
        List<Categories> listCategories = new ArrayList<>();
        openDatabase();
        Cursor cursor = this.mDatabase.rawQuery("SELECT * FROM Categories", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listCategories.add(new Categories(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return listCategories;
    }

    public List<Disher> getListDisher(String idCategory) {
        List<Disher> listDisher = new ArrayList<>();
        openDatabase();
        SQLiteDatabase sQLiteDatabase = this.mDatabase;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM Recipes where idCategory=");
        sb.append(idCategory);
        Cursor cursor = sQLiteDatabase.rawQuery(sb.toString(), null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Disher disher = new Disher(cursor.getInt(0), cursor.getString(1), cursor.getString(2), String.valueOf(cursor.getInt(3)), String.valueOf(cursor.getInt(4)), String.valueOf(cursor.getInt(5)), cursor.getString(6), cursor.getBlob(7), cursor.getInt(8), cursor.getString(9), cursor.getString(11));
            listDisher.add(disher);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return listDisher;
    }


    public List<Disher> getFauvarite(String favarite) {
        List<Disher> listDisher = new ArrayList<>();
        openDatabase();
        SQLiteDatabase sQLiteDatabase = this.mDatabase;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM Recipes where favaurite=");
        sb.append(favarite);
        Cursor cursor = sQLiteDatabase.rawQuery(sb.toString(), null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Disher disher = new Disher(cursor.getInt(0), cursor.getString(1), cursor.getString(2), String.valueOf(cursor.getInt(3)), String.valueOf(cursor.getInt(4)), String.valueOf(cursor.getInt(5)), cursor.getString(6), cursor.getBlob(7), cursor.getInt(8), cursor.getString(9), cursor.getString(11));
            listDisher.add(disher);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return listDisher;
    }
    public boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DB_NAME);
            StringBuilder sb = new StringBuilder();
            sb.append(this.DB_PATH);
            sb.append(DB_NAME);
            OutputStream outputStream = new FileOutputStream(sb.toString());
            byte[] buff = new byte[1024];
            while (true) {
                int read = inputStream.read(buff);
                int length = read;
                if (read > 0) {
                    outputStream.write(buff, 0, length);
                } else {
                    outputStream.flush();
                    outputStream.close();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void checkExistsDatabase(Context context) {
        if (!context.getDatabasePath(DB_NAME).exists()) {
            getReadableDatabase();
            if (!copyDatabase(context)) {
            }
        }
    }

    public long updateFavaurite(int id, String favaurite) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("favaurite", favaurite);
        String[] whereArgs = {Integer.toString(id)};
        openDatabase();
        long returnValue = (long) this.mDatabase.update("Recipes", contentValues,
                "idRecipes=?", whereArgs);
        closeDatabase();
        return returnValue;
    }


    public List<Disher> searchListDisher() {
        List<Disher> listDisher = new ArrayList<>();
        openDatabase();
        Cursor cursor = this.mDatabase.rawQuery("SELECT * FROM Recipes", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Disher disher = new Disher(cursor.getInt(0), cursor.getString(1), cursor.getString(2), String.valueOf(cursor.getInt(3)), String.valueOf(cursor.getInt(4)), String.valueOf(cursor.getInt(5)), cursor.getString(6), cursor.getBlob(7), cursor.getInt(8), cursor.getString(9), cursor.getString(11));
            listDisher.add(disher);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return listDisher;
    }

    public List<Disher> ramdomDish() {
        List<Disher> listDisher = new ArrayList<>();
        openDatabase();
        SQLiteDatabase sQLiteDatabase = this.mDatabase;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM Recipes ORDER BY RANDOM() LIMIT 10");
        Cursor cursor = sQLiteDatabase.rawQuery(sb.toString(), null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Disher disher = new Disher(cursor.getInt(0), cursor.getString(1), cursor.getString(2), String.valueOf(cursor.getInt(3)), String.valueOf(cursor.getInt(4)), String.valueOf(cursor.getInt(5)), cursor.getString(6), cursor.getBlob(7), cursor.getInt(8), cursor.getString(9), cursor.getString(11));
            listDisher.add(disher);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return listDisher;
    }


}
