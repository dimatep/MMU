package com.hit.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class CLI implements Runnable{
	private Scanner input;
	private PrintWriter output;
	private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
	private String value = null;
	
	public CLI(InputStream in, OutputStream out) {
		input = new Scanner(in);
		output = new PrintWriter(out);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		this.changes.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		this.changes.removePropertyChangeListener(pcl);
	}

	@Override
	public void run() {
		String clientInput = null;
		
		while (true) {
			write("Please enter your command: (start/stop)");
			clientInput = input.nextLine().toLowerCase();
			
			if(clientInput.equals("start")){
				write("Starting server.....");
				setValue(clientInput);
				}
			
			else if(clientInput.equals("stop")) {
				write("Shutdown Server");
				setValue(clientInput);
			}
			else write("Not a valid command");
		}
	}
	
	public void write(String string) {
		output.println(string);
		output.flush();
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String newValue) {
		this.value = newValue;
		this.changes.firePropertyChange("value",null, newValue);
	}

}

