package com.endre.java.java_ee_exam.frontend.controller;

import com.endre.java.java_ee_exam.backend.entity.Message;
import com.endre.java.java_ee_exam.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named
@SessionScoped
public class MessageController implements Serializable {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserInfoController userInfoController;

    private String receiver;

    private String message;

    private String messageSent;

    private List<Message> receivedMessages;

    private List<Message> sentMessages;



    public String toSendMessage(String receiver){
        this.receiver = receiver;
        messageSent = "";
        return "/ui/sendmessage.jsf?faces-redirect=true";
    }

    public void sendMessage(){
        String sender = userInfoController.getUserName();
        messageService.createMessage(sender, receiver, message);
        clearFields();
    }

    private void clearFields() {
        message = "";
        messageSent = "Message sent!";
    }


    public List<Message> getReceivedMessages(){
        receivedMessages = messageService.getReceivedMessages(userInfoController.getUserName());
        return receivedMessages;
    }

    public List<Message> getSentMessages(){
        sentMessages = messageService.getSentMessages(userInfoController.getUserName());
        return sentMessages;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageSent() {
        return messageSent;
    }
}
