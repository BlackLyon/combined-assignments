package com.cooksys.ftd.assignments.concurrency;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig;

/*
 * Just waits for a new client connection in which it will create a new thread for it and pass it off to client handler
 */

public class Server implements Runnable {
	
	/**
     * server configuration
     */
    private ServerConfig server;
    private ServerSocket svrSock;
    private Socket clientSock;

    public Server()
    {
    }
    
    public Server(ServerConfig config) {
    	server = config;
    }

    @Override
    public void run() {
    	
    	try {
    		System.out.println("Inside Server run");
    		svrSock = new ServerSocket(8080);
    		System.out.println("Connection recieved by server");
			
			while(true)
			{
				clientSock = svrSock.accept();
				new Thread(new ClientHandler(clientSock)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				svrSock.close();
				//clientSock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    	}
}