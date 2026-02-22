package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.TeachingAssignment.Teaching;
import com.school.miniinter.models.TeachingAssignment.TeachingAssignment;
import com.school.miniinter.utils.PasswordUtils;

import java.sql.*;

public class TeachingDAO {
    public Teaching[] read() {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        Teaching[] aulas = new Teaching[6];
        for (int n = 0; n < 6; n++) {
            aulas[n] = new Teaching();
        }
        Teaching aula;

        try {
            aula = new Teaching();
            conn = connection.connect();
            String sql = "SELECT A.id, S.name, T.full_name, A.class_num, A" +
                    ".class_hour FROM teachingAssignment A " +
                    "JOIN subjects S ON A.fk_subject = S.id_subject " +
                    "JOIN teachers T ON A.fk_teacher = T.id_employee";

            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                aula.setSubject(rset.getString("name"));
                aula.setIdTeaching(rset.getInt("id"));
                aula.setTeacher(rset.getString("full_name"));
                aula.setClassNum(rset.getInt("class_num"));
                aula.setClass_hour(rset.getString("class_hour"));

                aulas[aula.getClassNum()-1] = aula;
            }

            return aulas;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return null;
        } finally {
            connection.disconnect(conn);
        }
    }

    public Teaching[] readByIdClas(int id_class){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        Teaching[] aulas = new Teaching[6];
        for (int n = 0; n < 6; n++) {
            aulas[n] = new Teaching();
        }

        try {
            conn = connection.connect();
            String sql = "SELECT A.id, S.name, T.full_name, A.class_num, A" +
                    ".class_hour FROM teachingAssignment A JOIN subjects S ON" +
                    " A.fk_subject = S.id_subject JOIN teachers T ON A.fk_teacher = T.id_employee JOIN class C ON c.id_class = A.fk_class WHERE id_class = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id_class);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Teaching aula = new Teaching();
                aula.setSubject(rset.getString("name"));
                aula.setIdTeaching(rset.getInt("id"));
                aula.setTeacher(rset.getString("full_name"));
                aula.setClassNum(rset.getInt("class_num"));
                aula.setClass_hour(rset.getString("class_hour"));

                aulas[aula.getClassNum()-1] = aula;
            }
            for(int i=0;i<6;i++) {
                if (aulas[i].isEmpty()) {
                    Teaching aula = new Teaching();
                    aula.setClassNum(i + 1);
                    switch (i + 1) {
                        case (1) -> {
                            aula.setClass_hour("07:00 - 07:55");
                        }
                        case (2) -> {
                            aula.setClass_hour("08:00 - 08:55");
                        }
                        case (3) -> {
                            aula.setClass_hour("09:00 - 09:55");
                        }
                        case (4) -> {
                            aula.setClass_hour("10:30 - 11:25");
                        }
                        case (5) -> {
                            aula.setClass_hour("11:30 - 12:25");
                        }
                        case (6) -> {
                            aula.setClass_hour("12:30 - 13:25");
                        }
                    }
                    aulas[i] = aula;
                }
            }
            return aulas;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return null;
        } finally {
            connection.disconnect(conn);
        }
    }

    public boolean insert(TeachingAssignment aula) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();
            String sql = "INSERT INTO teachingassignment (fk_class, fk_subject, fk_teacher, class_num) VALUES " +
                    "(?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, aula.getIdClass());
            pstmt.setInt(2, aula.getIdSubject());
            pstmt.setInt(3, aula.getIdTeacher());
            pstmt.setInt(4, aula.getClassNumber());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } finally {
            connection.disconnect(conn);
        }
    }

    public boolean update(TeachingAssignment aula) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();
            String sql = "UPDATE teachingassignment " +
                    "SET fk_subject = ? AND fk_teacher = ?" +
                    "WHERE id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, aula.getIdSubject());
            pstmt.setInt(2, aula.getIdTeacher());
            pstmt.setObject(3, aula.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } finally {
            connection.disconnect(conn);
        }
    }
}
