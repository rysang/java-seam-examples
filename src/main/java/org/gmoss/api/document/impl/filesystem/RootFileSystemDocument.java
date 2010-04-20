package org.gmoss.api.document.impl.filesystem;

public class RootFileSystemDocument extends FileSystemDocument {

	public RootFileSystemDocument() {

	}

	@Override
	public FileSystemDocument getParent() {
		return null;
	}
}
