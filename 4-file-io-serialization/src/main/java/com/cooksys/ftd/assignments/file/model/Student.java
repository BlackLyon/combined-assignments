package com.cooksys.ftd.assignments.file.model;

public class Student {
    private Contact contact;
    
    public Student(Contact c) {
		contact = c;
	}

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
