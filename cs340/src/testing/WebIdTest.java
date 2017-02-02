package testing;

import hypeerweb.node.WebId;
import junit.framework.TestCase;

public class WebIdTest extends TestCase {

    public WebIdTest(String name) {
        super(name);
    }

    public void testWebId() {
        //Testing the default constructor and the NULL_WEB_ID
        assertTrue(WebId.NULL_WEB_ID.getValue() == -1 && WebId.NULL_WEB_ID.getHeight() == -1);
    }

    //The following is an example of Equivalence Partitioning.
    //There are two main partitions:
    //Valid Test Cases
    //InvalidTest Cases -- those that cause errors which means they do not satisfy the pre-condition.
    public void testWebIdInt() {
        //Equivalence Partitioning: Valid test cases
        //  1. Creating the smallest webId, webId 0
        //  2. Create the largest webId, the webId whose value = MAX_VALUE
        //  3. Create a webId whose value is somewhere in between, the webId with the value 100.
        WebId webId = new WebId(0);
        assertTrue(webId.getValue() == 0 && webId.getHeight() == 0);
        
        webId = new WebId(100);
        assertTrue(webId.getValue() == 100 && webId.getHeight() == 7);
        
        webId = new WebId(Integer.MAX_VALUE);
        assertTrue(webId.getValue() == Integer.MAX_VALUE && webId.getHeight() == 31);
        
        //Equivalence Partitioning: Invalid Test Cases
        //    There is only one pre-condition: id >= 0.  Therefore we have only have this one test 
        //  where the id = -1;
        try{
            webId = new WebId(-1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        //Boundary Value Analysis
        //The range of input values goes from Integer.MIN_VALUE to Integer.MAX_VALUE;
        //Anything input below -1 is considered erroneous.  Thus boundary values are:
        //Integer.MIN_VALUE, Integer.MIN_VALUE-1, some negative number > Integer.MIN_VALUE -1 and < -1,
        //-1, 0, 1, some value greater than 1 but less than Integer.MAX_VALUE -1, Integer.MAX_VALUE -1,
        //and Integer.MAX_VALUE.  Some of these test cases will repeat the ones performed in the
        //Equivalence Partitioning section.

        try{
            webId = new WebId(Integer.MIN_VALUE);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(Integer.MIN_VALUE+1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(-212);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(-1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        webId = new WebId(0);
        assertTrue(webId.getValue() == 0 && webId.getHeight() == 0);
        
        webId = new WebId(1);
        assertTrue(webId.getValue() == 1 && webId.getHeight() == 1);
        
        webId = new WebId(100);
        assertTrue(webId.getValue() == 100 && webId.getHeight() == 7);
        
        webId = new WebId(Integer.MAX_VALUE - 1);
        assertTrue(webId.getValue() == Integer.MAX_VALUE - 1 && webId.getHeight() == 31);
        
        webId = new WebId(Integer.MAX_VALUE);
        assertTrue(webId.getValue() == Integer.MAX_VALUE && webId.getHeight() == 31);
        
        //Boundary Value Analysis on the height of a webId.  The only possible values range
        //from 0, to 31.  There are no invalid heights.  Thus, the boundary values to test
        //are 0, 1, some value > 1 and < 30, 30, and 31.  Some of these test cases repeat
        //previous test cases of this section.
        webId = new WebId(0);
        assertTrue(webId.getValue() == 0 && webId.getHeight() == 0);
        
        webId = new WebId(1);
        assertTrue(webId.getValue() == 1 && webId.getHeight() == 1);
        
        webId = new WebId(100);
        assertTrue(webId.getValue() == 100 && webId.getHeight() == 7);
        
        webId = new WebId(Integer.MAX_VALUE/2);
        assertTrue(webId.getValue() == Integer.MAX_VALUE/2 && webId.getHeight() == 30);
        
        webId = new WebId(Integer.MAX_VALUE - 1);
        assertTrue(webId.getValue() == Integer.MAX_VALUE - 1 && webId.getHeight() == 31);
    }
    
    public void testWebIdIntInt() {        
        //Valid test cases
        WebId webId = new WebId(0, 0);
        assertTrue(webId.getValue() == 0 && webId.getHeight() == 0);
        webId = new WebId(0, 1);
        assertTrue(webId.getValue() == 0 && webId.getHeight() == 1);
        webId = new WebId(0, 10);
        assertTrue(webId.getValue() == 0 && webId.getHeight() == 10);
        webId = new WebId(0, WebId.MAX_HEIGHT - 1);
        assertTrue(webId.getValue() == 0 && webId.getHeight() == WebId.MAX_HEIGHT - 1);
        webId = new WebId(0, WebId.MAX_HEIGHT);
        assertTrue(webId.getValue() == 0 && webId.getHeight() == WebId.MAX_HEIGHT);
        
        webId = new WebId(1, 1);
        assertTrue(webId.getValue() == 1 && webId.getHeight() == 1);
        webId = new WebId(1, 10);
        assertTrue(webId.getValue() == 1 && webId.getHeight() == 10);
        webId = new WebId(1, WebId.MAX_HEIGHT - 1);
        assertTrue(webId.getValue() == 1 && webId.getHeight() == WebId.MAX_HEIGHT - 1);
        webId = new WebId(1, WebId.MAX_HEIGHT);
        assertTrue(webId.getValue() == 1 && webId.getHeight() == WebId.MAX_HEIGHT);
        
        webId = new WebId(2, 2);
        assertTrue(webId.getValue() == 2 && webId.getHeight() == 2);        
        webId = new WebId(2, 3);
        assertTrue(webId.getValue() == 2 && webId.getHeight() == 3);
        
        webId = new WebId(3, 2);
        assertTrue(webId.getValue() == 3 && webId.getHeight() == 2);        
        webId = new WebId(3, 4);
        assertTrue(webId.getValue() == 3 && webId.getHeight() == 4);
        
        webId = new WebId(4, 3);
        assertTrue(webId.getValue() == 4 && webId.getHeight() == 3);        
        webId = new WebId(4, 4);
        assertTrue(webId.getValue() == 4 && webId.getHeight() == 4);
        
        webId = new WebId(5, 3);
        assertTrue(webId.getValue() == 5 && webId.getHeight() == 3);        
        webId = new WebId(5, 4);
        assertTrue(webId.getValue() == 5 && webId.getHeight() == 4);
        
        webId = new WebId(6, 3);
        assertTrue(webId.getValue() == 6 && webId.getHeight() == 3);        
        webId = new WebId(6, 4);
        assertTrue(webId.getValue() == 6 && webId.getHeight() == 4);
        
        webId = new WebId(7, 3);
        assertTrue(webId.getValue() == 7 && webId.getHeight() == 3);        
        webId = new WebId(7, 4);
        assertTrue(webId.getValue() == 7 && webId.getHeight() == 4);
        
        webId = new WebId(8, 4);
        assertTrue(webId.getValue() == 8 && webId.getHeight() == 4);        
        webId = new WebId(8, 5);
        assertTrue(webId.getValue() == 8 && webId.getHeight() == 5);
        
        webId = new WebId(10, 4);
        assertTrue(webId.getValue() == 10 && webId.getHeight() == 4);
        webId = new WebId(10, 10);
        assertTrue(webId.getValue() == 10 && webId.getHeight() == 10);
        webId = new WebId(10, WebId.MAX_HEIGHT - 1);
        assertTrue(webId.getValue() == 10 && webId.getHeight() == WebId.MAX_HEIGHT - 1);
        webId = new WebId(10, WebId.MAX_HEIGHT);
        assertTrue(webId.getValue() == 10 && webId.getHeight() == WebId.MAX_HEIGHT);
        
        webId = new WebId(Integer.MAX_VALUE -1, WebId.MAX_HEIGHT);
        assertTrue(webId.getValue() == Integer.MAX_VALUE -1 && webId.getHeight() == WebId.MAX_HEIGHT);
        
        webId = new WebId(Integer.MAX_VALUE, WebId.MAX_HEIGHT);
        assertTrue(webId.getValue() == Integer.MAX_VALUE && webId.getHeight() == WebId.MAX_HEIGHT);
        
        //Invalid test cases.
        int maxInt = Integer.MAX_VALUE;
        int minInt = Integer.MIN_VALUE;
        
        try{
            webId = new WebId(0, 32);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(0, 1001);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(0, Integer.MAX_VALUE -1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(0, Integer.MAX_VALUE);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(1, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(2, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(2, 1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(15, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(15, 1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(15, 2);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(15, 3);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(-1, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(0, -1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(1, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(2, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(2, 1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(3, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(3, 1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(4, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(4, 1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(4, 2);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(5, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(5, 1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(5, 2);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(6, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(6, 1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(6, 2);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(7, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(7, 1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(7, 2);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(8, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(8, 1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(8, 2);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(8, 3);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(-1, -1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(-100, 100);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(minInt + 1, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(minInt, 0);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(minInt + 1, maxInt - 1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(minInt, maxInt);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(maxInt, -1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(maxInt - 1, -55);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(234, minInt + 1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId = new WebId(maxInt, minInt);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
    }
    
    public void testHashCode() {
        assertTrue(WebId.NULL_WEB_ID.hashCode() == -1);
        assertTrue(new WebId(0).hashCode() == 0);
        assertTrue(new WebId(1).hashCode() == 1);
        assertTrue(new WebId(22).hashCode() == 22);
        assertTrue(new WebId(Integer.MAX_VALUE - 1).hashCode() == Integer.MAX_VALUE - 1);
        assertTrue(new WebId(Integer.MAX_VALUE).hashCode() == Integer.MAX_VALUE);
    }

    public void testToString() {
        assertTrue(WebId.NULL_WEB_ID.toString().equals("NULL WEB ID"));
        
        assertTrue(new WebId(0,0).toString().equals(""));
        assertTrue(new WebId(0,1).toString().equals("0"));
        assertTrue(new WebId(0,5).toString().equals("00000"));
        assertTrue(new WebId(0,30).toString().equals("000000000000000000000000000000"));
        assertTrue(new WebId(0,31).toString().equals("0000000000000000000000000000000"));
        assertTrue(new WebId(1,1).toString().equals("1"));
        assertTrue(new WebId(1,2).toString().equals("01"));
        assertTrue(new WebId(1,5).toString().equals("00001"));
        assertTrue(new WebId(1,30).toString().equals("000000000000000000000000000001"));
        assertTrue(new WebId(1,31).toString().equals("0000000000000000000000000000001"));
        assertTrue(new WebId(11,4).toString().equals("1011"));
        assertTrue(new WebId(11,10).toString().equals("0000001011"));
        assertTrue(new WebId(11,30).toString().equals("000000000000000000000000001011"));
        assertTrue(new WebId(11,31).toString().equals("0000000000000000000000000001011"));
        assertTrue(new WebId(Integer.MAX_VALUE -1,31).toString().equals("1111111111111111111111111111110"));
        assertTrue(new WebId(Integer.MAX_VALUE,31).toString().equals("1111111111111111111111111111111"));
    }

    public void testEqualsObject() {
        WebId webId_0_a = new WebId(0, 0);
        WebId webId_0_b = new WebId(0, 0);
        WebId webId_0_c = new WebId(0, 1);
        WebId webId_1_a = new WebId(1, 1);
        WebId webId_1_b = new WebId(1, 1);
        WebId webId_1_c = new WebId(1, 2);
        WebId webId_27_a = new WebId(27, 5);
        WebId webId_27_b = new WebId(27, 5);
        WebId webId_27_c = new WebId(27, 7);
        WebId webId_MAX_INT_minus_1_a = new WebId(Integer.MAX_VALUE - 1, 31);
        WebId webId_MAX_INT_minus_1_b = new WebId(Integer.MAX_VALUE - 1, 31);
        WebId webId_MAX_INT_a = new WebId(Integer.MAX_VALUE, 31);
        WebId webId_MAX_INT_b = new WebId(Integer.MAX_VALUE, 31);
        WebId webIdBig_a = new WebId(Integer.MAX_VALUE/2, 30);
        WebId webIdBig_b = new WebId(Integer.MAX_VALUE/2, 30);
        WebId webIdBig_c = new WebId(Integer.MAX_VALUE/2, 31);
        
        assertTrue(webId_0_a.equals(webId_0_a) && webId_0_a == webId_0_a);
        assertTrue(webId_0_a.equals(webId_0_b) && webId_0_a != webId_0_b);
        assertFalse(webId_0_a.equals(webId_1_a));
        assertFalse(webId_0_a.equals(webId_27_a));
        assertFalse(webId_0_a.equals(webId_MAX_INT_minus_1_a));
        assertFalse(webId_0_a.equals(webId_MAX_INT_a));
        assertFalse(webId_0_a.equals(webIdBig_a));
        assertFalse(webId_0_a.equals(webIdBig_c));
        
        assertTrue(webId_1_a.equals(webId_1_a) && webId_1_a == webId_1_a);
        assertTrue(webId_1_a.equals(webId_1_b) && webId_1_a != webId_1_b);
        assertFalse(webId_1_a.equals(webId_0_a));
        assertFalse(webId_1_a.equals(webId_27_a));
        assertFalse(webId_1_a.equals(webId_MAX_INT_minus_1_a));
        assertFalse(webId_1_a.equals(webId_MAX_INT_a));
        assertFalse(webId_1_a.equals(webIdBig_a));
        assertFalse(webId_1_a.equals(webIdBig_c));
        
        assertTrue(webId_27_a.equals(webId_27_a) && webId_27_a == webId_27_a);
        assertTrue(webId_27_a.equals(webId_27_b) && webId_27_a != webId_27_b);
        assertFalse(webId_27_a.equals(webId_0_a));
        assertFalse(webId_27_a.equals(webId_1_a));
        assertFalse(webId_27_a.equals(webId_MAX_INT_minus_1_a));
        assertFalse(webId_27_a.equals(webId_MAX_INT_a));
        assertFalse(webId_27_a.equals(webIdBig_a));
        assertFalse(webId_27_a.equals(webIdBig_c));
        
        assertTrue(webId_MAX_INT_minus_1_a.equals(webId_MAX_INT_minus_1_a) && webId_MAX_INT_minus_1_a == webId_MAX_INT_minus_1_a);
        assertTrue(webId_MAX_INT_minus_1_a.equals(webId_MAX_INT_minus_1_b) && webId_MAX_INT_minus_1_a != webId_MAX_INT_minus_1_b);
        assertFalse(webId_MAX_INT_minus_1_a.equals(webId_0_a));
        assertFalse(webId_MAX_INT_minus_1_a.equals(webId_1_a));
        assertFalse(webId_MAX_INT_minus_1_a.equals(webId_27_a));
        assertFalse(webId_MAX_INT_minus_1_a.equals(webId_MAX_INT_a));
        assertFalse(webId_MAX_INT_minus_1_a.equals(webIdBig_a));
        assertFalse(webId_MAX_INT_minus_1_a.equals(webIdBig_c));
        
        assertTrue(webId_MAX_INT_a.equals(webId_MAX_INT_a) && webId_MAX_INT_a == webId_MAX_INT_a);
        assertTrue(webId_MAX_INT_a.equals(webId_MAX_INT_b) && webId_MAX_INT_a != webId_MAX_INT_b);
        assertFalse(webId_MAX_INT_a.equals(webId_0_a));
        assertFalse(webId_MAX_INT_a.equals(webId_1_a));
        assertFalse(webId_MAX_INT_a.equals(webId_27_a));
        assertFalse(webId_MAX_INT_a.equals(webId_MAX_INT_minus_1_a));
        assertFalse(webId_MAX_INT_a.equals(webIdBig_a));
        assertFalse(webId_MAX_INT_a.equals(webIdBig_c));
        
        assertTrue(webIdBig_a.equals(webIdBig_a) && webIdBig_a == webIdBig_a);
        assertTrue(webIdBig_a.equals(webIdBig_b) && webIdBig_a != webIdBig_b);
        
        assertFalse(webId_0_a.equals(webId_0_c));
        assertFalse(webId_1_a.equals(webId_1_c));
        assertFalse(webId_27_a.equals(webId_27_c));
        assertFalse(webIdBig_a.equals(webIdBig_c));
        
        assertFalse(webId_0_a.equals(null));
        assertFalse(webId_1_a.equals(1));
        
        //White Box Testing: Internal Boundary Testing on different Ids but identical heights.
        //  Greatest difference in ids at height 4.
        assertFalse(new WebId(0,4).equals(new WebId(15,4)));
        assertFalse(new WebId(15,4).equals(new WebId(0,4)));
        //  Greatest difference - 1 in ids at height 4.
        assertFalse(new WebId(0,4).equals(new WebId(14,4)));
        assertFalse(new WebId(15,4).equals(new WebId(1,4)));
        //  1 < difference in ids  < Greatest difference - 1 at height 4.
        assertFalse(new WebId(5,4).equals(new WebId(9,4)));
        assertFalse(new WebId(11,4).equals(new WebId(3,4)));
        //  Difference between ids is 1 in ids at height 4.
        assertFalse(new WebId(5,4).equals(new WebId(6,4)));
        assertFalse(new WebId(10,4).equals(new WebId(9,4)));
        //  Ids are identical
        assertTrue(new WebId(5,4).equals(new WebId(5,4)));
    }

    public void testGetValue() {
        WebId webId_0 = new WebId(0, 0);
        WebId webId_1 = new WebId(1, 1);
        WebId webId_1011 = new WebId(1011, 10);
        WebId webId_MAX_INT_minus_1 = new WebId(Integer.MAX_VALUE - 1, 31);
        WebId webId_MAX_INT = new WebId(Integer.MAX_VALUE, 31);
        
        assertTrue(webId_0.getValue() == 0);
        assertTrue(webId_1.getValue() == 1);
        assertTrue(webId_1011.getValue() == 1011);
        assertTrue(webId_MAX_INT_minus_1.getValue() == (Integer.MAX_VALUE - 1));
        assertTrue(webId_MAX_INT.getValue() == Integer.MAX_VALUE);
    }

    public void testGetHeight() {
        WebId webId_0 = new WebId(0, 0);
        WebId webId_1 = new WebId(1, 1);
        WebId webId_1011 = new WebId(1011, 10);
        WebId webId_MAX_INT_minus_1 = new WebId(Integer.MAX_VALUE/2, 30);
        WebId webId_MAX_INT = new WebId(Integer.MAX_VALUE, 31);
        
        assertTrue(webId_0.getHeight() == 0);
        assertTrue(webId_1.getHeight() == 1);
        assertTrue(webId_1011.getHeight() == 10);
        assertTrue(webId_MAX_INT_minus_1.getHeight() == 30);
        assertTrue(webId_MAX_INT.getHeight() == 31);
    }
    
    public void testIsNeighborOf() {
        //Valid Test Cases
        WebId webId1 = new WebId(0, 0);
        WebId webId2 = new WebId(1, 1);
        WebId webId3 = new WebId(0, 1);
        WebId webId4 = new WebId(5, 3);
        WebId webId5 = new WebId(1, 3);
        WebId webId6 = new WebId(4, 3);
        WebId webId7 = new WebId(7, 3);
        WebId webId8 = new WebId(Integer.MAX_VALUE, 31);
        WebId webId9 = new WebId(Integer.MAX_VALUE - 1, 31);
        WebId webIda = new WebId(0, 3);
        WebId webIdb = new WebId(6, 3);
        WebId webIdc = new WebId(3, 3);
        WebId webIdd = new WebId(2, 3);
        
        
        assertFalse(webId1.isNeighborOf(webId1));        
        assertFalse(webId1.isNeighborOf(webId2));        
        assertFalse(webId2.isNeighborOf(webId1));
        assertTrue(webId2.isNeighborOf(webId3));
        assertTrue(webId3.isNeighborOf(webId2));
        
        assertTrue(webId4.isNeighborOf(webId5));
        assertTrue(webId5.isNeighborOf(webId4));
        assertTrue(webId4.isNeighborOf(webId6));
        assertTrue(webId6.isNeighborOf(webId4));
        assertTrue(webId4.isNeighborOf(webId7));
        assertTrue(webId7.isNeighborOf(webId4));
        
        assertTrue(webId8.isNeighborOf(webId9));
        
        assertFalse(webId4.isNeighborOf(webIda));        
        assertFalse(webId4.isNeighborOf(webIdb));        
        assertFalse(webId4.isNeighborOf(webIdc));        
        assertFalse(webId4.isNeighborOf(webIdd));
        
        assertFalse(webIdb.isNeighborOf(webId3));
        
        assertFalse(webId4.isNeighborOf(webId2));
        assertFalse(webId2.isNeighborOf(webId4));
        
        WebId a = new WebId(10,6);
        WebId b = new WebId(8, 5);
        assertFalse(a.isNeighborOf(b));
        assertFalse(b.isNeighborOf(a));
        
        WebId c = new WebId(8, 7);
        assertTrue(a.isNeighborOf(c));
        assertTrue(c.isNeighborOf(a));
        
        //Invalid Test Cases
        try{
            webId4.isNeighborOf(WebId.NULL_WEB_ID);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId4.isNeighborOf(null);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            WebId.NULL_WEB_ID.isNeighborOf(webId7);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
    }
    
    public void testIsSurrogateNeighborOf() {
        //Valid Test Cases
        WebId webId1  = new WebId(0, 0);
        WebId webId2  = new WebId(0, 1);
        WebId webId3  = new WebId(1, 1);
        WebId webId4  = new WebId(1, 2);
        WebId webId5  = new WebId(2, 2);
        WebId webId6  = new WebId(21, 5);
        WebId webId7  = new WebId(7, 4);
        WebId webId8  = new WebId(13, 4);
        WebId webId9  = new WebId(8, 4);
        WebId webId10 = new WebId(1, 3);
        WebId webId11 = new WebId(2, 3);
        WebId webId12 = new WebId(4, 3);
        WebId webId13 = new WebId(62, 6);
        WebId webId14 = new WebId(31, 5);
        WebId webId15 = new WebId(13,5);
        WebId webId16 = new WebId(4, 4);
        WebId webId17 = new WebId(15, 4);
            
        assertTrue(webId3.isSurrogateNeighborOf(webId5));
        assertTrue(webId7.isSurrogateNeighborOf(webId6));
        assertTrue(webId8.isSurrogateNeighborOf(webId6));
        assertTrue(webId10.isSurrogateNeighborOf(webId9));
        assertTrue(webId11.isSurrogateNeighborOf(webId9));
        assertTrue(webId12.isSurrogateNeighborOf(webId9));
        assertTrue(webId14.isSurrogateNeighborOf(webId13));
            
        assertFalse(webId5.isSurrogateNeighborOf(webId3));
        assertFalse(webId6.isSurrogateNeighborOf(webId7));
        assertFalse(webId6.isSurrogateNeighborOf(webId8));
        assertFalse(webId9.isSurrogateNeighborOf(webId10));
        assertFalse(webId9.isSurrogateNeighborOf(webId11));
        assertFalse(webId9.isSurrogateNeighborOf(webId12));
        assertFalse(webId13.isSurrogateNeighborOf(webId14));
        
        assertFalse(webId2.isSurrogateNeighborOf(webId3));
        assertFalse(webId3.isSurrogateNeighborOf(webId2));
        assertFalse(webId4.isSurrogateNeighborOf(webId5));
        assertFalse(webId15.isSurrogateNeighborOf(webId6));
        assertFalse(webId16.isSurrogateNeighborOf(webId6));
        assertFalse(webId17.isSurrogateNeighborOf(webId6));
                
        //Invalid Test Cases        
        try{
            webId1.isSurrogateNeighborOf(webId3);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId4.isSurrogateNeighborOf(WebId.NULL_WEB_ID);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId4.isSurrogateNeighborOf(null);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            WebId.NULL_WEB_ID.isSurrogateNeighborOf(webId5);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
    }
    
    public void testIsFoldOf() {
        //Valid Test Cases
        WebId webId1  = new WebId(0, 0);
        WebId webId1a = new WebId(0, 0);
        WebId webId2  = new WebId(0, 1);
        WebId webId3  = new WebId(1, 1);
        WebId webId4  = new WebId(1, 2);
        WebId webId5  = new WebId(2, 2);
        WebId webId6  = new WebId(0, 2);
        WebId webId7  = new WebId(3, 2);
        WebId webId8  = new WebId(28, 5);
        WebId webId9  = new WebId(3, 5);
        WebId webId10 = new WebId(3, 4);
        WebId webId11 = new WebId(3, 3);
        WebId webId12 = new WebId(2, 2);
        WebId webId13 = new WebId(35, 6);
        WebId webId14 = new WebId(34, 6);
        
        assertTrue(webId1.isFoldOf(webId1));
        assertTrue(webId1.isFoldOf(webId1a));
            
        assertTrue(webId2.isFoldOf(webId3));
        assertTrue(webId3.isFoldOf(webId2));
        
        assertTrue(webId5.isFoldOf(webId3));
        assertTrue(webId3.isFoldOf(webId5));
        
        assertTrue(webId5.isFoldOf(webId4));
        assertTrue(webId4.isFoldOf(webId5));
        
        assertTrue(webId6.isFoldOf(webId7));
        assertTrue(webId7.isFoldOf(webId6));
        
        assertTrue(webId8.isFoldOf(webId9));
        assertTrue(webId9.isFoldOf(webId8));
        
        assertTrue(webId8.isFoldOf(webId13));
        assertTrue(webId13.isFoldOf(webId8));
        
        assertFalse(webId8.isFoldOf(webId14));
        assertFalse(webId14.isFoldOf(webId8));
        
        assertFalse(webId6.isFoldOf(webId1));
        assertFalse(webId1.isFoldOf(webId6));
        
        assertFalse(webId1.isFoldOf(webId2));
        assertFalse(webId2.isFoldOf(webId1));
        
        assertFalse(webId1.isFoldOf(webId3));
        assertFalse(webId3.isFoldOf(webId1));
        
        assertFalse(webId7.isFoldOf(webId1));
        assertFalse(webId1.isFoldOf(webId7));
        
        assertFalse(webId7.isFoldOf(webId2));
        assertFalse(webId2.isFoldOf(webId7));
        
        assertFalse(webId1.isFoldOf(webId3));
        assertFalse(webId3.isFoldOf(webId1));
        
        assertFalse(webId10.isFoldOf(webId8));
        assertFalse(webId8.isFoldOf(webId10));
        
        assertFalse(webId11.isFoldOf(webId8));
        assertFalse(webId8.isFoldOf(webId11));
        
        assertFalse(webId11.isFoldOf(webId8));
        assertFalse(webId8.isFoldOf(webId11));
        
        assertFalse(webId12.isFoldOf(webId6));
        assertFalse(webId6.isFoldOf(webId12));
        
        //Invalid Test Cases    
        try{
            webId4.isFoldOf(WebId.NULL_WEB_ID);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId4.isFoldOf(null);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            WebId.NULL_WEB_ID.isFoldOf(webId5);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
    }
    
    public void testIsSurrogateFoldOf() {
        //Valid Test Cases
        WebId webId1  = new WebId(1, 1);
        WebId webId2  = new WebId(0, 2);
        WebId webId3  = new WebId(3, 2);
        WebId webId4  = new WebId(0, 3);
        WebId webId5  = new WebId(10, 4);
        WebId webId6  = new WebId(5, 5);
        WebId webId7  = new WebId(10, 5);
        WebId webId8  = new WebId(5, 4);
        WebId webId9  = new WebId(21, 5);
        WebId webId10 = new WebId(11, 4);
        
        
        assertTrue(webId1.isSurrogateFoldOf(webId2));
        assertTrue(webId3.isSurrogateFoldOf(webId4));
        assertTrue(webId5.isSurrogateFoldOf(webId6));
        
        assertFalse(webId2.isSurrogateFoldOf(webId1));
        assertFalse(webId4.isSurrogateFoldOf(webId3));
        assertFalse(webId6.isSurrogateFoldOf(webId5));
        
        assertFalse(webId5.isSurrogateFoldOf(webId7));
        assertFalse(webId8.isSurrogateFoldOf(webId9));        
        assertFalse(webId10.isSurrogateFoldOf(webId6));
        
        //Invalid Test Cases    
        try{
            webId4.isSurrogateFoldOf(WebId.NULL_WEB_ID);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId4.isSurrogateFoldOf(null);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            WebId.NULL_WEB_ID.isSurrogateFoldOf(webId5);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        //White Box Testing: Relational Condition Coverage
        //   height + 1 == webId.height
        assertFalse(new WebId(3, 2).isSurrogateFoldOf(new WebId(4,3)));
        assertFalse(new WebId(3, 2).isSurrogateFoldOf(new WebId(1,3)));
        assertTrue(webId5.isSurrogateFoldOf(webId6));
        //   height == webId.height
        assertFalse(new WebId(10,5).isSurrogateFoldOf(new WebId(5,5)));
        //   height + 2 == webId.height
        assertFalse(new WebId(10,4).isSurrogateFoldOf(new WebId(5,6)));
        
        //   webId.id + 2 == id
        assertFalse(new WebId(2,2).isSurrogateFoldOf(new WebId(0,3)));
        //  webId.id + 1 == id
        assertTrue(new WebId(2,2).isSurrogateFoldOf(new WebId(1,3)));
        assertFalse(new WebId(2,2).isSurrogateFoldOf(new WebId(2,3)));
        //  webId.id == id
        assertFalse(new WebId(2,2).isSurrogateFoldOf(new WebId(2,3)));
        
        //  (mask & webId.id) == (mask & ~id);
        assertTrue(new WebId(18,5).isSurrogateFoldOf(new WebId(13,6)));
        //  (mask & (webId.id - 1)) == (mask & ~id);
        assertFalse(new WebId(16,5).isSurrogateFoldOf(new WebId(13,6)));
        //  (mask & (webId.id + 1)) == (mask & ~id);
        assertFalse(new WebId(19,5).isSurrogateFoldOf(new WebId(13,6)));
        
        //White Box Testing: Complete Condition Coverage
        //   height + 1 == webId.height AND webId.id < id AND (mask & webId.id) == (mask & ~id);
        assertTrue(new WebId(18,5).isSurrogateFoldOf(new WebId(13,6)));
        //   height + 1 == webId.height AND webId.id < id AND (mask & webId.id) != (mask & ~id);
        assertFalse(new WebId(18,5).isSurrogateFoldOf(new WebId(12,6)));
        //   height + 1 == webId.height AND webId.id >= id AND (mask & webId.id) == (mask & ~id);
        assertFalse(new WebId(7,5).isSurrogateFoldOf(new WebId(24,6)));
        //   height + 1 == webId.height AND webId.id >= id AND (mask & webId.id) != (mask & ~id);
        assertFalse(new WebId(7,5).isSurrogateFoldOf(new WebId(25,6)));
        //   height + 1 != webId.height AND webId.id < id AND (mask & webId.id) == (mask & ~id);
        assertFalse(new WebId(18,7).isSurrogateFoldOf(new WebId(13,6)));
        //   height + 1 != webId.height AND webId.id < id AND (mask & webId.id) != (mask & ~id);
        assertFalse(new WebId(18,6).isSurrogateFoldOf(new WebId(12,6)));
        //   height + 1 == webId.height AND webId.id >= id AND (mask & webId.id) == (mask & ~id);
        assertFalse(new WebId(7,6).isSurrogateFoldOf(new WebId(28,6)));
        //   height + 1 != webId.height AND webId.id >= id AND (mask & webId.id) != (mask & ~id);
        assertFalse(new WebId(7,7).isSurrogateFoldOf(new WebId(11,6)));
    }
    
    public void testIsInverseSurrogateFoldOf() {
        //Valid Test Cases
        WebId webId1  = new WebId(1, 1);
        WebId webId2  = new WebId(0, 2);
        WebId webId3  = new WebId(3, 2);
        WebId webId4  = new WebId(0, 3);
        WebId webId5  = new WebId(10, 4);
        WebId webId6  = new WebId(5, 5);
        WebId webId7  = new WebId(10, 5);
        WebId webId8  = new WebId(21, 6);
        WebId webId9  = new WebId(21, 5);
        WebId webId10 = new WebId(11, 4);
        
        //Valid Test Cases
        assertTrue(webId2.isInverseSurrogateFoldOf(webId1));
        assertTrue(webId4.isInverseSurrogateFoldOf(webId3));
        assertTrue(webId6.isInverseSurrogateFoldOf(webId5));
        
        assertFalse(webId8.isInverseSurrogateFoldOf(webId7));
        assertFalse(webId10.isInverseSurrogateFoldOf(webId9));
        
        //Invalid Test Cases
        try{
            webId4.isInverseSurrogateFoldOf(WebId.NULL_WEB_ID);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            webId4.isInverseSurrogateFoldOf(null);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        try{
            WebId.NULL_WEB_ID.isInverseSurrogateFoldOf(webId5);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
    
    }
    
    public void testLocationOfMostSignificantOneBit() {
        //Valid Test Cases
        assertTrue(WebId.locationOfMostSignificantOneBit(0) == -1);
        assertTrue(WebId.locationOfMostSignificantOneBit(1) == 0);
        assertTrue(WebId.locationOfMostSignificantOneBit(4) == 2);
        assertTrue(WebId.locationOfMostSignificantOneBit(Integer.MAX_VALUE - 1) == 30);
        assertTrue(WebId.locationOfMostSignificantOneBit(Integer.MAX_VALUE) == 30);
        
        //White Box Testing: Loop Testing
        //  No passes through loop
        assertTrue(WebId.locationOfMostSignificantOneBit(0) == -1);
        //  One pass through loop
        assertTrue(WebId.locationOfMostSignificantOneBit(1) == 0);
        //  Two passes through loop
        assertTrue(WebId.locationOfMostSignificantOneBit(3) == 1);
        //  M passes where M > 2 AND M < MAX-1
        assertTrue(WebId.locationOfMostSignificantOneBit(42) == 5);
        //  MAX - 1 passes through loop
        assertTrue(WebId.locationOfMostSignificantOneBit(Integer.MAX_VALUE/2) == 29);
        //  MAX passes through loop
        assertTrue(WebId.locationOfMostSignificantOneBit(Integer.MAX_VALUE) == 30);
        
        //Invalid Test Cases
        try{
            WebId.locationOfMostSignificantOneBit(-1);
            fail();
        }catch(AssertionError e){
            assertTrue(true);
        }
        
        //White Box Testing: Dataflow Testing
        // Path 1: id defined on line 0, used on line 1, 3, and 5
        // Path 2: id defined on line 0, used on line 1, and 3
        // Path 3: id defined on line 5, used on line 3 and line 5
        // Path 4: id defined on line 5, used on line 3 then exists program
        // Path 5: result defined on line 2, used on line 4
        // Path 6: result defined on line 4, used on line 4
        // Path 7: result defined on line 2, used on line 7
        // Path 8: result defined on line 4, used on line 7
        
        //I only need two test cases
        // Covers paths 2 and 7
        assertTrue(WebId.locationOfMostSignificantOneBit(0) == -1);
        // Covers paths 1, 3, 4, 5, 6, 8
        assertTrue(WebId.locationOfMostSignificantOneBit(21) == 4);
    }

}
