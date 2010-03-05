package org.test.datalists;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.test.User;

@Scope(ScopeType.CONVERSATION)
@Name("lazyListModel")
public class LazyListModel implements Serializable {
	private ExtendedListDataModel<User> exampleList = new ExtendedListDataModel<User>();
	private ExtendedFetchList<User> fetchList = new LazyFetchList();
	private static final long serialVersionUID = 102136548978979L;

	public LazyListModel() {
		exampleList.setFetchList(fetchList);
	}

	/**
	 * @return the exampleList
	 */
	public ExtendedListDataModel<User> getExampleList() {
		return exampleList;
	}

	/**
	 * @param exampleList
	 *            the exampleList to set
	 */
	public void setExampleList(ExtendedListDataModel<User> exampleList) {
		this.exampleList = exampleList;
	}

	/**
	 * @return the fetchList
	 */
	public ExtendedFetchList<User> getFetchList() {
		return fetchList;
	}

	/**
	 * @param fetchList
	 *            the fetchList to set
	 */
	public void setFetchList(ExtendedFetchList<User> fetchList) {
		this.fetchList = fetchList;
		exampleList.setFetchList(fetchList);
	}
}