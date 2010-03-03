package org.test.datalists;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * <h:panelGrid id="exampleContainer" columns="1"> <rich:dataTable
 * id="exampleListDT" value="#{exampleListModel.exampleList}" var="item"
 * rows="25" style="width:100%"> <h:column> <f:facet name="header">
 * <h:outputText value="Column1" /> </f:facet> <h:outputText
 * value="#{item['column1']}" /> </h:column> <h:column> <f:facet name="header">
 * <h:outputText value="Column2" /> </f:facet> <h:outputText
 * value="#{item['column2']}" /> </h:column> </rich:dataTable> <rich:spacer
 * height="5" /> <rich:datascroller align="left" for="exampleListDT"
 * maxPages="10" ajaxSingle="false"/> </h:panelGrid>
 * 
 * @author Price
 * 
 */
public class AnExampleFetchList implements ExtendedFetchList, Serializable {
	private static final long serialVersionUID = 1L;
	private int listSize;
	private int maxListSize;

	public List fetchList(int startRow, int size) {
		List list = new Vector();
		// call some method here to populate the list
		// For example
		// RangeQueryResult rqr = foo.getBars(startRow, size);
		// list = rqs.getResultList();
		// setListSize(list.size());
		// setMaxListSize(rqr.getMaxResult());
		return list;
	}

	@SuppressWarnings("unchecked")
	public BigDecimal getPk(Object obj) {
		Map columns = (Map) obj;
		return new BigDecimal(columns.get("id").toString());
	}

	public void update() {
	}

	public int getListSize() {
		return listSize;
	}

	public int getMaxListSize() {
		return maxListSize;
	}

	/**
	 * @param listSize
	 *            the listSize to set
	 */
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	/**
	 * @param maxListSize
	 *            the maxListSize to set
	 */
	public void setMaxListSize(int maxListSize) {
		this.maxListSize = maxListSize;
	}
}