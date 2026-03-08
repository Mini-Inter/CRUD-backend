package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Receive.Receive;
import com.school.miniinter.models.Reports.Reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReceiveDAO {
    String sql;
    public boolean insert(Receive receive){
        
        Connection conn = null;

        try{
            conn = ConnectionFactory.connect();

            for(int i = 0; i<receive.getFk_students().length;i++){
                sql = "INSERT INTO receive(fk_student,fk_report) VALUES(?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1,receive.getFk_students()[i]);
                pstmt.setInt(2,receive.getFk_report());

                pstmt.execute();
            }
            return true;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
}
