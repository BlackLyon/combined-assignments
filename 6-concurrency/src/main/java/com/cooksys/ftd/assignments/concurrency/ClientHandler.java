package com.cooksys.ftd.assignments.concurrency;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * handles the interaction between the server and the client in a new thread.
 */
public class ClientHandler implements Runnable {
	
	private final Socket client;
	private DataInputStream input;
	private DataOutputStream output;
	
	public ClientHandler(Socket cln) throws IOException {
		client = cln;
		input = new DataInputStream(client.getInputStream());
		output = new DataOutputStream(client.getOutputStream());
	}

    @Override
    public void run() {
    	
    	try {
    		System.out.println("Inside Client Handler run");
			while(!client.isClosed())
			{
				for(int i = 0; i < 5; i++)
				{
					output.writeUTF("Message: " + i + " of " + (i +1));
					output.flush();
					String msg = input.readUTF();
					System.out.println("Client said: " + msg);
				}
				
				output.writeUTF("Bye");
				output.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    }