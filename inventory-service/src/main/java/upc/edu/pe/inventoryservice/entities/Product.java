package upc.edu.pe.inventoryservice.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import upc.edu.pe.inventoryservice.model.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 200, nullable = false)
    private String description;

    @Column(length = 100, nullable = false)
    private String corporation;

    private Double price;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Column(length = 1, nullable = true)
    private String status;

    @Column(name = "user_id")
    private Long userId;

    @Transient
    private User user;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "product_category",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categoriesP;

    public boolean isWithThisCategory(Category category){ return this.getCategoriesP().contains(category); }

    public Product categoryWith(Category category) {
        if(!this.isWithThisCategory(category))
            this.getCategoriesP().add(category);
        return this;
    }

    public Product unCategoryWith(Category category) {
        if(this.isWithThisCategory(category))
            this.getCategoriesP().remove(category);
        return this;
    }

}
