package org.test.datalists;

import java.util.List;

public interface ExtendedFetchList<T> {
	public List<T> fetchList(int startRow, int size);

	public String getPk(Object obj);

	public void update();

	public int getListSize();

	public int getMaxListSize();
}