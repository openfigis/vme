package org.fao.fi.vme.batch.sync2.mapping;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.RelatedFisheries;
import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.batch.sync2.mapping.xml.DefaultObservationXml;
import org.fao.fi.vme.batch.sync2.mapping.xml.FigisDocBuilder;
import org.fao.fi.vme.domain.model.Vme;
import org.vme.dao.sources.figis.DefaultObservationDomain;
import org.vme.dao.sources.figis.PrimaryRule;
import org.vme.dao.sources.figis.PrimaryRuleValidator;
import org.vme.fimes.jaxb.JaxbMarshall;

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
 * Algorithm steps: (1) Collect all YearObject objects. Generate the Figis
 * objects per year.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class ObjectMapping {

	@Inject
	private FigisDocBuilder figisDocBuilder;

	private final JaxbMarshall marshall = new JaxbMarshall();
	private final PeriodGrouping grouping = new PeriodGrouping();
	private final SliceDuplicateFilter filter = new SliceDuplicateFilter();
	private final PrimaryRule primaryRule = new PrimaryRule();
	private final PrimaryRuleValidator primaryRuleValidator = new PrimaryRuleValidator();

	public VmeObservationDomain mapVme2Figis2(Vme vme) {

		List<DisseminationYearSlice> slices = grouping.collect(vme);

		// filter out the duplicates.
		filter.filter(slices);

		List<ObservationDomain> odList = new ArrayList<ObservationDomain>();

		for (DisseminationYearSlice disseminationYearSlice : slices) {
			ObservationDomain od = new DefaultObservationDomain().defineDefaultObservation();
			List<ObservationXml> observationsPerLanguage = new ArrayList<ObservationXml>();
			od.setObservationsPerLanguage(observationsPerLanguage);
			od.setReportingYear(String.valueOf(disseminationYearSlice.getYear()));
			odList.add(od);

			FIGISDoc figisDoc = new FIGISDoc();
			figisDocBuilder.dataEntryObjectSource(disseminationYearSlice.getVme().getRfmo().getId(), figisDoc);
			figisDocBuilder.vme(vme, disseminationYearSlice.getGeoRef(), disseminationYearSlice.getYear(), figisDoc);
			figisDocBuilder.fisheryArea(disseminationYearSlice.getFisheryAreasHistory(), figisDoc);
			figisDocBuilder.vmesHistory(disseminationYearSlice.getVmesHistory(), figisDoc);
			figisDocBuilder.specificMeasures(disseminationYearSlice.getSpecificMeasure(), figisDoc);
			figisDocBuilder.profile(disseminationYearSlice.getProfile(), figisDoc);
			figisDocBuilder.generalMeasures(disseminationYearSlice.getGeneralMeasure(), figisDoc,
					disseminationYearSlice.getYear());
			figisDocBuilder.informationSource(disseminationYearSlice.getInformationSourceList(),
					disseminationYearSlice.getYear(), figisDoc);

			ObservationXml xml = new DefaultObservationXml().define();

			String xmlString = marshall.marshalToString(figisDoc);

			if (xmlString.contains(RelatedFisheries.class.getSimpleName()) || xmlString.contains("<fi:Text xsi:nil=")
					|| xmlString.contains("<fi:Impacts/>")) {
				throw new VmeException(
						"Vme XML contains RelatedFisheries, <fi:Text xsi:nil=,  or <fi:Impacts/> is not correct.");

			}
			xml.setXml(xmlString);
			observationsPerLanguage.add(xml);
		}
		VmeObservationDomain vod = new VmeObservationDomain();
		vod.setObservationDomainList(odList);
		primaryRule.apply(vod);
		primaryRuleValidator.validate(vod);

		return vod;
	}

}
