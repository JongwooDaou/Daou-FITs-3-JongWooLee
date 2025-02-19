package org.example.loginproject.vo;

public class MemberVO {
    public String ID;
    public String Name;
    public String Password;

    public MemberVO() {

    }
    public MemberVO(String ID, String Name, String Password) {

    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
