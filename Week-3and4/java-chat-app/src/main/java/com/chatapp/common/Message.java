package com.chatapp.common;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sender;
    private String content;
    private String room;
    private String type;
    private String fileName;
    private byte[] fileData;

    public Message(String type, String sender, String room, String content) {
        this.type = type;
        this.sender = sender;
        this.room = room;
        this.content = content;
    }

    public Message(String type, String sender, String room, String fileName, byte[] fileData){
        this.type = type;
        this.sender = sender;
        this.room = room;
        this.fileName = fileName;
        this.fileData = fileData;
    }

    public String getType()    { return type; }
    public String getSender()  { return sender; }
    public String getRoom()    { return room; }
    public String getContent() { return content; }
    public String getFileName() { return fileName; }
    public byte[] getFileData() { return fileData; }

    @Override
    public String toString() {
        return "[" + room + "] " + sender + ": " + content;
    }
}