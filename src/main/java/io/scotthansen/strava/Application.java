package io.scotthansen.strava;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Application {

    private static final double KM_TO_MILES_CONVERSION_FACTOR = 1.60934;
    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the year for which to sum the miles:");
        int inputYear = scanner.nextInt();

        System.out.println("Enter the file path: ");
        final String csvFile = scanner.next();
        scanner.close();


        BigDecimal totalKilometers = BigDecimal.ZERO;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy, h:mm:ss a", Locale.ENGLISH);

        try (Reader reader = new FileReader(csvFile)) {
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

            for (final CSVRecord record : parser) {
                String activityType = record.get("Activity Type");
                String activityDateStr = record.get("Activity Date");
                String distanceStr = record.get("Distance");

                if (activityType.equals("Ride") || activityType.equals("Virtual Ride")) {
                    LocalDate activityDate = LocalDate.parse(activityDateStr, formatter);
                    if (activityDate.getYear() == inputYear) {
                        BigDecimal distanceKm = new BigDecimal(distanceStr);
                        totalKilometers = totalKilometers.add(distanceKm);
                    }

                }
            }
        }

        System.out.println("Total miles in " + inputYear + ": " + kilometersToMiles(totalKilometers.doubleValue()));
    }

    private static double kilometersToMiles(double kilometers) {
        return kilometers / KM_TO_MILES_CONVERSION_FACTOR;
    }
}
