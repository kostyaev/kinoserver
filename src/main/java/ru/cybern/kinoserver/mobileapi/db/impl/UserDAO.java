package ru.cybern.kinoserver.mobileapi.db.impl;

import ru.cybern.kinoserver.mobileapi.db.IUserDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.UserEntity;


/**
 * Created by virtuozzo on 19.02.14.
 */
public class UserDAO extends HibernateGenericDAO<UserEntity, Integer>
        implements IUserDAO {
}