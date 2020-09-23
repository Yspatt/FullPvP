package com.yan.fullpvp.libs.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface HikariConnection {

    Connection connection() throws SQLException;
}
