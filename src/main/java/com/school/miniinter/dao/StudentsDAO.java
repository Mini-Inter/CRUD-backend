package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Students.BasicInfo;
import com.school.miniinter.models.Students.GradeForStudent;
import com.school.miniinter.models.Students.Students;
import com.school.miniinter.models.Students.Summary;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StudentsDAO {

    String sql = "";
    public int delete(int id){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try{
            conn = connection.connect();

            sql = "DELETE FROM students WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            if(pstmt.executeUpdate() == 0){
                return 0;
            }else{
                return 1;
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }
        finally{
            connection.disconnect(conn);
        }
    }

    public boolean insert(int fk_class, String full_name, String first_name,
                           String last_name, Date birth_date,String login,
                           String password, String created_at){
        ConnectionFactory conexao = new ConnectionFactory();
        Connection conn = null;
        try{
            conn = conexao.connect();
            sql = "INSERT INTO " +
                    "students(fk_class,full_name,first_name,last_name," +
                    "birth_date,login,password,created_at) VALUES(?,?,?,?,?," +
                    "?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,fk_class);
            pstmt.setString(2,full_name);
            pstmt.setString(3,first_name);
            pstmt.setString(4,last_name);
            pstmt.setDate(5,birth_date);
            pstmt.setString(6,login);
            pstmt.setString(7,password);
            pstmt.setString(8,created_at);

            return pstmt.executeUpdate()>0;

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            conexao.disconnect(conn);
        }
    }

//   Precisa de mais informação de funcionamento, pois não sabemos o que
//   precisa ser mudado!
    public int update(int id){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        try {
            conn = connection.connect();
            sql = "UPDATE students " +
                    "SET   WHERE id=?";
//            Precisa de mais algumas informações do que será mudado
            PreparedStatement pstmt = conn.prepareStatement(sql);

//            Não esquecer de mudar o pstmt, pois o id não será mais o indice 1
            pstmt.setInt(1,id);

            if(pstmt.executeUpdate() == 0){
                return 0;
            }else{
                return 1;
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }
        finally{
            connection.disconnect(conn);
        }
    }

    public Students readById(int id_student){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        Students student = null;
        try {
            conn = connection.connect();

            sql = "SELECT * FROM students WHERE id_student=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_student);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                int fk_class = rs.getInt("fk_class");
                String full_name = rs.getString("full_name");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                Date birth_date = rs.getDate("birth_date");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String created_at = rs.getString("created_at");
                student = new Students(id_student,fk_class,full_name,
                        first_name,
                        last_name,birth_date,login,password,created_at);
            }

            return student;

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally {
            connection.disconnect(conn);
        }
    }

    public List<Students> readAll(){
        List list = new ArrayList<>();
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        try{
            conn = connection.connect();

            sql = "SELECT * FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){

                int id_student = rs.getInt("id_student");
                int fk_class = rs.getInt("fk_class");
                String full_name = rs.getString("full_name");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                Date birth_date = rs.getDate("birth_date");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String created_at = rs.getString("created_at");

                list.add(new Students(id_student,fk_class,full_name,first_name,
                        last_name,birth_date,login,password,created_at));
            }
            return list;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return list;
        }finally {
            connection.disconnect(conn);
        }
    }

    public ResultSet readName(String name){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        try {
            conn = connection.connect();

            sql = "SELECT * FROM students WHERE full_name LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,name);

            ResultSet rset = pstmt.executeQuery();

            return rset;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally {
            connection.disconnect(conn);
        }
    }

    public List<Students> readByTeach(int idTeacher, int idSubject) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<Students> students = new LinkedList<>();
        try{
            conn = connection.connect();

            sql = "SELECT DISTINCT S.* FROM Has H\n" +
                    "JOIN Class C on H.fk_class = C.id_class\n" +
                    "JOIN teach P on H.fk_teach = P.id\n" +
                    "JOIN students S on C.id_class = S.fk_class\n" +
                    "JOIN teachers T on P.fk_teacher = T.id_employee\n" +
                    "JOIN subjects D ON P.fk_subject = D.id_subject\n" +
                    "WHERE T.id_employee = ? AND D.id_subject = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, idTeacher);
            pstmt.setInt(2, idSubject);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){

                int id_student = rs.getInt("id_student");
                int fk_class = rs.getInt("fk_class");
                String full_name = rs.getString("full_name");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                Date birth_date = rs.getDate("birth_date");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String created_at = rs.getString("created_at");

                students.add(new Students(id_student,fk_class,full_name,first_name,
                        last_name,birth_date,login,password,created_at));
            }
            return students;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return students;
        }finally {
            connection.disconnect(conn);
        }
    }

    public Students readByLogin(String login) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        Students student = null;
        try {
            conn = connection.connect();

            sql = "SELECT * FROM students WHERE login=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, login);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                int fk_class = rs.getInt("fk_class");
                String full_name = rs.getString("full_name");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                Date birth_date = rs.getDate("birth_date");
                int id_student = rs.getInt("id_student");
                String password = rs.getString("password");
                String created_at = rs.getString("created_at");
                student = new Students(id_student,fk_class,full_name,
                        first_name,
                        last_name,birth_date,login,password,created_at);
            }

            return student;

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally {
            connection.disconnect(conn);
        }
    }

    public BasicInfo readBasicInfoStudent(String email){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        BasicInfo basicInfo = new BasicInfo();
        try{
            conn = connection.connect();

            sql = "SELECT s.full_name, c.classroom, c.series, s" +
                    ".id_student, EXTRACT(YEAR FROM CURRENT_DATE) AS " +
                    "School_year FROM" +
                    " class c JOIN students s ON c.id_class = s.fk_class WHERE login LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,email);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                basicInfo.setId_student(rs.getInt("id_student"));
                basicInfo.setClassroom(rs.getString("classroom"));
                basicInfo.setSeries(rs.getString("series"));
                basicInfo.setFull_name(rs.getString("full_name"));
                basicInfo.setSchool_year(rs.getInt("School_year"));
            }
            return basicInfo;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            connection.disconnect(conn);
        }

    }

    public Summary readSummary(int idStudent, int idSubject) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        Summary sum = new Summary();
        try {
            conn = connection.connect();

            sql = "SELECT S.id_student, S.full_name, C.series, C.classroom, avg(G.value) \"AVG\" FROM students S " +
                    "JOIN class C ON S.fk_class = C.id_class " +
                    "LEFT JOIN has H ON C.id_class = H.fk_class " +
                    "LEFT JOIN teach P on H.fk_teach = P.id " +
                    "LEFT JOIN subjects D ON P.fk_subject = D.id_subject " +
                    "LEFT JOIN grades G on S.id_student = G.fk_student AND D.id_subject = G.fk_subject " +
                    "WHERE S.id_student = ? AND D.id_subject = ? " +
                    "GROUP BY 1, 2, 3, 4, D.id_subject";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,idStudent);
            pstmt.setInt(2, idSubject);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                sum.setMatricula(rs.getInt("id_student"));
                sum.setClassroom(rs.getString("classroom"));
                sum.setSeries(rs.getString("series"));
                sum.setName(rs.getString("full_name"));
                sum.setAverage(rs.getDouble("AVG"));
                sum.setSituation(rs.getDouble("AVG")>=7);
                return sum;
            }

            return null;
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            connection.disconnect(conn);
        }
    }

    public Integer readAmountOfSubjects(int id_student){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        try{
            conn = connection.connect();

            sql = "SELECT count(h.*) AS amount_of_subjects FROM students s " +
                    "JOIN class" +
                    " c ON s" +
                    ".fk_class = c.id_class JOIN has h ON c.id_class = h" +
                    ".fk_class WHERE s.id_student = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_student);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("amount_of_subjects");
            }
            return null;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            connection.disconnect(conn);
        }
    }

    public Double readAverageGrade(int id_student){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        try{
            conn = connection.connect();

            sql = "SELECT ROUND(AVG(g.value),2) AS avg_grade FROM students s " +
                    "JOIN grades g" +
                    " ON s.id_student = g.fk_student AND EXTRACT(YEAR FROM g.send_at) = EXTRACT(YEAR FROM CURRENT_DATE) WHERE s.id_student = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_student);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                return rs.getDouble("avg_grade");
            }
            return null;

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            connection.disconnect(conn);
        }
    }

    public Integer readAmountReports(int id_student){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        try{
            conn = connection.connect();

            sql = "SELECT count(r.*) AS amount_reports FROM students s JOIN " +
                    "receive r ON s.id_student = r.fk_student WHERE s.id_student = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_student);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                return rs.getInt("amount_reports");
            }
            return null;

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            connection.disconnect(conn);
        }
    }

    public boolean isStudent(String login, String password) throws IllegalArgumentException {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset = null;
        try {
            conn = connection.connect();

            String sql = "SELECT * FROM students WHERE login=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,login);

            rset = pstmt.executeQuery();

            if (rset.next()) {
                if (rset.getString("password").equals(password)) {
                    return true;
                } throw new RuntimeException("Senha incorreta!");
            } else {
                return false;
            }

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            connection.disconnect(conn);
        }
    }
}

