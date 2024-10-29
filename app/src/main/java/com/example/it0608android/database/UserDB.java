package com.example.it0608android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UserDB extends SQLiteOpenHelper {
    // tao bang database
    // dinh nghia bang database bang code java
    public static final String DB_NAME = "user_db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "users";
    //dinh nghia cac cot trong bang database
    public static final String ID_COL = "id"; // khoa chinh (primary key)
    public static final String USERNAME_COL = "username";
    public static final String PASSWORD_COL = "password";
    public static final String EMAIL_COL = "email";
    public static final String PHONE_COL = "phone";
    public static final String ADDRESS_COL = "address";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String DELETED_AT = "deleted_at";


    public UserDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // tao bang du lieu - create table
        String query = "CREATE TABLE " + TABLE_NAME + " ( "
                       + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                       + USERNAME_COL + " VARCHAR(60) NOT NULL, "
                       + PASSWORD_COL + " VARCHAR(200) NOT NULL, "
                       + EMAIL_COL + " VARCHAR(60) NOT NULL, "
                       + PHONE_COL + " VARCHAR(30) NOT NULL, "
                       + ADDRESS_COL + " VARCHAR(200), "
                       + CREATED_AT + " DATETIME, "
                       + UPDATED_AT + " DATETIME, "
                       + DELETED_AT + " DATETIME) ";
        db.execSQL(query); // tao bang
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME ); // xoa bang neu co loi
        onCreate(db); // tao lai bang
    }
    // viet lenh insert du lieu cua tai khoan nguoi dung
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long addNewUser(
            String username,
            String password,
            String email,
            String phone,
            String address) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        ZonedDateTime now = ZonedDateTime.now();
        String dateNow = dtf.format(now);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME_COL, username);
        values.put(PASSWORD_COL, password);
        values.put(EMAIL_COL, email);
        values.put(PHONE_COL, phone);
        values.put(ADDRESS_COL, address);
        values.put(CREATED_AT, dateNow);
        return db.insert(TABLE_NAME, null, values);
    }


}
