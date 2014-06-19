package org.fao.fi.vme.msaccess.tables;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.reference.InformationSourceType;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.msaccess.formatter.MeetingDateParser;
import org.fao.fi.vme.msaccess.mapping.ReferenceDependentTableDomainMapper;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.ReferenceConceptProvider;

public class Meetings implements ReferenceDependentTableDomainMapper {
	final static private Long DEFAULT_INFORMATION_SOURCE_TYPE_ID = 2L; // Meeting
																		// documents

	@PostConstruct
	public void foobaz() {
		System.out.println("HEREIAM!");
	}

	private int ID;
	private String RFB_ID;
	private int Year_ID;
	private String Meeting_Date;
	private String Report_Summary;
	private String Committee;
	private String Citation;
	private String Link_Source;
	private int Source_Type;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getRFB_ID() {
		return RFB_ID;
	}

	public void setRFB_ID(String rFB_ID) {
		RFB_ID = rFB_ID;
	}

	public int getYear_ID() {
		return Year_ID;
	}

	public void setYear_ID(int year_ID) {
		Year_ID = year_ID;
	}

	public String getMeeting_Date() {
		return Meeting_Date;
	}

	public void setMeeting_Date(String meeting_Date) {
		Meeting_Date = meeting_Date;
	}

	public String getReport_Summary() {
		return Report_Summary;
	}

	public void setReport_Summary(String report_Summary) {
		Report_Summary = report_Summary;
	}

	public String getCommittee() {
		return Committee;
	}

	public void setCommittee(String committee) {
		Committee = committee;
	}

	public String getCitation() {
		return Citation;
	}

	public void setCitation(String citation) {
		Citation = citation;
	}

	public String getLink_Source() {
		return Link_Source;
	}

	public void setLink_Source(String link_Source) {
		Link_Source = link_Source;
	}

	public int getSource_Type() {
		return Source_Type;
	}

	public void setSource_Type(int source_Type) {
		Source_Type = source_Type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fao.fi.vme.msaccess.mapping.TableDomainMapper#map(org.gcube.application
	 * .
	 * rsg.support.compiler.bridge.interfaces.reference.ReferenceConceptProvider
	 * )
	 */
	@Override
	public Object map(ReferenceConceptProvider<Long> provider) {
		InformationSource is = new InformationSource();

		InformationSourceType defaultInformationSourceType = null;

		try {
			defaultInformationSourceType = (InformationSourceType) provider.getReferenceByID(
					InformationSourceType.class, DEFAULT_INFORMATION_SOURCE_TYPE_ID);
		} catch (Throwable t) {
			throw new RuntimeException("Unable to retrieve default information source type", t);
		}

		is.setSourceType(defaultInformationSourceType);
		MultiLingualStringUtil u = new MultiLingualStringUtil();
		is.setCommittee(u.english(this.Committee));

		if (this.Meeting_Date != null) {
			MeetingDateParser p = new MeetingDateParser(this.Meeting_Date);
			is.setMeetingStartDate(p.getStart());
			is.setMeetingEndDate(p.getEnd());
		}

		is.setId(Long.valueOf(this.ID));
		is.setReportSummary(u.english(this.getReport_Summary()));
		is.setPublicationYear(this.Year_ID);

		try {
			is.setSourceType((InformationSourceType) provider.getReferenceByID(InformationSourceType.class, new Long(
					Source_Type)));
		} catch (Throwable t) {
			throw new RuntimeException("Unable build information source type", t);
		}

		try {
			URL url = new URL(this.getLink_Source());
			is.setUrl(url);
		} catch (MalformedURLException e) {
			throw new VmeException(e);
		}

		is.setCitation(u.english(this.getCitation()));

		if (is.getSourceType() == null || is.getSourceType().getId() == 0) {
			throw new VmeException("Unable to retrieve default information source type" + this.Source_Type);
		}

		return is;
	}

	@Override
	public Object map() {
		throw new RuntimeException("Unimplemented");
	}
}
