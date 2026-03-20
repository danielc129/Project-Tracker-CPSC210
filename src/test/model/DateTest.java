package model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class DateTest {
    private Date date1;
    private Date date2;
    private Date date3;
    private Date date4;
    private Date date5;
    private Date date6;
    private Date date7;
    private Date date8;
    private Date date9;
    private Date date10;
    private Date date11;
    private Date date12;
    private Date date13;
    private Date date14;
    private Date date15;

    @BeforeEach
    public void setup() {
        date1 = new Date(8, 1, 2025);
        date2 = new Date(12, 2, 2024);
        date3 = new Date(31, 3, 2025);
        date4 = new Date(20, 4, 1);
        date5 = new Date(15, 5, 2025);
        date6 = new Date(1, 6, 2025);
        date7 = new Date(2, 7, 2025);
        date8 = new Date(3, 8, 2026);
        date9 = new Date(4, 9, 2025);
        date10 = new Date(5, 10, 2025);
        date11 = new Date(6, 11, 2025);
        date12 = new Date(7, 12, 2025);
        date13 = new Date(7, 1, 2025);
        date14 = new Date(9, 1, 2025);
        date15 = new Date(8, 1, 2025);
    }

    @Test
    public void testConstructor() {
        assertEquals(8, date1.getDay());
        assertEquals(1, date1.getMonth());
        assertEquals(2025, date1.getYear());

        assertEquals(12, date2.getDay());
        assertEquals(2, date2.getMonth());
        assertEquals(2024, date2.getYear());
    }

    @Test
    public void testGetMonthAsString() {
        assertEquals("January", date1.getMonthAsString());
        assertEquals("February", date2.getMonthAsString());
        assertEquals("March", date3.getMonthAsString());
        assertEquals("April", date4.getMonthAsString());
        assertEquals("May", date5.getMonthAsString());
        assertEquals("June", date6.getMonthAsString());
        assertEquals("July", date7.getMonthAsString());
        assertEquals("August", date8.getMonthAsString());
        assertEquals("September", date9.getMonthAsString());
        assertEquals("October", date10.getMonthAsString());
        assertEquals("November", date11.getMonthAsString());
        assertEquals("December", date12.getMonthAsString());
    }

    @Test
    public void testGetDateAsString() {
        assertEquals("January 8, 2025", date1.getDateAsString());
        assertEquals("February 12, 2024", date2.getDateAsString());
    }

    @Test
    public void testCompareToAfter() {
        assertEquals(1, date14.compareTo(date1));
        assertEquals(1, date3.compareTo(date1));
        assertEquals(1, date8.compareTo(date1));
    }

    @Test
    public void testCompareToEqual() {
        assertEquals(0, date15.compareTo(date1));
    }

    @Test
    public void testCompareToBefore() {
        assertEquals(-1, date13.compareTo(date1));
        assertEquals(-1, date10.compareTo(date11));
        assertEquals(-1, date2.compareTo(date1));
    }

    @Test
    public void testIsDateValidInvalidMonthUpper() {
        assertFalse(Date.isDateValid(5, 13, 2026));
    }

    @Test
    public void testIsDateValidInvalidMonthLower() {
        assertFalse(Date.isDateValid(5, 0, 2026));
    }

    @Test
    public void testIsDateValidJanuaryInvalidDateUpper() {
        assertFalse(Date.isDateValid(32, 1, 2026));
    }

    @Test
    public void testIsDateValidJanuaryInvalidDateLower() {
        assertFalse(Date.isDateValid(0, 1, 2026));
    }

    @Test
    public void testIsDateValidAprilInvalidDateUpper() {
        assertFalse(Date.isDateValid(31, 4, 2026));
    }

    @Test
    public void testIsDateValidAprilInvalidDateLower() {
        assertFalse(Date.isDateValid(0, 4, 2026));
    }

    @Test
    public void testIsDateValidFebruaryLeapYearInvalidDate() {
        assertFalse(Date.isDateValid(30, 2, 2024));
        assertFalse(Date.isDateValid(0, 2, 2024));
    }

    @Test
    public void testIsDateValidFebruaryNonLeapYearInvalidDate() {
        assertFalse(Date.isDateValid(29, 2, 2026));
        assertFalse(Date.isDateValid(0, 2, 2026));
    }

    @Test
    public void testIsDateValidValid() {
        assertTrue(Date.isDateValid(5, 12, 2026));
        assertTrue(Date.isDateValid(31, 1, 2026));
        assertTrue(Date.isDateValid(30, 4, 2026));
        assertTrue(Date.isDateValid(29, 2, 2024));
        assertTrue(Date.isDateValid(28, 2, 2026));
        assertTrue(Date.isDateValid(5, 1, 2026));
        assertTrue(Date.isDateValid(5, 2, 2026));
        assertTrue(Date.isDateValid(5, 3, 2026));
        assertTrue(Date.isDateValid(5, 4, 2026));
        assertTrue(Date.isDateValid(5, 5, 2026));
        assertTrue(Date.isDateValid(5, 6, 2026));
        assertTrue(Date.isDateValid(5, 7, 2026));
        assertTrue(Date.isDateValid(5, 8, 2026));
        assertTrue(Date.isDateValid(5, 9, 2026));
        assertTrue(Date.isDateValid(5, 10, 2026));
        assertTrue(Date.isDateValid(5, 11, 2026));
        assertTrue(Date.isDateValid(5, 12, 2026));
        assertTrue(Date.isDateValid(5, 2, 2000));
        assertTrue(Date.isDateValid(5, 2, 1900));
        assertTrue(Date.isDateValid(5, 2, 2026));
    }
}
