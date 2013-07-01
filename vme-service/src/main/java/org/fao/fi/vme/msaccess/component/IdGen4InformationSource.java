package org.fao.fi.vme.msaccess.component;

public class IdGen4InformationSource {

	private static final long ID = 1000;
	private long id = ID;

	public long nextId() {
		return ++this.id;
	}
}
