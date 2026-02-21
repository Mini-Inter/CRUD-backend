package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Class.Class;
import com.school.miniinter.models.Grades.GradeForSubject;
import com.school.miniinter.models.Grades.Grades;
import com.school.miniinter.models.Students.GradeForStudent;
import com.school.miniinter.models.Teacher.HomeTeacherInfo;
import com.school.miniinter.models.Teacher.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TeachersDAO {

    String sql;
    public int insert(Teacher teacher){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();
            sql = "INSERT INTO teachers" +
                    "(last_name, first_name, full_name, birth_date, login, password, created_at)" +
                    "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,teacher.getLastName());
            pstmt.setString(2,teacher.getFirstName());
            pstmt.setString(3,teacher.getFirstName() + " " + teacher.getLastName());
            pstmt.setDate(4,teacher.getBirthDate());
            pstmt.setString(5,teacher.getLogin());
            pstmt.setString(6,teacher.getPassword());
            pstmt.setDate(7,teacher.getCreatedAt());

            if (pstmt.executeUpdate()>0) {
                return 1;
            } else {
                return 0;
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }finally {
            connection.disconnect(conn);
        }
    }

    public Teacher read(int id) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = connection.connect();

            sql = "SELECT * FROM teachers WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            rset = pstmt.executeQuery();
            if (rset.next()) {
                return new Teacher(
                        rset.getInt("id"),
                        rset.getString("first_name"),
                        rset.getString("last_name"),
                        rset.getString("login"),
                        rset.getString("password"),
                        rset.getDate("birth_date"),
                        rset.getDate("created_at")
                );
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
        } finally {
            connection.disconnect(conn);
        }
        return null;
    }

    public List<Teacher> read(String name) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset = null;
        List<Teacher> teachers = new LinkedList<>();

        try {
            conn = connection.connect();

            sql = "SELECT * FROM teachers WHERE name=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);

            rset = pstmt.executeQuery();
            while (rset.next()) {
                teachers.add(new Teacher(
                        rset.getInt("id"),
                        rset.getString("first_name"),
                        rset.getString("last_name"),
                        rset.getString("login"),
                        rset.getString("password"),
                        rset.getDate("birth_date"),
                        rset.getDate("created_at")
                ));
            }

            return teachers;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            connection.disconnect(conn);
        }
    }

    public HomeTeacherInfo readHomeInfoTeacher(int id_teacher){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rs;
        PreparedStatement pstmt;
        HomeTeacherInfo homeTeacherInfo = new HomeTeacherInfo();
        try {
            conn = connection.connect();

//            Read full_name
            sql = "SELECT full_name FROM teachers WHERE id_employee = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);

            rs = pstmt.executeQuery();
            if(rs.next()){
                homeTeacherInfo.setFull_name(rs.getString("full_name"));
            }

//            Read teacher's subject
            sql = "SELECT sub.name AS subject FROM subjects sub JOIN teach t " +
                    "ON sub.id_subject = t.fk_subject JOIN teachers ON teachers.id_employee = t.fk_teacher WHERE id_employee = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);

            List<String> list = new ArrayList<>();
            rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getString("subject"));
            }
            homeTeacherInfo.setSubjects(list);

//            Amount Students
            sql = "SELECT count(DISTINCT s.*) AS amountStudents FROM students" +
                    " s JOIN class c ON s.fk_class = c.id_class JOIN has h ON h.fk_class = c.id_class JOIN teach t ON t.id = h.fk_teach JOIN  teachers te ON te.id_employee = t.fk_teacher WHERE id_employee = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);

            rs = pstmt.executeQuery();
            if(rs.next()){
                homeTeacherInfo.setAmountStudents(rs.getInt("amountStudents"));
            }

