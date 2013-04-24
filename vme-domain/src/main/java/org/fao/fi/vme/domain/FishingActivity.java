package org.fao.fi.vme.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class FishingActivity {

	/** 
	 * 
	 */
	@Id
	@GeneratedValue
	private int fishingActivityId;

	private Date updatedInYear;

	/** */
	private String rfbFishingAreaGeneralText;

	/** */
	private String vmeGeneralText;

}
