package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.spec.ECFieldF2m;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

import com.hit.services.CacheUnitController;

public class Server implements PropertyChangeListener,Runnable{
	
	private ServerSocket server;
	private Executor executor;
	private CacheUnitController<String>controller;
	private boolean power; //On/Off
	
	public Server() {
		server = null;
		executor = null;
		controller =  new CacheUnitController<String>();
		power = false;
	}
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(12345);//port 12345
			executor = Executors.newFixedThreadPool(3);
			while(power){
				Socket socket = server.accept();
				executor.execute(new HandleRequest<String>(socket,controller));
			}
		}catch(SocketException e) { }
		catch (IOException e) {
		e.printStackTrace();
		}
		finally {
				try {
				if(server != null)
					server.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String action =(String) evt.getNewValue();
		
		switch(action) {
		case "start":
			if(power == false) {
				power = true;
				new Thread(this).start();
				break;
			}
			else 
				System.out.println("Server is already ON\n");
			break;
		case "stop":
			if(power == false) 
				System.out.println("Server is already OFF\n");
			else {
				try {
					power=false;
					server.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			break;
			default:
				System.out.println("Not a valid command");
				break;
		}
	}
}
