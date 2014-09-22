package org.fao.fi.vme.domain.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

@Entity
public class MediaReference {

	@RSGIdentifier
	@RSGConverter(LongDataConverter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Vme vme;

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

	public Vme getVme() {
		return vme;
	}

	public void setVme(Vme vme) {
		this.vme = vme;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((credits == null) ? 0 : credits.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((vme == null) ? 0 : vme.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MediaReference other = (MediaReference) obj;
		if (credits == null) {
			if (other.credits != null) {
				return false;
			}
		} else if (!credits.equals(other.credits)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		if (vme == null) {
			if (other.vme != null) {
				return false;
			}
		} else if (!vme.equals(other.vme)) {
			return false;
		}
		return true;
	}

}
