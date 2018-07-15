import java.util.TreeSet;

/**
 * The data structure for storing off CampSites. Underlying data structure is a TreeSet.
 * A TreeSet is chosen because we want to sort the CampSites (by id), but we do not want duplicates.
 * @author Paul Salmon
 */
public class CampSiteStore {

    private TreeSet<CampSite> campSiteData = new TreeSet<>();

    private static CampSiteStore myInstance = null;

    /**
     * Singleton instance getter.
     * There should be only one store for CampSites, so a Singleton was used.
     * Singleton is initialized in the handler because we use reflections to deal with testing our
     * static types.
     * @return the singleton CampSiteStore instance.
     */
    public static CampSiteStore getInstance() {
        if (myInstance == null){
            myInstance = new CampSiteStore();
        }
        return myInstance;
    }

    /**
     * Lookup campsite name given it's ID.
     * @param id the ID to get the campsite name of
     * @return string of the campsites name.
     */
    public String getNameById(int id) {
        for (CampSite cs : campSiteData) {
            if (cs.getID() == id) {
                return cs.getNAME();
            }
        }
        return "None";
    }

    /**
     * Adds the new CampSite to our underlying TreeSet if it has a unique ID.
     * @param campSite the CampSite that we are trying to add, but will check the ID
     *                 to see if it already exists in our store.
     * @return true if we can add, false if not (because the ID exists).
     */
    public boolean addCampsite(CampSite campSite) {
        //Returns true if this is the first appearance of the CampSite (hashed off it's ID only).
        //Returns false if this turned out to be a duplicate.
        return campSiteData.add(campSite);
    }

    /**
     * Returns the TreeSet data structure as a copy of the underlying structure.
     * @return a copy of the camp site data. Used in finding camps with no reservations.
     */
    public TreeSet<CampSite> getCampSiteDataCopy() {
        TreeSet<CampSite> campSiteDataCopy = new TreeSet<>();
        campSiteDataCopy.addAll(campSiteData);
        return campSiteDataCopy;
    }
}
