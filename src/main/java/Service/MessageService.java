package Service;

import java.util.List;

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
        return messageDAO.addMessage(message);
    }
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
    public List<Message> getAllMessageByUserID(Message message){
        return messageDAO.getAllMessagesByUser(message);
    }
    public Message deleteMessageByMessageID(Message message){
        if(messageDAO.getMessageByMessageId(message) !=null){
            return messageDAO.deleteMessage(message);
        }
        return null;
    }
    public Message updateMessage(Message oldMessage, String newMessage){
    if (messageDAO.getMessageByMessageId(oldMessage) != null){
        messageDAO.updateMessage(oldMessage, newMessage);
        oldMessage.setMessage_text(newMessage);
        return oldMessage;
    }
    return null;
    }
}
