package com.example.prototype10;

public class StudentSignUpConnectFirebase {

    public String  FirstName,LastName,Gender,Age,EmployeeNumber,ContactNumber,Email,Password;

    public StudentSignUpConnectFirebase(){

    }

    public StudentSignUpConnectFirebase(String FirstName,String LastName,String Gender,String Age,String EmployeeNumber,String ContactNumber,String Email,String Password){
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Gender = Gender;
        this.Age = Age;
        this.EmployeeNumber = EmployeeNumber;
        this.ContactNumber = ContactNumber;
        this.Email = Email;
        this.Password = Password;
    }
}
