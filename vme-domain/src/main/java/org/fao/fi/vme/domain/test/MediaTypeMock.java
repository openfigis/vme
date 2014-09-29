package org.fao.fi.vme.domain.test;

import org.fao.fi.vme.domain.model.reference.MediaType;

public class MediaTypeMock {

	public static Long ID = 10L;

	public static MediaType create() {
		MediaType m = new MediaType(ID, "Image");
		return m;
	}

}
