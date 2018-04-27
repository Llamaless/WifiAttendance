package com.example.kobej.attendancesystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Kobe Davis - 6905105
 * 13/04/18
 */
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
    public static final String ST = "startTime";
    public static final String ET = "endTime";
    public static final String DAY = "day";
    public static final String BSSID =  "bssid";


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
        db.execSQL("create table lectureClass" + "(id integer primary key, idNumber inetger, code text)");
        db.execSQL("create table signedIn" + "(id integer primary key, username text, time text, code text)");
        db.execSQL("create table classI" + "(id integer primary key, code text, day text, bssid text)");
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
        db.execSQL("DROP TABLE IF EXISTS lectureClass");
        db.execSQL("DROP TABLE IF EXISTS signedIn");
        db.execSQL("DROP TABLE IF EXISTS classI");
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

    //Inserts new set of staff details into the database
    public boolean insertDetailsS(Integer idNumber, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("idNumber", idNumber);
        db.insert("loginStaff", null, contentValues);
        return true;
    }

    //inserts a new student class relationship
    public boolean insertSC(Integer id, String code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", code);
        contentValues.put("id", id);
        db.insert("studentClass", null, contentValues);
        return true;
    }

    //inserts a new class into the database
    public boolean insertClass2(String code, String name, String startTime, String endTime, String day){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", code);
        contentValues.put("name", name);
        contentValues.put("startTime", startTime);
        contentValues.put("endTime", endTime);
        contentValues.put("day", day);
        db.insert("class2", null, contentValues);
        return true;
    }

    //Inserts a new lecture class relationship
    public boolean insertLC(Integer id, String code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", code);
        contentValues.put("id", id);
        db.insert("lectureClass", null, contentValues);
        return true;
    }

    //Inserts a new class bssid relationship
    public boolean insertClassI(String code, String day, String bssid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", code);
        contentValues.put("day", day);
        contentValues.put("bssid", bssid);
        db.insert("classI", null, contentValues);
        return true;
    }

    //Insert a new record when a student signs in
    public boolean insertSigned(String username, String time, String code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("time", time);
        contentValues.put("code", code);
        db.insert("signedIn", null, contentValues);
        return true;
    }

    //gets an array list of student passwords
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

    //gets an array list of staff passwords
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

    //These two methods gather thee code for necessary classes
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
            code.add(cursor.getString(cursor.getColumnIndex("code")));
            cursor.moveToNext();
        }
        return code;
    }

    //These 3 methods gather the 3 different types of data from the class2 table
    public ArrayList getTimes1(String code){
        ArrayList times = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM class2 WHERE code" + "=?", new String[]{code});
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            times.add(cursor.getString(cursor.getColumnIndex(ST)));
            cursor.moveToNext();
        }
        return times;
    }

    public ArrayList getTimes2(String code){
        ArrayList times = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT endTime FROM class2 WHERE code" + "=?", new String[]{code});
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            times.add(cursor.getString(cursor.getColumnIndex(ET)));
            cursor.moveToNext();
        }
        return times;
    }

    public ArrayList getTimes3(String code){
        ArrayList times = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT day FROM class2 WHERE code" + "=?", new String[]{code});
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            times.add(cursor.getString(cursor.getColumnIndex(DAY)));
            cursor.moveToNext();
        }
        return times;
    }

    //Gets the bssid from class I to match with the bssid gathere by the WifiManager
    public ArrayList getTimes4(String code){
        ArrayList times = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT bssid FROM classI WHERE code" + "=?", new String[]{code});
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            times.add(cursor.getString(cursor.getColumnIndex(BSSID)));
            cursor.moveToNext();
        }
        return times;
    }

    //These two methods get the code for classes to populate the list view
    public ArrayList getLClasses1(String username1){
        ArrayList<String> id = new ArrayList<>();
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT idNumber FROM loginStaff WHERE username" + " =?", new String[]{username1});
        res.moveToFirst();
        while(res.isAfterLast()==false){
            id.add(res.getString(0));
            res.moveToNext();
        }
        return id;
    }

    public ArrayList getLClasses2(String id){
        ArrayList<String> code = new ArrayList<>();
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lectureClass WHERE id" + "=?", new String[]{id});
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            code.add(cursor.getString(cursor.getColumnIndex("code")));
            cursor.moveToNext();
        }
        return code;
    }

    //Gets the list of students from the signed in table in relation to class
    public ArrayList<String> getAllStudents(String code){
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from signedIn WHERE code =?", new String[]{code} );
        res.moveToFirst();

        while(res.isAfterLast()== false){
            arrayList.add(res.getString(res.getColumnIndex(DETAILS_USERNAME)));
            res.moveToNext();
        }
        return arrayList;
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
