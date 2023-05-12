import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {

        String csvFile = "googleplaystore.csv";
        String line = "";
        String csvDelimiter = ",";

        Map<String, Double> highestRatings = new HashMap<>();
        Map<String, Double> lowestRatings = new HashMap<>();
        Map<String, Double> sumRatings = new HashMap<>();
        Map<String, Integer> countRatings = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // skip header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] appRecord = line.split(csvDelimiter);
                String category = appRecord[1];

                if (appRecord[2].equals("NaN")) {
                    continue;
                }

                if (category.equals("1.9")) {
                    continue;
                }

                double rating = Double.parseDouble(appRecord[2]);

                // update highest rating
                if (!highestRatings.containsKey(category) || rating > highestRatings.get(category)) {
                    highestRatings.put(category, rating);
                }

                // update lowest rating
                if (!lowestRatings.containsKey(category) || rating < lowestRatings.get(category)) {
                    lowestRatings.put(category, rating);
                }

                // update sum and count of ratings for category
                if (!sumRatings.containsKey(category)) {
                    sumRatings.put(category, rating);
                    countRatings.put(category, 1);
                } else {
                    sumRatings.put(category, sumRatings.get(category) + rating);
                    countRatings.put(category, countRatings.get(category) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // print highest, lowest, and average ratings for each category
        for (String category : highestRatings.keySet()) {
            double highestRating = highestRatings.get(category);
            double lowestRating = lowestRatings.get(category);
            double averageRating = sumRatings.get(category) / countRatings.get(category);
            System.out.println("Category: " + category);
            System.out.println("Highest rating: " + highestRating);
            System.out.println("Lowest rating: " + lowestRating);
            System.out.printf("Average rating: %.2f\n", averageRating);
            System.out.println();
        }
    }
}
