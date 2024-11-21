package com.example.it0608android.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.it0608android.model.ExpenseModel;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ExpenseDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "expense_db";
    public static final int DB_VERSION = 2;
    public static final String TABLE_NAME = "expenses";
    public static final String ID_COL = "id";
    public static final String NAME_COL = "name";
    public static final String PRICE_COL = "price";
    public static final String DESCRIPTION_COL = "description";
    public static final String CREATED_AT_COL = "created_at";
    public static final String UPDATED_AT_COL = "updated_at";

    public ExpenseDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // tao bang du lieu - create table
        String query = "CREATE TABLE " + TABLE_NAME + " ( "
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " VARCHAR(100) NOT NULL, "
                + PRICE_COL + " INTEGER NOT NULL, "
                + DESCRIPTION_COL + " VARCHAR(200) NOT NULL, "
                + CREATED_AT_COL + " DATETIME, "
                + UPDATED_AT_COL + " DATETIME) ";
        db.execSQL(query); // tao bang
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME ); // xoa bang neu co loi
        onCreate(db); // tao lai bang
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long addNewExpense(String name, int price, String description){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime now = ZonedDateTime.now();
        String dateNow = dtf.format(now);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(PRICE_COL, price);
        values.put(DESCRIPTION_COL, description);
        values.put(CREATED_AT_COL, dateNow);
        long insert = db.insert(TABLE_NAME, null, values);
        db.close();
        return insert;
    }

    @SuppressLint("NewApi")
    public int updateExpense(String name, int price, String description, int id){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime now = ZonedDateTime.now();
        String dateNow = dtf.format(now);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(PRICE_COL, price);
        values.put(DESCRIPTION_COL, description);
        values.put(UPDATED_AT_COL, dateNow);
        String condition = ID_COL + " =? ";
        String[] params = { String.valueOf(id) };
        int result = db.update(TABLE_NAME, values, condition, params);
        db.close();
        return  result;
    }

    @SuppressLint("Range")
    public ArrayList<ExpenseModel> getListExpenses(){
        ArrayList<ExpenseModel> expensesArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorQuery = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursorQuery != null && cursorQuery.getCount() > 0) {
            if (cursorQuery.moveToFirst()) {
                do {
                    expensesArrayList.add(
                        new ExpenseModel(
                            cursorQuery.getInt(cursorQuery.getColumnIndex(ID_COL)),
                            cursorQuery.getString(cursorQuery.getColumnIndex(NAME_COL)),
                            cursorQuery.getInt(cursorQuery.getColumnIndex(PRICE_COL)),
                            cursorQuery.getString(cursorQuery.getColumnIndex(DESCRIPTION_COL)),
                            cursorQuery.getString(cursorQuery.getColumnIndex(CREATED_AT_COL)),
                            cursorQuery.getString(cursorQuery.getColumnIndex(UPDATED_AT_COL))
                        )
                    );
                } while (cursorQuery.moveToNext());
            }
        }

        assert cursorQuery != null;
        cursorQuery.close();
        db.close();
        return expensesArrayList;
    }

    public void deleteExpenseById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = ID_COL + " =? ";
        String[] params = { String.valueOf(id) };
        db.delete(TABLE_NAME, condition, params);
        db.close();
        // delete from expense where id = ?
    }
}
