package com.lapin.common.commands;

import com.lapin.network.AccessType;
import com.lapin.network.ClientType;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CheckAccessTest {

    @Test
    void checkLocalAll() {
        assertTrue(CheckAccess.check(ClientType.LOCAL, AccessType.ALL));
    }
    @Test
    void checkLocalAdmin() {
        assertTrue(CheckAccess.check(ClientType.LOCAL, AccessType.ADMIN));
    }
    @Test
    void checkLocalUser() {
        assertFalse(CheckAccess.check(ClientType.LOCAL, AccessType.USER));
    }
    @Test
    void checkLocalNoOne() {
        assertFalse(CheckAccess.check(ClientType.LOCAL, AccessType.NO_ONE));
    }

    @Test
    void checkRemoteAll() {
        assertTrue(CheckAccess.check(ClientType.REMOTE, AccessType.ALL));
    }
    @Test
    void checkRemoteUser() {
        assertTrue(CheckAccess.check(ClientType.REMOTE, AccessType.USER));
    }
    @Test
    void checkRemoteAdmin() {
        assertFalse(CheckAccess.check(ClientType.REMOTE, AccessType.ADMIN));
    }
    @Test
    void checkRemoteNoOne() {
        assertFalse(CheckAccess.check(ClientType.REMOTE, AccessType.NO_ONE));
    }

}