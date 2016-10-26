package com.cooksys.socket.assignment;



import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.Utils;
import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Server extends Utils {

    /**
     * Reads a {@link Student} object from the given file path
     *
     * @param studentFilePath the
     * @param jaxb
     * @return
     * @throws JAXBException 
     */
    public static Student loadStudent(String studentFilePath, JAXBContext jaxb) throws JAXBException {
    	
    	if(studentFilePath == null)
    		return null;
    	
    	if(jaxb == null)
    		jaxb = JAXBContext.newInstance(Config.class, LocalConfig.class, RemoteConfig.class, Student.class);
    	
    	File file = new File(studentFilePath);
    	
    	Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
    	Student stu = (Student) jaxbUnmarshaller.unmarshal(file);
    	
        return stu;
    }

    public static void main(String[] args) throws JAXBException, IOException {
    	JAXBContext jc = createJAXBContext();
    	Config cf = loadConfig("config/config.xml", jc);
    	Student stu = loadStudent("config/student.xml", jc);
    
    	ServerSocket ss = new ServerSocket(cf.getLocal().getPort());
    	
    	Socket client = ss.accept();
    	
    	Marshaller jaxbMarshaller = jc.createMarshaller();
    	jaxbMarshaller.marshal(stu, client.getOutputStream());
    	client.close();
    	ss.close();
    }
}
