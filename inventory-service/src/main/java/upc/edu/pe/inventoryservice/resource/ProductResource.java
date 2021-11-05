package upc.edu.pe.inventoryservice.resource;

import upc.edu.pe.inventoryservice.entities.Category;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class ProductResource {
    private Long id;
    private String name;
    private String description;
    private String corporation;
    private Double price;
    private Date createAt;
    private String status;

    public Long getId() {
        return id;
    }

    public ProductResource setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductResource setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductResource setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCorporation() {
        return corporation;
    }

    public ProductResource setCorporation(String corporation) {
        this.corporation = corporation;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ProductResource setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public ProductResource setCreateAt(Date createAt) {
        this.createAt = createAt;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ProductResource setStatus(String status) {
        this.status = status;
        return this;
    }
}
