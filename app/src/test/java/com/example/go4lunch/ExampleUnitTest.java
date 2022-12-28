package com.example.go4lunch;

import static org.junit.Assert.assertEquals;

import com.firebase.ui.auth.data.model.User;

import org.junit.Before;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private User user;

    @Before
    public void setup() throws Exception {
       // user = new User("1234azer", "user@gmail.com", "0606060606", "user", null);
    }

    @Test
    public void getUserInfo() {
        assertEquals("1234azer", user.getProviderId());
        assertEquals("user@gmail.com", user.getEmail());
        assertEquals("0606060606", user.getPhoneNumber());
        assertEquals("user", user.getName());
    }


}