//            Amount Class
            sql = "SELECT count(DISTINCT h.fk_class) AS amountClass FROM " +
                    "teachers te JOIN " +
                    "teach t ON te.id_employee = t.fk_teacher JOIN has h ON t.id = h.fk_teach WHERE id_employee = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_teacher);

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
        } finally {
            connection.disconnect(conn);
        }
    }

    public Teacher readByLogin(String login) {
            ConnectionFactory connection = new ConnectionFactory();
            Connection conn = null;
            ResultSet rset = null;

            try {
                conn = connection.connect();

                sql = "SELECT * FROM teachers WHERE login=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1,login);

                rset = pstmt.executeQuery();
                if (rset.next()) {
                    return new Teacher(
                            rset.getInt("id_employee"),
                            rset.getString("first_name"),
                            rset.getString("last_name"),
                            login,
                            rset.getString("password"),
                            rset.getDate("birth_date"),
                            rset.getDate("created_at")
                    );
                }
            } catch(SQLException sqle){
                sqle.printStackTrace();
            } finally {
                connection.disconnect(conn);
            }
            return null;
    }

    public List<GradeForStudent> readGradeStudentBySubjectAndClass(int id_teacher, int id_subject, int id_class){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<GradeForStudent> list = new ArrayList<>();
        try {
            conn = connection.connect();

            sql = "SELECT DISTINCT S.full_name, S.id_student, D.name, g.grade_type, G.value, G.id_grade FROM students S " +
                    "JOIN class C ON S.fk_class = C.id_class " +
                    "JOIN has H ON C.id_class = H.fk_class " +
                    "JOIN teach P ON H.fk_teach = P.id " +
                    "JOIN teachers T ON P.fk_teacher = T.id_employee " +
                    "JOIN grades G ON S.id_student = G.fk_student " +
                    "JOIN subjects D ON P.fk_subject = D.id_subject AND G.fk_subject = D.id_subject " +
                    "WHERE EXTRACT(YEAR FROM g.send_at) = EXTRACT(YEAR FROM CURRENT_DATE) AND T.id_employee=? AND D.id_subject = ? AND C.id_class = ?";
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
                    }
                    list.add(gradeForStudent);
                }
            }
            return list;
        } catch(SQLException sqle){
            sqle.printStackTrace();
        } finally {
            connection.disconnect(conn);
        }
        return null;
    }

    public List<GradeForSubject> readGradesByStudent(int id_student) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<GradeForSubject> grades = new LinkedList<>();
        try {
            conn = connection.connect();

            sql = "SELECT S.full_name ,D.name, G.grade_type, G.value FROM students S " +
                    "JOIN class C ON S.fk_class = C.id_class " +
                    "JOIN has H ON C.id_class = H.fk_class " +
                    "JOIN teach P ON H.fk_teach = P.id " +
                    "JOIN teachers T ON P.fk_teacher = T.id_employee " +
                    "JOIN grades G ON S.id_student = G.fk_student " +
                    "RIGHT JOIN subjects D ON P.fk_subject = D.id_subject AND G.fk_subject = D.id_subject " +
                    "WHERE EXTRACT(YEAR FROM g.send_at) = EXTRACT(YEAR FROM CURRENT_DATE) AND S.id_student = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_student);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                GradeForSubject grade = new GradeForSubject();
                boolean existe = false;

                if (!grades.isEmpty()) {
                    for (GradeForSubject grade1 : grades) {
                        if (rs.getString("name").equals(grade1.getSubject())) {
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
        } finally {
            connection.disconnect(conn);
        }
        return null;
    }

    public boolean isTeacher(String login, String pw) throws IllegalArgumentException {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset = null;

        try {
            conn = connection.connect();

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
        } finally {
            connection.disconnect(conn);
        }
    }

    public int update(Teacher teacher) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();

            sql = "UPDATE teachers SET first_name=?, last_name=?, full_name=?, birth_date=?, login=?, password=?, created_at=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, teacher.getFirstName());
            pstmt.setString(2, teacher.getLastName());
            pstmt.setString(3, teacher.getFirstName() + " " + teacher.getLastName());
            pstmt.setDate(4, teacher.getBirthDate());
            pstmt.setString(5, teacher.getLogin());
            pstmt.setString(6, teacher.getPassword());
            pstmt.setDate(7, teacher.getCreatedAt());
            pstmt.setInt(8, teacher.getId());

            if (pstmt.executeUpdate() == 0) {
                return 0;
            } else {
                return 1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            connection.disconnect(conn);
        }
    }

    public int delete(int id) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();
            sql = "DELETE FROM teachers WHERE id=?";
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
        } finally {
            connection.disconnect(conn);
        }
    }
}
