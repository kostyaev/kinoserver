package mobileapi.db;

import mobileapi.dto.Music;

/**
 * Created by virtuozzo on 17.02.14.
 */
public interface MusicDAO {

    public Music getById(long id);
    public void save(Music music);
    public void deleteById(long id);


}
