package com.hit.services;
import com.hit.dm.DataModel;


public class CacheUnitController<T>{
	
	private CacheUnitService<T> cacheService;
	
	public CacheUnitController() {
		cacheService = new CacheUnitService<>();
	}
	
	public boolean update(DataModel<T>[] dataModels) {
		return cacheService.update(dataModels);
	}
	
	public boolean delete(DataModel<T>[] dataModels) throws Exception {
		return cacheService.delete(dataModels);
	}
	
	public DataModel<T>[] get(DataModel<T>[] dataModels) throws Exception{
		return cacheService.get(dataModels);
	}
	
	public String getStats() {
		return cacheService.showStatisticsString(); //controller -> service
	}
}
