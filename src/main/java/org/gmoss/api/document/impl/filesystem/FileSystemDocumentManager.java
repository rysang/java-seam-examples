package org.gmoss.api.document.impl.filesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.gmoss.api.document.Document;
import org.gmoss.api.document.DocumentManager;
import org.gmoss.api.document.security.Credentials;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FileSystemDocumentManager implements DocumentManager {

	private static final Logger LOG = Logger
			.getLogger(FileSystemDocumentManager.class);

	private FileSystemDocument rootDoc;

	public FileSystemDocumentManager() {
		File rootFile = new File(System.getProperty("user.home"),
				"FileSystemRootDocument");
		if (!rootFile.exists()) {
			if (!rootFile.mkdirs()) {
				LOG.warn("Failed to create root directory.");
			}
		}

		rootDoc = new RootFileSystemDocument();
		rootDoc.setFile(rootFile);
	}

	public Document createDocument(Document parent, String name)
			throws IOException {
		FileSystemDocument pDoc = (FileSystemDocument) parent;
		File newDoc = new File(pDoc.getFile(), name);
		if (newDoc.exists()) {
			throw new IOException("File already exists.");
		}

		if (!newDoc.createNewFile()) {
			LOG.error("Failed to create document " + newDoc.getPath());
		}

		FileSystemDocument doc = new FileSystemDocument();
		doc.setFile(newDoc);

		return doc;
	}

	public Document createFolderDocument(Document parent, String name)
			throws IOException {
		FileSystemDocument pDoc = (FileSystemDocument) parent;
		File newDoc = new File(pDoc.getFile(), name);
		if (newDoc.exists()) {
			throw new IOException("File already exists.");
		}

		if (!newDoc.mkdirs()) {
			LOG.error("Failed to create document " + newDoc.getPath());
		}

		FileSystemDocument doc = new FileSystemDocument();
		doc.setFile(newDoc);

		return doc;
	}

	public void deleteDocument(String path) throws IOException {
		deleteDocument(getDocument(path));
	}

	public Document[] getChildren(Document document) {
		File[] files = ((FileSystemDocument) document).getFile().listFiles();
		FileSystemDocument[] children = new FileSystemDocument[files.length];

		for (int i = 0; i < children.length; i++) {
			children[i] = new FileSystemDocument();
			children[i].setFile(files[i]);
		}

		return children;
	}

	public Document getDocument(String path) throws IOException {
		StringBuilder pathBuilder = new StringBuilder(path.length() + 10);
		String[] pathSegments = path.split("/");

		if (pathSegments.length != 0) {
			for (int i = 0; i < pathSegments.length; i++) {
				pathBuilder.append(pathSegments[i].equals("") ? rootDoc
						.getFile().getPath() : pathSegments[i]);
				pathBuilder
						.append(i != (pathBuilder.length() - 1) ? File.separatorChar
								: "");
			}
		} else {
			pathBuilder.append(rootDoc.getFile().getPath());
		}

		File f = new File(pathBuilder.toString());
		if (!f.exists()) {
			throw new FileNotFoundException();
		}

		return new FileSystemDocument(f);
	}

	public Document getParent(Document document) {
		return ((FileSystemDocument) document).getParent();
	}

	public Document getRoot() {
		return rootDoc;
	}

	public void moveDocument(String oldPath, String newPath) {
		throw new NotImplementedException();
	}

	public void setRootFile(File rootFile) {
		rootDoc.setFile(rootFile);
	}

	public File getRootFile() {
		return rootDoc.getFile();
	}

	public void deleteDocument(Document doc) throws IOException {
		FileSystemDocument docl = (FileSystemDocument) doc;
		if (!docl.getFile().delete()) {
			throw new IOException("Failed to delete document " + doc.getPath());
		}
	}

	public void lockDocument(Document document) throws IOException {

	}

	public void unlockDocument(Document document) throws IOException {

	}

	public Document getDocument(Document parent, String name)
			throws IOException {
		FileSystemDocument parentDoc = (FileSystemDocument) parent;
		File f = new File(parentDoc.getFile(), name);

		if (!f.exists()) {
			throw new FileNotFoundException();
		}

		return new FileSystemDocument(f);
	}

	public void login(Credentials credentials) throws SecurityException {
		throw new NotImplementedException();
	}

	public Map<String, String> isDocumentLocked(Document document) {
		return null;
	}

}
