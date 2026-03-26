package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Event class
 */
// ATTRIBUTION: from AlarmSystem
@ExcludeFromJacocoGeneratedReport
public class EventTest {
	private Event e1;
	private Date d;
	
	
	@BeforeEach
	public void runBefore() {
		e1 = new Event("Sensor open at door");   // (1)
        d = Calendar.getInstance().getTime();
	}
	
	@Test
	public void testEvent() {
		assertEquals("Sensor open at door", e1.getDescription());
		assertEquals(d.toString(), e1.getDate().toString());
	}

	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "Sensor open at door", e1.toString());
	}

}
