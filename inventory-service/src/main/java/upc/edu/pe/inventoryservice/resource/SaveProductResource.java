package upc.edu.pe.inventoryservice.resource;

import java.util.Date;

public class SaveProductResource {
    private String name;
    private String description;
    private String corporation;
    private Double price;
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public SaveProductResource setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }



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
}
