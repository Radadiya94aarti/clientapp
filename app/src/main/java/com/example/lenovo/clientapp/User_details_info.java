package com.example.lenovo.clientapp;

/**
 * Created by lenovo on 17-03-2017.
 */

public class User_details_info {

    private String id;
    private String username;
    private String emailid;
    private String mobileno;
    private String emergncyno;
    private String text;
    private boolean isSelected = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    boolean isChecked = false;

    public String getKey() {
        return id;
    }

    User_details_info()
    {

    }

    User_details_info(String id, String username, String mobileno, String emailid, String emergncyno, String text) {
        this.id = id;
        this.username = username;
        this.mobileno = mobileno;
        this.emailid = emailid;
        this.emergncyno = emergncyno;
        this.text = text;
    }

    User_details_info(String username,String mobileno){
        this.username=username;
        this.mobileno=mobileno;
    }

    User_details_info( String username, String mobileno, String emailid, String emergncyno, String text) {
        this.username = username;
        this.mobileno = mobileno;
        this.emailid = emailid;
        this.emergncyno = emergncyno;
        this.text = text;
    }

    User_details_info(boolean isChecked){
        this.isChecked = isChecked;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setEmergncyno(String emergncyno) {
        this.emergncyno = emergncyno;
    }

    public String getEmergencyno() {
        return emergncyno;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean isSelected(){
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }


}
