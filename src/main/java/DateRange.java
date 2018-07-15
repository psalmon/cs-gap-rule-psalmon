import java.util.Date;

/**
 * Data structure representing a range of dates.
 * Will not allow the start date to precede the end date.
 * Implements Comparable so we can sort by start dates.
 * @author Paul Salmon
 */
public class DateRange implements Comparable<DateRange> {
    private final Date START_DATE;
    private final Date END_DATE;

    /**
     * Constructor. Will not allow end date to precede start date.
     * @param startDate Date type for starting date.
     * @param endDate Date type for ending date.
     */
    public DateRange(Date startDate, Date endDate) {
        if(endDate.before(startDate)) {
            throw new IllegalArgumentException();
        } else {
            this.START_DATE = startDate;
            this.END_DATE = endDate;
        }
    }

    /**
     * Start date getter.
     * @return Date object of the start date.
     */
    public Date getSTART_DATE() {
        return START_DATE;
    }

    /**
     * End date getter.
     * @return Date object of the end date.
     */
    public Date getEND_DATE() {
        return END_DATE;
    }

    /**
     * Compares Date Ranges so we can sort by whichever date is first.
     * @param o The date we are comparing to.
     * @return Whether or not the provided date is before or after this one.
     */
    public int compareTo(DateRange o) {
        return (this.getSTART_DATE().after(o.getSTART_DATE())) ? 1 : -1;
    }
}
