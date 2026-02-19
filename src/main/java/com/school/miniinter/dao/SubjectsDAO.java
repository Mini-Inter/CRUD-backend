package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Subject.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public Subject read(int id) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset = null;
        Subject subject;

        try {
            conn = connection.connect();

            String sql = "SELECT * FROM subjects WHERE id=?";
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
    public List<Subject> read(String name) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset = null;
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
    public int update(Subject subject) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();
            String sql = "UPDATE Subjects SET name=?, description=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, subject.getName());
            pstmt.setString(2, subject.getDescription());
            pstmt.setInt(3, subject.getId());

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
    public int delete(int id) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();

            String sql = "DELETE FROM Subjects WHERE id=?";
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
