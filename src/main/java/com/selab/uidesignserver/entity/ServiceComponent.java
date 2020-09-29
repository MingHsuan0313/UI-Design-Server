package com.selab.uidesignserver.entity;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"ServiceComponent\"")
public class ServiceComponent {

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

    @Column(name = "classID")
    private int classID;

    @Column(name = "projectID")
    private int projectID;

    @Column(name = "serviceTypeID")
    private int serviceTypeID;

    public ServiceComponent() {

    }

    public ServiceComponent(int theArgumentCount, String theCode, String theDocString, String theIsDatabase,
            String theIsWeb, String theMethodException, String theName, int theFrameworkTypeID, int theClassID,
            int theProjectID, int theServiceTypeID) {
        this.argumentCount = theArgumentCount;
        this.code = theCode;
        this.name = theName;
        this.docString = theDocString;

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

    public int getClassID() {
        return classID;
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

    public void setClassID(int classID) {
        this.classID = classID;
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
}
