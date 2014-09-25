package org.fao.fi.vme.domain.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.support.MultiLingualStringConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenceReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGRichInput;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGSimpleReference;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGWeight;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.LongDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.ReferenceReport;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@RSGReferenceReport(name = "VME Regional Definitions")
@Entity(name = "REGIONAL_DEFINITIONS")
public class RegionalDefinitions implements ObjectId<Long>, ReferenceReport, Serializable {
	private static final long serialVersionUID = -6703934250783141637L;

	@RSGIdentifier
	@RSGConverter(LongDataConverter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@RSGName("Authority")
	@RSGSimpleReference
	@RSGWeight(0)
	@ManyToOne(fetch = FetchType.EAGER)
	private Rfmo rfmo;

	@RSGName("Category")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(1)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString category;

	@RSGName("Title")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(2)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString title;

	@RSGName("Description")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(3)
	@OneToOne(cascade = { CascadeType.ALL })
	private @RSGRichInput MultiLingualString description;
	
	@RSGName("Source")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(4)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString source;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Rfmo getRfmo() {
		return rfmo;
	}

	public void setRfmo(Rfmo rfmo) {
		this.rfmo = rfmo;
	}

	/**
	 * @return the 'category' value
	 */
	public MultiLingualString getCategory() {
		return this.category;
	}

	/**
	 * @param category the 'category' value to set
	 */
	public void setCategory(MultiLingualString category) {
		this.category = category;
	}

	/**
	 * @return the 'title' value
	 */
	public MultiLingualString getTitle() {
		return this.title;
	}

	/**
	 * @param title the 'title' value to set
	 */
	public void setTitle(MultiLingualString title) {
		this.title = title;
	}

	/**
	 * @return the 'description' value
	 */
	public MultiLingualString getDescription() {
		return this.description;
	}

	/**
	 * @param description the 'description' value to set
	 */
	public void setDescription(MultiLingualString description) {
		this.description = description;
	}

	/**
	 * @return the 'source' value
	 */
	public MultiLingualString getSource() {
		return this.source;
	}

	/**
	 * @param source the 'source' value to set
	 */
	public void setSource(MultiLingualString source) {
		this.source = source;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.category == null) ? 0 : this.category.hashCode());
		result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.rfmo == null) ? 0 : this.rfmo.hashCode());
		result = prime * result + ((this.source == null) ? 0 : this.source.hashCode());
		result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegionalDefinitions other = (RegionalDefinitions) obj;
		if (this.category == null) {
			if (other.category != null)
				return false;
		} else if (!this.category.equals(other.category))
			return false;
		if (this.description == null) {
			if (other.description != null)
				return false;
		} else if (!this.description.equals(other.description))
			return false;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.rfmo == null) {
			if (other.rfmo != null)
				return false;
		} else if (!this.rfmo.equals(other.rfmo))
			return false;
		if (this.source == null) {
			if (other.source != null)
				return false;
		} else if (!this.source.equals(other.source))
			return false;
		if (this.title == null) {
			if (other.title != null)
				return false;
		} else if (!this.title.equals(other.title))
			return false;
		return true;
	}
}
