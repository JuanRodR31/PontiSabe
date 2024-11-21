package com.pontisabe.pontisabe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pontisabe.pontisabe.Services.AccountService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @Test
    public void testLogin_Successful() {
        String username = "jalimana";
        String password = "Jalimana1234.";

        boolean result = accountService.login(username, password);
        assertTrue(result, "El login debería ser exitoso con credenciales correctas");
    }

    @Test
    public void testLogin_Unsuccessful() {
        String username = "jalimana";
        String password = "wrongPassword";
        boolean result = accountService.login(username, password);
        assertFalse(result, "El login debería fallar con credenciales incorrectas");
    }
}