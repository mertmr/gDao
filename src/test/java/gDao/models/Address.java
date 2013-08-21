package gDao.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Mert Meral
 */
@Entity
public class Address implements Serializable{
    private static final long serialVersionUID = 3231703401950522667L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ADDRESS")
    private String address;

    public Address(String address) {
        this.address = address;
    }

    public Address() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
