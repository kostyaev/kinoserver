package ru.cybern.kinoserver.mobileapi.controllers.impl;

import ru.cybern.kinoserver.mobileapi.controllers.IUserBean;
import ru.cybern.kinoserver.mobileapi.db.IUserDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.UserEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserBean implements IUserBean {

    @Inject
    private IUserDAO userDAO;


    @Override
    public UserEntity saveUser(UserEntity user) {
        return null;
    }

    @Override
    public void deleteUser(UserEntity user) {

    }

    @Override
    public UserEntity getUser(int id) {
        return null;
    }

    @Override
    public boolean deleteUser(int id) {
        return false;
    }

    @Override
    public List<UserEntity> getUsers() {
        return null;
    }
}
