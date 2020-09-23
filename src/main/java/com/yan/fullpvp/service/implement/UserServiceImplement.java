package com.yan.fullpvp.service.implement;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.yan.fullpvp.Main;
import com.yan.fullpvp.data.user.User;
import com.yan.fullpvp.service.IUserService;

import java.util.List;
import java.util.UUID;

public class UserServiceImplement implements IUserService {

    private List<User> users = Lists.newArrayList();

    @Override
    public User create(UUID uuid) {
        User user = User.builder().uuid(uuid).build();
        return user;
    }

    @Override
    public User get(UUID id) {
        return all().stream().filter(user -> user.getUuid().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<User> all() {
        return users;
    }

    @Override
    public void init() {

    }

    @Override
    public void stop() {
        Gson gson = Main.getInstance().getGson();
        Main.getInstance().getHikariSource().query("INSERT INTO USERS(uuid,data) VALUES(?,?) ON DUPLICATE KEY UPDATE DATA=?",(statement) ->{
            for (User user : all()) {
                statement.setString(1,user.getUuid().toString());
                statement.setString(2,gson.toJson(user));
                statement.setString(3,gson.toJson(user));
                statement.addBatch();
                statement.clearParameters();
            }
            statement.executeBatch();
            statement.clearBatch();
        });
    }


    @Override
    public void load(UUID uuid) {
        Gson gson = Main.getInstance().getGson();
        Main.getInstance().getHikariSource().query("select * from users where uuid=?", (statement) -> {
            statement.setString(1, uuid.toString());
            statement.execute();
           Main.getInstance().getHikariSource().result(statement,(resultSet ->{
                if (!resultSet.next()){
                    create(uuid);
                    return;
                }
                all().add(gson.fromJson(resultSet.getString("data"), User.class));
            }));
        });
    }

    @Override
    public void save(User user) {
        Gson gson = Main.getInstance().getGson();

        Main.getInstance().getHikariSource().query("INSERT INTO USERS(uuid,data) VALUES(?,?) ON DUPLICATE KEY UPDATE DATA=?", (statement) -> {
            statement.setString(1, user.getUuid().toString());
            statement.setString(2, gson.toJson(user));
            statement.setString(3, gson.toJson(user));
            statement.execute();
        });
    }
}
