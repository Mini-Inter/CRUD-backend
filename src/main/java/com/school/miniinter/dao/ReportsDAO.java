package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Reports.CompleteInformationReport;
import com.school.miniinter.models.Reports.Reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ReportsDAO {

    String sql = "";

    public List<CompleteInformationReport> readAllCompleteInfoReport(int id_student){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<CompleteInformationReport> list= new ArrayList<>();
        try{
            conn = connection.connect();

            sql = "SELECT teachers.full_name AS teacher_name, repo.description, 'nothing' AS type, CAST(repo.send_at AS DATE) FROM students s " +
                    "JOIN receive r on r.fk_student = s.id_student " +
                    "JOIN reports repo on repo.id = r.fk_report " +
                    "JOIN teachers ON teachers.id_employee = repo.fk_teacher " +
                    "WHERE id_student = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_student);

            ResultSet rs = pstmt.executeQuery();

            String teacher_name;
            String description;
            String type;
            Date send_at;
            while(rs.next()){

                teacher_name = rs.getString("teacher_name");
                description = rs.getString("description");
                type = rs.getString("type");
                send_at = rs.getDate("send_at");

                list.add(new CompleteInformationReport(teacher_name,
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

    public List<CompleteInformationReport> readReportsByTeacher(int id_teacher){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<CompleteInformationReport> list= new ArrayList<>();
        try{
            conn = connection.connect();

            sql = "SELECT teachers.full_name AS teacher_name, repo.description, 'nothing' AS type, CAST(repo.send_at AS DATE) FROM students s " +
                    "JOIN receive r on r.fk_student = s.id_student " +
                    "JOIN reports repo on repo.id = r.fk_report " +
                    "JOIN teachers ON teachers.id_employee = repo.fk_teacher " +
                    "WHERE id_employee = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id_teacher);

            ResultSet rs = pstmt.executeQuery();

            String teacher_name;
            String description;
            String type;
            Date send_at;
            while(rs.next()){

                teacher_name = rs.getString("teacher_name");
                description = rs.getString("description");
                type = rs.getString("type");
                send_at = rs.getDate("send_at");

                list.add(new CompleteInformationReport(teacher_name,description,type,send_at));
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

                teacher_name = rs.getString("teacher_name");
                description = rs.getString("description");
                type = rs.getString("type");
                send_at = rs.getDate("send_at");

                list.add(new CompleteInformationReport(teacher_name,
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

    public boolean createReport(Reports report) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();
            sql = "INSERT INTO reports (type, fk_teacher, description, send_at) VALUES " +
                    "(?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, report.getType());
            pstmt.setInt(2, report.getFk_teachers());
            pstmt.setString(3, report.getDescription());
            pstmt.setDate(4, report.getSend_at());

            return pstmt.execute();
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } finally {
            connection.disconnect(conn);
        }
    }
}
