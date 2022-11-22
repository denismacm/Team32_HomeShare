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
        assertFalse(ChangePasswordActivity.validateMatchingPassword("LASDJFLASDJF", "LaSDJFLASDJF"));
        assertFalse(ChangePasswordActivity.validateMatchingPassword("BZXCVMZNXCV", "BZXCVMZNXCv"));
        assertTrue(ChangePasswordActivity.validateMatchingPassword("L0s90asf", "L0s90asf"));
        assertTrue(ChangePasswordActivity.validateMatchingPassword("  ALSDKFJALS12  ", "ALSDKFJALS12"));
    }
}
