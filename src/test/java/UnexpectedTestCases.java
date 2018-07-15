import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

public class UnexpectedTestCases {

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
    public void testExtremeDates(){
        ReservationStore resStore = ReservationStore.getInstance();
        CampSiteStore campSiteStore = CampSiteStore.getInstance();

        CampSite newCampSite = new CampSite(1, "Smokeys");
        Reservation newReservation = new Reservation(1, new DateRange(TestConstants.TEST_DATE_HALF_MAX,
                TestConstants.TEST_DATE_MAX));

        campSiteStore.addCampsite(newCampSite);
        resStore.addReservation(newReservation);

        assert(resStore.getOpenCamps(new DateRange(TestConstants.TEST_DATE_LOW_2,
                TestConstants.TEST_DATE_HIGH_2)).contains("Smokeys"));

        assert(resStore.getOpenCamps(new DateRange(TestConstants.TEST_DATE_HIGH_2,
                TestConstants.TEST_DATE_HALF_MAX)).contains("Smokeys"));

        assert(resStore.getOpenCamps(new DateRange(TestConstants.TEST_DATE_MAX,
                TestConstants.TEST_DATE_MAX)).contains("Smokeys"));

        assert(!resStore.getOpenCamps(new DateRange(TestConstants.TEST_DATE_LOW,
                TestConstants.TEST_DATE_MAX)).contains("Smokeys"));

        assert(!resStore.getOpenCamps(new DateRange(TestConstants.TEST_DATE_HALF_MAX,
                TestConstants.TEST_DATE_MAX)).contains("Smokeys"));

        //negative date. Technically okay because the long value represents the offset from a specific date (some 1970?)
        assert(resStore.getOpenCamps(new DateRange(TestConstants.TEST_DATE_MIN,
                TestConstants.TEST_DATE_HALF_MAX)).contains("Smokeys"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRange(){
        ReservationStore resStore = ReservationStore.getInstance();
        CampSiteStore campSiteStore = CampSiteStore.getInstance();

        CampSite newCampSite = new CampSite(1, "Smokeys");
        Reservation newReservation = new Reservation(1, new DateRange(TestConstants.TEST_DATE_HIGH,
                TestConstants.TEST_DATE_LOW));

        campSiteStore.addCampsite(newCampSite);
        resStore.addReservation(newReservation);

        assert(!resStore.getOpenCamps(new DateRange(TestConstants.TEST_DATE_LOW,
                TestConstants.TEST_DATE_HIGH)).contains("Smokeys"));
    }

    @Test
    public void testLargeStores(){
        ReservationStore resStore = ReservationStore.getInstance();
        CampSiteStore campSiteStore = CampSiteStore.getInstance();

        for(int i = 0; i < 100000; i++){
            Date newDateLow = new Date(604800000); //Month in millis
            Date newDateHigh = new Date(604800000+86400000); //The month + a new day in millis

            CampSite newCampsite = new CampSite(i, Integer.toString(i));
            campSiteStore.addCampsite(newCampsite);

            Reservation newReservation = new Reservation(i, new DateRange(newDateLow, newDateHigh));
            try{
                resStore.addReservation(newReservation);
            }catch(IllegalArgumentException e){
                System.out.println(e);
            }
        }

        assert(resStore.getOpenCamps(new DateRange(TestConstants.TEST_DATE_LOW_2,
                TestConstants.TEST_DATE_HIGH_2)).contains("500"));

        resStore.addReservation(new Reservation(200000,
                new DateRange(TestConstants.TEST_DATE_LOW, TestConstants.TEST_DATE_HIGH)));

        assert(!resStore.getOpenCamps(new DateRange(TestConstants.TEST_DATE_LOW,
                TestConstants.TEST_DATE_MAX)).contains("20000"));
    }
}
