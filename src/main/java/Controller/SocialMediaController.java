package Controller;

import java.rmi.NoSuchObjectException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * changes to be made:
 * fix message creation
 * 
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register",this::postAccountRegisterHandler);
        app.post("/login",this::postAccountLoginHandler);
        app.post("/messages",this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByMessageIdHandler);
        app.get("/accounts/{account_id}/messages",this::getAllMessageByAccountIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::patchMessageHandler);
        return app;
    }
    private void postAccountRegisterHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(),Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null){
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    private void postAccountLoginHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(),Account.class);
        Account accountLoggedIn = accountService.getAccount(account);
        if(accountLoggedIn != null){
            ctx.json(mapper.writeValueAsString(accountLoggedIn));
            ctx.status(200);
        }else{
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(),Message.class);
        Message postedMessage = messageService.addMessage(message);
        if(postedMessage != null){
            ctx.json(mapper.writeValueAsString(postedMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }
    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());
        ctx.status(200);
    }

    private void getMessageByMessageIdHandler(Context ctx) throws NoSuchObjectException{
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message m = messageService.getMessageByMessageID(message_id);
        if (m!=null){
            ctx.json(m);
        }
        ctx.status(200);

    }
  
    private void deleteMessageHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageByMessageID(message_id);
        if (deletedMessage != null){
            ctx.json(deletedMessage);
        }
        ctx.status(200);
        return;    
    }
    private void patchMessageHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(),Message.class);
        //String m = mapper.readValue(ctx.body(),String.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage =messageService.updateMessage(message, message_id);
        if (updatedMessage != null){
            ctx.json(updatedMessage);
            ctx.status(200);
        }
        else{
            ctx.status(400);
        }
    }

    private void getAllMessageByAccountIdHandler(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessageByUserID(id);
        ctx.json(messages);
        ctx.status(200);
    }
}