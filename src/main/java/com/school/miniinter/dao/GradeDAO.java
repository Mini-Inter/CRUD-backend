package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Grades.GradeForSubject;
import com.school.miniinter.models.Grades.SimpleGrade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    String sql = "";
    public List<GradeForSubject> readAllGradesForStudent(int id_student) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<GradeForSubject> grades = new ArrayList<>();

        try{
            conn = connection.connect();

            sql = "SELECT sub.name as name_subject, g.value AS value, g.grade_type AS grade_type FROM students s JOIN grades g on s.id_student = g.fk_student AND EXTRACT(YEAR FROM g.send_at) = EXTRACT(YEAR FROM CURRENT_DATE) JOIN subjects sub on sub.id_subject = g.fk_subject WHERE s.id_student = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id_student);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                GradeForSubject grade = new GradeForSubject();
                boolean existe = false;

                if (!grades.isEmpty()) {
                    for (GradeForSubject grade1 : grades) {
                        if (rs.getString("name_subject").equals(grade1.getSubject())) {
                            existe = true;

                            if (grade1.getN1() == -1.0) {
                                grade1.setN1(rs.getDouble("value"));
                            } else {
                                grade1.setN2(rs.getDouble("value"));
                            }
                            grade1.setAverage();
                            grade1.setSituation();
                        }
                    }
                }

                if (!existe) {
                    int type = rs.getInt("grade_type");
                    grade.setSubject(rs.getString("name"));
                    if (type == 1) {
                        grade.setN1(rs.getDouble("value"));
                        grade.setN2();
                    } else if (type == 2) {
                        grade.setN1();
                        grade.setN2(rs.getDouble("value"));
                    }
                    grade.setAverage();
                    grade.setSituation();
                    grades.add(grade);
                }
            }
            return grades;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            connection.disconnect(conn);
        }
    }

    public int updateGradeById(int id_grade, Double value){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try{
            conn = connection.connect();

            sql = "UPDATE grades SET value = ? WHERE id_grade = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setDouble(1,value);
            pstmt.setInt(2,id_grade);

            return pstmt.executeUpdate();
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }finally {
            connection.disconnect(conn);
        }
    }

    public boolean insertGradeByStudent(SimpleGrade simpleGrade){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        try{
            conn = connection.connect();

            sql = "INSERT INTO grades(fk_student,fk_subject,grade_type,value)" +
                    " VALUES(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,simpleGrade.getId_student());
            pstmt.setInt(2,simpleGrade.getId_subject());
            pstmt.setString(3,simpleGrade.getGrade_type());
            pstmt.setDouble(4,simpleGrade.getValue());

            return pstmt.execute();
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            connection.disconnect(conn);
        }
    }
}
