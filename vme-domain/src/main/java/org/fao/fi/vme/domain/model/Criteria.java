package org.fao.fi.vme.domain.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenceReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.LongDataConverter;

/**
 * Criteria is related to a Vme. There is also the VmeCriteria, which is the
 * lookup table for Criteria.
 * 
 * This table has been added as a result of the Vme Workshop Rome 2014
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
@RSGReferenceReport(name = "VmeCritera")
public class Criteria implements ObjectId<Long>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6262852470337303047L;

	@RSGIdentifier("id")
	@RSGConverter(LongDataConverter.class)
	@Id
	private Long id;

	/**
	 * 
	 */
	@OneToOne(cascade = CascadeType.MERGE)
	private Vme vme;

	public Vme getVme() {
		return vme;
	}

	public void setVme(Vme vme) {
		this.vme = vme;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
