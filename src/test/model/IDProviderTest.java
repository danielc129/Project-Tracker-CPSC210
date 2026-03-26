package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class IDProviderTest {
    private IDProvider idProvider;

    @BeforeEach
    public void setup() {
        idProvider = IDProvider.getInstance();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(idProvider);
        IDProvider idProvider2 = IDProvider.getInstance();
        assertEquals(idProvider, idProvider2);
    }

    @Test
    public void testGetUniqueIdentifier() {
        int startingID = idProvider.getUniqueIdentifier();
        assertEquals(startingID + 1, idProvider.getUniqueIdentifier());
        assertEquals(startingID + 2, idProvider.getUniqueIdentifier());
        assertEquals(startingID + 3, idProvider.getUniqueIdentifier());
    }
}
