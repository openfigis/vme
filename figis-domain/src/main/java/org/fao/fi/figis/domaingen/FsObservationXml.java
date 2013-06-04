package org.fao.fi.figis.domaingen;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FS_OBSERVATION_XML database table.
 * 
 */
@Entity
@Table(name="FS_OBSERVATION_XML")
public class FsObservationXml implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_XML", unique=true, nullable=false, length=765)
	private String cdXml;

	@Column(name="CD_LANGUAGE", nullable=false, precision=10)
	private BigDecimal cdLanguage;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_CREATION", nullable=false)
	private Date dtCreation;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_LAST_EDIT", nullable=false)
	private Date dtLastEdit;

	@Column(name="FG_STATUS", nullable=false, precision=10)
	private BigDecimal fgStatus;

	@Lob
	private String xml;

	//bi-directional many-to-one association to FsObservation
	@ManyToOne
	@JoinColumn(name="CD_OBSERVATION")
	private FsObservation fsObservation;

	public FsObservationXml() {
	}

	public String getCdXml() {
		return this.cdXml;
	}

	public void setCdXml(String cdXml) {
		this.cdXml = cdXml;
	}

	public BigDecimal getCdLanguage() {
		return this.cdLanguage;
	}

	public void setCdLanguage(BigDecimal cdLanguage) {
		this.cdLanguage = cdLanguage;
	}

	public Date getDtCreation() {
		return this.dtCreation;
	}

	public void setDtCreation(Date dtCreation) {
		this.dtCreation = dtCreation;
	}

	public Date getDtLastEdit() {
		return this.dtLastEdit;
	}

	public void setDtLastEdit(Date dtLastEdit) {
		this.dtLastEdit = dtLastEdit;
	}

	public BigDecimal getFgStatus() {
		return this.fgStatus;
	}

	public void setFgStatus(BigDecimal fgStatus) {
		this.fgStatus = fgStatus;
	}

	public String getXml() {
		return this.xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public FsObservation getFsObservation() {
		return this.fsObservation;
	}

	public void setFsObservation(FsObservation fsObservation) {
		this.fsObservation = fsObservation;
	}

}