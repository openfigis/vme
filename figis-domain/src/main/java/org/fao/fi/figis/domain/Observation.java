package org.fao.fi.figis.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "FS_OBSERVATION")
public class Observation {

	// <id name="id" column="CD_OBSERVATION" type="integer">
	// <generator class="assigned"/>
	// </id>
	// <property name="order" column="OBS_ORDER" type="short" not-null="true"/>
	// <property name="primary" column="FG_PRIMARY" type="boolean" not-null="true"/>
	// <property name="reference" column="FG_REFERENCE" type="boolean" not-null="true"/>
	// <many-to-one name="collection" column="CD_COLLECTION" cascade="none" lazy="false" not-null="true"/>
	// <many-to-one name="coverPage" column="CD_COVER_PAGE" cascade="none" lazy="false"/>

	@Column(name = "CD_OBSERVATION", nullable = false)
	@Id
	private int id;

	@Column(name = "FG_PRIMARY", nullable = false)
	private boolean primary;

	@Column(name = "FG_REFERENCE", nullable = false)
	private boolean reference;

	@Column(name = "CD_COLLECTION", nullable = false)
	private int collection;

	@OneToMany(mappedBy = "observation")
	private List<ObservationXml> observationsPerLanguage;

}
