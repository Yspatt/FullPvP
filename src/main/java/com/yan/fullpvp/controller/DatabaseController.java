package com.yan.fullpvp.controller;

import com.yan.fullpvp.Main;

import java.sql.PreparedStatement;


public class DatabaseController {

    public static void init(){
        Main.getInstance().getHikariSource().query("CREATE TABLE IF NOT EXISTS `users` (`uuid` VARCHAR(100) primary key,`data` TEXT);",
                PreparedStatement::execute);

    }
}
