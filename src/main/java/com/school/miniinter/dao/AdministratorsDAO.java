package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Administrator.Administrator;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class AdministratorsDAO {

    DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("pt","BR"));

    public int insert(Administrator admin){
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();

            String sql = "INSERT INTO administrators" +
                    "(name, birth_date, type, login, password)" +
                    "VALUES(?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,admin.getName());
            java.util.Date utilDate = format.parse(admin.getBirthDate());
            pstmt.setDate(2,new Date(utilDate.getTime()));
            pstmt.setString(3,admin.getType());
            pstmt.setString(4,admin.getLogin());
            pstmt.setString(5,admin.getPassword());


            if (pstmt.executeUpdate()>0) {
                return 1;
            } else {
                return 0;
            }
        }catch(SQLException | ParseException sqle){
            sqle.printStackTrace();
            return -1;
        }
    }

    public Administrator read(int id) {
        Connection conn = null;
        ResultSet rset = null;
        Administrator admin;

        try {
            conn = ConnectionFactory.connect();

            String sql = "SELECT * FROM administrators WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            rset = pstmt.executeQuery();
            if (rset.next()) {
                return new Administrator(
                        rset.getInt("id"),
                        rset.getString("name"),
                        rset.getDate("birth_date"),
                        rset.getString("type"),
                        rset.getString("login"),
                        rset.getString("password")
                );
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
        } 
        return null;
    }
    public List<Administrator> read(String name) {
        Connection conn = null;
        ResultSet rset = null;
        List<Administrator> admins = new LinkedList<>();

        try {
            conn = ConnectionFactory.connect();

            String sql = "SELECT * FROM administrators WHERE name=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);

            rset = pstmt.executeQuery();
            while (rset.next()) {
                admins.add(new Administrator(
                        rset.getInt("id"),
                        rset.getString("name"),
                        rset.getDate("birth_date"),
                        rset.getString("type"),
                        rset.getString("login"),
                        rset.getString("password")
                ));
            }

            return admins;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        } 
    }
    public boolean isAdmin(String login, String pw) throws IllegalArgumentException {
        Connection conn = null;
        ResultSet rset = null;
        try {
            conn = ConnectionFactory.connect();

            String sql = "SELECT * FROM administrators WHERE login=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,login);

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
    public int update(Administrator admin) {
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();

            String sql = "UPDATE administrators SET name=?, birth_date=?, type=?, login=?, password=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, admin.getName());
            pstmt.setDate(2, Date.valueOf(format.format(admin.getBirthDate())));
            pstmt.setString(3, admin.getType());
            pstmt.setString(4, admin.getLogin());
            pstmt.setString(5, admin.getPassword());
            pstmt.setInt(6, admin.getId());

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
    public int delete(int id) {
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();

            String sql = "DELETE FROM administrators WHERE id=?";
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
