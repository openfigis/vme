package org.fao.fi.figis.domain;

import java.io.Serializable;
import java.sql.Clob;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FS_OBSERVATION_XML", schema = "figis")
public class ObservationXml implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5224910306107015509L;

	// <class name="FsLanguageXml" table="FS_OBSERVATION_XML">
	// <id name="id" column="CD_XML" type="string">
	// <generator class="assigned" />
	// </id>
	// <many-to-one name="parent" column="CD_OBSERVATION" cascade="none" lazy="false" not-null="true"
	// class="org.fao.fi.figis.fs.common.data.object.FsObservation" />
	// <many-to-one name="lang" column="CD_LANGUAGE" cascade="none" lazy="false" not-null="true"
	// class="org.fao.fi.figis.util.common.Language" />
	// <property name="default" column="FG_DEFAULT" type="boolean" not-null="true" />
	// <property name="status" column="FG_STATUS" type="integer" not-null="true" />
	// <property name="valid" column="FG_VALID" type="integer" not-null="true" />
	// <component name="inputSource" class="org.fao.fi.figis.fs.common.data.object.FsInputSource">
	// <property name="creationDate" type="date" column="DT_CREATION" not-null="false" />
	// <property name="lastEditDate" type="date" column="DT_LAST_EDIT" not-null="false" />
	// <many-to-one name="editor" cascade="none" column="CD_EDITOR" not-null="false"
	// class="org.fao.fi.figis.util.db.TransactionUserFaoUser" />
	// <property name="sessionId" type="integer" column="CD_TRANSACTION" />
	// <property name="filename" type="string" column="FILE_NAME" />
	// </component>
	// <!-- <many-to-one name="transaction" cascade="none" column="CD_TRANSACTION"
	// class="org.fao.fi.figis.util.db.AppTransaction"/> -->
	// <property name="xmlAsString" column="XML" type="text" length="10000000" />
	// </class>

	@Id
	@Column(name = "CD_XML", unique = true, nullable = false, length = 765)
	private String id;

	// bi-directional many-to-one association to FsObservation
	@ManyToOne
	@JoinColumn(name = "CD_OBSERVATION")
	private Observation observation;

	@Column(name = "CD_LANGUAGE", nullable = false, precision = 10)
	private int language;

	@Column(name = "FG_STATUS", nullable = false)
	private int status;

	@Column(name = "DT_CREATION", nullable = false)
	private Date creationDate;

	@Column(name = "DT_LAST_EDIT", nullable = false)
	private Date lastEditDate;

	private Clob xml;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Observation getObservation() {
		return observation;
	}

	public void setObservation(Observation observation) {
		this.observation = observation;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastEditDate() {
		return lastEditDate;
	}

	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}

	public Clob getXml() {
		return xml;
	}

	public void setXml(Clob xml) {
		this.xml = xml;
	}

}
