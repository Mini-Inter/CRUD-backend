package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Reports.CompleteInformationReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportsDAO {

    String sql = "";
    public List<CompleteInformationReport> readAllCompleteInfoReport(int id_student){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<CompleteInformationReport> list= new ArrayList<>();
        try{
            conn = connection.connect();

            sql = "SELECT sub.name AS subject, teachers.full_name AS " +
                    "teacher_name, repo.description, repo.type, CAST(repo" +
                    ".send_at AS DATE) FROM students s JOIN receive r on r.fk_student = s.id_student JOIN reports repo on repo.id = r.fk_report JOIN teachers ON teachers.id_employee = repo.fk_teacher JOIN teach t ON teachers.id_employee = t.fk_teacher JOIN subjects sub ON sub.id_subject = t.fk_subject WHERE id_student = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_student);

            ResultSet rs = pstmt.executeQuery();

            String subject;
            String teacher_name;
            String description;
            String type;
            Date send_at;
            while(rs.next()){

                subject = rs.getString("subject");
                teacher_name = rs.getString("teacher_name");
                description = rs.getString("description");
                type = rs.getString("type");
                send_at = rs.getDate("send_at");

                list.add(new CompleteInformationReport(subject,teacher_name,
                        description,type,send_at));
            }
            return list;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            connection.disconnect(conn);
        }
    }

    public List<CompleteInformationReport> readCompleteInfoReportByType(int id_student, String typeChoose){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<CompleteInformationReport> list= new ArrayList<>();
        try{
            conn = connection.connect();

            sql = "SELECT sub.name AS subject, teachers.full_name AS " +
                    "teacher_name, repo.description, repo.type, CAST(repo" +
                    ".send_at AS DATE) FROM students s JOIN receive r on r" +
                    ".fk_student = s.id_student JOIN reports repo on repo.id " +
                    "= r.fk_report JOIN teachers ON teachers.id_employee = " +
                    "repo.fk_teacher JOIN teach t ON teachers.id_employee = t" +
                    ".fk_teacher JOIN subjects sub ON sub.id_subject = t" +
                    ".fk_subject WHERE id_student = ? AND repo.type LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_student);
            pstmt.setString(2,typeChoose);

            ResultSet rs = pstmt.executeQuery();

            String subject;
            String teacher_name;
            String description;
            String type;
            Date send_at;
            while(rs.next()){

                subject = rs.getString("subject");
                teacher_name = rs.getString("teacher_name");
                description = rs.getString("description");
                type = rs.getString("type");
                send_at = rs.getDate("send_at");

                list.add(new CompleteInformationReport(subject,teacher_name,
                        description,type,send_at));
            }
            return list;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            connection.disconnect(conn);
        }
    }
}
