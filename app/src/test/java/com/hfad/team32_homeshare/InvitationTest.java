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

}