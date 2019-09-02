package com.example.goalseta;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

//    App database object
    private Database database;

    private DatabaseClient(Context mCtx){
        this.mCtx = mCtx;

//        Creating the app database using room database builder
        database = Room.databaseBuilder(mCtx, Database.class, "MyToDo").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx){
        if (mInstance == null){
            mInstance = new DatabaseClient(mCtx);
        } return mInstance;
    }

    public Database getDatabase(){
        return database;
    }
}
