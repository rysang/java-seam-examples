package org.test.datalists;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

public abstract class BaseExtendedDataModel<T, ID extends Serializable> extends
		ExtendedDataModel {

	private ID currentId;
	private Map<ID, T> wrappedData = new HashMap<ID, T>();
	private List<ID> wrappedKeys;
	private Long rowCount; // better to buffer row count locally

	public abstract Long getCount();

	public abstract List<T> getList(Integer firstRow, Integer maxResults);

	public abstract T findById(ID id);

	public void wrap(FacesContext context, DataVisitor visitor, Range range,
			Object argument, List<T> list) throws IOException {
		wrappedKeys = new ArrayList<ID>();
		wrappedData = new HashMap<ID, T>();
		for (T row : list) {
			Idable<ID> idable = (Idable<ID>) row;
			ID id = idable.getId();
			wrappedKeys.add(id);
			wrappedData.put(id, row);
			visitor.process(context, id, argument);
		}
	}

	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range range,
			Object argument) throws IOException {
		int firstRow = ((SequenceRange) range).getFirstRow();
		int maxResults = ((SequenceRange) range).getRows();
		wrap(context, visitor, range, argument, getList(firstRow, maxResults));
	}

	/*
	 * This method normally called by Visitor before request Data Row.
	 */
	@Override
	public void setRowKey(Object key) {
		this.currentId = (ID) key;
	}

	@Override
	public Object getRowKey() {
		return currentId;
	}

	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}

	@Override
	public int getRowCount() {
		if (rowCount == null)
			return (rowCount = this.getCount()).intValue();
		else
			return rowCount.intValue();
	}

	@Override
	public boolean isRowAvailable() {
		if (currentId == null) {
			return false;
		} else {
			return wrappedData.containsKey(currentId);
		}
	}

	/**
	 * This is main way to obtain data row. It is intensively used by framework.
	 * We strongly recommend use of local cache in that method.
	 */
	@Override
	public Object getRowData() {
		if (currentId == null) {
			return null;
		} else {
			T ret = wrappedData.get(currentId);
			if (ret == null) {
				ret = this.findById(currentId);
				wrappedData.put(currentId, ret);
				return ret;
			} else {
				return ret;
			}
		}
	}

	@Override
	public Object getWrappedData() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRowIndex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRowIndex(int rowIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWrappedData(Object data) {
		throw new UnsupportedOperationException();
	}
}