package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Guardian.Guardian;

import java.sql.*;
import java.text.DateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class GuardiansDAO {
    DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("en","US"));

    public List<Guardian> read() {
        
        Connection conn = null;
        List<Guardian> guardians = new LinkedList<>();

        try {
            conn = ConnectionFactory.connect();
            String sql = "SELECT * FROM guardian";
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                guardians.add( new Guardian(
                    rset.getInt("id_guardian"),
                    rset.getString("full_name"),
                    rset.getDate("birth_date")
                    )
                );
            }

            return guardians;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return null;
        } 

    }

    public boolean insert(Guardian g){
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();
            String sql = "INSERT INTO guardian(full_name,first_name," +
                    "last_name,birth_date) VALUES(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,g.getName());
            pstmt.setString(2,g.getFirstName());
            pstmt.setString(3,g.getLastName());
            pstmt.setDate(4,Date.valueOf(format.format(g.getBirthDate())));

            return pstmt.execute();
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } 
    }
    public Guardian read(int idGuardian) {
        
        Connection conn = null;
        List<Guardian> guardians = new LinkedList<>();

        try {
            conn = ConnectionFactory.connect();
            String sql = "SELECT * FROM guardian WHERE id_guardian = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, idGuardian);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                return new Guardian(
                    rset.getInt("id_guardian"),
                    rset.getString("full_name"),
                    rset.getDate("birth_date")
                );
            }

            return null;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return null;
        } 
    }
    public boolean update(Guardian guardian) {
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();
            String sql = "UPDATE guardian " +
                    "SET full_name = ?, first_name = ?, last_name = ?, birth_date = ? " +
                    "WHERE id_guardian = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, guardian.getName());
            pstmt.setString(2, guardian.getFirstName());
            pstmt.setString(3, guardian.getLastName());
            pstmt.setDate(4,
                    Date.valueOf(format.format(guardian.getBirthDate())));
            pstmt.setInt(5, guardian.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } 
    }
    public boolean delete(int idGuardian) {
        
        Connection conn = null;

        try {
            conn = ConnectionFactory.connect();
            String sql = "DELETE FROM guardian WHERE id_guardian = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, idGuardian);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        } 
    }
}
