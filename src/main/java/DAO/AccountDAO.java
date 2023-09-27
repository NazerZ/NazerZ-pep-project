package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.h2.command.Prepared;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    /*
     * 
     * Requirements:
     *      register
     *      login
     * 
     * 
     * //
     */
    /* 
     public Account getLogin(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account temp = new Account(rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("password"));
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    */
    public Account getLogin(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "select * from account where username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,account.getUsername());
            ps.setString(2,account.getPassword());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account temp = new Account(rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("password"));
                return temp;
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        
        return null;

    }
    public Account addUser(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "insert into account (account_id,username,password) values(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,account.getAccount_id());
            ps.setString(2,account.getUsername());
            ps.setString(3,account.getPassword());
            ps.executeUpdate();
            return account;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public boolean isUsernameAvailable(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "select * from account where username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,account.getUsername());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                return false;
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    
    }
}

