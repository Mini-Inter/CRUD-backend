package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.PreRegistration.PreRegistration;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PreRegistrationDAO {

    String sql;
    public int insert(PreRegistration preReg){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();

            sql = "INSERT INTO pre_registration" +
                    "(cpf)" +
                    "VALUES(?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,preReg.getCpf());

            if (pstmt.executeUpdate()>0) {
                return 1;
            } else {
                return 0;
            }
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return -1;
        } finally {
            connection.disconnect(conn);
        }
    }

    public int insertIdStudentOnCpf(Integer idStudent, int idCpf){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try{
            conn = connection.connect();

            sql = "UPDATE preRegistration SET fk_student = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,idStudent);
            pstmt.setInt(2,idCpf);

            return pstmt.executeUpdate();
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }finally {
            connection.disconnect(conn);
        }
    }

    public List<PreRegistration> readAllAvailableCpf(){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        List<PreRegistration> list = new LinkedList<>();
        PreRegistration pre = null;
        try{
            conn = connection.connect();

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
        }finally {
            connection.disconnect(conn);
        }
    }
    public PreRegistration read(String cpf) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset;

        try {
            conn = connection.connect();

            sql = "SELECT * FROM preRegistration WHERE cpf Like ? AND " +
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
        } finally {
            connection.disconnect(conn);
        }
    }

    public boolean updateFkStudentById(PreRegistration preReg){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();
            sql = "UPDATE pre_registration SET fk_student = ? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,preReg.getFk_student());
            pstmt.setInt(2, preReg.getId());

            return pstmt.executeUpdate() >0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            connection.disconnect(conn);
        }
    }

    public int update(PreRegistration preReg) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();
            sql = "UPDATE pre_registration SET cpf=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, preReg.getCpf());
            pstmt.setInt(2, preReg.getId());

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

            sql = "DELETE FROM pre_registration WHERE id=?";
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
    public int delete(String cpf) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();

            sql = "DELETE FROM pre_registration WHERE cpf=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, cpf);

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
