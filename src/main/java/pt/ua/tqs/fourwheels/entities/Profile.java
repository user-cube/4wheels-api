package pt.ua.tqs.fourwheels.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int type;
    private String name;
    private String mail;
    private int contact;
    private String address;
    private String zipCode;
    private String city;
    private int nif;
    @Column(columnDefinition = "LONGTEXT")
    private String photo;

    public Profile() {
    }

    public Profile(int type, String name, String mail, int contact, String address, String zipCode, String city, int nif, String photo) {
        this.type = type;
        this.name = name;
        this.mail = mail;
        this.contact = contact;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.nif = nif;
        this.photo = photo;
    }

    public Profile(Integer id, int type, String name, String mail, int contact, String address, String zipCode, String city, int nif, String photo) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.mail = mail;
        this.contact = contact;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.nif = nif;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId(){ return id;}
    public int getType(){
        return type;
    }
    public String getName(){
        return name;
    }
    public String getMail(){
        return mail;
    }
    public int getContact(){
        return contact;
    }
    public String getAddress(){
        return address;
    }
    public String getZipCode(){
        return zipCode;
    }
    public String getCity(){
        return city;
    }
    public int getNif(){
        return nif;
    }

    public void setId(int id){this.id = id;}
    public void setType(int type){
        this.type = type;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setMail(String mail){
        this.mail = mail;
    }
    public void setContact(int contact){
        this.contact = contact;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setNif(int nif){
        this.nif = nif;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", contact=" + contact +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", nif=" + nif +
                ", photo='" + photo + '\'' +
                '}';
    }
}
