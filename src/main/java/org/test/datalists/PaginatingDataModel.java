package org.test.datalists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.Expression;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.ajax4jsf.model.SerializableDataModel;
import org.richfaces.model.FilterField;
import org.richfaces.model.Modifiable;
import org.richfaces.model.Ordering;
import org.richfaces.model.SortField2;

/**
 * @param <T>
 * @param <U>
 */
public abstract class PaginatingDataModel<T, U> extends SerializableDataModel
        implements Modifiable {
    /** */
    private static final long serialVersionUID = 2954923950179861809L;
    /** */
    protected U currentPk;
    /** */
    protected boolean descending = true;
    /** */
    protected String sortField = getDefaultSortField();
    /** */
    protected boolean detached = false;
    /** */
    protected List<U> wrappedKeys = new ArrayList<U>();
    /** */
    protected Integer rowCount;
    /** */
    protected Map<U, T> wrappedData = new HashMap<U, T>();

    /**
     * @see org.ajax4jsf.model.ExtendedDataModel#getRowKey()
     */
    @Override
    public Object getRowKey() {
        return currentPk;
    }

    /**
     * @see org.ajax4jsf.model.ExtendedDataModel#setRowKey(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setRowKey(final Object key) {
        this.currentPk = (U) key;
    }

    /**
     * @see org.ajax4jsf.model.SerializableDataModel#update()
     */
    @Override
    public void update() {
        this.detached = false;
    }

    /**
     * @return Object
     */
    protected Object getSortFieldObject() {
        final FacesContext context = FacesContext.getCurrentInstance();
        final Object sortFieldObject = context.getExternalContext()
                .getRequestParameterMap().get("sortField");
        return sortFieldObject;
    }

    /**
     * @param sortField
     */
    public void setSortField(final String sortField) {
        if (this.sortField.equals(sortField)) {
            descending = !descending;
        } else {
            this.sortField = sortField;
        }
    }

    /**
     * @return String
     */
    public String getSortField() {
        return sortField;
    }

    /**
     * @see org.ajax4jsf.model.ExtendedDataModel#getSerializableModel(org.ajax4jsf.model.Range)
     */
    @Override
    public SerializableDataModel getSerializableModel(final Range range) {
        if (wrappedKeys != null) {
            detached = true;
            return this;
        }
        return null;
    }

    /**
     * @see javax.faces.model.DataModel#setRowIndex(int)
     */
    @Override
    public void setRowIndex(final int rowIndex) {
        throw new UnsupportedOperationException();

    }

    /**
     * @see javax.faces.model.DataModel#setWrappedData(java.lang.Object)
     */
    @Override
    public void setWrappedData(final Object data) {
        throw new UnsupportedOperationException();

    }

    /**
     * @see javax.faces.model.DataModel#getRowIndex()
     */
    @Override
    public int getRowIndex() {
        throw new UnsupportedOperationException();
    }

    /**
     * @see javax.faces.model.DataModel#getWrappedData()
     */
    @Override
    public Object getWrappedData() {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.ajax4jsf.model.ExtendedDataModel#walk(javax.faces.context.FacesContext,
     *      org.ajax4jsf.model.DataVisitor, org.ajax4jsf.model.Range,
     *      java.lang.Object)
     */
    @Override
    public void walk(final FacesContext context, final DataVisitor visitor,
            final Range range, final Object argument) throws IOException {
        final int firstRow = ((SequenceRange) range).getFirstRow();
        final int numberOfRows = ((SequenceRange) range).getRows();
        if (detached && (getSortFieldObject() != null)) {
            for (final U key : wrappedKeys) {
                setRowKey(key);
                visitor.process(context, key, argument);
            }
        } else { // if not serialized, than we request data from data
            // provider
            wrappedKeys = new ArrayList<U>();
            for (final T object : findObjects(firstRow, numberOfRows,
                    sortField, descending)) {
                wrappedKeys.add(getId(object));
                wrappedData.put(getId(object), object);
                visitor.process(context, getId(object), argument);
            }
        }
    }

    /**
     * @see javax.faces.model.DataModel#isRowAvailable()
     */
    @Override
    public boolean isRowAvailable() {
        if (currentPk == null) {
            return false;
        }
        if (wrappedKeys.contains(currentPk)) {
            return true;
        }
        if (wrappedData.entrySet().contains(currentPk)) {
            return true;
        }
        try {
            if (getObjectById(currentPk) != null) {
                return true;
            }
        } catch (final Exception e) {

        }
        return false;
    }

    /**
     * @see javax.faces.model.DataModel#getRowData()
     */
    @Override
    public Object getRowData() {
        if (currentPk == null) {
            return null;
        }

        T object = wrappedData.get(currentPk);
        if (object == null) {
            object = getObjectById(currentPk);
            wrappedData.put(currentPk, object);
        }
        return object;
    }

    /**
     * @see javax.faces.model.DataModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        if (rowCount == null) {
            rowCount = getNumRecords();
        }
        return rowCount;
    }

    /**
     * 
     * @param object
     * @return U
     */
    public abstract U getId(T object);

    /**
     * @param firstRow
     * @param numberOfRows
     * @param sortField
     * @param descending
     * @return List<T>
     */
    public abstract List<T> findObjects(int firstRow, int numberOfRows,
            String sortField, boolean descending);

    /**
     * @param id
     * @return T
     */
    public abstract T getObjectById(U id);

    /**
     * @return String
     */
    public abstract String getDefaultSortField();

    /**
     * @return int
     */
    public abstract int getNumRecords();

    public void modify(List<FilterField> filterFields,
            List<SortField2> sortFields) {
        if ((sortFields != null) && !sortFields.isEmpty()) {
            SortField2 sortField2 = sortFields.get(0);
            Expression expression = sortField2.getExpression();
            String expressionStr = expression.getExpressionString();
            if (!expression.isLiteralText()) {
                expressionStr = expressionStr.replaceAll("[#|$]{1}\\{.*?\\.",
                        "").replaceAll("\\}", "");
            }
            this.sortField = expressionStr;

            Ordering ordering = sortField2.getOrdering();
            if (ordering == Ordering.DESCENDING) {
                this.descending = true;
            } else {
                this.descending = false;
            }
        }
    }

}
