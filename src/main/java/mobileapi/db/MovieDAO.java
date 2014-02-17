package mobileapi.db;

import mobileapi.dto.Film;

/**
 * Created by virtuozzo on 17.02.14.
 */
public interface MovieDAO {

    public Film getById(long id);
    public void save(Film movie);
    public void deleteById(long id);


}
