package com.unibmi.gamersconnect;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class UserTest {


    @Test
    public void toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("username","testusername");
        map.put("email", "test@gmail.hu");

        Map<String, Object> result = new HashMap<>();
        result.put("username", "testusername");
        result.put("email", "test@gmail.hu");

        assertEquals(map, result);
    }

}