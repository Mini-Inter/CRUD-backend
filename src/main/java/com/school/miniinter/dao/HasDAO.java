package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Has;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HasDAO {

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

    public int insertRelation(Has has) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();

            String sql = "INSERT INTO has" +
                    "(fk_class, fk_teach)" +
                    "VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, has.getClassroom().getId());
            pstmt.setInt(2, has.getProfessoria().getId());

            if (pstmt.executeUpdate() > 0) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            connection.disconnect(conn);
        }
    }
    public int deleteRelation(int classID, int teachID) {
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try {
            conn = connection.connect();

            String sql = "DELETE FROM has WHERE fk_class=? AND fk_teach=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, classID);
            pstmt.setInt(2, teachID);

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
