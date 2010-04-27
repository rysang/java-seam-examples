package org.gmoss.api.document;

import java.io.IOException;

public interface DocumentManager {

	public Document getRoot();

	public Document createDocument(Document parent, String name)
			throws IOException;

	public Document getParent(Document document);

	public Document[] getChildren(Document document);

	public Document getDocument(String path) throws IOException;

	public Document getDocument(Document parent, String name)
			throws IOException;

	public void deleteDocument(String path) throws IOException;

	public void deleteDocument(Document doc) throws IOException;

	public void moveDocument(String oldPath, String newPath) throws IOException;

	public void lockDocument(Document document) throws IOException;

	public void unlockDocument(Document document) throws IOException;
}
