package pt.ua.tqs.fourwheels.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Favourite {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String mail;
    private int car;
}
