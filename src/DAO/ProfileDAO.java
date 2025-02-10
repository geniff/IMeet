package DAO;

import Model.Profile;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProfileDAO {
    private final ConcurrentHashMap<Long, Profile> profiles;
    private final AtomicLong idStorage;

    public ProfileDAO() {
        this.profiles = new ConcurrentHashMap<>();
        this.idStorage = new AtomicLong();
    }

    public Profile save(Profile profile) {
        long id = idStorage.getAndIncrement();
        profile.setId(id);
        profiles.put(id, profile);
        return profile;
    }

    public Optional<Profile> findById(long id) {
        return Optional.ofNullable(profiles.get(id));
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
