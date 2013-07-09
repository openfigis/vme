package org.fao.fi.vme.sync2.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.Profile;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.YearObject;

/**
 * Stage A: domain objects without the year dimension: Vme and Rfmo.
 * 
 * Stage B: domain objects with the year dimension:
 * 
 * Vme trough History, SpecificMeasures, Profile
 * 
 * Vme-Rfmo through History, GeneralMesaures-InformationSource.
 * 
 * Stage B has only YearObject objects.
 * 
 * Algorithm steps: (1) Collect all YearObject objects. Generate the Figis objects per year.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class ObjectMapping {

	YearGrouping groupie = new YearGrouping();

	public VmeObservationDomain mapVme2Figis(Vme vme) {
		// precondition
		if (vme.getRfmo() == null) {
			throw new VmeException("Detected Vme without Rfmo");
		}

		// logic
		Map<Integer, List<YearObject<?>>> map = groupie.collect(vme);// not processed here InformationSource, To be done
		Object[] years = map.keySet().toArray();
		List<ObservationDomain> odList = new ArrayList<ObservationDomain>();
		for (Object year : years) {
			ObservationDomain od = new ObservationDomain();
			ObservationXml xml = new DefaultObservationXml().defineDefaultObservationXml();

			List<ObservationXml> observationsPerLanguage = new ArrayList<ObservationXml>();
			observationsPerLanguage.add(xml);
			od.setObservationsPerLanguage(observationsPerLanguage);
			od.setReportingYear(year.toString());
			odList.add(od);

			List<YearObject<?>> l = map.get(year);
			for (YearObject<?> yearObject : l) {

				if (yearObject instanceof SpecificMeasures) {
				}
				if (yearObject instanceof VmeHistory) {
				}
				if (yearObject instanceof RfmoHistory) {
				}
				if (yearObject instanceof Profile) {
				}
				if (yearObject instanceof GeneralMeasures) {
				}
			}
		}
		VmeObservationDomain vod = new VmeObservationDomain();
		vod.setObservationDomainList(odList);
		return vod;
	}
}
