package org.fao.fi.vme.batch.sync2.mapping.xml;

import java.util.Comparator;

import org.fao.fi.vme.domain.model.InformationSource;

public class InformationSourceComparator implements Comparator<InformationSource> {

	@Override
	public int compare(InformationSource o1, InformationSource o2) {
		int result = o2.getPublicationYear().compareTo(o1.getPublicationYear());
		if (result == 0 && o2.getMeetingEndDate() != null && o1.getMeetingEndDate() != null) {
			result = o2.getMeetingEndDate().compareTo(o1.getMeetingEndDate());
		}
		return result;
	}
}
