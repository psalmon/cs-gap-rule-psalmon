# gap-rule

A program to search for Camp Sites available during a given date range.

This is a Maven project so build in your IDE and import dependencies, or from the command line:
navigate to pom.xml
mvn clean install

Note that there is a test case "testLargeStores" that may take a minute or so to execute. This is expected as
it is testing a very large amount of data.

To skip the test cases, you can use:
mvn install -DskipTests

The program is run by providing a JSON file directory as a parameter to the project.

The assumption that we do not care about output order was made. Refactoring this is possible if we suddenly care.

We chose data structures based on lack of care for order and elimination of duplicates.

A mocking framework may have it's place in test cases. Will have to revisit the test cases.

Possible new feature:
Multithread the range date checks on a new thread per campsite basis. This should improve performance.
Drawbacks: Testing gets a bit nastier. Code gets more cluttered. Scaling this could get complicated.

The date range we are searching for, along with campsites and booked reservations for them is provided in a JSON file
which can be read through JSONHandler.

Most code explanation can be found in the accompanying javadoc.

Sample formatting:
{
  "search": {
    "START_DATE": "2018-06-04",
    "END_DATE": "2018-06-06"
  },
  "campsites": [
    {
      "id": 1,
      "NAME": "Cozy Cabin"
    },
    {
      "id": 2,
      "NAME": "Comfy Cabin"
    },
    {
      "id": 3,
      "NAME": "Rustic Cabin"
    },
    {
      "id": 4,
      "NAME": "Rickety Cabin"
    },
    {
      "id": 5,
      "NAME": "Cabin in the Woods"
    }
  ],
  "reservations": [
    {"campsiteId": 1, "START_DATE": "2018-06-01", "END_DATE": "2018-06-03"},
    {"campsiteId": 1, "START_DATE": "2018-06-08", "END_DATE": "2018-06-10"},
    {"campsiteId": 2, "START_DATE": "2018-06-01", "END_DATE": "2018-06-01"},
    {"campsiteId": 2, "START_DATE": "2018-06-02", "END_DATE": "2018-06-03"},
    {"campsiteId": 2, "START_DATE": "2018-06-07", "END_DATE": "2018-06-09"},
    {"campsiteId": 3, "START_DATE": "2018-06-01", "END_DATE": "2018-06-02"},
    {"campsiteId": 3, "START_DATE": "2018-06-08", "END_DATE": "2018-06-09"},
    {"campsiteId": 4, "START_DATE": "2018-06-07", "END_DATE": "2018-06-10"}
  ]
}
