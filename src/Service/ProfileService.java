package Service;

import DAO.ProfileDAO;
import Model.Profile;

import java.util.Optional;

public class ProfileService {
    private final ProfileDAO dao;

    public ProfileService(ProfileDAO dao) {
        this.dao = dao;
    }

    public Profile save(Profile profile) {
        dao.save(profile);
        return profile;
    }

    public Optional<Profile> findById(Long id) {
        if(id == null)
        {
            return Optional.empty();
        }
        else {
            return dao.findById(id);
        }
    }

    //WILL ADD DELTE(true/false), UPDATE(void), FINDALL(list)
}
