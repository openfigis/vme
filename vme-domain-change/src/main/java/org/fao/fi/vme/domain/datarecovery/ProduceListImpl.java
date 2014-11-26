package org.fao.fi.vme.domain.datarecovery;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Alternative;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

import au.com.bytecode.opencsv.CSVReader;

@Alternative
public class ProduceListImpl implements ProduceList {

	/**
	 * this list has been taken from the VME development database which has been given to use from a backup from the
	 * 20th of November.
	 */
	String csvFileName = "src/test/resources/export.csv";
	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Override
	public Map<Long, MultiLingualString> loadFromCsv() {

		Map<Long, MultiLingualString> map = new HashMap<Long, MultiLingualString>();

		try {
			CSVReader reader = new CSVReader(new FileReader(csvFileName));
			reader.readNext();
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				MultiLingualString m = u.english(nextLine[1]);
				m.setId(Long.parseLong(nextLine[0]));
				map.put(m.getId(), m);
			}
			reader.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return map;
	}
}
