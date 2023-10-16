package com.lapin.common.utility;

import java.io.Serializable;

public interface Client_IO {
    public Pair handle(String command, String arg, Serializable argObj);
    public Pair handle(String command, String arg);
}
