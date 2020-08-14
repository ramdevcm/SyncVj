package com.example.syncvj;

public class DBcontrol_intercom {


    private String Name;
    private String Post;
    private Long Int_comm;
    private String Department;


    DBcontrol_intercom(String Name, String Post, Long Int_comm, String department){
        this.setName(Name);
        this.setPost(Post);
        this.setInt_comm(Int_comm);
        this.setDepartment(department);

    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
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

    public Long getInt_comm() {
        return Int_comm;
    }

    public void setInt_comm(Long number) {
        Int_comm = number;
    }
}
