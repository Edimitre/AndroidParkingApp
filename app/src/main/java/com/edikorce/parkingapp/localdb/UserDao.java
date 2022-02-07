package com.edikorce.parkingapp.localdb;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.edikorce.parkingapp.model.User;

import java.util.List;


@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("DELETE FROM user_table")
    void deleteProfile();

    @Query("SELECT * FROM user_table WHERE id LIKE :id")
    User loadProfileById(int id);

    @Query("SELECT * FROM user_table")
    List<User> loadProfileList();

}
