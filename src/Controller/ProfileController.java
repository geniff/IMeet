package Controller;

import Model.Profile;
import Service.ProfileService;

public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    public String work(String request){
    }
}
