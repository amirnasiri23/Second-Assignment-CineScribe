import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
public class Actors {
    public static final String API_KEY = "zu0ZwSh9rXGbAypL7GqrUm13AeCT7Z4vR7czrOFj";
    String netWorth;
    Boolean isAlive;
    String gender;
    Double height;

    public Actors(String netWorth, boolean isAlive){
        this.netWorth = netWorth;
        this.isAlive = isAlive;
    }

    public Actors(String title) { // new Constructor

    }

    @SuppressWarnings({"deprecation"})
    /**
     * Retrieves data for the specified actor.
     * @param name for which Actor should be retrieved
     * @return a string representation of the Actors info or null if an error occurred
     */
    public String getActorData(String name) {
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/celebrity?name="+
                    name.replace(" ", "+")+"&apikey="+API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", API_KEY);
            System.out.println(connection);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                return response.toString();
            } else {
                return "Error: " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getNetWorthViaApi(String actorsInfoJson){
        JSONArray jsonArray = new JSONArray(actorsInfoJson);
        JSONObject jsonObject = new JSONObject(jsonArray.getJSONObject(0));
        double result = 0.0;
        result = jsonArray.getJSONObject(0).getDouble("net_worth");

        return result;
    }

    public boolean isAlive(String actorsInfoJson){
        JSONArray jsonArray = new JSONArray(actorsInfoJson);
        JSONObject jsonObject = new JSONObject(jsonArray.getJSONObject(0));
        boolean statues = false;
        statues = jsonArray.getJSONObject(0).getBoolean("is_alive");

        return statues;
    }

    public String getDateOfDeathViaApi(String actorsInfoJson){
        JSONArray jsonArray = new JSONArray(actorsInfoJson);
        JSONObject jsonObject = new JSONObject(jsonArray.getJSONObject(0));
        String date = "";
        date = jsonArray.getJSONObject(0).getString("death");
        return date;
    }

    public String getGender(String actorsInfoJson) {
        JSONArray jsonArray = new JSONArray(actorsInfoJson);
        JSONObject jsonObject = new JSONObject(jsonArray.getJSONObject(0));
        String result = jsonArray.getJSONObject(0).getString("gender");
        return result;
    }

    public double getHeight(String actorsInfoJson) {
        JSONArray jsonArray = new JSONArray(actorsInfoJson);
        JSONObject jsonObject = new JSONObject(jsonArray.getJSONObject(0));
        double result = 0.0;
        result = jsonArray.getJSONObject(0).getDouble("height");

        return result;
    }

}