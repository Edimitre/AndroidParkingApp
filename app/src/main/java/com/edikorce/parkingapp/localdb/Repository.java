package com.edikorce.parkingapp.localdb;

import android.content.Context;

import com.edikorce.parkingapp.model.User;

import java.util.ArrayList;
import java.util.List;


public class Repository {

    Context context;

    UserDao userDao;

    User user;

    List<User> userList = new ArrayList<>();

    public Repository(Context context) {
        this.context = context;
        userDao = UserDatabase.getInstance(context).usersDao();
    }

    // USER
    public void saveUser(User user){

        Thread thread = new Thread(()->{
            userDao.insert(user);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void deleteProfile(){

        Thread thread = new Thread(()->{
            userDao.deleteProfile();
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public User getProfileFromLocal() throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                userList = userDao.loadProfileList();

                for (User user1:userList){
                    user = user1;

                }
            }
        });
        thread.start();
        thread.join();
        return user;

    }

}
