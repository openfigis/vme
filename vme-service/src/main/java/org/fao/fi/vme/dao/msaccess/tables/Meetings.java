package org.fao.fi.vme.dao.msaccess.tables;

import java.net.MalformedURLException;
import java.net.URL;

import org.fao.fi.vme.dao.msaccess.VmeDaoException;
import org.fao.fi.vme.dao.msaccess.formatter.MeetingDateParser;
import org.fao.fi.vme.dao.msaccess.mapping.TableDomainMapper;
import org.fao.fi.vme.domain.Meeting;

public class Meetings implements TableDomainMapper {

	private int ID;
	private String RFB_ID;
	private int Year_ID;
	private String Meeting_Date;
	private String Report_Summary;
	private String Committee;
	private String Citation;
	private String Link_Tagged_File;
	private String Link_Source;

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

	public String getLink_Tagged_File() {
		return Link_Tagged_File;
	}

	public void setLink_Tagged_File(String link_Tagged_File) {
		Link_Tagged_File = link_Tagged_File;
	}

	public String getLink_Source() {
		return Link_Source;
	}

	public void setLink_Source(String link_Source) {
		Link_Source = link_Source;
	}

	@Override
	public Object map() {
		Meeting o = new Meeting();
		o.setCommittee(this.Committee);

		MeetingDateParser p = new MeetingDateParser(this.Meeting_Date);
		o.setStart(p.getStart());
		o.setEnd(p.getEnd());

		// o.setGeneralMeasures(this.G)
		o.setId(this.ID);
		try {
			URL url = new URL(this.getLink_Source());
			o.getMeetingDocument().setUrl(url);
		} catch (MalformedURLException e) {
			throw new VmeDaoException(e);
		}
		o.getMeetingDocument().setCitation(this.getLink_Tagged_File());
		o.setReportSummary(this.getReport_Summary());
		// o.setSpecificMeasures(specificMeasures)

		o.setYear(this.Year_ID);

		return o;
	}
}
