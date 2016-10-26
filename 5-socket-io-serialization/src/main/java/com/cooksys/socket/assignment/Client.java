package com.cooksys.socket.assignment;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.Utils;
import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;



public class Client {

    public static void main(String[] args) throws JAXBException, UnknownHostException, IOException {
    	JAXBContext jc = Utils.createJAXBContext();
    	Config cf = Utils.loadConfig("config/config.xml", jc);
    	
    	Socket sock = new Socket(cf.getRemote().getHost(), cf.getRemote().getPort());
    	
    	DataInputStream input = new DataInputStream(sock.getInputStream());
    	
    	JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
    	
    	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Student stu = (Student) jaxbUnmarshaller.unmarshal(input);
		System.out.println(stu);
		
		sock.close();
    }
}
