package Service;

import java.util.ArrayList;
import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    /*requirements
         * requirements: 
     *      create message
     *      Get all messages 
     *      get message by id
     *      delete message by message id
     *      update message by message id
     *      get message by user id 
    */

    MessageDAO messageDAO;
    public MessageService(){
        this.messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    public Message addMessage(Message message){
        if (!isMessageValid(message.getMessage_text())|| AccountDAO.isAccountIdValid(message.getPosted_by())==  false){
            return null;
        }
        return messageDAO.addMessage(message);
    }
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
    public List<Message> getAllMessageByUserID(int id){
        List<Message> messages = messageDAO.getAllMessagesByUser(id);
        return messages;
    }
    public Message deleteMessageByMessageID(int message_id){
        if(messageDAO.getMessageByMessageId(message_id) !=null){
            Message message = messageDAO.getMessageByMessageId(message_id);
            messageDAO.deleteMessage(message_id);
            return message;
        }
        return null;
    }

    public Message updateMessage(Message message,int message_id){
        String text = message.getMessage_text();
        if (messageDAO.getMessageByMessageId(message_id) == null || !isMessageValid(text)){
            return null;
        }
        messageDAO.updateMessage(message, message_id);
        return messageDAO.getMessageByMessageId(message_id);
    }
    public Message getMessageByMessageID(int message_id){
        Message m = messageDAO.getMessageByMessageId(message_id);
        if (m==null){
            return null;
        }
        return messageDAO.getMessageByMessageId(message_id);
    }
    public boolean isMessageValid(String message){
        if(message.length() < 1 || message.length() >= 255){
            return false;
        }
        return true;
    }    
}
