package pe.edu.upc.usersservice.entity;


import lombok.Data;
import pe.edu.upc.usersservice.model.Publicity;
import pe.edu.upc.usersservice.model.Subscription;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="full_name", length = 100, nullable = false)
    private String fullName;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 200, nullable = false)
    private String email;

    @Column(length = 11, nullable = false)
    private String ruc;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Column(length = 20, nullable = true)
    private String status;

    public List<Publicity> getPublicities() {
        return publicities;
    }

    public void setPublicities(List<Publicity> publicities) {
        this.publicities = publicities;
    }

    @Transient
    private List<Publicity> publicities;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
