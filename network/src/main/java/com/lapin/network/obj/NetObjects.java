package com.lapin.network.obj;

import java.io.Serializable;
import java.util.HashMap;

public interface NetObjects extends Serializable {

    public HashMap<NetObjBodyKeys,Object> getBody();

}
