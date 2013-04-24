package org.fao.fi.vme.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * 
 * 
 * @author Erik van Ingen
 */
@Entity
public class Meeting {

	@Id
	@GeneratedValue
	private int meetingId;

	/** */
	private Date date;

	/** */
	private String reportSummary;

	/** */
	private String committee;

	/**
	 * The Vme where the meeting is on about.
	 */
	public Vme discussedVme;

	/**
	 * meetingDocument
	 */
	public Source meetingDocument;

}
