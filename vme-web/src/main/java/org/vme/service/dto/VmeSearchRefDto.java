package org.vme.service.dto;


public class VmeSearchRefDto {


	
	private int  id;
	
	private String lang;
	
	private String name;

	/**
	 * 
	 */
	public VmeSearchRefDto() {
		
	}
	
	
	

	/**
	 * @param id
	 * @param lang
	 * @param name
	 */
	public VmeSearchRefDto(int id, String lang, String name) {
		super();
		this.id = id;
		this.lang = lang;
		this.name = name;
	}




	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	



	
	
	
	
}
