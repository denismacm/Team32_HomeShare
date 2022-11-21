package com.hfad.team32_homeshare;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ResponsesTest {
    @Test
    public void responsesList_Name_Ascending() {
        ResponsesFragment res = new ResponsesFragment();
        res.responsesList = new ArrayList<>();
        Response res1 = new Response();
        res1.SenderName = 3;
        res.responsesList.add(res1);
        Response res2 = new Response();
        res2.SenderName = 5;
        res.responsesList.add(res2);
        Response res3 = new Response();
        res3.SenderName = 1;
        res.responsesList.add(res3);
        Response res4 = new Response();
        res4.SenderName = 2;
        res.responsesList.add(res4);

        res.sortResponsesList_ClassStandingNum_Ascending(res.responsesList);
        assertEquals(res.responsesList.get(0).ownerClassStandingNum, 1);
        assertEquals(res.responsesList.get(1).ownerClassStandingNum, 2);
        assertEquals(res.responsesList.get(2).ownerClassStandingNum, 3);
        assertEquals(res.responsesList.get(3).ownerClassStandingNum, 5);
    }

    @Test
    public void responsesList_Name_Descending() {
        ResponsesFragment res = new ResponsesFragment();
        res.responsesList = new ArrayList<>();
        Response res1 = new Response();
        res1.SenderName = "M";
        res.responsesList.add(res1);
        Response res2 = new Response();
        res2.SenderName = 5;
        res.responsesList.add(res2);
        Response res3 = new Response();
        res3.SenderName = 1;
        res.responsesList.add(res3);
        Response res4 = new Response();
        res4.SenderName = 2;
        res.responsesList.add(res4);

        res.sortResponsesList_ClassStandingNum_Descending(res.responsesList);
        assertEquals(res.responsesList.get(0).ownerClassStandingNum, 5);
        assertEquals(res.responsesList.get(1).ownerClassStandingNum, 3);
        assertEquals(res.responsesList.get(2).ownerClassStandingNum, 2);
        assertEquals(res.responsesList.get(3).ownerClassStandingNum, 1);
    }

}