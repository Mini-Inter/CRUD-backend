package com.school.miniinter.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionFactory {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection connect(){
        Connection conn = null;
        Dotenv envVars = Dotenv.load();
        String host = envVars.get("HOST");
        String port = envVars.get("PORT");
        String database = envVars.get("DATABASE");
        String user = envVars.get("USERNAME");
        String password = envVars.get("PASSWORD");
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, password);
        } catch(SQLException sql){
            sql.printStackTrace();
        }
        return conn;
    }

    public void disconnect(Connection conn){
        try {
            if(conn!= null && !conn.isClosed()) {
                conn.close();
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }
}
