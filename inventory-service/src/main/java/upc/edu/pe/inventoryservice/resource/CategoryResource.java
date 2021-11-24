package upc.edu.pe.inventoryservice.resource;


public class CategoryResource {

    private Long id;

    private String name;

    private String description;

    public Long getId() {
        return id;
    }

    public CategoryResource setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryResource setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryResource setDescription(String description) {
        this.description = description;
        return this;
    }
}
