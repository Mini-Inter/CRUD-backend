package com.school.miniinter.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://");

        }catch(SQLException sql){
            sql.printStackTrace();
        }
        finally {
            return conn;
        }
    }

    public void disconnect(Connection conn){
        try{
            if(conn!= null && !conn.isClosed()) {
                conn.close();
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

    }
}
