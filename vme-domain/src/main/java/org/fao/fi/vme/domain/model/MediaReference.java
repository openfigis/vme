package org.fao.fi.vme.domain.model;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.model.reference.MediaType;
import org.fao.fi.vme.domain.support.MultiLingualStringConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGMandatory;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGOneAmong;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGWeight;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.constants.ConceptData;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.LongDataConverter;

/**
 * For media, through some preliminary investigations I understand that and easy
 * and quick way would be to have such images loaded somewhere (i.e. in FIGIS
 * but also elsewhere) and linked through a URL in the RSG. Metadata such as
 * Type (e.g. video, image), Title, Description and Credits should be associated
 * to each URL.
 * 
 * @author Erik van Ingen
 *
 */

// @Entity
public class MediaReference {

	@RSGIdentifier
	@RSGConverter(LongDataConverter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@RSGName(value = "Media Type", hideHeading = true)
	@RSGMandatory
	@RSGOneAmong(concept = MediaType.class, label = ConceptData.NAME, value = ConceptData.ID)
	@RSGConverter(LongDataConverter.class)
	@RSGWeight(2)
	private Long type;

	@RSGName("Title")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGMandatory
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString title;

	@RSGName("Description")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGMandatory
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString description;

	@RSGName("Credits")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGMandatory
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString credits;

	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public MultiLingualString getTitle() {
		return title;
	}

	public void setTitle(MultiLingualString title) {
		this.title = title;
	}

	public MultiLingualString getDescription() {
		return description;
	}

	public void setDescription(MultiLingualString description) {
		this.description = description;
	}

	public MultiLingualString getCredits() {
		return credits;
	}

	public void setCredits(MultiLingualString credits) {
		this.credits = credits;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
