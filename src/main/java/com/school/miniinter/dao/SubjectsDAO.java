package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Subject.Subject;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SubjectsDAO {

    public int insert(Subject subject){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();

            String sql = "INSERT INTO subjects" +
                    "(description, name)" +
                    "VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,subject.getName());
            pstmt.setString(2,subject.getDescription());

            if (pstmt.executeUpdate()>0) {
                return 1;
            } else {
                return 0;
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return -1;
        } finally {
            connection.disconnect(conn);
        }
    }
    public List<Subject> read() {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset = null;
        List<Subject> subjects = new LinkedList<>();

        try {
            conn = connection.connect();

            String sql = "SELECT * FROM Subjects";
            Statement pstmt = conn.createStatement();

            rset = pstmt.executeQuery(sql);
            while (rset.next()) {
                subjects.add(new Subject(
                        rset.getInt("id_subject"),
                        rset.getString("description"),
                        rset.getString("name")
                ));
            }

            return subjects;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            connection.disconnect(conn);
        }
    }
    public Subject read(int id) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset;

        try {
            conn = connection.connect();

            String sql = "SELECT * FROM subjects WHERE id_subject=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            rset = pstmt.executeQuery();
            if (rset.next()) {
                return new Subject(
                        rset.getInt("id"),
                        rset.getString("description"),
                        rset.getString("name")
                );
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
        } finally {
            connection.disconnect(conn);
        }
        return null;
    }
    public List<Subject> readByName(String name) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset;
        List<Subject> subjects = new LinkedList<>();

        try {
            conn = connection.connect();

            String sql = "SELECT * FROM Subjects WHERE name=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);

            rset = pstmt.executeQuery();
            while (rset.next()) {
                subjects.add(new Subject(
                        rset.getInt("id"),
                        rset.getString("description"),
                        rset.getString("name")
                ));
            }

            return subjects;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            connection.disconnect(conn);
        }
    }
    public List<Subject> readByStudent(int idStudent) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset;
        List<Subject> subjects = new LinkedList<>();

        try {
            conn = connection.connect();

            String sql = "SELECT D.id_subject, D.name, D.description FROM Class C JOIN teachingAssignment P on C.id_class = P.fk_class JOIN subjects D on P.fk_subject = D.id_subject JOIN students S on C.id_class = S.fk_class WHERE id_student = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, idStudent);

            rset = pstmt.executeQuery();
            while (rset.next()) {
                subjects.add(new Subject(
                        rset.getInt("id_subject"),
                        rset.getString("description"),
                        rset.getString("name")
                ));
            }
            return subjects;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            connection.disconnect(conn);
        }
    }
    public List<Subject> readByTeacher(int idTeacher) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset;
        List<Subject> subjects = new LinkedList<>();

        try {
            conn = connection.connect();

            String sql = "SELECT S.* FROM teachers T JOIN teachingAssignment P ON T.id_employee = P.fk_teacher JOIN subjects S on P.fk_subject = S.id_subject WHERE T.id_employee = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, idTeacher);

            rset = pstmt.executeQuery();
            while (rset.next()) {
                subjects.add(new Subject(
                        rset.getInt("id_subject"),
                        rset.getString("description"),
                        rset.getString("name")
                ));
            }
            return subjects;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            connection.disconnect(conn);
        }
    }
    public boolean update(Subject subject) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();
            String sql = "UPDATE Subjects SET name=?, description=? WHERE " +
                    "id_subject=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, subject.getName());
            pstmt.setString(2, subject.getDescription());
            pstmt.setInt(3, subject.getId());

            return  pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            connection.disconnect(conn);
        }
    }
    public int delete(int id) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();

            String sql = "DELETE FROM Subjects WHERE id_subject=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            if (pstmt.executeUpdate() == 0) {
                return 0;
            } else {
                return 1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            connection.disconnect(conn);
        }
    }
}
