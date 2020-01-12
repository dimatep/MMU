package com.hit.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.hit.dm.DataModel;

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
	public String filePath;
	private HashMap<Long, DataModel<T>> data;
	private int capacity;
	
	public DaoFileImpl(String filePath) { //default capacity
		this.filePath=filePath;
		data = new HashMap<Long,DataModel<T>>();
		this.capacity = 1000; //default
	}

	public DaoFileImpl(String filePath, int capacity) {
		this.filePath=filePath;
		data = new HashMap<Long,DataModel<T>>();
		this.capacity=capacity;
	}
	
	@Override
	public void save(DataModel<T> entity){
		if(data.containsKey(entity.getDataModelId())) 
			data.replace(entity.getDataModelId(), entity);// exist and going to replace it
		else { //if does not exist in the map
			data.put(entity.getDataModelId(), entity);
			capacity--; //cannot be more then capacity
		}
		writeDataToFile(this.data); 
	}

	@Override
	public void delete(DataModel<T> entity) throws Exception {
		this.data = readDataModelsFromFile();
		if(this.data.containsKey(entity.getDataModelId())) {
			this.data.remove(entity.getDataModelId());
			this.writeDataToFile(this.data);
		}
	}

	@Override
	public DataModel<T> find(Long id) {
		if(id == null) {
			throw new IllegalArgumentException("The ID Cannot be null");
		}
		if(data.containsKey(id))
			return data.get(id);
		else {
			this.data = readDataModelsFromFile();
			return this.data.get(id);
		}
	}
	
	public void writeDataToFile(HashMap<Long,DataModel<T>>data){
		File file = new File(filePath);
		if (!file.exists()) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
					oos.writeObject(data);
					oos.flush();
					oos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
	
	public HashMap<Long, DataModel<T>> readDataModelsFromFile(){
		HashMap<Long, DataModel<T>> fileData = new HashMap<Long, DataModel<T>>();
		File file = new File(filePath);
		if (file.exists()) 
		try {
			FileInputStream fin = new FileInputStream(filePath);
			ObjectInputStream oin = new ObjectInputStream(fin);
			data = (HashMap<Long, DataModel<T>>)oin.readObject();
			oin.close();
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return fileData;
	}
}
