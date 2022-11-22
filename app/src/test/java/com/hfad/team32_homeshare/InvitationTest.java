package com.hfad.team32_homeshare;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class InvitationTest {
    @Test
    public void invitationsList_classStandingNum_Ascending() {
       InvitationsFragment inv = new InvitationsFragment();
       inv.invitationsList = new ArrayList<>();
       Invitation inv1 = new Invitation();
       inv1.ownerClassStandingNum = 3;
       inv.invitationsList.add(inv1);
       Invitation inv2 = new Invitation();
       inv2.ownerClassStandingNum = 5;
       inv.invitationsList.add(inv2);
       Invitation inv3 = new Invitation();
       inv3.ownerClassStandingNum = 1;
       inv.invitationsList.add(inv3);
       Invitation inv4 = new Invitation();
       inv4.ownerClassStandingNum = 2;
       inv.invitationsList.add(inv4);

       inv.sortInvitationsList_ClassStandingNum_Ascending(inv.invitationsList);
       assertEquals(inv.invitationsList.get(0).ownerClassStandingNum, 1);
       assertEquals(inv.invitationsList.get(1).ownerClassStandingNum, 2);
       assertEquals(inv.invitationsList.get(2).ownerClassStandingNum, 3);
       assertEquals(inv.invitationsList.get(3).ownerClassStandingNum, 5);
    }

   @Test
   public void invitationsList_classStandingNum_Descending() {
      InvitationsFragment inv = new InvitationsFragment();
      inv.invitationsList = new ArrayList<>();
      Invitation inv1 = new Invitation();
      inv1.ownerClassStandingNum = 3;
      inv.invitationsList.add(inv1);
      Invitation inv2 = new Invitation();
      inv2.ownerClassStandingNum = 5;
      inv.invitationsList.add(inv2);
      Invitation inv3 = new Invitation();
      inv3.ownerClassStandingNum = 1;
      inv.invitationsList.add(inv3);
      Invitation inv4 = new Invitation();
      inv4.ownerClassStandingNum = 2;
      inv.invitationsList.add(inv4);

      inv.sortInvitationsList_ClassStandingNum_Descending(inv.invitationsList);
      assertEquals(inv.invitationsList.get(0).ownerClassStandingNum, 5);
      assertEquals(inv.invitationsList.get(1).ownerClassStandingNum, 3);
      assertEquals(inv.invitationsList.get(2).ownerClassStandingNum, 2);
      assertEquals(inv.invitationsList.get(3).ownerClassStandingNum, 1);
   }

   @Test
   public void getYearPosted_isValid() {
      Invitation inv1 = new Invitation();
      inv1.date = "10/18/22";
      Invitation inv2 = new Invitation();
      inv2.date = "04/17/20";
      Invitation inv3 = new Invitation();
      inv3.date = "06/28/21";
      Invitation inv4 = new Invitation();
      inv4.date = "02/25/19";
      assertNotEquals(inv1.getYearPosted(), "19");
      assertEquals(inv1.getYearPosted(), "22");
      assertNotEquals(inv2.getYearPosted(), "22");
      assertEquals(inv2.getYearPosted(), "20");
      assertNotEquals(inv3.getYearPosted(), "20");
      assertEquals(inv3.getYearPosted(), "21");
      assertNotEquals(inv4.getYearPosted(), "21");
      assertEquals(inv4.getYearPosted(), "19");
   }

   @Test
   public void invitationsList_date_Ascending() {
      InvitationsFragment inv = new InvitationsFragment();
      inv.invitationsList = new ArrayList<>();
      Invitation inv1 = new Invitation();
      inv1.date = "06/28/22";
      inv.invitationsList.add(inv1);
      Invitation inv2 = new Invitation();
      inv2.date = "10/18/20";
      inv.invitationsList.add(inv2);
      Invitation inv3 = new Invitation();
      inv3.date = "04/17/21";
      inv.invitationsList.add(inv3);
      Invitation inv4 = new Invitation();
      inv4.date = "02/25/19";
      inv.invitationsList.add(inv4);
      inv.sortInvitationsList_Date_Ascending(inv.invitationsList);
      assertEquals(inv.invitationsList.get(0).date, "02/25/19");
      assertEquals(inv.invitationsList.get(1).date, "10/18/20");
      assertEquals(inv.invitationsList.get(2).date, "04/17/21");
      assertEquals(inv.invitationsList.get(3).date, "06/28/22");
   }
   @Test
   public void invitationsList_date_Descending() {
      InvitationsFragment inv = new InvitationsFragment();
      inv.invitationsList = new ArrayList<>();
      Invitation inv1 = new Invitation();
      inv1.date = "06/28/22";
      inv.invitationsList.add(inv1);
      Invitation inv2 = new Invitation();
      inv2.date = "10/18/20";
      inv.invitationsList.add(inv2);
      Invitation inv3 = new Invitation();
      inv3.date = "04/17/21";
      inv.invitationsList.add(inv3);
      Invitation inv4 = new Invitation();
      inv4.date = "02/25/19";
      inv.invitationsList.add(inv4);
      inv.sortInvitationsList_Date_Descending(inv.invitationsList);
      assertEquals(inv.invitationsList.get(0).date, "06/28/22");
      assertEquals(inv.invitationsList.get(1).date, "04/17/21");
      assertEquals(inv.invitationsList.get(2).date, "10/18/20");
      assertEquals(inv.invitationsList.get(3).date, "02/25/19");
   }
}