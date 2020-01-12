package com.hit.dao;

import java.io.Serializable;

public interface IDao <ID extends Serializable,T> {
	
	void save(T entity);
	void delete(T entity) throws Exception;//in case the given entity is null.
	T find(ID id) throws Exception;//if id is null
	
}
