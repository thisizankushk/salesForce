package com.example.salesforce.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReasonOfVisit {

    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("reason")
    @Expose
    private String reason;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}

