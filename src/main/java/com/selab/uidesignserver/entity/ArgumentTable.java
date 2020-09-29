package com.selab.uidesignserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"Argument\"")
public class ArgumentTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "argumentID")
    private int argumentID;
    
    @Column(name = " name")
    private String name;
    
    @Column(name = "nth")
    private int nth;
    
    @Column(name = "primitiveType")
    private String primitiveType;
    
    @Column(name = "complexTypeID")
    private int complexTypeID;
    
    @Column(name = "serviceID")
    private int serviceID;
    
    public ArgumentTable() {};
    
    public void setArgumentID(int argumentID) {
        this.argumentID = argumentID;
    }
    
    public void setComplexTypeID(int complexTypeID) {
        this.complexTypeID = complexTypeID;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setNth(int nth) {
        this.nth = nth;
    }
    
    public void setPrimitiveType(String primitiveType) {
        this.primitiveType = primitiveType;
    }
    
    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }
    
    public int getArgumentID() {
        return argumentID;
    }
    
    public int getComplexTypeID() {
        return complexTypeID;
    }
    
    public String getName() {
        return name;
    }
    
    public int getNth() {
        return nth;
    }
    
    public String getPrimitiveType() {
        return primitiveType;
    }
    
    public int getServiceID() {
        return serviceID;
    }
}
