package com.cooksys.ftd.assignments.file;

import com.cooksys.ftd.assignments.file.model.Contact;
import com.cooksys.ftd.assignments.file.model.Instructor;
import com.cooksys.ftd.assignments.file.model.Session;
import com.cooksys.ftd.assignments.file.model.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Main {

    /**
     * Creates a {@link Student} object using the given studentContactFile.
     * The studentContactFile should be an XML file containing the marshaled form of a
     * {@link Contact} object.
     *
     * @param studentContactFile the XML file to use
     * @param jaxb the JAXB context to use
     * @return a {@link Student} object built using the {@link Contact} data in the given file
     */
    public static Student readStudent(File studentContactFile, JAXBContext jaxb) throws JAXBException  {
    	
    	if(studentContactFile == null)
    		return null;
    	
    	if(jaxb == null)
    		jaxb = JAXBContext.newInstance(Student.class);
    		
    		Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
    		
    		Student stu = (Student) jaxbUnmarshaller.unmarshal(studentContactFile);
    		
		return stu;
    }

    /**
     * Creates a list of {@link Student} objects using the given directory of student contact files.
     *
     * @param studentDirectory the directory of student contact files to use
     * @param jaxb the JAXB context to use
     * @return a list of {@link Student} objects built using the contact files in the given directory
     */
    public static List<Student> readStudents(File studentDirectory, JAXBContext jaxb) throws JAXBException {
    	
    	if(studentDirectory == null)
    		return null;
    	
    	if(jaxb == null)
    		jaxb = JAXBContext.newInstance(Contact.class);
    	
    	
    	List<Student> stu = new ArrayList<>();
    	
    	for(File i : studentDirectory.listFiles())
    	{
    		Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
    		stu.add(new Student((Contact) jaxbUnmarshaller.unmarshal(i)));
    	}
    	
    	return stu;
    }

    /**
     * Creates an {@link Instructor} object using the given instructorContactFile.
     * The instructorContactFile should be an XML file containing the marshaled form of a
     * {@link Contact} object.
     *
     * @param instructorContactFile the XML file to use
     * @param jaxb the JAXB context to use
     * @return an {@link Instructor} object built using the {@link Contact} data in the given file
     */
    public static Instructor readInstructor(File instructorContactFile, JAXBContext jaxb) throws JAXBException{
    	
    	if(instructorContactFile == null)
    		return null;
    	
    	if(jaxb == null)
    		jaxb = JAXBContext.newInstance(Contact.class);
    		
    		Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
    		
		return new Instructor((Contact) jaxbUnmarshaller.unmarshal(instructorContactFile));
    }

    /**
     * Creates a {@link Session} object using the given rootDirectory. A {@link Session}
     * root directory is named after the location of the {@link Session}, and contains a directory named
     * after the start date of the {@link Session}. The start date directory in turn contains a directory named
     * `students`, which contains contact files for the students in the session. The start date directory
     * also contains an instructor contact file named `instructor.xml`.
     *
     * @param rootDirectory the root directory of the session data, named after the session location
     * @param jaxb the JAXB context to use
     * @return a {@link Session} object built from the data in the given directory
     */
    public static Session readSession(File rootDirectory, JAXBContext jaxb) throws JAXBException {
    	if(rootDirectory == null)
    		return null;
    	
    	if(jaxb == null)
    		jaxb = JAXBContext.newInstance(Instructor.class);
    	
    	Session sess = new Session();
    	
   
    	sess.setLocation(rootDirectory.getName());
    	sess.setStartDate(rootDirectory.listFiles()[0].getName());
    	sess.setInstructor(readInstructor(rootDirectory.listFiles()[0].listFiles()[0], jaxb));
    	sess.setStudents(readStudents(rootDirectory.listFiles()[0].listFiles()[1], jaxb));    	
    	
        return sess; // TODO
    }

    /**
     * Writes a given session to a given XML file
     *
     * @param session the session to write to the given file
     * @param sessionFile the file to which the session is to be written
     * @param jaxb the JAXB context to use
     */
    public static void writeSession(Session session, File sessionFile, JAXBContext jaxb) throws JAXBException {
    	
    	if(jaxb == null)
    		jaxb = JAXBContext.newInstance(Session.class);

    	Marshaller jaxbMarshaller = jaxb.createMarshaller();


		jaxbMarshaller.marshal(session, sessionFile);

    }

    /**
     * Main Method Execution Steps:
     * 1. Configure JAXB for the classes in the com.cooksys.serialization.assignment.model package
     * 2. Read a session object from the <project-root>/input/memphis/ directory using the methods defined above
     * 3. Write the session object to the <project-root>/output/session.xml file.
     *
     * JAXB Annotations and Configuration:
     * You will have to add JAXB annotations to the classes in the com.cooksys.serialization.assignment.model package
     *
     * Check the XML files in the <project-root>/input/ directory to determine how to configure the {@link Contact}
     *  JAXB annotations
     *
     * The {@link Session} object should marshal to look like the following:
     *      <session location="..." start-date="...">
     *           <instructor>
     *               <contact>...</contact>
     *           </instructor>
     *           <students>
     *               ...
     *               <student>
     *                   <contact>...</contact>
     *               </student>
     *               ...
     *           </students>
     *      </session>
     */
    public static void main(String[] args) {
        
    	Session sess = new Session();
    	File inputFile = new File("input/memphis");
    	File outputFile = new File("output/session.xml");
    	JAXBContext jaxb = null;
    	
    	try {
			sess = readSession(inputFile, jaxb);
			writeSession(sess, outputFile, jaxb);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
