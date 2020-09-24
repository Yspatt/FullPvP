package com.yan.fullpvp.service;

import com.yan.fullpvp.data.warps.Warp;
import com.yan.fullpvp.model.Service;

public interface IWarpService extends Service<Warp> {

    Warp get(String name);

    Warp create(String name);
}
