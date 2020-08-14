package com.example.syncvj;

public class DBcontrol {

    private String Name;
    private String Post;
    private Long Number;
    private String Email;
    private String Department;
    private int sync_status;


    DBcontrol(String Name, String Post, Long Number, String Email, String department, int sync_status){
        this.setName(Name);
        this.setPost(Post);
        this.setNumber(Number);
        this.setEmail(Email);
        this.setSync_status(sync_status);
        this.setDepartment(department);

    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public int getSync_status() {
        return sync_status;
    }

    public void setSync_status(int sync_status) {
        this.sync_status = sync_status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public Long getNumber() {
        return Number;
    }

    public void setNumber(Long number) {
        Number = number;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
