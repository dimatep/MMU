package com.hit.services;

import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitService<T> {

	private IDao<Long, DataModel<T>> dao;
	private CacheUnit<T> cache;
	private int requests = 0;// will count the number of requests
	private int swaps = 0;// will count the number of swaps
	private int dataModelsCounter=0; // will count the number of dataModels
	private String chosedAlgorithm;
	private Integer cacheCapacity;

	public CacheUnitService() {
		this.chosedAlgorithm = "LRU";
		this.cacheCapacity = 7;
		this.cache = new CacheUnit<T>(new LRUAlgoCacheImpl<Long, DataModel<T>>(this.cacheCapacity)); // capacity 7
		this.dao = new DaoFileImpl<>("src/main/resources/dataresource.txt");
	}

	public boolean update(DataModel<T>[] dataModels) {
		this.requests++;
		this.dataModelsCounter += dataModels.length;
		DataModel<T>[] tempDM = cache.putDataModels(dataModels);
		for (int i = 0; i < dataModels.length; i++) {
			if (tempDM[i] != null) {
				dao.save(tempDM[i]);
				this.swaps++;
			}
		}

		return true;
	}

	public boolean delete(DataModel<T>[] dataModels) {
		try {
			this.requests++;
			Long ids[] = new Long[dataModels.length];
	
			for (int i = 0; i < dataModels.length; i++) {
				dao.delete(dataModels[i]);
			}
			for (int i = 0; i < ids.length; i++) {
				ids[i] = dataModels[i].getDataModelId();
				this.dataModelsCounter++;
			}
			cache.removeDataModels(ids);
		}catch(Exception ex) {
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	public DataModel<T>[] get(DataModel<T>[] dataModels) throws Exception {
		this.requests++;

		Long[] ids = new Long[dataModels.length];
		for (int i = 0; i < dataModels.length; i++) {
			ids[i] = dataModels[i].getDataModelId();
			this.dataModelsCounter++;
		}
		DataModel<T>[] tempDM = new DataModel[dataModels.length];
		tempDM = (DataModel<T>[]) cache.getDataModels(ids);

		for (int i = 0; i < dataModels.length; i++) {
			if (tempDM[i] == null)
				tempDM[i] = (DataModel<T>) dao.find(dataModels[i].getDataModelId());
		}
		return tempDM;
	}

	public String getChosedAlgorithm() {
		return chosedAlgorithm;
	}

	public void setChosedAlgorithm(String chosedAlgorithm) {
		this.chosedAlgorithm = chosedAlgorithm;
	}

	public Integer getCacheCapacity() {
		return cacheCapacity;
	}

	public void setCacheCapacity(Integer cacheCapacity) {
		this.cacheCapacity = cacheCapacity;
	}

	public String showStatisticsString() { //to handleRequest
		String stats = chosedAlgorithm + "," + cacheCapacity + "," + swaps + "," + requests + "," + dataModelsCounter;
		return stats;
	}

}
