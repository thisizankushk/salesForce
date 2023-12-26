package com.example.salesforce.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetailerResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Data {

        @SerializedName("StoreId")
        @Expose
        private Integer storeId;
        @SerializedName("StoreMob")
        @Expose
        private String storeMob;
        @SerializedName("StoreName")
        @Expose
        private String storeName;
        @SerializedName("StoreState")
        @Expose
        private String storeState;
        @SerializedName("StoreStateId")
        @Expose
        private Integer storeStateId;
        @SerializedName("StoreDist")
        @Expose
        private String storeDist;
        @SerializedName("StoreDistId")
        @Expose
        private Integer storeDistId;

        public Integer getStoreId() {
            return storeId;
        }

        public void setStoreId(Integer storeId) {
            this.storeId = storeId;
        }

        public String getStoreMob() {
            return storeMob;
        }

        public void setStoreMob(String storeMob) {
            this.storeMob = storeMob;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreState() {
            return storeState;
        }

        public void setStoreState(String storeState) {
            this.storeState = storeState;
        }

        public Integer getStoreStateId() {
            return storeStateId;
        }

        public void setStoreStateId(Integer storeStateId) {
            this.storeStateId = storeStateId;
        }

        public String getStoreDist() {
            return storeDist;
        }

        public void setStoreDist(String storeDist) {
            this.storeDist = storeDist;
        }

        public Integer getStoreDistId() {
            return storeDistId;
        }

        public void setStoreDistId(Integer storeDistId) {
            this.storeDistId = storeDistId;
        }

    }

}
