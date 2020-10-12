package com.selab.uidesignserver.entity.serviceComponent;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "\"ServiceComponent\"")
public class ServiceComponentTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceID")
    private int serviceID;

    @Column(name = "argumentcount")
    private int argumentCount;

    @Column(name = "code")
    private String code;

    @Column(name = "docString")
    private String docString;

    @Column(name = "isDatabase")
    private boolean isDatabase;

    @Column(name = "isWeb")
    private boolean isWeb;

    @Column(name = "methodException")
    private String methodException;

    @Column(name = "name")
    private String name;

    @Column(name = "frameworkTypeID")
    private int frameworkTypeID;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "classID")
    private ClassTable klass;

    @Column(name = "projectID")
    private int projectID;

    @Column(name = "serviceTypeID")
    private int serviceTypeID;

    public ServiceComponentTable() {

    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getArgumentCount() {
        return argumentCount;
    }

    public String getDocString() {
        return docString;
    }

    public int getFrameworkTypeID() {
        return frameworkTypeID;
    }

    public String getMethodException() {
        return methodException;
    }
    
    public ClassTable getKlass() {
        return klass;
    }

    public int getProjectID() {
        return projectID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public int getServiceTypeID() {
        return serviceTypeID;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArgumentCount(int argumentCount) {
        this.argumentCount = argumentCount;
    }

    public void setDatabase(boolean isDatabase) {
        this.isDatabase = isDatabase;
    }

    public void setDocString(String docString) {
        this.docString = docString;
    }

    public void setFrameworkTypeID(int frameworkTypeID) {
        this.frameworkTypeID = frameworkTypeID;
    }

    public void setMethodException(String methodException) {
        this.methodException = methodException;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public void setServiceTypeID(int serviceTypeID) {
        this.serviceTypeID = serviceTypeID;
    }

    public void setWeb(boolean isWeb) {
        this.isWeb = isWeb;
    }
    
    public void setKlass(ClassTable klass) {
        this.klass = klass;
    }
}
