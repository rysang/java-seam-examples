package org.test.datalists;

import java.math.BigDecimal;
import java.util.List;

public interface ExtendedFetchList {
	public List fetchList(int startRow, int size);

	public BigDecimal getPk(Object obj);

	public void update();

	public int getListSize();

	public int getMaxListSize();
}