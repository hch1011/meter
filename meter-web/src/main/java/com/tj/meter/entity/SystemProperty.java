package com.tj.meter.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device_type")
public class SystemProperty implements Serializable{
	private static final long serialVersionUID = 1L;
	 
	@Id
	@Column(name = "code")
	private String code;
	
	@Column(name = "value")
	private String value;	
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "notes")
	private String notes;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}