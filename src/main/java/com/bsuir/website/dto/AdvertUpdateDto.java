package com.bsuir.website.dto;

public class AdvertUpdateDto {
    private Integer advertId;
    private String advertName;
    private String advertGroup;
    private Double advertPrice;
    private String advertCountry;
    private Integer advertGuarantee;
    private String advertDescription;

    public AdvertUpdateDto() {
    }

    public Integer getAdvertId() {
        return advertId;
    }

    public void setAdvertId(Integer advertId) {
        this.advertId = advertId;
    }

    public String getAdvertName() {
        return advertName;
    }

    public void setAdvertName(String advertName) {
        this.advertName = advertName;
    }

    public String getAdvertGroup() {
        return advertGroup;
    }

    public void setAdvertGroup(String advertGroup) {
        this.advertGroup = advertGroup;
    }

    public Double getAdvertPrice() {
        return advertPrice;
    }

    public void setAdvertPrice(Double advertPrice) {
        this.advertPrice = advertPrice;
    }

    public String getAdvertCountry() {
        return advertCountry;
    }

    public void setAdvertCountry(String advertCountry) {
        this.advertCountry = advertCountry;
    }

    public Integer getAdvertGuarantee() {
        return advertGuarantee;
    }

    public void setAdvertGuarantee(Integer advertGuarantee) {
        this.advertGuarantee = advertGuarantee;
    }

    public String getAdvertDescription() {
        return advertDescription;
    }

    public void setAdvertDescription(String advertDescription) {
        this.advertDescription = advertDescription;
    }
}
