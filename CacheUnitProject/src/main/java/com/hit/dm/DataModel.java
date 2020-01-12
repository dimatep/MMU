package com.hit.dm;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DataModel<T> implements Serializable {

	private Long dataModelId;
	private T content; 
	
	public DataModel(Long id, T content){
		this.dataModelId = id;
		this.content = content;
	}

	public Long getDataModelId() {
		return dataModelId;
	}

	public void setDataModelId(Long dataModelId) {
		this.dataModelId = dataModelId;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}
	
	@Override 
	public int hashCode() {
		return dataModelId.intValue();
	}
	
	@Override
	public boolean equals(Object O) {
		return dataModelId.equals(O);
	}
	
	@Override
	public String toString() {
		return "[ID:"+dataModelId+"Content:"+content+"]";
	}
	
}
