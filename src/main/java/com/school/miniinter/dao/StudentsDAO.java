package com.school.miniinter.dao;

import com.school.miniinter.connection.ConnectionFactory;
import com.school.miniinter.models.Students.Students;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentsDAO {

//    Métodos
//
//- Deletar por id;
//- Criar um;
//- Atualizar um por id;
//- Ler um por id;
//- Buscar por nome.

    public int delete(int id){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;

        try{
            conn = connection.connect();

            String sql = "DELETE FROM students WHERE id=?";
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
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " +
                    "students(fk_class,full_name,first_name,last_name," +
                    "birth_date,login,password,created_at) VALUES(?,?,?,?,?," +
                    "?,?,?)");

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
//            Precisa de mais algumas informações do que será mudado
            PreparedStatement pstmt = conn.prepareStatement("UPDATE students " +
                    "SET   WHERE id=?");

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
    public ResultSet readId(int id){
        ConnectionFactory connection = new ConnectionFactory();
        Connection conn = null;
        ResultSet rset = null;
        try {
            conn = connection.connect();

            String sql = "SELECT * FROM students WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            rset = pstmt.executeQuery();

            return rset;
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

            String sql = "SELECT * FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){

                int fk_class = rs.getInt("fk_class");
                String full_name = rs.getString("full_name");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                Date birth_date = rs.getDate("birth_date");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String created_at = rs.getString("created_at");

                list.add(new Students(fk_class,full_name,first_name,
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
        ResultSet rset = null;
        try {
            conn = connection.connect();

            String sql = "SELECT * FROM students WHERE full_name LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,name);

            rset = pstmt.executeQuery();

            return rset;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally {
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
