package com.example.kobej.attendancesystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "attendance.db";
    public static final String STUDENT_TABLE = "student";
    public static final String STUDENT_NAME  = "fullName";
    public static final String STUDENT_ID_NO = "idNumber";
    public static final String STAFF_TABLE = "staff";
    public static final String STAFF_NAME = "fullName";
    public static final String STAFF_ID_NO = "idNumber";
    public static final String DETAILS_TABLE = "loginDetails";
    public static final String DETAILS_USERNAME = "username";
    public static final String DETAILS_PASSWORD = "password";
    public static final String DETAILS_ID_NO = "idNumber";
    public static final String SC_ID  = "id";


    //defines the database
    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    //Creates the database tables
    @Override
    public void onCreate(SQLiteDatabase db){
        // TODO Auto-generated method stub
        db.execSQL("create table student" + "(id integer primary key, fullName text, idNumber integer)");
        db.execSQL("create table loginDetails" + "(id integer primary key, username text, password text, idNumber integer)");
        db.execSQL("create table loginStaff" + "(id integer primary key, username text, password text, idNumber integer)");
        db.execSQL("create table staff" + "(id integer primary key, fullName text, idNumber integer)");
        db.execSQL("create table class" + "(id integer primary key, code text, name text, startTime text, endTime text)");
        db.execSQL("create table class2" + "(id integer primary key, code text, name text, startTime text, endTime text, day text)");
        db.execSQL("create table studentClass" + "(id integer primary key, idNumber integer, code text)");
    }

    //when checking the database if tables already  exists drops them
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS student");
        db.execSQL("DROP TABLE IF EXISTS loginDetails");
        db.execSQL("DROP TABLE IF EXISTS staff");
        db.execSQL("DROP TABLE IF EXISTS loginStaff");
        db.execSQL("DROP TABLE IF EXISTS class");
        db.execSQL("DROP TABLE IF EXISTS studentClass");
        db.execSQL("DROP TABLE IF EXISTS class2");
        onCreate(db);
    }


    //inserts a new student into the database
    public boolean insertStudent(Integer idNumber, String fullName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullName", fullName);
        contentValues.put("idNumber", idNumber);
        db.insert("student", null, contentValues);
        return true;
    }

    //inserts a new staff member into the database
    public boolean insertStaff(Integer idNumber, String fullName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullName", fullName);
        contentValues.put("idNumber", idNumber);
        db.insert("staff", null, contentValues);
        return true;
    }

    //inserts a new set of login details into the database
    public boolean insertDetails(Integer idNumber, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("idNumber", idNumber);
        db.insert("loginDetails", null, contentValues);
        return true;
    }

    public boolean insertDetailsS(Integer idNumber, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("idNumber", idNumber);
        db.insert("loginStaff", null, contentValues);
        return true;
    }

    public boolean insertClass(String code, String name, String startTime, String endTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", code);
        contentValues.put("name", name);
        contentValues.put("startTime", startTime);
        contentValues.put("endTime", endTime);
        db.insert("class", null, contentValues);
        return true;
    }

    public boolean inesertSC(Integer id, String code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", code);
        contentValues.put("id", id);
        db.insert("studentClass", null, contentValues);
        return true;
    }

    public boolean insertClass2(String code, String name, String startTime, String endTime, String day){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", code);
        contentValues.put("name", name);
        contentValues.put("startTime", startTime);
        contentValues.put("endTime", endTime);
        contentValues.put("day", day);
        db.insert("class", null, contentValues);
        return true;
    }

    public ArrayList getPasswordStudent(String username1){
        ArrayList<String> password = new ArrayList<>();
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT password FROM loginDetails WHERE username" + " =?", new String[]{username1});
       res.moveToFirst();
       while(res.isAfterLast()==false){
           password.add(res.getString(res.getColumnIndex(DETAILS_PASSWORD)));
           res.moveToNext();
       }
       return password;
    }

    public ArrayList getPasswordStaff(String username1){
        ArrayList<String> password = new ArrayList<>();
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT password FROM loginStaff WHERE username" + " =?", new String[]{username1});
        res.moveToFirst();
        while(res.isAfterLast()==false){
            password.add(res.getString(res.getColumnIndex(DETAILS_PASSWORD)));
            res.moveToNext();
        }
        return password;
    }

    public ArrayList getStudentClasses1(String username1){
        ArrayList<String> id = new ArrayList<>();
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT idNumber FROM loginDetails WHERE username" + " =?", new String[]{username1});
        res.moveToFirst();
        while(res.isAfterLast()==false){
            id.add(res.getString(0));
            res.moveToNext();
        }
        return id;
    }

    public ArrayList getStudentClasses2(String id){
        ArrayList<String> code = new ArrayList<>();
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT code FROM studentClass WHERE id" + "=?", new String[]{id});
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            code.add(cursor.getString(0));
            cursor.moveToNext();
        }
        return code;
    }


    //deletes a tour from the database
    public Integer deleteDetails (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("staff", "id = ?", new String[]
                {Integer.toString(id)});
    }

    public Integer deleteStudent (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("staff", "id = ?", new String[]
                {Integer.toString(id)});
    }


}
