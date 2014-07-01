package org.fao.fi.vme.batch.sync2.mapping.xml;

import javax.inject.Inject;

import org.fao.fi.figis.devcon.ObjectFactory;
import org.fao.fi.vme.batch.sync2.mapping.BiblioEntryFromInformationSource;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.support.VmeSimpleDateFormat;
import org.fao.fi.vme.domain.util.Lang;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.vme.dao.ReferenceDAO;

/**
 * Abstract class for FigisDocBuilderRegolatory and FigisDocBuilderVme
 * 
 * 
 * @author Erik van Ingen
 * 
 * 
 */
abstract class FigisDocBuilderAbstract {

	@Inject
	@ConceptProvider
	protected ReferenceDAO refDao;

	protected ObjectFactory f = new ObjectFactory();
	protected MultiLingualStringUtil u = new MultiLingualStringUtil();
	protected EnglishTextUtil ut = new EnglishTextUtil();
	protected JAXBElementUtil uj = new JAXBElementUtil();
	protected GeneralMeasureManagementMethodEntryBuilder mmeBuilder = new GeneralMeasureManagementMethodEntryBuilder();
	protected CurrentDate currentDate = new CurrentDate();
	protected BiblioEntryFromInformationSource bu = new BiblioEntryFromInformationSource();
	protected VmeSimpleDateFormat du = new VmeSimpleDateFormat();

	public static final String VULNERABLE_MARINE_ECOSYSTEMS = "Vulnerable Marine Ecosystems";

	public static final String VME_SPECIFIC_MEASURES = "VME-specific measures";

	protected String safeGetMultilingualString(MultiLingualString mls) {
		return this.safeGetMultilingualString(mls, Lang.EN);
	}

	protected String safeGetMultilingualString(MultiLingualString mls, Integer lang) {
		if (mls != null) {
			return mls.getStringMap().get(lang);
		}

		return null;
	}
}
