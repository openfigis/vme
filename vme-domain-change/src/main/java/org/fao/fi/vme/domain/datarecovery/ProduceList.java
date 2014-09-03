package org.fao.fi.vme.domain.datarecovery;

import java.util.Map;

import org.fao.fi.vme.domain.model.MultiLingualString;

public interface ProduceList {

	Map<Long, MultiLingualString> loadFromCsv();

}
