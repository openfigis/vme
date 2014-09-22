package org.fao.fi.vme.domain.test;

import org.fao.fi.vme.domain.model.reference.MediaType;

public class MediaTypeMock {

	public static Long ID = new Long(10l);

	public static MediaType create() {
		MediaType m = new MediaType(ID, "Image");
		return m;
	}

}
