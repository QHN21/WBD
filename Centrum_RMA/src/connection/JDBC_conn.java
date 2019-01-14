package connection;

import java.sql.*;
import java.util.LinkedList;

public class JDBC_conn {
    private Connection con = null;

    public void getConnection(String user, String pass) throws SQLException {

        // Connect to database
        String DB_USER = user;
        String DB_PASS = pass;
        String DB_URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";

        con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        System.out.println("Query data example:");
        System.out.println("=========================================");
    }

    public LinkedList getData() throws SQLException {
        // Create and execute a SELECT SQL statement.
        ResultSet rs;
        LinkedList<String> ll = new LinkedList<>();
        String selectSql = "SELECT * FROM \"Komponenty\"";

        Statement statement = con.createStatement();
        rs = statement.executeQuery(selectSql);
        String x, y, z;
        int i = 0;
        while (rs.next()) {
            x = rs.getString(1);
            y = rs.getString(3);
            ll.add(i++,x);
            ll.add(i++,y);
        }
        return ll;
    }
}