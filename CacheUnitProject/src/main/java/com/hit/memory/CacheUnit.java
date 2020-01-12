package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.dm.DataModel;

public class CacheUnit<T> extends Object {

	private IAlgoCache<Long,DataModel<T>> algo;//AlgoCache
	
	public CacheUnit(IAlgoCache<Long,DataModel<T>> algo) {
		super();
		this.algo=algo;
	}
	
	 @SuppressWarnings("unchecked")
	public DataModel<T>[] getDataModels(Long[] ids){
		DataModel<T>[] dataModel = new DataModel[ids.length];
		
		for (int i = 0; i < ids.length; i++) {
			DataModel<T> tempDM = null; 
			tempDM = algo.getElement(ids[i]);
			if(tempDM != null)
				dataModel[i]= new DataModel<T>(tempDM.getDataModelId(),tempDM.getContent());
			else
			dataModel[i] = new DataModel<T>(null,null);
		}	
		return dataModel;
	} 
	 
	@SuppressWarnings("unchecked")
	public DataModel<T>[] putDataModels(DataModel<T>[] datamodels){
		DataModel<T>[] dataModel = new DataModel[datamodels.length];
		int count  = 0;
		
		for(int i = 0 ; i< datamodels.length ; i++) {
			DataModel<T> tempDM = null; 
			tempDM = algo.putElement(datamodels[i].getDataModelId(), datamodels[i]);
			if(tempDM != null)
				dataModel[count++]=new DataModel<T>(tempDM.getDataModelId(), tempDM.getContent());
		}
		return dataModel;
	} 
	
	public void removeDataModels(Long[] ids){
		for(int i = 0 ; i < ids.length ; i++)
			algo.removeElement(ids[i]);
	}


}

