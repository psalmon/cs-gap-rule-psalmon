import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

/**
 * Singleton.
 * Reads in a JSON file to see if the provided dates are open.
 * @author Paul Salmon
 */
public class JSONHandler {

    private static JSONHandler myInstance = new JSONHandler();

    public static JSONHandler getInstance() {
        if (myInstance == null){
            myInstance = new JSONHandler();
        }
        return myInstance;
    }

    /**
     * Checks the given filePath's JSON file to see if the provided daterange is available considering
     * the given campsite reservations in the JSON file.
     *
     * A HashSet is chosen as the return type of available campsites because we do not want duplicates, nor
     * do we care about it being ordered. If we in the future care about order, refactoring to an ArrayList or
     * implementing a sort method should be trivial.
     *
     * @param filePath direct path to the json file.
     * @return a HashSet of available CampSites.
     * @throws IOException as much can go wrong if a JSON file is corrupted or improperly formatted.
     */
    public HashSet<String> getAvailableCampsites(String filePath) throws IOException{
        File queryFile = new File(filePath);
        String queryString = FileUtils.readFileToString(queryFile, "utf-8");
        JSONObject queryJsonObject = new JSONObject(queryString);

        ReservationStore reservationStore = ReservationStore.getInstance();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        Date queryStartDate;
        Date queryEndDate;
        DateRange queryDateRange;

        //@TODO: Not my favorite way to do nested try blocks. Could refactor.
        try {
            JSONObject searchJSONObj = queryJsonObject.getJSONObject("search");
            try {
                queryStartDate = df.parse(searchJSONObj.get("startDate").toString() + "T00:00:00.000-0600");
                queryEndDate = df.parse(searchJSONObj.get("endDate").toString() + "T00:00:00.000-0600");
                try {
                    queryDateRange = new DateRange(queryStartDate, queryEndDate);
                } catch(IllegalArgumentException e){
                    System.out.println("End date is before begin date. " + e.getMessage());
                    return new HashSet<>();
                }
            } catch(java.text.ParseException e) {
                System.out.println("Error parsing the query start/end date. Check JSON file\n" + e);
                return new HashSet<>();
            }

            JSONArray campsiteJSONArray = queryJsonObject.getJSONArray("campsites");
            CampSiteStore campSiteStore = CampSiteStore.getInstance();

            for(int i = 0; i < campsiteJSONArray.length(); i++){
                JSONObject campSiteJSONObject = campsiteJSONArray.getJSONObject(i);
                int campSiteId = Integer.parseInt(campSiteJSONObject.get("id").toString());
                String campSiteName = campSiteJSONObject.get("name").toString();
                CampSite newCampSite = new CampSite(campSiteId, campSiteName);
                campSiteStore.addCampsite(newCampSite);
            }

            JSONArray reservationsJSONArray = queryJsonObject.getJSONArray("reservations");

            for(int i = 0; i < reservationsJSONArray.length(); i++) {
                JSONObject reservationJSONObject  = reservationsJSONArray.getJSONObject(i);
                int reservationCampSiteId = Integer.parseInt(reservationJSONObject.get("campsiteId").toString());

                Date reservationStartDate;
                Date reservationEndDate;
                DateRange reservationDateRange;

                try {
                    reservationStartDate = df.parse(reservationJSONObject.get("startDate").toString() +
                            "T00:00:00.000-0600");
                    reservationEndDate = df.parse(reservationJSONObject.get("endDate").toString() +
                            "T00:00:00.000-0600");
                } catch(java.text.ParseException e) {
                    System.out.println("Error parsing reservations. Check JSON file" + e.getMessage());
                    return new HashSet<>();
                }
                try {
                    reservationDateRange = new DateRange(reservationStartDate, reservationEndDate);
                    Reservation reservation = new Reservation(reservationCampSiteId, reservationDateRange);
                    reservationStore.addReservation(reservation);
                } catch(IllegalArgumentException e) {
                    System.out.println("End date is before start date");
                }
            }

        } catch(JSONException e) {
                System.out.println("JSON is improperly formatted: " + e.getMessage());
                return new HashSet<>();
        }
        return (reservationStore.getOpenCamps(queryDateRange));
    }
}
