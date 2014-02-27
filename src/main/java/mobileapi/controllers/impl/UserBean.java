package mobileapi.controllers.impl;

import mobileapi.controllers.*;
import mobileapi.db.entities.UserEntity;

import java.util.List;

public class UserBean implements IUserBean {

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
