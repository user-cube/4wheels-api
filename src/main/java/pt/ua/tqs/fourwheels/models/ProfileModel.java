package pt.ua.tqs.fourwheels.models;

import pt.ua.tqs.fourwheels.entities.Profile;

public class ProfileModel {

    private Profile profile;

    public ProfileModel() {
    }

    public ProfileModel(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "ProfileModel{" +
                "profile=" + profile +
                '}';
    }
}
