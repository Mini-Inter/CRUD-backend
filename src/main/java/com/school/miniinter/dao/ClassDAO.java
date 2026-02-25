package com.school.miniinter.dao;

import com.itextpdf.layout.element.Link;
import com.school.miniinter.connection.ConnectionFactory;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.school.miniinter.models.Class.Class;

public class ClassDAO {

    String sql;
    public List<Class> readClassByTeacherAndSubject(int id_teacher,
                                                    int id_subject){
        
        Connection conn = null;
        List<Class> list = new ArrayList<>();

        try{
            conn = ConnectionFactory.connect();

            sql = "SELECT c.* FROM class c JOIN teachingAssignment t ON t.fk_class = c.id_class JOIN teachers te ON te.id_employee = t.fk_teacher JOIN subjects s ON s.id_subject = t.fk_subject WHERE te.id_employee = ? AND s.id_subject = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);
            pstmt.setInt(2,id_subject);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int id_class = rs.getInt("id_class");
                char series = rs.getString("series").charAt(0);
                char classroom = rs.getString("classroom").charAt(0);
                list.add(new Class(id_class
                        ,series,classroom));
            }
            return list;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            ConnectionFactory.disconnect();
        }
    }

    public boolean insert(Class classInsert){
        
        Connection conn = null;

        try{
            conn = ConnectionFactory.connect();

            sql = "INSERT INTO class(series,classroom,academic_year) VALUES" +
                    "(?,?,EXTRACT(YEAR FROM CURRENT_DATE))";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,String.valueOf(classInsert.getSeries()));
            pstmt.setString(2,String.valueOf(classInsert.getSeries()));

            return pstmt.execute();
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public Class read(int id_class){
        
        Connection conn = null;

        try{
            conn = ConnectionFactory.connect();

            sql = "SELECT * FROM class WHERE id_class = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_class);

            ResultSet rs = pstmt.executeQuery();
            Class classChoose = null;
            if(rs.next()){
                Character series = rs.getString("series").charAt(0);
                Character classroom = rs.getString("classroom").charAt(0);

                classChoose = new Class(id_class,series,classroom);
            }
            return classChoose;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            ConnectionFactory.disconnect();
        }
    }

    public boolean delete(int id_class){
        
        Connection conn = null;

        try{
            conn = ConnectionFactory.connect();

            sql = "DELETE FROM class WHERE id_class = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_class);

            return pstmt.executeUpdate() > 0;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally{
            ConnectionFactory.disconnect();
        }
    }

    public List<Class> read(){
        
        Connection conn = null;
        List<Class> classes = new LinkedList<>();

        try{
            conn = ConnectionFactory.connect();

            sql = "SELECT * FROM class";
            Statement pstmt = conn.createStatement();

            ResultSet rs = pstmt.executeQuery(sql);

            while (rs.next()) {
                int id_class = Integer.parseInt(rs.getString("id_class"));
                char series = rs.getString("series").charAt(0);
                char classroom = rs.getString("classroom").charAt(0);

                classes.add(new Class(id_class,series,classroom));
            }

            return classes;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            ConnectionFactory.disconnect();
        }
    }

    public boolean update(Class classroom) {
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();

            sql = "UPDATE class " +
                    "SET series = ?, classroom = ? " +
                    "WHERE id_class = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, String.valueOf(classroom.getSeries()));
            pstmt.setString(2, String.valueOf(classroom.getClassroom()));
            pstmt.setInt(3, classroom.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } 
    }
}
