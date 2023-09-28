package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    /*
     * requirements: 
     *      create message
     *      Get all messages 
     *      get message by id
     *      delete message by message id
     *      update message by message id
     *      get message by user id 
    */
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "select * from message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message temp = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
            messages.add(temp);
            }
            return messages;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Message addMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "insert into message(posted_by,message_text,time_posted_epoch) values(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,message.getPosted_by());
            ps.setString(2,message.getMessage_text());
            ps.setLong(3,message.getTime_posted_epoch());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                message.setMessage_id(id);
                return message;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Message getMessageByMessageId(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "select * from message where message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,message.getMessage_id());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message temp = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                return temp;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Message getMessageByMessageId(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "select * from message where message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,message_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message temp = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                return temp;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessagesByUser(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "select * from message where posted_by = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message curr = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                messages.add(curr);
            }
            return messages;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Message deleteMessage(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "delete from message where message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                Message curr = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                return curr;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Message updateMessage(Message message, int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "update message set message_text=? where message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,message.getMessage_text());
            ps.setInt(2,id);
            ps.executeUpdate();
            return message;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
