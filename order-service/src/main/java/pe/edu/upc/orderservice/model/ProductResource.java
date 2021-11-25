package pe.edu.upc.orderservice.model;

public class ProductResource {
    private Long id;
    private String name;
    private String description;
    private String corporation;
    private Double price;
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public ProductResource setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }


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
}
