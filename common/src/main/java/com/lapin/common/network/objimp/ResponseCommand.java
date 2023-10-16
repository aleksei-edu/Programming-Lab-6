package com.lapin.common.network.objimp;

import com.lapin.network.StatusCodes;
import com.lapin.network.obj.NetObj;
import com.lapin.network.obj.NetObjBodyKeys;
import com.lapin.network.obj.ResponseBodyKeys;

import java.io.Serializable;
import java.util.HashMap;

public class ResponseCommand extends NetObj implements Serializable {
    public ResponseCommand(StatusCodes statusCode, String ans){
        super(setBody(statusCode,ans));
    }
    private static HashMap<NetObjBodyKeys,Object> setBody(StatusCodes statusCode, String ans){
        HashMap<NetObjBodyKeys, Object> body = new HashMap<>();
        body.put(ResponseBodyKeys.STATUS_CODE, statusCode);
        body.put(ResponseBodyKeys.ANSWER, ans);
        return body;
    }
}

