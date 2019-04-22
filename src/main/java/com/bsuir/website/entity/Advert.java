package com.bsuir.website.entity;

import com.bsuir.website.constants.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(JsonViews.Public.class)
    private Integer id;

    @JsonView(JsonViews.Public.class)
    private String name;

    @JsonView(JsonViews.Public.class)
    private Double price;

    @JsonView(JsonViews.Public.class)
    private String country;

    @JsonView(JsonViews.Public.class)
    private Integer guaranteeAmountOfMonth;

    @Column(columnDefinition="TEXT")
    @JsonView(JsonViews.Public.class)
    private String description;

    @JsonView(JsonViews.Public.class)
    private String groupName;

    @JsonView(JsonViews.Public.class)
    private String imageName;

    @OneToMany(mappedBy = "advert", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private Set<OrderItem> items = new HashSet<>();

    public Advert() {
    }

    public Advert(String name, Double price, String country, Integer guaranteeAmountOfMonth, String description, String groupName) {
        this.name = name;
        this.price = price;
        this.country = country;
        this.guaranteeAmountOfMonth = guaranteeAmountOfMonth;
        this.description = description;
        this.groupName = groupName;
        this.imageName = "testAdvert.png";
    }

    public Advert(String name, Double price, String country, Integer guaranteeAmountOfMonth, String description, String groupName, String imageName) {
        this(name, price, country, guaranteeAmountOfMonth, description, groupName);
        this.imageName = imageName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getGuaranteeAmountOfMonth() {
        return guaranteeAmountOfMonth;
    }

    public void setGuaranteeAmountOfMonth(Integer guaranteeAmountOfMonth) {
        this.guaranteeAmountOfMonth = guaranteeAmountOfMonth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }
}
