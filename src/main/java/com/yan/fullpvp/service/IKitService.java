package com.yan.fullpvp.service;

import com.yan.fullpvp.data.kits.Kit;
import com.yan.fullpvp.model.Service;


public interface IKitService extends Service<Kit> {

    Kit get(String name);

    Kit create(String name);

}


