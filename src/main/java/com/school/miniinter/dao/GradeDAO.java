package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Grades.GradeForSubject;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
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
                    "LIKE '1' THEN g.value END AS n1, CASE WHEN g.grade_type LIKE '2' THEN g.value END AS n2 FROM students s JOIN grades g on s.id_student = g.fk_student AND EXTRACT(YEAR FROM g.send_at) = EXTRACT(YEAR FROM CURRENT_DATE) JOIN subjects sub on sub.id_subject = g.fk_subject WHERE s.id_student = ? GROUP BY 1,2,3";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id_student);

            ResultSet rs = pstmt.executeQuery();
            String name_subject;
            Object n1;
            Object n2;
            while (rs.next()) {
                name_subject = rs.getString("name_subject");
                n1 = rs.getObject("n1");
                if (n1  == null){
                    n1 = 0.0;
                }
                n2 = rs.getDouble("n2");
                if (n1  == null){
                    n1 = 0.0;
                }
                GradeForSubject gradeForSubject = new GradeForSubject(name_subject,
                        Double.parseDouble(String.valueOf(n1)),
                        Double.parseDouble(String.valueOf(n2)));
                gradeForSubject.setAverage();
                gradeForSubject.setSituation();
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
}
