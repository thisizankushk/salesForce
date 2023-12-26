package com.example.salesforce.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetailerVisitModel {

    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("reasonofvisit")
    @Expose
    private String reasonofvisit;
    @SerializedName("createdate")
    @Expose
    private String createdate;
    @SerializedName("activitydate")
    @Expose
    private String activitydate;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("Createdby")
    @Expose
    private String createdby;
    @SerializedName("updatestatus")
    @Expose
    private String updatestatus;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("bytearray")
    @Expose
    private String bytearray;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getReasonofvisit() {
        return reasonofvisit;
    }

    public void setReasonofvisit(String reasonofvisit) {
        this.reasonofvisit = reasonofvisit;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getActivitydate() {
        return activitydate;
    }

    public void setActivitydate(String activitydate) {
        this.activitydate = activitydate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getUpdatestatus() {
        return updatestatus;
    }

    public void setUpdatestatus(String updatestatus) {
        this.updatestatus = updatestatus;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBytearray() {
        return bytearray;
    }

    public void setBytearray(String bytearray) {
        this.bytearray = bytearray;
    }

}
