package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Teacher.HomeTeacherInfo;
import com.school.miniinter.models.Teacher.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TeachersDAO {

    String sql;
    public int insert(Teacher teacher){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();
            sql = "INSERT INTO teachers" +
                    "(last_name, first_name, full_name, birth_date, login, password, created_at)" +
                    "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,teacher.getLastName());
            pstmt.setString(2,teacher.getFirstName());
            pstmt.setString(3,teacher.getFirstName() + " " + teacher.getLastName());
            pstmt.setDate(4,teacher.getBirthDate());
            pstmt.setString(5,teacher.getLogin());
            pstmt.setString(6,teacher.getPassword());
            pstmt.setDate(7,teacher.getCreatedAt());

            if (pstmt.executeUpdate()>0) {
                return 1;
            } else {
                return 0;
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }finally {
            connection.disconnect(conn);
        }
    }

    public Teacher read(int id) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = connection.connect();

            sql = "SELECT * FROM teachers WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            rset = pstmt.executeQuery();
            if (rset.next()) {
                return new Teacher(
                        rset.getInt("id"),
                        rset.getString("first_name"),
                        rset.getString("last_name"),
                        rset.getString("login"),
                        rset.getString("password"),
                        rset.getDate("birth_date"),
                        rset.getDate("created_at")
                );
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
        } finally {
            connection.disconnect(conn);
        }
        return null;
    }

    public List<Teacher> read(String name) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset = null;
        List<Teacher> teachers = new LinkedList<>();

        try {
            conn = connection.connect();

            sql = "SELECT * FROM teachers WHERE name=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);

            rset = pstmt.executeQuery();
            while (rset.next()) {
                teachers.add(new Teacher(
                        rset.getInt("id"),
                        rset.getString("first_name"),
                        rset.getString("last_name"),
                        rset.getString("login"),
                        rset.getString("password"),
                        rset.getDate("birth_date"),
                        rset.getDate("created_at")
                ));
            }

            return teachers;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            connection.disconnect(conn);
        }
    }

    public HomeTeacherInfo readHomeInfoTeacher(int id_teacher){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rs;
        PreparedStatement pstmt;
        HomeTeacherInfo homeTeacherInfo = new HomeTeacherInfo();
        try {
            conn = connection.connect();

//            Read full_name
            sql = "SELECT full_name FROM teachers WHERE id_employee = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);

            rs = pstmt.executeQuery();
            if(rs.next()){
                homeTeacherInfo.setFull_name(rs.getString("full_name"));
            }

//            Read teacher's subject
            sql = "SELECT sub.name AS subject FROM subjects sub JOIN teach t " +
                    "ON sub.id_subject = t.fk_subject JOIN teachers ON teachers.id_employee = t.fk_teacher WHERE id_employee = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);

            List<String> list = new ArrayList<>();
            rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getString("subject"));
            }
            homeTeacherInfo.setSubjects(list);

//            Amount Students
            sql = "SELECT count(DISTINCT s.*) AS amountStudents FROM students" +
                    " s JOIN class c ON s.fk_class = c.id_class JOIN has h ON h.fk_class = c.id_class JOIN teach t ON t.id = h.fk_teach JOIN  teachers te ON te.id_employee = t.fk_teacher WHERE id_employee = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);

            rs = pstmt.executeQuery();
            if(rs.next()){
                homeTeacherInfo.setAmountStudents(rs.getInt("amountStudents"));
            }

//            Amount Class
            sql = "SELECT count(DISTINCT h.fk_class) AS amountClass FROM " +
                    "teachers te JOIN " +
                    "teach t ON te.id_employee = t.fk_teacher JOIN has h ON t.id = h.fk_teach WHERE id_employee = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);

            rs = pstmt.executeQuery();
            if(rs.next()){
                homeTeacherInfo.setAmountClass(rs.getInt("amountClass"));
            }

//            Amount Observations
            sql = "SELECT count(fk_teacher) AS amountObservations FROM " +
                    "reports WHERE fk_teacher = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);

            rs = pstmt.executeQuery();
            if(rs.next()){
                homeTeacherInfo.setAmountObservations(rs.getInt("amountObservations"));
            }

            return homeTeacherInfo;

        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            connection.disconnect(conn);
        }
    }

    public Teacher readByLogin(String login) {
            ConnectionFactory connection = new ConnectionFactory();
            Connection conn = null;
            ResultSet rset = null;

            try {
                conn = connection.connect();

                sql = "SELECT * FROM teachers WHERE login=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1,login);

                rset = pstmt.executeQuery();
                if (rset.next()) {
                    return new Teacher(
                            rset.getInt("id_employee"),
                            rset.getString("first_name"),
                            rset.getString("last_name"),
                            login,
                            rset.getString("password"),
                            rset.getDate("birth_date"),
                            rset.getDate("created_at")
                    );
                }
            } catch(SQLException sqle){
                sqle.printStackTrace();
            } finally {
                connection.disconnect(conn);
            }
            return null;
    }

    public boolean isTeacher(String login, String pw) throws IllegalArgumentException {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = connection.connect();

            sql = "SELECT * FROM teachers WHERE login=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, login);

            rset = pstmt.executeQuery();

            if (rset.next()) {
                if (rset.getString("password").equals(pw)) {
                    return true;
                } throw new RuntimeException("Senha incorreta!");
            } else {
                return false;
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        } finally {
            connection.disconnect(conn);
        }
    }

    public int update(Teacher teacher) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();

            sql = "UPDATE teachers SET first_name=?, last_name=?, full_name=?, birth_date=?, login=?, password=?, created_at=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, teacher.getFirstName());
            pstmt.setString(2, teacher.getLastName());
            pstmt.setString(3, teacher.getFirstName() + " " + teacher.getLastName());
            pstmt.setDate(4, teacher.getBirthDate());
            pstmt.setString(5, teacher.getLogin());
            pstmt.setString(6, teacher.getPassword());
            pstmt.setDate(7, teacher.getCreatedAt());
            pstmt.setInt(8, teacher.getId());

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
            sql = "DELETE FROM teachers WHERE id=?";
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
