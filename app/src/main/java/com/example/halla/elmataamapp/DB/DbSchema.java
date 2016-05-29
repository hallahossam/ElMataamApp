package com.example.halla.elmataamapp.db;

/**
 * Created by user on 12/4/2015.
 */
public class DbSchema {
    final String  tableUser = "Users";
    String userID = "id";
    String email = "email";
    String  password = "password";
   // String birthDate = "bd";
    //boolean isAdmin = false;

    final String createTableUser = "create table "+tableUser+"("+userID+" integer primary key autoincrement, "+
            email+" text not null, "+password+" password not null)";


}
