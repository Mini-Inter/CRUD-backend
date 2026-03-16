package com.school.miniinter.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionFactory {

    private static Connection conn;
    static {
        try {
            Class.forName("org.postgresql.Driver");
            Dotenv envVars = Dotenv.load();
            String host = envVars.get("HOST");
            String port = envVars.get("PORT");
            String database = envVars.get("DATABASE");
            String user = envVars.get("USER");
            String password = envVars.get("PASSWORD");
            try {
                    conn = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, password);
            } catch(SQLException sql){
                sql.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection connect(){
        return conn;
    }

    public static void disconnect(){
        try {
            if(conn!= null && !conn.isClosed()) {
                conn.close();
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }
}
