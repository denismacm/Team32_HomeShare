package com.hfad.team32_homeshare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.util.Patterns;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RegisterActivityTest {
    @Test
    public void firstAndLastName_isCorrect() {
        assertFalse(RegisterActivity.validateFirstAndLastName(""));
        assertFalse(RegisterActivity.validateFirstAndLastName(" "));
        assertFalse(RegisterActivity.validateFirstAndLastName("     "));
        assertFalse(RegisterActivity.validateFirstAndLastName("Denis"));
        assertFalse(RegisterActivity.validateFirstAndLastName("MichaelKim"));
        assertTrue(RegisterActivity.validateFirstAndLastName("Denis Mac"));
        assertTrue(RegisterActivity.validateFirstAndLastName("Michael Kim"));
        assertTrue(RegisterActivity.validateFirstAndLastName("Aaron Wong"));
    }

    @Test
    public void password_isValid() {
        assertFalse(RegisterActivity.isValidPassword(""));
        assertFalse(RegisterActivity.isValidPassword(" "));
        assertFalse(RegisterActivity.isValidPassword("pass"));
        assertFalse(RegisterActivity.isValidPassword("    pass    "));
        assertTrue(RegisterActivity.isValidPassword("J2K$S!BL"));
        assertTrue(RegisterActivity.isValidPassword("  DLKF02^L!   "));
    }

    @Test
    public void passwords_Match() {
        assertFalse(RegisterActivity.validateMatchingPassword("ZYXWVUT", "zyxwvut"));
        assertFalse(RegisterActivity.validateMatchingPassword("Abcdefg", "abcdefg"));
        assertTrue(RegisterActivity.validateMatchingPassword("Lmbldflk1", "Lmbldflk1"));
        assertTrue(RegisterActivity.validateMatchingPassword("  LSKjdk109  ", "LSKjdk109"));
    }


}