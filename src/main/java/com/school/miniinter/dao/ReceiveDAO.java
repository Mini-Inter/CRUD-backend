package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Receive.Receive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReceiveDAO {
    String sql;
    public boolean insert(Receive receive){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try{
            conn = connection.connect();

            sql = "INSERT INTO receive(fk_student,fk_report) VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,receive.getFk_student());
            pstmt.setInt(2,receive.getFk_report());

            return pstmt.execute();
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            connection.disconnect(conn);
        }
    }
}
