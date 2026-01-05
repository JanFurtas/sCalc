package de.sCalc.logic;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private String lastName;
    private String email;
    private Date birthday;

    public User(int id, String name, String lastName, String email, Date birthday){
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public Date getBirthday() {return birthday;}

}
