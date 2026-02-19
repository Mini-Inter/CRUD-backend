package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Teach.Teach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeachDAO {

//  Métodos
//
// - Criar;
// - Buscar;
//   - Por ID
//   - Por nome
// - Atualizar;
//   - Por ID
// - Deletar;
//   - Por ID

    public int insertRelation(Teach professoria){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();

            String sql = "INSERT INTO teach" +
                    "(fk_subject, fk_teacher)" +
                    "VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, professoria.getSubject().getId());
            pstmt.setInt(2, professoria.getTeacher().getId());

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
    public int deleteRelation(Teach professoria) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();

            String sql = "DELETE FROM teach WHERE fk_subject=? AND fk_teacher=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, professoria.getSubject().getId());
            pstmt.setInt(2, professoria.getTeacher().getId());

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
