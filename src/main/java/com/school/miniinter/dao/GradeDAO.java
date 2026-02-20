package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Grades.GradeForSubject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    String sql = "";
    public List<GradeForSubject> readAllGradesForStudent(int id_student) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<GradeForSubject> gradeForSubjects = new ArrayList<>();

        try{
            conn = connection.connect();

            sql = "SELECT sub.name as name_subject, CASE WHEN g.grade_type " +
                    "LIKE '1' THEN g.value END AS n1, CASE WHEN g.grade_type LIKE '2' THEN g.value END AS n2 FROM students s JOIN grades g on s.id_student = g.fk_student AND EXTRACT(YEAR FROM g.send_at) = EXTRACT(YEAR FROM CURRENT_DATE) JOIN subjects sub on sub.id_subject = g.fk_subject WHERE s.id_student = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id_student);

            ResultSet rs = pstmt.executeQuery();
            String name_subject;
            Double n1;
            Double n2;
            while (rs.next()) {
                name_subject = rs.getString("name_subject");
                n1 = rs.getDouble("n1");
                if(rs.wasNull()){
                    n1 = -1.0;
                }
                n2 = rs.getDouble("n2");
                if(rs.wasNull()){
                    n2 = -1.0;
                }
                GradeForSubject gradeForSubject =
                        new GradeForSubject(name_subject,n1,n2);
                gradeForSubjects.add(gradeForSubject);
            }
            return gradeForSubjects;
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

    public boolean insertGradeByStudent(Double value, Integer id_student,
                                    Integer id_subject, String grade_type){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        try{
            conn = connection.connect();

            sql = "INSERT INTO grades(fk_student,fk_subject,grade_type,value)" +
                    " VALUES(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_student);
            pstmt.setInt(2,id_subject);
            pstmt.setString(3,grade_type);
            pstmt.setDouble(4,value);

            return pstmt.execute();
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            connection.disconnect(conn);
        }
    }
}
