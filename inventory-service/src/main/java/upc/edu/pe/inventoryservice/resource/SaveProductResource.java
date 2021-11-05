package upc.edu.pe.inventoryservice.resource;

import java.util.Date;

public class SaveProductResource {
    private String name;
    private String description;
    private String corporation;
    private Double price;
    private Date createAt;
    private String status;

    public String getName() {
        return name;
    }

    public SaveProductResource setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SaveProductResource setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCorporation() {
        return corporation;
    }

    public SaveProductResource setCorporation(String corporation) {
        this.corporation = corporation;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public SaveProductResource setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public SaveProductResource setCreateAt(Date createAt) {
        this.createAt = createAt;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SaveProductResource setStatus(String status) {
        this.status = status;
        return this;
    }
}
