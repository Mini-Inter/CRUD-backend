package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Reports.*;
import com.school.miniinter.models.Reports.Reports;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReportsDAO {

    String sql = "";

    public List<CompleteInformationReport> readAllCompleteInfoReport(int id_student){
        
        Connection conn = null;
        List<CompleteInformationReport> list= new ArrayList<>();
        try{
            conn = ConnectionFactory.connect();

            sql = "SELECT te.full_name AS teacher_name, repo.description, repo.type AS type, CAST(repo.send_at AS DATE) FROM students s JOIN receive r on r.fk_student = s.id_student JOIN reports repo on repo.id = r.fk_report JOIN teachers te ON te.id_employee = repo.fk_teacher WHERE id_student = ?";
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
            ConnectionFactory.disconnect();
        }
    }
    public List<CompleteInformationReport> readReportsByTeacher(int id_teacher){
        
        Connection conn = null;
        List<CompleteInformationReport> list= new ArrayList<>();
        try{
            conn = ConnectionFactory.connect();

            sql = "SELECT DISTINCT teachers.full_name AS teacher_name, repo" +
                    ".description, repo.type AS type, CAST(repo.send_at AS DATE) FROM students s JOIN receive r on r.fk_student = s.id_student JOIN reports repo on repo.id = r.fk_report JOIN teachers ON teachers.id_employee = repo.fk_teacher WHERE id_employee = ?";
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
            ConnectionFactory.disconnect();
        }
    }
    public List<CompleteInformationReport> readCompleteInfoReportByType(int id_student, String typeChoose){
        
        Connection conn = null;
        List<CompleteInformationReport> list= new ArrayList<>();
        try{
            conn = ConnectionFactory.connect();

            sql = "SELECT teachers.full_name AS teacher_name, repo" +
                    ".description, repo.type, CAST(repo.send_at AS DATE) AS " +
                    "date_report" +
                    " FROM students s JOIN receive r on r.fk_student = s.id_student JOIN reports repo on repo.id = r.fk_report JOIN teachers ON teachers.id_employee = repo.fk_teacher WHERE id_student = ? AND repo.type LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_student);
            pstmt.setString(2,typeChoose);

            ResultSet rs = pstmt.executeQuery();

            String teacher_name;
            String description;
            String type;
            Date send_at;
            while(rs.next()){

                teacher_name = rs.getString("teacher_name");
                description = rs.getString("description");
                type = rs.getString("type");
                send_at = rs.getDate("date_report");

                list.add(new CompleteInformationReport(teacher_name,
                        description,type,send_at));
            }
            return list;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            ConnectionFactory.disconnect();
        }
    }
    public boolean insert(Reports insertReport) {
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();
            sql = "INSERT INTO reports (type, fk_teacher, description) VALUES " +
                    "(?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, insertReport.getType());
            pstmt.setInt(2, insertReport.getFk_teachers());
            pstmt.setString(3, insertReport.getDescription());

            return pstmt.execute();
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } 
    }

    public Integer readIdByDescription(String description){
        
        Connection conn = null;

        try{
            conn = ConnectionFactory.connect();

            sql = "SELECT id FROM reports WHERE description LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,description);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                return rs.getInt("id");
            }
            return 0;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return 0;
        }
    }
    public List<CompleteReport> readCompleteReport() {
        
        Connection conn = null;
        List<CompleteReport> reports = new LinkedList<>();
        CompleteReport rep;

        try {
            conn = ConnectionFactory.connect();
            String sql = "SELECT R.id, R.send_at, R.type, R.description, T.full_name \"teacher\", STRING_AGG(S.full_name, '|') \"students\" FROM reports R " +
                    "JOIN receive H ON R.id = H.fk_report " +
                    "JOIN students S ON H.fk_student = S.id_student " +
                    "JOIN teachers T ON R.fk_teacher = T.id_employee " +
                    "GROUP BY 1, 2, 3, 4, 5";
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                rep = new CompleteReport();
                rep.setId(rset.getInt("id"));
                rep.setType(rset.getString("type"));
                rep.setDescription(rset.getString("description"));
                rep.setSend_at(rset.getDate("send_at"));
                rep.setStudents(rset.getString("students").split("\\|"));
                rep.setTeacher(rset.getString("teacher"));
                reports.add(rep);
            }

            return reports;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return null;
        } 

    }
    public CompleteReport readCompleteReport(int id) {
        
        Connection conn = null;
        CompleteReport rep;

        try {
            conn = ConnectionFactory.connect();
            String sql = "SELECT R.id AS id,R.send_at, R.type, R.description," +
                    " T.full_name \"teacher\", STRING_AGG(S.full_name, '|') \"students\" FROM reports R " +
                    "JOIN receive H ON R.id = H.fk_report " +
                    "JOIN students S ON H.fk_student = S.id_student " +
                    "JOIN teachers T ON R.fk_teacher = T.id_employee " +
                    "WHERE R.id = ? " +
                    "GROUP BY 1, 2, 3, 4,5";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                rep = new CompleteReport();
                rep.setId(rset.getInt("id"));
                rep.setType(rset.getString("type"));
                rep.setDescription(rset.getString("description"));
                rep.setSend_at(rset.getDate("send_at"));
                rep.setStudents(rset.getString("students").split("\\|"));
                rep.setTeacher(rset.getString("teacher"));
                return rep;
            }
            return null;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return null;
        } 

    }

    public List<Reports> read() {
        
        Connection conn = null;
        List<Reports> reports = new LinkedList<>();

        try {
            conn = ConnectionFactory.connect();
            String sql = "SELECT * FROM reports";
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                reports.add(new Reports(
                        rset.getInt("id"),
                        rset.getInt("fk_teacher"),
                        rset.getString("type"),
                        rset.getString("description"),
                        rset.getDate("send_at")
                ));
            }

            return reports;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return null;
        } 

    }
    public Reports read(int idReports) {
        
        Connection conn = null;
        List<Reports> reports = new LinkedList<>();

        try {
            conn = ConnectionFactory.connect();
            String sql = "SELECT * FROM reports WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, idReports);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                return new Reports(
                        idReports,
                        rset.getInt("fk_teacher"),
                        rset.getString("type"),
                        rset.getString("description"),
                        rset.getDate("send_at")
                );
            }

            return null;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return null;
        } 
    }
    public boolean update(Reports report) {
        
        Connection conn = null;
        int n = 1;

        try {
            conn = ConnectionFactory.connect();

            conn.setAutoCommit(false);
            try {
                String sql = "UPDATE reports " +
                        "SET type = ?, description = ? " +
                        "WHERE id = ?; " +
                        "DELETE FROM receive WHERE fk_report = ?; ";

                for (String idStudents : report.getFk_students()) {
                    sql += "INSERT INTO receive (fk_student, fk_report) " +
                            "VALUES (?, ?); ";
                }

                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(n++, report.getType());
                pstmt.setString(n++, report.getDescription());
                pstmt.setInt(n++, report.getId());
                pstmt.setInt(n++, report.getId());

                for (String idStudents : report.getFk_students()) {
                    pstmt.setInt(n++, Integer.parseInt(idStudents));
                    pstmt.setInt(n++, report.getId());
                }

                pstmt.execute();

                conn.commit();
                return true;
            } catch (SQLException exc) {
                exc.printStackTrace();
                conn.rollback();
                return false;
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } 
    }
    public boolean delete(int idReports) {
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();
            String sql = "DELETE FROM reports WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, idReports);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } 
    }
}
