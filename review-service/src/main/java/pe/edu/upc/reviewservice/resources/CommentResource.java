package pe.edu.upc.reviewservice.resources;

import pe.edu.upc.reviewservice.model.Product;
import pe.edu.upc.reviewservice.model.User;

public class CommentResource {
    private Long id;
    private String message;
    private User user;
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
