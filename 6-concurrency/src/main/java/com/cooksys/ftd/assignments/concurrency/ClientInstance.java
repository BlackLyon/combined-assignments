package com.cooksys.ftd.assignments.concurrency;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.plaf.synth.SynthSplitPaneUI;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ClientInstance implements Runnable {
	
	private ClientInstanceConfig config;
	private Socket clnSock;
	private DataInputStream input;
	private DataOutputStream output;

	public ClientInstance(Socket cs) throws IOException
	{
		clnSock = cs;
		input = new DataInputStream(clnSock.getInputStream());
		output = new DataOutputStream(clnSock.getOutputStream());
	}
	
    public ClientInstance(ClientInstanceConfig config, Socket cs) throws IOException {
    	this.config = config;
    	clnSock = cs;
		input = new DataInputStream(clnSock.getInputStream());
		output = new DataOutputStream(clnSock.getOutputStream());
    }

    @Override
    public void run() {
    	
		
		System.out.println("Connection started by client instance");
		
		try {
			while(true)
			{
				String msg = input.readUTF();
				System.out.println(msg + " recieved");
				output.writeUTF("I got your message");
				output.flush();
				
				if(msg.equals("Bye"))
				System.exit(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
				output.close();
				clnSock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}