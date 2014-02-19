package mobileapi.db.entities2;

import mobileapi.dto.UserIdentity;

/**
 * Created by virtuozzo on 14.02.14.
 */
public class User extends UserIdentity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
