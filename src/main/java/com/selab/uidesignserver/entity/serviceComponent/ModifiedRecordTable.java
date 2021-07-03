package com.selab.uidesignserver.entity.serviceComponent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Modified_Record")
public class ModifiedRecordTable {
	@Id
	@Column(name="modified_recordID", nullable = false)
	private String modifiedRecordID;

	@Column(name="methodSignature", nullable = true)
	private String methodSignature;

	@Column(name="serviceID", nullable = true)
	private String serviceID;

	public ModifiedRecordTable() {}

	public ModifiedRecordTable(String modifiedRecordID, String methodSignature, String serviceID) {
		this.modifiedRecordID = modifiedRecordID;
		this.methodSignature = methodSignature;
		this.serviceID = serviceID;
	}

	public String getModifiedRecordID() {
		return modifiedRecordID;
	}

	public String getMethodSignature() {
		return methodSignature;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setMethodSignature(String methodSignature) {
		this.methodSignature = methodSignature;
	}

	public void setModifiedRecordID(String modifiedRecordID) {
		this.modifiedRecordID = modifiedRecordID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
}
