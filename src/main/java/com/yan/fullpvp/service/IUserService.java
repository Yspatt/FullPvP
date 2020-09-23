package com.yan.fullpvp.service;

import com.yan.fullpvp.data.user.User;
import com.yan.fullpvp.model.Service;

import java.util.UUID;

public interface IUserService extends Service<User> {

    User create(UUID uuid);

    void load(UUID uuid);
    void save(User user);
}
