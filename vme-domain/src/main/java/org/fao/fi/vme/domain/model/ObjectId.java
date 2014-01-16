package org.fao.fi.vme.domain.model;

import java.io.Serializable;

public interface ObjectId<ID extends Serializable> {
	ID getId();
	
	void setId(ID id);
}
