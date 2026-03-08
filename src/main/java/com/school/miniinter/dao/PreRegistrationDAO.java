package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.PreRegistration.PreRegistration;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PreRegistrationDAO {

    String sql;
    public boolean insert(PreRegistration preReg){
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();

            sql = "INSERT INTO pre_registration" +
                    "(cpf)" +
                    "VALUES(?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,preReg.getCpf());

            return pstmt.executeUpdate() > 0;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        } 
    }

    public int insertIdStudentOnCpf(Integer idStudent, int idCpf){
        
        Connection conn = null;

        try{
            conn = ConnectionFactory.connect();

            sql = "UPDATE preRegistration SET fk_student = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,idStudent);
            pstmt.setInt(2,idCpf);

            return pstmt.executeUpdate();
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }
    }

    public List<PreRegistration> readAllAvailableCpf(){
        
        Connection conn = null;
        List<PreRegistration> list = new LinkedList<>();
        PreRegistration pre = null;
        try{
            conn = ConnectionFactory.connect();

            sql = "SELECT * FROM preRegistration WHERE fk_student is null";
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                pre = new PreRegistration(rs.getInt("id"),rs.getString("cpf"));
                list.add(pre);
            }
            return list;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return list;
        }
    }
    public PreRegistration read(String cpf) {
        
        Connection conn = null;
        ResultSet rset;

        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT * FROM preRegistration WHERE cpf = ? AND " +
                    "fk_student is null ";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, cpf);

            rset = pstmt.executeQuery();
            if (rset.next()) {
                return new PreRegistration(
                        rset.getInt("id"),
                        rset.getString("cpf")
                );
            }

            return null;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        } 
    }

    public PreRegistration readById(int id) {

        Connection conn = null;
        ResultSet rset;

        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT p.*,s.full_name AS full_name FROM preRegistration p" +
                    " LEFT JOIN students s ON " +
                    "p.fk_student = s.id_student WHERE p.id = ? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            rset = pstmt.executeQuery();
            if (rset.next()) {
                return new PreRegistration(
                        rset.getInt("id"),
                        rset.getInt("fk_student"),
                        rset.getString("cpf"),
                        rset.getString("full_name")
                );
            }

            return null;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    public List<PreRegistration> read(){
        Connection conn = null;
        ResultSet rset;
        List<PreRegistration> list = new ArrayList<>();
        try {
            conn = ConnectionFactory.connect();

            sql = "SELECT p.*, s.full_name AS full_name FROM preRegistration " +
                    "p LEFT JOIN " +
                    "students s ON p" +
                    ".fk_student = s.id_student";
            Statement stmt = conn.createStatement();

            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                int id = rset.getInt("id");
                String cpf = rset.getString("cpf");
                String name_student = rset.getString("full_name");
                int fk_student = rset.getInt("fk_student");
                PreRegistration preRegistration =
                        new PreRegistration(id,fk_student,cpf,name_student);
                list.add(preRegistration);
            }

            return list;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    public boolean updateFkStudentById(PreRegistration preReg){
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();
            sql = "UPDATE pre_registration SET fk_student = ? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,preReg.getFk_student());
            pstmt.setInt(2, preReg.getId());

            return pstmt.executeUpdate() >0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } 
    }

    public boolean update(PreRegistration preReg) {
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();
            sql = "UPDATE pre_registration SET cpf=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, preReg.getCpf());
            pstmt.setInt(2, preReg.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } 
    }
    public boolean delete(int id) {
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();

            sql = "DELETE FROM pre_registration WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } 
    }
    public boolean delete(String cpf) {
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();

            sql = "DELETE FROM pre_registration WHERE cpf=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, cpf);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } 
    }
}
