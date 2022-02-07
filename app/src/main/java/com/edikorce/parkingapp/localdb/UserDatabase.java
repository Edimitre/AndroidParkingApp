package com.edikorce.parkingapp.localdb;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.edikorce.parkingapp.model.User;


@Database(entities = {User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao usersDao();

    private static volatile UserDatabase INSTANCE;


    public static UserDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (UserDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class,"ParkingDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

