package com.example.greenzone.Class;

public class Messages {
    String idMes,Text,time,Usercreate,idGroup;
    boolean isuer;

    public boolean isIsuer() {
        return isuer;
    }

    public void setIsuer(boolean isuer) {
        this.isuer = isuer;
    }

    public Messages() {
    }

    public Messages(String idMes, String text, String time, String usercreate) {
        this.idMes = idMes;
        Text = text;
        this.time = time;
        Usercreate = usercreate;
    }

    public String getIdMes() {
        return idMes;
    }

    public void setIdMes(String idMes) {
        this.idMes = idMes;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsercreate() {
        return Usercreate;
    }

    public void setUsercreate(String usercreate) {
        Usercreate = usercreate;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }
}
