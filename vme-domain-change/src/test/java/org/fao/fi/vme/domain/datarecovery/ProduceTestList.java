package org.fao.fi.vme.domain.datarecovery;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Alternative;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

@Alternative
public class ProduceTestList implements ProduceList {

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Override
	public Map<Long, MultiLingualString> loadFromCsv() {
		MultiLingualString m1 = u
				.english("The technological singularity, or simply the singularity, is the hypothesis that accelerating progress in technologies such as artificial intelligence will cause non-human intelligence to exceed human");
		m1.setId(1l);
		MultiLingualString m2 = u
				.english("What is Singularity University? Our mission is to educate, inspire and empower leaders to apply exponential technologies to address humanity's grand ...");
		m2.setId(2l);

		Map<Long, MultiLingualString> l = new HashMap<Long, MultiLingualString>();
		l.put(m1.getId(), m1);
		l.put(m2.getId(), m2);
		return l;
	}
}
