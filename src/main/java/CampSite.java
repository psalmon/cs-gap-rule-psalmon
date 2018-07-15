import java.util.Objects;

/**
 * Represents a CampSite with references to it's ID and it's NAME.
 * Is able to be compared for the sake of sorting CampSites by ID.
 * @author Paul Salmon
 */
public class CampSite implements Comparable<CampSite> {
    private final int ID;
    private final String NAME;

    /**
     * @param id Unique ID representing the CampSite.
     * @param name String representation of the CampSite. Important for I/O.
     */
    public CampSite(int id, String name) {
        this.ID = id;
        this.NAME = name;
    }

    /**
     * ID getter.
     * @return ID constant.
     */
    public int getID() {
        return ID;
    }

    /**
     * NAME getter.
     * @return NAME constant.
     */
    public String getNAME() {
        return NAME;
    }

    /**
     * @param o Another CampSite to be compared to. For the sake of sorting, we sort by ID.
     * @return Returns whether or not this CampSite should be sorted before or after the compared.
     */
    public int compareTo(CampSite o) {
        return (this.ID >= o.ID) ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampSite campSite = (CampSite) o;
        return ID == campSite.ID &&
                Objects.equals(NAME, campSite.NAME);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
