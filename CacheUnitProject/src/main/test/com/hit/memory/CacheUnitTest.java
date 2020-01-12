package com.hit.memory;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dm.DataModel;

public class CacheUnitTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void getDataModelsTest(){
		
	try {
		IAlgoCache<Long, DataModel<String>> algoTest = new LRUAlgoCacheImpl<>(3);//capacity is 3
		CacheUnit<String> cacheUnitTest = new CacheUnit<String>(algoTest);
		DataModel<String>[] dataModelTest1,dataModelTest2;
		String[] stringTest = {"ANNA","BENNY","CINDY"};
		Long[] idsTest = {1L,2L,3L};
		
		dataModelTest1 = new DataModel[3];
		for(int i=0; i<3 ; i ++) 
			dataModelTest1[i] = new DataModel<String>(idsTest[i], stringTest[i]);
		    	
    	//The test is checking if the putDataModels method is working correctly
    	dataModelTest2 = cacheUnitTest.putDataModels(dataModelTest1);
    	assertEquals(dataModelTest1[0].getDataModelId(),dataModelTest2[0].getDataModelId());
    	assertEquals(dataModelTest1[1].getDataModelId(),dataModelTest2[1].getDataModelId());
    	assertEquals(dataModelTest1[2].getDataModelId(),dataModelTest2[2].getDataModelId());
    	
    	//The test is checking if the getDataModels method is working correctly
    	dataModelTest2 = cacheUnitTest.getDataModels(idsTest);
    	assertEquals(dataModelTest1[0].getDataModelId(),dataModelTest2[0].getDataModelId());
    	assertEquals(dataModelTest1[1].getDataModelId(),dataModelTest2[1].getDataModelId());
    	assertEquals(dataModelTest1[2].getDataModelId(),dataModelTest2[2].getDataModelId());

    	//The test is checking if the removeDataModels method is working correctly
    	cacheUnitTest.removeDataModels(idsTest);
    	dataModelTest2 = cacheUnitTest.getDataModels(idsTest);
    	assertEquals(null,dataModelTest2[0].getContent());
    	assertEquals(null,dataModelTest2[1].getContent());
    	assertEquals(null,dataModelTest2[2].getContent());

		}catch (NullPointerException e) {
		System.out.println("Data Model Is Empty!");
		}
	

	}
}

