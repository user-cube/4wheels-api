package pt.ua.tqs.fourwheels.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pt.ua.tqs.fourwheels.entities.Car;
import pt.ua.tqs.fourwheels.entities.Profile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfileModel {

    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
