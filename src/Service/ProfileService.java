package Service;

import DAO.ProfileDAO;
import Model.Profile;

import java.util.List;
import java.util.Optional;

public class ProfileService {
    private final ProfileDAO dao;

    //Принимает объект и сохраняет его в поле. Это позволяет сервису использовать DAO для взаимодействия с БД
    public ProfileService(ProfileDAO dao) {
        this.dao = dao;
    }

    //Сохраняет объект в базе данных и возвращает сохраненный объект
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

    public void update(Profile profile)
    {
        if (profile != null)
        {
            dao.update(profile);
        }
    }

    public boolean delete(Long id)
    {
        if(id != null)
        {
            return dao.delete();
        }
        else
        {
            return false;
        }
    }

    public List<Profile> findAll()
    {
        return dao.findAll();
    }
}
