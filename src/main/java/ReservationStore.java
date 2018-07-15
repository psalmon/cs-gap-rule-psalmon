import java.util.*;

/**
 * Data structure for storing off reservations.
 * Underlying data structure is a TreeMap.
 * TreeMap was chosen because we need to pair camps to date ranges reserved.
 * We want to order by campsite for the sake of checking ranges.
 * The TreeMap value is a TreeSet of DateRanges, representing all the reserved dates.
 * The values are sorted as per the overridden compareTo method in DateRange, also important for checking ranges.
 * @author Paul Salmon
 */
public class ReservationStore {
    private TreeMap<String, TreeSet<DateRange>> reservationData = new TreeMap<>();

    private static ReservationStore myInstance = null;

    /**
     * Singleton instance getter.
     * Like CampSite store, there should only be one store for reservations.
     * Like CampSiteStore, Singleton is initialized in the handler because we use reflections to deal with testing our
     * static types.
     * @return the singleton ReservationStore instance.
     */
    public static ReservationStore getInstance() {
        if(myInstance == null){
            myInstance = new ReservationStore();
        }
        return myInstance;
    }

    /**
     * Adds a reservation to the underlying structure.
     * Maps the CampSites name to the reserved date ranges.
     * @param newReservation The reservation we are going to add to the structure.
     * @return true if added successfully, false if not (meaning the range exists)
     */
    public boolean addReservation(Reservation newReservation) {

        CampSiteStore campsiteStore = CampSiteStore.getInstance();

        int reservationId = newReservation.getCAMP_SITE_ID();
        String reservationCampsiteName = campsiteStore.getNameById(reservationId);
        DateRange reservationDateRange = newReservation.getDATE_RANGE();

        if(reservationData.containsKey(reservationCampsiteName)) {
            for(DateRange dr : reservationData.get(reservationCampsiteName)) {
                if (dr.getSTART_DATE() == reservationDateRange.getSTART_DATE() &&
                        dr.getEND_DATE() == reservationDateRange.getEND_DATE()) {
                    System.out.println("Date range is already booked for campsite: " + reservationCampsiteName);
                    return false;
                }
            }
            TreeSet<DateRange> updatedValue = new TreeSet<>(reservationData.get(reservationCampsiteName));
            updatedValue.add(reservationDateRange);
            reservationData.put(reservationCampsiteName, updatedValue);
        } else {
            TreeSet<DateRange> initialValue = new TreeSet<>();
            initialValue.add(reservationDateRange);
            reservationData.put(reservationCampsiteName, initialValue);
        }
        return true;
    }

    /**
     * Queries the underlying reservation data structure for open campsites during
     * the provided date range.
     * @param queriedDateRange The range we are getting campsite availability for.
     * @return All of the CampSites that are available be booked in the given date range.
     */
    public HashSet<String> getOpenCamps(DateRange queriedDateRange) {
        HashSet<String> availableCampsites = new HashSet<>();

        for(String campSite: reservationData.keySet()){

            TreeSet<DateRange> reservedTimes = reservationData.get(campSite);

            Date queriedStart = queriedDateRange.getSTART_DATE();
            Date queriedEnd = queriedDateRange.getEND_DATE();

            Date t1;
            Date t2;

            boolean isAvailable = true;

            Iterator<DateRange> reservedTimeIterator = reservedTimes.iterator();
            DateRange currentDR = reservedTimeIterator.next();

            t1 = currentDR.getSTART_DATE();
            t2 = currentDR.getEND_DATE();

            for(int i = 0; i < reservedTimes.size(); i++){

                if(t1.before(queriedEnd) && queriedStart.before(t2)) {
                    isAvailable = false;
                    break;//overlap detected, this campsite is not available.
                }
                //no overlap! So:
                t1 = t2;
                if(i%2 == 0 && reservedTimeIterator.hasNext()) {
                    currentDR = reservedTimeIterator.next();
                    t2 = currentDR.getSTART_DATE();
                }
                else if(i%2 != 0 && reservedTimeIterator.hasNext()) {
                    t2 = currentDR.getEND_DATE();
                } else if (!reservedTimeIterator.hasNext()){
                    t2 = new Date(Long.MAX_VALUE);
                }
            }
            if(isAvailable){
                availableCampsites.add(campSite);
            }
        }

        //Corner Case (No reservations made for a given campsite)
        CampSiteStore campsiteStore = CampSiteStore.getInstance();
        for(CampSite campSite : campsiteStore.getCampSiteDataCopy()) {
            if (!reservationData.keySet().contains(campSite.getNAME())) {
                availableCampsites.add(campSite.getNAME());
            }
        }
        return availableCampsites;
    }
}
