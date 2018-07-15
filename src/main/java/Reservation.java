/**
 * Data type to represent reservations by campsite ID and date ranges.
 * @author Paul Salmon
 */

public class Reservation {
    private final int CAMP_SITE_ID;
    private final DateRange DATE_RANGE;

    /**
     * Constructor for the reservation we are making.
     * We can still make a reservation if it already exists or is overlapping, but we cannot put it in the reservation
     * store. This functionality is for future considerations, such as creating wait lists for a campsite time
     * (consider cancellations as a possible cause for this feature.)
     * @param campSiteId the ID associated with the campsite we are making a reservation for.
     * @param dateRange the date range to make the reservation on.
     */
    public Reservation(int campSiteId, DateRange dateRange) {
        this.CAMP_SITE_ID = campSiteId;
        this.DATE_RANGE = dateRange;
    }

    /**
     * Getter for the campsite ID.
     * @return int of the campsite ID.
     */
    public int getCAMP_SITE_ID() {
        return CAMP_SITE_ID;
    }

    /**
     * Getter for the date range.
     * @return DateRange of the reserved dates.
     */
    public DateRange getDATE_RANGE() {
        return DATE_RANGE;
    }
}
