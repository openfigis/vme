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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Meetings implements ReferenceDependentTableDomainMapper {
	private static final Long DEFAULT_INFORMATION_SOURCE_TYPE_ID = 2L; // Meeting
																		// documents
	private static final Logger log = LoggerFactory.getLogger(Meetings.class);
	
	@PostConstruct
	public void foobaz() {
		log.info("HEREIAM!");
	}

	private int iD;
	private String rfbId;
	private int yearId;
	private String meetingDate;
	private String reportSummary;
	private String committee;
	private String citation;
	private String linkSource;
	private int sourceType;

	public int getID() {
		return iD;
	}

	public void setID(int iD) {
		this.iD = iD;
	}

	public String getRfbId() {
		return rfbId;
	}

	public void setRfbId(String rfbId) {
		this.rfbId = rfbId;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public String getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getReportSummary() {
		return reportSummary;
	}

	public void setReportSummary(String reportSummary) {
		this.reportSummary = reportSummary;
	}

	public String getCommittee() {
		return committee;
	}

	public void setCommittee(String committee) {
		this.committee = committee;
	}

	public String getCitation() {
		return citation;
	}

	public void setCitation(String citation) {
		this.citation = citation;
	}

	public String getLinkSource() {
		return linkSource;
	}

	public void setLinkSource(String linkSource) {
		this.linkSource = linkSource;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
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
		} catch (Exception t) {
			throw new RuntimeException("Unable to retrieve default information source type", t);
		}

		is.setSourceType(defaultInformationSourceType);
		MultiLingualStringUtil u = new MultiLingualStringUtil();
		is.setCommittee(u.english(this.committee));

		if (this.meetingDate != null) {
			MeetingDateParser p = new MeetingDateParser(this.meetingDate);
			is.setMeetingStartDate(p.getStart());
			is.setMeetingEndDate(p.getEnd());
		}

		is.setId(Long.valueOf(this.iD));
		is.setReportSummary(u.english(this.getReportSummary()));
		is.setPublicationYear(this.yearId);

		try {
			is.setSourceType((InformationSourceType) provider.getReferenceByID(InformationSourceType.class, Long.valueOf(
					sourceType)));
		} catch (Exception t) {
			throw new RuntimeException("Unable build information source type", t);
		}

		try {
			URL url = new URL(this.getLinkSource());
			is.setUrl(url);
		} catch (MalformedURLException e) {
			throw new VmeException(e);
		}

		is.setCitation(u.english(this.getCitation()));

		if (is.getSourceType() == null || is.getSourceType().getId() == 0) {
			throw new VmeException("Unable to retrieve default information source type" + this.sourceType);
		}

		return is;
	}

	@Override
	public Object map() {
		throw new RuntimeException("Unimplemented");
	}
}
