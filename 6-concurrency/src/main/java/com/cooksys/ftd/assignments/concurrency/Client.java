package com.cooksys.ftd.assignments.concurrency;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;

public class Client implements Runnable {

	private ClientConfig client;
	private Socket clnSock;
	private List<ClientInstanceConfig> list;

	public Client() {
	}

	public Client(ClientConfig config) {
		client = config;
		list = client.getInstances();
	}

	@Override
	public void run() {

		try {
			clnSock = new Socket(client.getHost(), client.getPort());

			System.out.println("Connection started by client");

			new Thread(new ClientInstance(list, clnSock)).start();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
