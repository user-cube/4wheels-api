package pt.ua.tqs.fourwheels.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String type;
    private String name;
    private String mail;
    private int contacto;
    private String morada;
    private int codigPostal;
    private String cidade;
    private int numeroContribuinte;

    public int getId(){ return id;}
    public String getType(){
        return type;
    }
    public String getName(){
        return name;
    }
    public String getMail(){
        return mail;
    }
    public int getContacto(){
        return contacto;
    }
    public String getMorada(){
        return morada;
    }
    public int getCodigPostal(){
        return codigPostal;
    }
    public String getCidade(){
        return cidade;
    }
    public int getNumeroContribuinte(){
        return numeroContribuinte;
    }

    public void setType(String type){
        this.type = type;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setMail(String mail){
        this.mail = mail;
    }
    public void setContacto(int contacto){
        this.contacto = contacto;
    }
    public void setMorada(String morada){
        this.morada = morada;
    }
    public void setCodigPostal(int codigPostal){
        this.codigPostal = codigPostal;
    }
    public void setCidade(String cidade){
        this.cidade = cidade;
    }
    public void setNumeroContribuinte(int numeroContribuinte){
        this.numeroContribuinte = numeroContribuinte;
    }
}
