package com.school.miniinter.dao;


import com.itextpdf.layout.element.Link;
import com.school.miniinter.models.Class.*;
import com.school.miniinter.models.Class.Class;
import com.school.miniinter.models.Students.BasicInfo;
import com.school.miniinter.models.Students.CompleteInfo;
import com.school.miniinter.models.Students.Students;
import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Students.Summary;
import com.school.miniinter.models.Teacher.Teacher;

import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class StudentsDAO {

    String sql = "";
    DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("en","US"));

    public int delete(int id){
        
        Connection conn = null;

        try{
            conn = ConnectionFactory.connect();

            sql = "DELETE FROM students WHERE id_student=?";
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
            ConnectionFactory.disconnect();
        }
    }

    public boolean insert(int idStudent, int fk_class, String full_name, String first_name,
                           String last_name, Date birth_date,String login,
                           String password, String created_at){
        
        Connection conn = null;
        try{
            conn = ConnectionFactory.connect();
            sql = "INSERT INTO " +
                    "students(id_student,fk_class,full_name,first_name,last_name,birth_date,login,password,created_at) " +
                    "VALUES(floor(random() * (999999 - 100000 + 1) + 100000)::bigint,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,fk_class);
            pstmt.setString(2,full_name);
            pstmt.setString(3,first_name);
            pstmt.setString(4,last_name);
            pstmt.setDate(5,birth_date);
            pstmt.setString(6,login);
            pstmt.setString(7, password);
            pstmt.setString(8,created_at);

            return pstmt.executeUpdate()>0;

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean insertInitial(Students student){
        
        Connection conn = null;
        try{
            conn = ConnectionFactory.connect();
            sql = "INSERT INTO " +
                    "students(id_student,full_name,first_name,last_name,birth_date,login,password) " +
                    "VALUES(floor(random() * (999999 - 100000 + 1) + 100000)::bigint,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,student.getFull_name());
            pstmt.setString(2,student.getFirst_name());
            pstmt.setString(3,student.getLast_name());
            pstmt.setDate(4, Date.valueOf(format.format(student.getBirth_date())));
            pstmt.setString(5,student.getLogin());
            pstmt.setString(6, student.getPassword());

            return pstmt.executeUpdate()>0;

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean insert(Students student){
        
        Connection conn = null;
        try{
            conn = ConnectionFactory.connect();
            sql = "INSERT INTO " +
                    "students(id_student,fk_class,full_name,first_name,last_name,birth_date,login,password, phone) " +
                    "VALUES(floor(random() * (999999 - 100000 + 1) + 100000)::bigint,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,student.getFk_class());
            pstmt.setString(2,student.getFull_name());
            pstmt.setString(3,student.getFirst_name());
            pstmt.setString(4,student.getLast_name());
            pstmt.setDate(5, Date.valueOf(format.format(student.getBirth_date())));
            pstmt.setString(6,student.getLogin());
            pstmt.setString(7, student.getPassword());
            pstmt.setString(8, student.getPhone());

            return pstmt.executeUpdate()>0;

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public int update(Students student) {
    
    Connection conn = null;

    try {
        conn = ConnectionFactory.connect();

        sql = "UPDATE students SET fk_class = ?, full_name = ?, first_name = ?, last_name = ?, birth_date = ?, login =?, password = ?, phone = ?" +
                " WHERE id_student=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, student.getFk_class());
        pstmt.setString(2, student.getFull_name());
        pstmt.setString(3, student.getFirst_name());
        pstmt.setString(4, student.getLast_name());
        pstmt.setDate(5, Date.valueOf(format.format(student.getBirth_date())));
        pstmt.setString(6, student.getLogin());
        pstmt.setString(7, student.getPassword());
        pstmt.setString(8, student.getPhone());
        pstmt.setInt(9, student.getId_student());

        if (pstmt.executeUpdate() == 0) {
            return 0;
        } else {
            return 1;
        }
    } catch (SQLException sqle) {
        sqle.printStackTrace();
        return -1;
    } finally {
        ConnectionFactory.disconnect();
    }
}

    public Integer readIdByName(String name){
        
        Connection conn = null;
        Students student = null;
        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT id_student FROM students WHERE full_name LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,name);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
               return rs.getInt("id_student");
            }
            return 0;

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return 0;
        }
    }

    public Students readById(int id_student){
        
        Connection conn = null;
        Students student = null;
        try {
            conn = ConnectionFactory.connect();

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
                Date created_at = rs.getDate("created_at");
                String phone = rs.getString("phone");
                student = new Students(id_student,fk_class,full_name,
                        birth_date,login,password,created_at, phone);
            }

            return student;

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    public List<Students> readAll(){
        List list = new ArrayList<>();
        
        Connection conn = null;
        try{
            conn = ConnectionFactory.connect();

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
                Date created_at = rs.getDate("created_at");
                String phone = rs.getString("phone");

                list.add(new Students(id_student,fk_class,full_name,birth_date,login,password,created_at, phone));
            }
            return list;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return list;
        }
    }

    public ResultSet readName(String name){
        
        Connection conn = null;
        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT * FROM students WHERE full_name LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,name);

            ResultSet rset = pstmt.executeQuery();

            return rset;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    public List<Students> readByTeach(int idTeacher, int idSubject) {
        
        Connection conn = null;
        ArrayList<Students> students = new ArrayList<>();
        try{
            conn = ConnectionFactory.connect();

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
                Date created_at = rs.getDate("created_at");
                String phone = rs.getString("phone");

                Students students1 = new Students(id_student,fk_class,full_name,birth_date,login,password,created_at, phone);
                students.add(students1);
            }
            return students;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return students;
        }
    }

    public Students readByLogin(String login) {
        
        Connection conn;
        try {
            conn = ConnectionFactory.connect();

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
                Date created_at = rs.getDate("created_at");
                String phone = rs.getString("phone");
                return new Students(id_student,fk_class,full_name,
                        birth_date,login,password,created_at, phone);
            }

            return null;

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    public BasicInfo readBasicInfoStudent(String email){
        
        Connection conn = null;
        try{
            conn = ConnectionFactory.connect();

            sql = "SELECT s.full_name,s.first_name, c.classroom, c.series, s" +
                    ".id_student, EXTRACT(YEAR FROM CURRENT_DATE) AS " +
                    "School_year FROM" +
                    " class c JOIN students s ON c.id_class = s.fk_class WHERE login LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,email);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                int id_student = rs.getInt("id_student");
                String classroom = rs.getString("classroom");
                String series = rs.getString("series");
                String full_name = rs.getString("full_name");
                String first_name = rs.getString("first_name");
                int school_year = rs.getInt("School_year");
                return new BasicInfo(id_student,full_name,first_name,
                        classroom,series,school_year);
            }
            return null;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            ConnectionFactory.disconnect();
        }

    }

    public List<Summary> readSummary() {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        Summary sum;
        List<Summary> summaries = new LinkedList<>();
        try {
            conn = connection.connect();

            sql = "SELECT S.created_at, S.phone, G.full_name AS \"guardian\", S.login ,S.id_student, S.full_name, C.series, C.classroom, avg(G.value) \"AVG\" FROM students S " +
                    "JOIN guardian G ON S.fk_guardian = G.id_guardian " +
                    "JOIN class C ON S.fk_class = C.id_class " +
                    "LEFT JOIN has H ON C.id_class = H.fk_class " +
                    "LEFT JOIN teach P on H.fk_teach = P.id " +
                    "LEFT JOIN subjects D ON P.fk_subject = D.id_subject " +
                    "LEFT JOIN grades G on S.id_student = G.fk_student AND D.id_subject = G.fk_subject " +
                    "GROUP BY 1, 2, 3, 4, 5, 6, D.id_subject";
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()) {
                sum = new Summary();
                sum.setMatricula(rs.getInt("id_student"));
                sum.setClassroom(rs.getString("classroom").charAt(0));
                sum.setSeries(Integer.parseInt(rs.getString("series")));
                sum.setName(rs.getString("full_name"));
                sum.setAverage(rs.getDouble("AVG"));
                sum.setSituation(rs.getDouble("AVG")>=7);
                sum.setEmail(rs.getString("login"));
                sum.setGuardian(rs.getString("guardian"));
                sum.setPhone(rs.getString("phone"));
                sum.setCreatedAt(rs.getDate("created_at"));
                summaries.add(sum);
            }

            return summaries;
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            connection.disconnect(conn);
        }
    }
    public Summary readSummary(int idStudent, int idSubject) {
        
        Connection conn = null;
        Summary sum = new Summary();
        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT S.created_at, S.phone, G.full_name AS \"guardian\", S.login ,S.id_student, S.full_name, C.series, C.classroom, avg(G.value) \"AVG\" FROM students S " +
                    "JOIN guardian G ON S.fk_guardian = G.id_guardian " +
                    "JOIN class C ON S.fk_class = C.id_class " +
                    "LEFT JOIN has H ON C.id_class = H.fk_class " +
                    "LEFT JOIN teach P on H.fk_teach = P.id " +
                    "LEFT JOIN subjects D ON P.fk_subject = D.id_subject " +
                    "LEFT JOIN grades G on S.id_student = G.fk_student AND D.id_subject = G.fk_subject " +
                    "WHERE S.id_student = ? AND D.id_subject = ? " +
                    "GROUP BY 1, 2, 3, 4, 5, 6, D.id_subject";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,idStudent);
            pstmt.setInt(2, idSubject);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                sum.setMatricula(rs.getInt("id_student"));
                sum.setClassroom(rs.getString("classroom").charAt(0));
                sum.setSeries(Integer.parseInt(rs.getString("series")));
                sum.setName(rs.getString("full_name"));
                sum.setAverage(rs.getDouble("AVG"));
                sum.setSituation(rs.getDouble("AVG")>=7);
                sum.setEmail(rs.getString("login"));
                sum.setGuardian(rs.getString("guardian"));
                sum.setPhone(rs.getString("phone"));
                sum.setCreatedAt(rs.getDate("created_at"));
                return sum;
            }

            return null;
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            ConnectionFactory.disconnect();
        }
    }

    public CompleteInfo readCompleteInfoStudent(int id_student){
        
        Connection conn = null;
        try{
            conn = ConnectionFactory.connect();

            sql = "SELECT s.full_name AS nameStudent,g" +
                    ".first_name AS nameGuardian,c.*,EXTRACT(YEAR FROM " +
                    "CURRENT_DATE) as school_year, CAST(s" +
                    ".created_at AS DATE) AS date_registration,CAST(s" +
                    ".birth_date AS DATE) AS birth_dateStudent,s.login AS " +
                    "login, s.phone AS phone FROM" +
                    " students s JOIN guardian g ON g.id_guardian = s" +
                    ".fk_guardian JOIN class c ON c.id_class = s.fk_class " +
                    " WHERE s" +
                    ".id_student = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_student);

            ResultSet rs = pstmt.executeQuery();
            CompleteInfo completeInfo = null;
            if(rs.next()){
                String full_name = rs.getString("nameStudent");
                String nameGuardian = rs.getString("nameGuardian");
                Class studentClass =
                        new Class(rs.getInt("id_class"),
                        rs.getString("series").charAt(0),rs.getString(
                                "classroom").charAt(0));
                Date created_at = rs.getDate("date_registration");
                Integer school_year = rs.getInt("school_year");
                Date birth_dateStudent = rs.getDate("birth_dateStudent");
                String login = rs.getString("login");
                String phone = rs.getString("phone");

                completeInfo = new CompleteInfo(full_name,id_student,
                        nameGuardian,studentClass,created_at,school_year,
                        birth_dateStudent,login,phone);
            }
            return completeInfo;
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            ConnectionFactory.disconnect();
        }
    }

    public Summary readSummary(int idStudent) {
        
        Connection conn = null;
        Summary sum = new Summary();
        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT S.id_student, S.full_name, C.series, C.classroom FROM students S " +
                    "JOIN class C ON S.fk_class = C.id_class " +
                    "WHERE S.id_student = ? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,idStudent);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                sum.setMatricula(rs.getInt("id_student"));
                sum.setClassroom(rs.getString("classroom").charAt(0));
                sum.setSeries(Integer.parseInt(rs.getString("series")));
                sum.setName(rs.getString("full_name"));
                return sum;
            }

            return null;
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            ConnectionFactory.disconnect();
        }
    }

    public Integer readAmountOfSubjects(int id_student){
        
        Connection conn = null;
        try{
            conn = ConnectionFactory.connect();

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
            ConnectionFactory.disconnect();
        }
    }

    public Double readAverageGrade(int id_student){
        
        Connection conn = null;
        try{
            conn = ConnectionFactory.connect();

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
            ConnectionFactory.disconnect();
        }
    }

    public Integer readAmountReports(int id_student){
        
        Connection conn = null;
        try{
            conn = ConnectionFactory.connect();

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
            ConnectionFactory.disconnect();
        }
    }

    public boolean isStudent(String login, String password) throws IllegalArgumentException {
        
        Connection conn = null;
        ResultSet rset = null;
        try {
            conn = ConnectionFactory.connect();

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
        }
    }
}

