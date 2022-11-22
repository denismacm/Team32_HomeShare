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
        res1.senderName = "Michael Kim";
        res.responsesList.add(res1);
        Response res2 = new Response();
        res2.senderName = "Aaron Wong";
        res.responsesList.add(res2);
        Response res3 = new Response();
        res3.senderName = "Denis Mac";
        res.responsesList.add(res3);
        Response res4 = new Response();
        res4.senderName = "Jingbo Wang";
        res.responsesList.add(res4);

        res.sortResponsesList_Name_Ascending(res.responsesList);
        assertEquals(res.responsesList.get(0).senderName, "Aaron Wong");
        assertEquals(res.responsesList.get(1).senderName, "Denis Mac");
        assertEquals(res.responsesList.get(2).senderName, "Jingbo Wang");
        assertEquals(res.responsesList.get(3).senderName, "Michael Kim");
    }

    @Test
    public void responsesList_Name_Descending() {
        ResponsesFragment res = new ResponsesFragment();
        res.responsesList = new ArrayList<>();
        Response res1 = new Response();
        res1.senderName = "Michael Kim";
        res.responsesList.add(res1);
        Response res2 = new Response();
        res2.senderName = "Aaron Wong";
        res.responsesList.add(res2);
        Response res3 = new Response();
        res3.senderName = "Denis Mac";
        res.responsesList.add(res3);
        Response res4 = new Response();
        res4.senderName = "Jingbo Wang";
        res.responsesList.add(res4);

        res.sortResponsesList_Name_Descending(res.responsesList);
        assertEquals(res.responsesList.get(0).senderName, "Michael Kim");
        assertEquals(res.responsesList.get(1).senderName, "Jingbo Wang");
        assertEquals(res.responsesList.get(2).senderName, "Denis Mac");
        assertEquals(res.responsesList.get(3).senderName, "Aaron Wong");
    }

    @Test
    public void responsesList_Gender_Ascending() {
        ResponsesFragment res = new ResponsesFragment();
        res.responsesList = new ArrayList<>();
        Response res1 = new Response();
        res1.senderGender = "male";
        res.responsesList.add(res1);
        Response res2 = new Response();
        res2.senderGender = "female";
        res.responsesList.add(res2);
        Response res3 = new Response();
        res3.senderGender = "female";
        res.responsesList.add(res3);
        Response res4 = new Response();
        res4.senderGender = "female";
        res.responsesList.add(res4);
        Response res5 = new Response();
        res5.senderGender = "male";
        res.responsesList.add(res5);
        Response res6 = new Response();
        res6.senderGender = "non-binary";
        res.responsesList.add(res6);

        res.sortResponsesList_Gender_Ascending(res.responsesList);
        assertEquals(res.responsesList.get(0).senderGender, "female");
        assertEquals(res.responsesList.get(1).senderGender, "female");
        assertEquals(res.responsesList.get(2).senderGender, "female");
        assertEquals(res.responsesList.get(3).senderGender, "male");
        assertEquals(res.responsesList.get(4).senderGender, "male");
        assertEquals(res.responsesList.get(5).senderGender, "non-binary");
    }

    @Test
    public void responsesList_Gender_Descending() {
        ResponsesFragment res = new ResponsesFragment();
        res.responsesList = new ArrayList<>();
        Response res1 = new Response();
        res1.senderGender = "male";
        res.responsesList.add(res1);
        Response res2 = new Response();
        res2.senderGender = "female";
        res.responsesList.add(res2);
        Response res3 = new Response();
        res3.senderGender = "female";
        res.responsesList.add(res3);
        Response res4 = new Response();
        res4.senderGender = "female";
        res.responsesList.add(res4);
        Response res5 = new Response();
        res5.senderGender = "male";
        res.responsesList.add(res5);
        Response res6 = new Response();
        res6.senderGender = "non-binary";
        res.responsesList.add(res6);

        res.sortResponsesList_Gender_Descending(res.responsesList);
        assertEquals(res.responsesList.get(0).senderGender, "non-binary");
        assertEquals(res.responsesList.get(1).senderGender, "male");
        assertEquals(res.responsesList.get(2).senderGender, "male");
        assertEquals(res.responsesList.get(3).senderGender, "female");
        assertEquals(res.responsesList.get(4).senderGender, "female");
        assertEquals(res.responsesList.get(5).senderGender, "female");
    }

    @Test
    public void responsesList_Name_Filtered(){
        ResponsesFragment res = new ResponsesFragment();
        res.responsesList = new ArrayList<>();
        Response res1 = new Response();
        res1.senderName = "Michael Kim";
        res.responsesList.add(res1);
        Response res2 = new Response();
        res2.senderName = "Denis Mac";
        res.responsesList.add(res2);
        Response res3 = new Response();
        res3.senderName = "Michelle Kim";
        res.responsesList.add(res3);
        Response res4 = new Response();
        res4.senderName = "Jingbo Wang";
        res.responsesList.add(res4);
        Response res5 = new Response();
        res5.senderName = "Micah Kim" ;
        res.responsesList.add(res5);
        Response res6 = new Response();
        res6.senderName = "Aaron Wong";
        res.responsesList.add(res6);
        Response res7 = new Response();
        res7.senderName = "Mitch Kim";
        res.responsesList.add(res7);

        res.filterResponsesList_Name(res.responsesList,"Mi");

        assertEquals(res.responsesList.get(0).senderName, "Michael Kim");
        assertEquals(res.responsesList.get(1).senderName, "Michelle Kim");
        assertEquals(res.responsesList.get(2).senderName, "Micah Kim");
        assertEquals(res.responsesList.get(3).senderName, "Mitch Kim");
    }

    @Test
    public void responsesList_Gender_Filtered(){
        ResponsesFragment res = new ResponsesFragment();
        res.responsesList = new ArrayList<>();
        Response res1 = new Response();
        res1.senderGender = "male";
        res.responsesList.add(res1);
        Response res2 = new Response();
        res2.senderGender = "female";
        res.responsesList.add(res2);
        Response res3 = new Response();
        res3.senderGender = "female";
        res.responsesList.add(res3);
        Response res4 = new Response();
        res4.senderGender = "non-binary";
        res.responsesList.add(res4);
        Response res5 = new Response();
        res5.senderGender = "male" ;
        res.responsesList.add(res5);
        Response res6 = new Response();
        res6.senderGender = "male";
        res.responsesList.add(res6);
        Response res7 = new Response();
        res7.senderGender = "female";
        res.responsesList.add(res7);

        res.filterResponsesList_Gender(res.responsesList,"female");

        assertEquals(res.responsesList.get(0).senderGender, "female");
        assertEquals(res.responsesList.get(1).senderGender, "female");
        assertEquals(res.responsesList.get(2).senderGender, "female");
    }
}