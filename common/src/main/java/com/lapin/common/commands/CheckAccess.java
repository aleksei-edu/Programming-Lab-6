package com.lapin.common.commands;

import com.lapin.network.AccessType;
import com.lapin.network.ClientType;

public class CheckAccess {
    public static boolean check(ClientType clientType, AccessType accessType) {
        if (accessType.equals(AccessType.NO_ONE)) return false;
        else if (accessType.equals(AccessType.USER)) return clientType.equals(ClientType.REMOTE);
        else if (accessType.equals(AccessType.ADMIN)) return clientType.equals(ClientType.LOCAL);
        else return accessType.equals(AccessType.ALL);
    }
}
