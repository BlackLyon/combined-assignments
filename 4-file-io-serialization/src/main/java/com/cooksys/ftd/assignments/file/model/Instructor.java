package com.cooksys.ftd.assignments.file.model;

public class Instructor {
    private Contact contact;
    
    public Instructor(Contact c) {
		contact = c;
	}

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
