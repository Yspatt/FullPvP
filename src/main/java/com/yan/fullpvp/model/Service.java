package com.yan.fullpvp.model;

import java.util.List;
import java.util.UUID;

public interface Service<T> {

    T get(UUID id);

    void delete(T t);

    List<T> all();

    void init();
    void stop();
}
