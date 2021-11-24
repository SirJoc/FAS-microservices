package upc.edu.pe.inventoryservice.resource;

public class SaveCategoryResource {
    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public SaveCategoryResource setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SaveCategoryResource setDescription(String description) {
        this.description = description;
        return this;
    }
}
