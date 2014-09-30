package org.fao.fi.vme.batch.sync2.mapping;

import org.apache.commons.lang.StringUtils;
import org.fao.fi.figis.devcon.BiblioEntry;
import org.fao.fi.figis.devcon.ObjectFactory;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.batch.sync2.mapping.xml.AddWhenContentRule;
import org.fao.fi.vme.batch.sync2.mapping.xml.CdataUtil;
import org.fao.fi.vme.batch.sync2.mapping.xml.DateFormatter;
import org.fao.fi.vme.domain.model.InformationSource;
import org.purl.agmes._1.CreatorCorporate;
import org.purl.dc.elements._1.Date;
import org.purl.dc.elements._1.Identifier;
import org.purl.dc.elements._1.Type;
import org.purl.dc.terms.Abstrakt;
import org.purl.dc.terms.BibliographicCitation;
import org.purl.dc.terms.Created;

public class BiblioEntryFromInformationSource {

	private ObjectFactory f = new ObjectFactory();
	private AddWhenContentRule<Object> rule = new AddWhenContentRule<Object>();
	private CdataUtil cu = new CdataUtil();

	private org.purl.dc.elements._1.ObjectFactory dcf = new org.purl.dc.elements._1.ObjectFactory();

	private DateFormatter df = new DateFormatter();

	public BiblioEntry transform(InformationSource is) {
		BiblioEntry be = null;

		if (is != null) {
			be = f.createBiblioEntry();
			BibliographicCitation citation = new BibliographicCitation();

			citation.setContent(cu.getCdataString(is.getCitation()));
			be.getContent().add(citation);
			rule.check(cu.getCdataString(is.getCitation()));

			if (is.getUrl() != null && !StringUtils.isBlank(is.getUrl().getPath())) {
				Identifier identifier = new Identifier();
				identifier.setType("URI");
				identifier.setContent(is.getUrl().toString());
				new AddWhenContentRule<Object>().check(is.getUrl()).beforeAdding(identifier).to(be.getContent());
			}

			Type type = dcf.createType();
			type.setType(is.getSourceType() == null ? null : String.valueOf(is.getSourceType().getId()));
			type.setContent(is.getSourceType() == null ? null : is.getSourceType().getName());

			be.getContent().add(type);

			CreatorCorporate cc = new CreatorCorporate();
			cc.setContent(cu.getCdataString(is.getCommittee()));
			be.getContent().add(cc);

			// Created
			// publicationYear
			// fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/dcterms:Created
			if (is.getPublicationYear() != null && is.getPublicationYear() > 0) {
				Created created = new Created();
				created.setContent(Integer.toString(is.getPublicationYear()));
				be.getContent().add(created);
			} else {
				throw new VmeException("is without valid publicationYear");
			}

			// meetingStartDate - meetingEndDate
			// fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/dc:Date
			if (is.getMeetingStartDate() != null) {
				Date createDate = dcf.createDate();
				createDate.setContent(df.format(is.getMeetingStartDate(), is.getMeetingEndDate()));
				be.getContent().add(createDate);
			}

			Abstrakt bibAbstract = new Abstrakt();

			bibAbstract.setContent(cu.getCdataString(is.getReportSummary()));
			be.getContent().add(bibAbstract);

		}
		return be;
	}
}
