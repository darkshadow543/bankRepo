package dao;

import java.util.ArrayList;

public interface ChildTableDAO<T> extends DAO<T> {
	public ArrayList<T> queryByTag(String fk) throws Exception;
	public void close(T object) throws Exception;
}
