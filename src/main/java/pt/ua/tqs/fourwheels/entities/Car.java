package pt.ua.tqs.fourwheels.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String photo;
    private String brand;
    private String model;
    private int year;
    private int month;
    private String description;
    private int kilometers;
    private String typeOfFuel;



}
