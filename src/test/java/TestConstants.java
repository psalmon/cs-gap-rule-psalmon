import java.util.Date;

public class TestConstants {
    final static Date TEST_DATE_LOW = new Date(604800000);
    final static Date TEST_DATE_HIGH = new Date(604800000 + 86400000);//+1 day

    final static Date TEST_DATE_LOW_2 = new Date(1209600000);
    final static Date TEST_DATE_HIGH_2 = new Date(1209600000 + 172800000);//+2 days

    final static Date TEST_DATE_HALF_MAX = new Date(Long.MAX_VALUE/2);
    final static Date TEST_DATE_MAX = new Date(Long.MAX_VALUE);
    final static Date TEST_DATE_MIN = new Date(Long.MIN_VALUE);
}
