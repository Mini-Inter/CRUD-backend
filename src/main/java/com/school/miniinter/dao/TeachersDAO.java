package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Class.Class;
import com.school.miniinter.models.Grades.GradeForSubject;
import com.school.miniinter.models.Teacher.AmountStudentByTeacher;
import com.school.miniinter.models.Teacher.CompleteInfo;
import com.school.miniinter.models.Students.GradeForStudent;
import com.school.miniinter.models.Teacher.HomeTeacherInfo;
import com.school.miniinter.models.Teacher.Teacher;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

public class TeachersDAO {

    String sql;
    DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("pt","BR"));

    public boolean insert(Teacher teacher){
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();
            sql = "INSERT INTO teachers" +
                    "(last_name, first_name, full_name, birth_date, login," +
                    "phone, password)" +
                    "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,teacher.getLastName());
            pstmt.setString(2,teacher.getFirstName());
            pstmt.setString(3,teacher.getName());
            java.util.Date utilDate = format.parse(teacher.getBirthDate());
            pstmt.setDate(4, new Date(utilDate.getTime()));
            pstmt.setString(5,teacher.getLogin());
            pstmt.setString(6,teacher.getPhone());
            pstmt.setString(7,teacher.getPassword());

            return pstmt.execute();
        }catch(SQLException | ParseException sqle){
            sqle.printStackTrace();
            return false;
        }
    }


    public List<Teacher> read() {
        
        Connection conn = null;
        ResultSet rset = null;
        List<Teacher> teachers = new LinkedList<>();

        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT * FROM teachers";
            Statement pstmt = conn.createStatement();

            rset = pstmt.executeQuery(sql);

            while (rset.next()) {
                teachers.add(new Teacher(
                        rset.getInt("id_employee"),
                        rset.getString("full_name"),
                        rset.getString("login"),
                        rset.getString("phone"),
                        rset.getString("password"),
                        rset.getDate("birth_date"),
                        rset.getDate("created_at")
                ));
            }

            return teachers;
        } catch(SQLException sqle){
            sqle.printStackTrace();
        } 
        return null;
    }

    public Teacher read(int id) {
        
        Connection conn = null;
        ResultSet rset;

        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT * FROM teachers WHERE id_employee=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            rset = pstmt.executeQuery();
            if (rset.next()) {
                return new Teacher(
                        rset.getInt("id_employee"),
                        rset.getString("full_name"),
                        rset.getString("login"),
                        rset.getString("phone"),
                        rset.getString("password"),
                        rset.getDate("birth_date"),
                        rset.getDate("created_at")
                );
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
        } 
        return null;
    }

    public List<Teacher> read(String name) {
        
        Connection conn = null;
        ResultSet rset;
        List<Teacher> teachers = new LinkedList<>();

        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT * FROM teachers WHERE full_name=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);

            rset = pstmt.executeQuery();
            while (rset.next()) {
                teachers.add(new Teacher(
                        rset.getInt("id"),
                        rset.getString("full_name"),
                        rset.getString("login"),
                        rset.getString("phone"),
                        rset.getString("password"),
                        rset.getDate("birth_date"),
                        rset.getDate("created_at")
                ));
            }

            return teachers;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        } 
    }

    public HomeTeacherInfo readHomeInfoTeacher(int id_teacher, int id_subject){
        
        Connection conn = null;
        ResultSet rs;
        PreparedStatement pstmt;
        HomeTeacherInfo homeTeacherInfo = new HomeTeacherInfo();
        try {
            conn = ConnectionFactory.connect();

//            Read full_name
            sql = "SELECT full_name, first_name FROM teachers WHERE " +
                    "id_employee = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);

            rs = pstmt.executeQuery();
            if(rs.next()){
                homeTeacherInfo.setFull_name(rs.getString("full_name"));
                homeTeacherInfo.setFirst_name(rs.getString("first_name"));
            }

//            Amount Students
            sql = "SELECT count(DISTINCT s.*) AS amountStudents FROM students s JOIN class c ON s.fk_class = c.id_class JOIN teachingAssignment t ON t.fk_class = c.id_class JOIN teachers te ON te.id_employee = t.fk_teacher JOIN subjects sub ON sub.id_subject = t.fk_subject WHERE te.id_employee = ? AND sub.id_subject = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);
            pstmt.setInt(2,id_subject);

            rs = pstmt.executeQuery();
            if(rs.next()){
                homeTeacherInfo.setAmountStudents(rs.getInt("amountStudents"));
            }

//            Amount Class
            sql = "SELECT count(DISTINCT c.id_class) AS amountClass FROM " +
                    "teachers te JOIN teachingAssignment t ON te.id_employee " +
                    "= t.fk_teacher JOIN class c ON  c.id_class = t.fk_class " +
                    "JOIN subjects sub ON sub.id_subject = t.fk_subject WHERE te.id_employee = ? AND sub.id_subject = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);
            pstmt.setInt(2,id_subject);

            rs = pstmt.executeQuery();
            if(rs.next()){
                homeTeacherInfo.setAmountClass(rs.getInt("amountClass"));
            }

//            Amount Observations
            sql = "SELECT count(fk_teacher) AS amountObservations FROM " +
                    "reports WHERE fk_teacher = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);

            rs = pstmt.executeQuery();
            if(rs.next()){
                homeTeacherInfo.setAmountObservations(rs.getInt("amountObservations"));
            }

            return homeTeacherInfo;

        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        } 
    }

    public Teacher readByLogin(String login) {
            
            Connection conn = null;
            ResultSet rset = null;

            try {
                conn = ConnectionFactory.connect();

                sql = "SELECT * FROM teachers WHERE login=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1,login);

                rset = pstmt.executeQuery();
                if (rset.next()) {
                    return new Teacher(
                            rset.getInt("id_employee"),
                            rset.getString("full_name"),
                            login,
                            rset.getString("phone"),
                            rset.getString("password"),
                            rset.getDate("birth_date"),
                            rset.getDate("created_at")
                    );
                }
            } catch(SQLException sqle){
                sqle.printStackTrace();
            }
            return null;
    }

    public List<GradeForStudent> readGradeStudentBySubjectAndClass(int id_teacher, int id_subject, int id_class){
        
        Connection conn = null;
        List<GradeForStudent> list = new ArrayList<>();
        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT S.full_name, S.id_student, T.id_employee, D.id_subject, C.id_class, D.name, g.grade_type, G.value, G.id_grade FROM students S\n" +
                    "                    LEFT JOIN class C ON S.fk_class = C.id_class " +
                    "                    LEFT JOIN teachingassignment H ON C.id_class = H.fk_class  " +
                    "                    LEFT JOIN subjects D ON H.fk_subject = D.id_subject " +
                    "                    JOIN teachers T ON H.fk_teacher = T.id_employee " +
                    "                    LEFT JOIN grades G on S.id_student = G.fk_student AND D.id_subject = G.fk_subject " +
                    "                    WHERE T.id_employee = ? " +
                    "                    AND D.id_subject = ? AND C.id_class = ? " +
                    "                    GROUP BY 1, 2, 3, 4, 5, 6, 7, 8, 9";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);
            pstmt.setInt(2,id_subject);
            pstmt.setInt(3,id_class);

            ResultSet rs = pstmt.executeQuery();
            GradeForStudent gradeForStudent;

            while (rs.next()) {
                gradeForStudent = new GradeForStudent();
                boolean existe = false;
                if (!list.isEmpty()) {
                    for (GradeForStudent student : list) {
                        if (rs.getInt("id_student") == (student.getId_student())) {
                            existe = true;
                            if (student.getN1() == -1.0) {
                                student.setN1(rs.getDouble("value"));
                                student.setIdN1(rs.getInt("id_grade"));
                                student.setAverage();
                            } else {
                                student.setN2(rs.getDouble("value"));
                                student.setIdN2(rs.getInt("id_grade"));
                                student.setAverage();
                            }
                        }
                    }
                }

                if (!existe) {
                    int type = rs.getInt("grade_type");
                    gradeForStudent.setFull_name(rs.getString("full_name"));
                    gradeForStudent.setId_student(rs.getInt("id_student"));
                    if (type == 1) {
                        gradeForStudent.setN1(rs.getDouble("value"));
                        gradeForStudent.setIdN1(rs.getInt("id_grade"));
                        gradeForStudent.setN2(-1.0);
                        gradeForStudent.setIdN2(-1);
                    } else if (type == 2) {
                        gradeForStudent.setN1(-1.0);
                        gradeForStudent.setIdN1(-1);
                        gradeForStudent.setN2(rs.getDouble("value"));
                        gradeForStudent.setIdN2(rs.getInt("id_grade"));
                    } else {
                        gradeForStudent.setN1(-1.0);
                        gradeForStudent.setN2(-1.0);
                    }
                    list.add(gradeForStudent);
                }
            }
            return list;
        } catch(SQLException sqle){
            sqle.printStackTrace();
        } 
        return null;
    }

    public List<GradeForSubject> readGradesByStudent(int id_student) {
        
        Connection conn = null;
        List<GradeForSubject> grades = new LinkedList<>();
        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT\n" +
                    "    S.full_name,\n" +
                    "    D.name AS subject,\n" +
                    "    G.grade_type,\n" +
                    "    G.value\n" +
                    "FROM students S\n" +
                    "         JOIN class C\n" +
                    "              ON S.fk_class = C.id_class\n" +
                    "         JOIN teachingAssignment P\n" +
                    "              ON C.id_class = P.fk_class\n" +
                    "         JOIN teachers T\n" +
                    "              ON P.fk_teacher = T.id_employee\n" +
                    "         JOIN subjects D\n" +
                    "              ON P.fk_subject = D.id_subject\n" +
                    "         LEFT JOIN grades G\n" +
                    "                   ON S.id_student = G.fk_student\n" +
                    "                       AND G.fk_subject = D.id_subject\n" +
                    "                       AND EXTRACT(YEAR FROM G.send_at) = EXTRACT(YEAR FROM CURRENT_DATE)\n" +
                    "WHERE S.id_student = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_student);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                GradeForSubject grade = new GradeForSubject();
                boolean existe = false;

                if (!grades.isEmpty()) {
                    for (GradeForSubject grade1 : grades) {
                        if (rs.getString("subject").equals(grade1.getSubject())) {
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
                    grade.setSubject(rs.getString("subject"));
                    if (type == 1) {
                        grade.setN1(rs.getDouble("value"));
                        grade.setN2();
                    } else if (type == 2) {
                        grade.setN1();
                        grade.setN2(rs.getDouble("value"));
                    } else {
                        grade.setN1();
                        grade.setN2();
                    }
                    grade.setAverage();
                    grade.setSituation();
                    grades.add(grade);
                }
            }

            return grades;
        } catch(SQLException sqle){
            sqle.printStackTrace();
        } 
        return null;
    }

    public CompleteInfo readCompleteInfoTeacher(int id_teacher){
        
        Connection conn = null;

        try{
            conn = ConnectionFactory.connect();

            sql = "SELECT te.full_name,te.created_at AS date_admission, " +
                    "EXTRACT(YEAR FROM CURRENT_DATE) as school_year,te" +
                    ".birth_date,te.login,te.phone,a.formated_address, te" +
                    ".profile_image_url FROM teachers te " +
                    "JOIN address a ON te.id_address_teacher = a.id_address " +
                    "WHERE te.id_employee = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);

            ResultSet rs = pstmt.executeQuery();
            CompleteInfo completeInfo = null;
            if(rs.next()){
                String full_name = rs.getString("full_name");
                Date date_admission = rs.getDate("date_admission");
                Integer school_year = rs.getInt("school_year");
                Date birth_date = rs.getDate("birth_date");
                String login = rs.getString("login");
                String phone = rs.getString("phone");
                String formated_address = rs.getString("formated_address");
                String imgUrl =rs.getString("profile_image_url");

                completeInfo = new CompleteInfo(date_admission,full_name,
                        school_year,"Ativo",birth_date,login,phone,
                        formated_address,imgUrl);
            }
            return completeInfo;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    public List<AmountStudentByTeacher> amountStudentByTeacherAndClass(int id_teacher, int id_class){
        
        Connection conn = null;
        ResultSet rs;
        List<AmountStudentByTeacher> list = new ArrayList<>();
        try{
            conn = ConnectionFactory.connect();

            sql = "SELECT c.*, count(s.id_student) AS qtd_students FROM " +
                    "students s JOIN class c on s.fk_class = c.id_class JOIN " +
                    "teachingAssignment t on t.fk_class = c.id_class JOIN " +
                    "teachers te on t.fk_teacher = te.id_employee WHERE te" +
                    ".id_employee = ? AND c.id_class =? GROUP BY 1 ";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);
            pstmt.setInt(2,id_class);

            rs = pstmt.executeQuery();
            AmountStudentByTeacher amountStuentByTeacher = null;
            if(rs.next()){
                Integer academic_year = LocalDate.now().getYear();
                Class teacherClass = new Class(rs.getInt("id_class"),
                        rs.getString("series").charAt(0), rs.getString(
                                "classroom").charAt(0),academic_year);
                Integer qtd_students = rs.getInt("qtd_students");
                amountStuentByTeacher =
                        new AmountStudentByTeacher(teacherClass,qtd_students);
                list.add(amountStuentByTeacher);
            }
            return list;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    public boolean isTeacher(String login, String pw) throws IllegalArgumentException {
        
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT * FROM teachers WHERE login=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, login);

            rset = pstmt.executeQuery();

            if (rset.next()) {
                if (rset.getString("password").equals(pw)) {
                    return true;
                } throw new RuntimeException("Senha incorreta!");
            } else {
                return false;
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        } 
    }

    public boolean update(Teacher teacher) {
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();

            sql = "UPDATE teachers SET first_name=?, last_name=?, full_name=?, birth_date=?, login=?, password=?, created_at=?, phone = ? WHERE id_employee=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, teacher.getFirstName());
            pstmt.setString(2, teacher.getLastName());
            pstmt.setString(3, teacher.getFirstName() + " " + teacher.getLastName());
            java.util.Date utilDate = format.parse(teacher.getBirthDate());
            pstmt.setDate(4, new Date(utilDate.getTime()));
            pstmt.setString(5, teacher.getLogin());
            pstmt.setString(6, teacher.getPassword());
            utilDate = format.parse(teacher.getCreatedAt());
            pstmt.setDate(7, new Date(utilDate.getTime()));
            pstmt.setString(8, teacher.getPhone());
            pstmt.setInt(9, teacher.getId());

            return  pstmt.executeUpdate() > 0;
        } catch (SQLException | ParseException sqle) {
            sqle.printStackTrace();
            return false;
        } 
    }

    public boolean updateImage(String url, int id_teacher){
        Connection conn = null;
        try{
            conn = ConnectionFactory.connect();
            sql = "UPDATE teachers SET profile_image_url = ? WHERE " +
                    "id_employee = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,url);
            pstmt.setInt(2,id_teacher);

            return pstmt.executeUpdate()>0;

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public int delete(int id) {
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();
            sql = "DELETE FROM teachers WHERE id_employee=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            if (pstmt.executeUpdate() == 0) {
                return 0;
            } else {
                return 1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } 
    }
}
