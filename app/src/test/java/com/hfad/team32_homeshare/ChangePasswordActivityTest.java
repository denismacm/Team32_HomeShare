package com.hfad.team32_homeshare;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class ChangePasswordActivityTest {
    @Test
    public void password_isValid() {
        assertFalse(ChangePasswordActivity.validateMatchingPassword("ZYXWVUT", "zyxwvut"));
        assertFalse(ChangePasswordActivity.validateMatchingPassword("Abcdefg", "abcdefg"));
        assertTrue(ChangePasswordActivity.validateMatchingPassword("Lmbldflk1", "Lmbldflk1"));
        assertTrue(ChangePasswordActivity.validateMatchingPassword("  LSKjdk109  ", "LSKjdk109"));
    }
}
