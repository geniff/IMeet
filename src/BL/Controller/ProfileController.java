package BL.Controller;

import BL.Service.ProfileService;

public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    public String work(String request){
        return request;
    }
}
