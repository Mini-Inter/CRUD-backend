package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Reports.CompleteReport;
import com.school.miniinter.models.Reports.Reports;
import com.school.miniinter.models.Reports.CompleteInformationReport;
import com.school.miniinter.models.Reports.Reports;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReportsDAO {

    String sql = "";

    public List<CompleteInformationReport> readAllCompleteInfoReport(int id_student){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<CompleteInformationReport> list= new ArrayList<>();
        try{
            conn = connection.connect();

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
            connection.disconnect(conn);
        }
    }
    public List<CompleteInformationReport> readReportsByTeacher(int id_teacher){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<CompleteInformationReport> list= new ArrayList<>();
        try{
            conn = connection.connect();

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
            connection.disconnect(conn);
        }
    }
    public List<CompleteInformationReport> readCompleteInfoReportByType(int id_student, String typeChoose){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<CompleteInformationReport> list= new ArrayList<>();
        try{
            conn = connection.connect();

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
            connection.disconnect(conn);
        }
    }
    public boolean insert(Reports report) {
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
    public List<CompleteReport> readCompleteReport() {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<CompleteReport> reports = new LinkedList<>();
        CompleteReport rep;

        try {
            conn = connection.connect();
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
        } finally {
            connection.disconnect(conn);
        }

    }
    public CompleteReport readCompleteReport(int id) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        CompleteReport rep;

        try {
            conn = connection.connect();
            String sql = "SELECT R.send_at, R.type, R.description, T.full_name \"teacher\", STRING_AGG(S.full_name, '|') \"students\" FROM reports R " +
                    "JOIN receive H ON R.id = H.fk_report " +
                    "JOIN students S ON H.fk_student = S.id_student " +
                    "JOIN teachers T ON R.fk_teacher = T.id_employee " +
                    "WHERE R.id = ? " +
                    "GROUP BY 1, 2, 3, 4";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rset = stmt.executeQuery();

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
        } finally {
            connection.disconnect(conn);
        }

    }
    public List<Reports> read() {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<Reports> reports = new LinkedList<>();

        try {
            conn = connection.connect();
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
        } finally {
            connection.disconnect(conn);
        }

    }
    public Reports read(int idReports) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<Reports> reports = new LinkedList<>();

        try {
            conn = connection.connect();
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
        } finally {
            connection.disconnect(conn);
        }
    }
    public boolean update(Reports report) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();
            String sql = "UPDATE reports " +
                    "SET type = ? AND description = ? " +
                    "WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, report.getType());
            pstmt.setString(2, report.getDescription());
            pstmt.setInt(3, report.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } finally {
            connection.disconnect(conn);
        }
    }
    public boolean delete(int idReports) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();
            String sql = "DELETE FROM reports WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, idReports);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } finally {
            connection.disconnect(conn);
        }
    }
}
