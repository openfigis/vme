package org.fao.fi.figis.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REF_VME", schema = "figis")
public class RefVme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 668544146447799769L;

	@Id
	@Column(name = "CD_VME")
	private Long id;

	@Column(name = "CD_META")
	private Integer meta;

	@Column(name = "INVENTORY_ID")
	private String inventoryId;

	@Column(name = "NAME_A")
	private String nameA;

	@Column(name = "NAME_C")
	private String nameC;

	@Column(name = "NAME_E")
	private String nameE;

	@Column(name = "NAME_F")
	private String nameF;

	@Column(name = "NAME_R")
	private String nameR;

	@Column(name = "NAME_S")
	private String nameS;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMeta() {
		return meta;
	}

	public void setMeta(Integer meta) {
		this.meta = meta;
	}

	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getNameA() {
		return nameA;
	}

	public void setNameA(String nameA) {
		this.nameA = nameA;
	}

	public String getNameC() {
		return nameC;
	}

	public void setNameC(String nameC) {
		this.nameC = nameC;
	}

	public String getNameE() {
		return nameE;
	}

	public void setNameE(String nameE) {
		this.nameE = nameE;
	}

	public String getNameF() {
		return nameF;
	}

	public void setNameF(String nameF) {
		this.nameF = nameF;
	}

	public String getNameR() {
		return nameR;
	}

	public void setNameR(String nameR) {
		this.nameR = nameR;
	}

	public String getNameS() {
		return nameS;
	}

	public void setNameS(String nameS) {
		this.nameS = nameS;
	}

}