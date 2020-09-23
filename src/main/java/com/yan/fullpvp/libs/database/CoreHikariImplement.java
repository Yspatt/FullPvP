package com.yan.fullpvp.libs.database;

import com.yan.fullpvp.model.SQLConsumer;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class CoreHikariImplement implements HikariConnection {

    private final HikariDataSource hikari;

    public void query(String query, SQLConsumer<? super PreparedStatement> consumer) {
        try {
            PreparedStatement preparedStatement = connection().prepareStatement(query);
            consumer.accept(preparedStatement);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void result(PreparedStatement preparedStatement, SQLConsumer<? super ResultSet> consumer) {
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            consumer.accept(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Connection connection() {
        try {
            return hikari.getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
