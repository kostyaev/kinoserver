package ru.cybern.kinoserver.mobileapi.db;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {
	
    T save(T entity);
	
    void delete(T entity);
	
    T findById(ID id, boolean lock);
	
    List<T> findAll();
    
}
