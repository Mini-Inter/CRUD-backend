package com.school.miniinter.dao;

import com.itextpdf.layout.element.Link;
import com.school.miniinter.connection.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.school.miniinter.models.Class.Class;

public class ClassDAO {

    String sql;
    public List<Class> readClassByTeacherAndSubject(int id_teacher,
                                                    int id_subject){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<Class> list = new ArrayList<>();

        try{
            conn = connection.connect();

            sql = "SELECT c.* FROM class c JOIN has h ON c.id_class = h" +
                    ".fk_class JOIN teach t ON t.id = h.fk_teach JOIN " +
                    "teachers te ON te.id_employee = t.fk_teacher JOIN subjects s ON s.id_subject = t.fk_subject WHERE te.id_employee = ? AND s.id_subject = ?";
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
            connection.disconnect(conn);
        }
    }

    public Class read(int id_class){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try{
            conn = connection.connect();

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
            connection.disconnect(conn);
        }
    }

    public boolean delete(int id_class){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try{
            conn = connection.connect();

            sql = "DELETE FROM class WHERE id_class = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_class);

            return pstmt.executeUpdate() > 0;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally{
            connection.disconnect(conn);
        }
    }

    public List<Class> read(){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<Class> classes = new LinkedList<>();

        try{
            conn = connection.connect();

            sql = "SELECT * FROM class";
            Statement pstmt = conn.createStatement();

            ResultSet rs = pstmt.executeQuery(sql);

            while (rs.next()) {
                int id_class = rs.getString("id_class").charAt(0);
                char series = rs.getString("series").charAt(0);
                char classroom = rs.getString("classroom").charAt(0);

                classes.add(new Class(id_class,series,classroom));
            }

            return classes;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            connection.disconnect(conn);
        }
    }

    public boolean update(Class classroom) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();

            sql = "UPDATE class " +
                    "SET series = ? AND classroom = ? " +
                    "WHERE id_class = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, classroom.getSeries());
            pstmt.setInt(2, classroom.getClassroom());
            pstmt.setInt(3, classroom.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } finally {
            connection.disconnect(conn);
        }
    }
}
