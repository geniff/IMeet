package Service;

import DAO.ProfileDAO;
import Model.Profile;

import java.util.List;
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

    public void Update()
    {

    }

    public boolean Delete()
    {
        return false;
    }

    public List<Profile> findAll()
    {
        return null;
    }
}
