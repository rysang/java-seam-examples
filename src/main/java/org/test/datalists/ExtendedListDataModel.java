package org.test.datalists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.ajax4jsf.model.SerializableDataModel;

public class ExtendedListDataModel<T> extends SerializableDataModel {
	private boolean detached = false;
	private String currentPk;
	private Map<String, Object> wrappedData = new HashMap<String, Object>();
	private List<String> wrappedKeys = null;
	private ExtendedFetchList<T> fetchList = null;
	private int rowIndex;
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.model.SerializableDataModel#update()
	 */
	@Override
	public void update() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.model.ExtendedDataModel#getRowKey()
	 */
	@Override
	public Object getRowKey() {
		return currentPk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.model.ExtendedDataModel#setRowKey(java.lang.Object)
	 */
	@Override
	public void setRowKey(Object key) {
		if (key != null)
			this.currentPk = key.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ajax4jsf.model.ExtendedDataModel#walk(javax.faces.context.FacesContext
	 * , org.ajax4jsf.model.DataVisitor, org.ajax4jsf.model.Range,
	 * java.lang.Object)
	 */
	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range range,
			Object argument) throws IOException {
		int firstRow = ((SequenceRange) range).getFirstRow();
		int numberOfRows = ((SequenceRange) range).getRows();
		if (detached) { // Is this serialized model
			// Here we just ignore current Rage and use whatever data was saved
			// in serialized model.
			// Such approach uses much more getByPk() operations, instead of
			// just one request by range.
			// Concrete case may be different from that, so you can just load
			// data from data provider by range.
			// We are using wrappedKeys list only to preserve actual order of
			// items.
			for (String key : wrappedKeys) {
				setRowKey(key);
				visitor.process(context, key, argument);
			}
		} else { // if not serialized, than we request data from data provider
			wrappedKeys = new ArrayList<String>();
			for (Object obj : fetchList.fetchList(firstRow, numberOfRows)) {
				wrappedKeys.add(fetchList.getPk(obj));
				wrappedData.put(fetchList.getPk(obj), obj);
				visitor.process(context, fetchList.getPk(obj), argument);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.model.DataModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		// return fetchList.getListSize();
		return fetchList.getMaxListSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.model.DataModel#getRowData()
	 */
	@Override
	public Object getRowData() {
		if (currentPk == null) {
			return null;
		} else {
			Object ret = wrappedData.get(currentPk);
			return ret;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.model.DataModel#getRowIndex()
	 */
	@Override
	public int getRowIndex() {
		// throw new UnsupportedOperationException();
		return rowIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.model.DataModel#getWrappedData()
	 */
	@Override
	public Object getWrappedData() {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.model.DataModel#isRowAvailable()
	 */
	@Override
	public boolean isRowAvailable() {
		if (currentPk == null) {
			return false;
		} else {
			return wrappedData.containsKey(currentPk);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.model.DataModel#setRowIndex(int)
	 */
	@Override
	public void setRowIndex(int arg0) {
		// throw new UnsupportedOperationException();
		rowIndex = arg0;
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.model.DataModel#setWrappedData(java.lang.Object)
	 */
	@Override
	public void setWrappedData(Object arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SerializableDataModel getSerializableModel(Range range) {
		if (wrappedKeys != null) {
			detached = true;
			// Some activity to detach persistent data from wrappedData map may
			// be taken here.
			// In that specific case we are doing nothing.
			return this;
		} else {
			return null;
		}
	}

	/**
	 * @return the fetchList
	 */
	public ExtendedFetchList<T> getFetchList() {
		return fetchList;
	}

	/**
	 * @param fetchList
	 *            the fetchList to set
	 */
	public void setFetchList(ExtendedFetchList<T> fetchList) {
		this.fetchList = fetchList;
	}

}