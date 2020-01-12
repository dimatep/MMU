package com.hit.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;

public class HandleRequest<T> implements Runnable {

	private Socket socket;
	private CacheUnitController<T> controller;
	
	private Map<String, String> header;

	public HandleRequest(Socket s, CacheUnitController<T> controller) {
		this.socket = s;
		this.controller = controller;
	}

	@Override
	public void run() {
		try (Scanner reader = new Scanner(new InputStreamReader (socket.getInputStream()))) {
			try (PrintWriter pw = new PrintWriter (new OutputStreamWriter (socket.getOutputStream()))) {
					String response="";
					String req = reader.nextLine();
					Type ref = new TypeToken<Request<DataModel<T>[]>>() {}.getType();
					Request<DataModel<T>[]> request = new Gson().fromJson(req, ref);
					header = request.getHeaders();
					boolean success = false;
					switch (header.get("action")) {
	
					case "GET": {
						try {
							controller.get(request.getBody());
							if (controller != null) {
								success = true;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
	
					case "DELETE": {
						try {
							success = controller.delete(request.getBody());
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
	
					case "UPDATE": {
						success = controller.update(request.getBody());
						break;
					}
					
					case "SHOWSTATS":{
						response = controller.getStats(); //handleReqest -> controller -> service
						break;
					}
	
					default:
						break;
					}

					if(!header.get("action").equals("SHOWSTATS"))
						response = success ? "Succeeded": "Failed";
					pw.println(response);
					pw.flush();
				}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
