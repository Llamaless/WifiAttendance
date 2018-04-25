package com.example.kobej.attendancesystem;

import android.content.Context;

public class Data {
    private Context context;
    //creates database handler object
    private DBHandler dbHandler;
    public Data(Context context){

        //define the database object as a new instance of the database handler
        this.context = context;
        dbHandler = new DBHandler(context);
    }
    public void insertDetails(Integer idNumber, String username, String password){
        dbHandler.insertDetails(idNumber, username, password);
    }



}
