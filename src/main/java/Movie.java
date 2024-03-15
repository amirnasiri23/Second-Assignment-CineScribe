import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Movie {
    public static final String API_KEY = "4cc68bea";
    int ImdbVotes;
    ArrayList<String> actorsList;
    String rating;
    String year;
    String language;

    public Movie(ArrayList<String> actorsList, String rating, int ImdbVotes){
        this.actorsList = actorsList;
        this.rating = rating;
        this.ImdbVotes = ImdbVotes;
    }

    public Movie(String title) {   // new Constructor

    }

    @SuppressWarnings("deprecation")
    /**
     * Retrieves data for the specified movie.
     *
     * @param title the name of the title for which MovieData should be retrieved
     * @return a string representation of the MovieData, or null if an error occurred
     */

    public String getMovieData(String title) throws IOException {
        URL url = new URL("https://www.omdbapi.com/?t=" + title + "&apikey=" + API_KEY);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Authorization", "Key" + API_KEY);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();

        String movieData = stringBuilder.toString();

        if (isMovieFound(movieData)) {
            return movieData;
        } else {
            throw new IOException("Movie not found on the site");
        }
    }

    private boolean isMovieFound(String movieData) {
        JSONObject jsonObject = new JSONObject(movieData);
        String response = jsonObject.getString("Response");

        return "True".equals(response);
    }
    public int getImdbVotesViaApi(String moviesInfoJson){
        JSONObject jsonObject = new JSONObject(moviesInfoJson);
        String ImdbVotesStr;
        String ImdbVotesNum;
        int ImdbVotes;

        ImdbVotesStr = jsonObject.getString("imdbVotes");
        ImdbVotesNum = createVotesString(ImdbVotesStr);

        ImdbVotes = Integer.parseInt(ImdbVotesNum);
        return ImdbVotes;
    }

    private String createVotesString(String text) {  // function to Remove , from Votes String
        List<String> tempList = new ArrayList<>();
        String regex = "[0-9]+";
        String ans = "";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            tempList.add(matcher.group(0));
        }
        for (String str : tempList) {
            ans += str;
        }
        return ans;
    }

    public String getRatingViaApi(String moviesInfoJson){
        JSONObject jsonObject = new JSONObject(moviesInfoJson);
        JSONArray ratingsArray = jsonObject.getJSONArray("Ratings");

        for (int i = 0; i < ratingsArray.length(); i++) {
            JSONObject ratingObject = ratingsArray.getJSONObject(i);
            if (ratingObject.getString("Source").equals("Internet Movie Database")) {
                return ratingObject.getString("Value");
            }
        }
        return "";
    }

    private List<String> actorsList(String str) {  // function to make a list of Actors with a String
        List<String> ans = new ArrayList<>();

        String regex = "\\w+.[^,]+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            ans.add(matcher.group(0));
        }

        return ans;
    }

    public void getActorListViaApi(String movieInfoJson){
        JSONObject jsonObject = new JSONObject(movieInfoJson);
        String temp = jsonObject.getString("Actors");
        List<String> ans = actorsList(temp);

        for (String str : ans) {
            actorsList.add(str);
        }
    }

    public String getYear(String movieInfoJson) {
        JSONObject jsonObject = new JSONObject(movieInfoJson);
        return jsonObject.getString("Year");
    }

    public String getLanguage(String movieInfoJson) {
        JSONObject jsonObject = new JSONObject(movieInfoJson);
        return jsonObject.getString("Language");
    }

    public List<String> getActorsList() {
        return actorsList;
    }
}