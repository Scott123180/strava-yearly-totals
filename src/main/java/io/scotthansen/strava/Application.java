package io.scotthansen.strava;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the year for which to sum the miles:");
        int year = scanner.nextInt();
        scanner.close();

        String csvFile = "path/to/your/csvfile.csv"; // Replace with the actual file path
        String line = "";
        String csvSplitBy = ",";
        double totalMiles = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] activity = line.split(csvSplitBy);
                String activityType = activity[3]; // Assuming 'Activity Type' is the fourth column
                String activityDate = activity[1]; // Assuming 'Activity Date' is the second column

                // Check if the activity is a Ride or Virtual Ride
                if (activityType.equals("Ride") || activityType.equals("Virtual Ride")) {
                    Date date = new SimpleDateFormat("MMM d, yyyy, h:mm:ss a").parse(activityDate);
                    if (date.getYear() + 1900 == year) {
                        double distance = Double.parseDouble(activity[6]); // Assuming 'Distance' is the seventh column
                        totalMiles += distance;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Total miles in " + year + ": " + totalMiles);
    }
}
