import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

public class ExpectedTestCases {

    @Before
    public void resetReservationStore() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field instance = ReservationStore.class.getDeclaredField("myInstance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Before
    public void resetCampSiteStore() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field instance = CampSiteStore.class.getDeclaredField("myInstance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void testExpected() {
        ReservationStore resStore = ReservationStore.getInstance();
        CampSiteStore campSiteStore = CampSiteStore.getInstance();

        CampSite newCampSite = new CampSite(1, "Smokeys");
        Reservation newReservation = new Reservation(1, new DateRange(TestConstants.TEST_DATE_LOW,
                TestConstants.TEST_DATE_HIGH));

        campSiteStore.addCampsite(newCampSite);
        resStore.addReservation(newReservation);

        assert(resStore.getOpenCamps(new DateRange(TestConstants.TEST_DATE_LOW_2,
                TestConstants.TEST_DATE_HIGH_2)).contains("Smokeys"));
    }

    @Test
    public void testSameDate() {
        ReservationStore resStore = ReservationStore.getInstance();
        CampSiteStore campSiteStore = CampSiteStore.getInstance();

        CampSite newCampSite = new CampSite(1, "Smokeys");
        Reservation newReservation = new Reservation(1, new DateRange(TestConstants.TEST_DATE_LOW,
                TestConstants.TEST_DATE_HIGH));

        campSiteStore.addCampsite(newCampSite);
        resStore.addReservation(newReservation);

        assert(!resStore.getOpenCamps(new DateRange(TestConstants.TEST_DATE_LOW,
                TestConstants.TEST_DATE_HIGH)).contains("Smokeys"));
    }
}
