package gDao.models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: mmeral
 * Date: 5/15/13
 * Time: 10:07 AM
 */
@Entity
public class Mobile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "MOBILE_NO")
    private Integer mobileNo;
    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID", nullable = false, updatable = false, insertable = false)
    private Address address;

    public Mobile(Integer mobileNo, Address address) {
        this.mobileNo = mobileNo;
        this.address = address;
    }

    public Mobile() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Integer mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
