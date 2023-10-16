package com.lapin.network.obj;

import java.io.Serializable;
import java.util.HashMap;

/**
 * It's abstract impl of Requestable interface
 */
public class NetObj implements Serializable, NetObjects {
    private HashMap<NetObjBodyKeys,Object> body;
    public NetObj(){
    }
    public NetObj(HashMap<NetObjBodyKeys,Object> body){
        this.body = body;
    }

    @Override
    public HashMap<NetObjBodyKeys, Object> getBody() {
        return body;
    }
}
