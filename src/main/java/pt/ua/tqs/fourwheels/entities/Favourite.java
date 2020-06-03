package pt.ua.tqs.fourwheels.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Favourite {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String mail;
    private int car;

    public Favourite(Integer id, String mail, int car) {
        this.id = id;
        this.mail = mail;
        this.car = car;
    }

    public Favourite() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }
}
