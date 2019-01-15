package Model.Connection;

import java.sql.*;

public class JDBC_conn {
    private Connection con = null;

    public boolean getConnection(String user, String pass) throws SQLException {
        boolean conSucess = true;
        // Connect to database
        String DB_USER = user;
        String DB_PASS = pass;
        String DB_URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
        try {
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e){
            if(e.getErrorCode() == 1017) conSucess = false;
        }
        return conSucess;
    }

    public ResultSet getDataServer(String sqlSelect) throws SQLException {
        // Create and execute a SELECT SQL statement.
        ResultSet rs;
        Statement statement = con.createStatement();
        rs = statement.executeQuery(sqlSelect);
        return rs;
    }
}