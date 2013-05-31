package org.fao.fi.figis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "FS_OBSERVATION_XML")
public class ObservationXml {

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

	@Column(name = "CD_XML", nullable = false)
	@Id
	private String id;

	@ManyToOne
	private Observation observation;

	@Column(name = "CD_LANGUAGE", nullable = false)
	private int language;

	@Column(name = "FG_STATUS", nullable = false)
	private int status;

}
