import java.io.IOException;

/**
 * Main entry point. System args are used to take input JSON file.
 * JSON file is verified, but any incorrect formatting or corruption will cause exceptions.
 * @author Paul Salmon
 */
public class Main {

    /**
     * Passes the filepath through to the JSONHandler.
     * @param args One arg only. Path to .json file.
     */
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Please provide directory for camp site JSON file.");
            System.exit(1);
        } else if(!args[0].endsWith(".json")) {
            System.out.println("Be sure this is a valid json file!");
            System.exit(1);
        }

        String filePath = args[0];
        JSONHandler myParser = JSONHandler.getInstance();
        try {
            System.out.println(myParser.getAvailableCampsites(filePath));
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
