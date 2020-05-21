package pt.ua.tqs.fourwheels.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pt.ua.tqs.fourwheels.entities.Car;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarDTO {
    private Car car;
}
