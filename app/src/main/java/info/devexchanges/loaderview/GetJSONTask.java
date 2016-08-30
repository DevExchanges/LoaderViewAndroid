package info.devexchanges.loaderview;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetJSONTask extends AsyncTask<Void, Void, String> {

    private HttpURLConnection urlConnection;
    private final String JSON_URL = "https://gist.githubusercontent.com/DevExchanges/57430306b4a060eee19a4d845b07a478/raw/e8c05e8ed7e83e7cc3d00d840cca0558c125330d/example.json";
    private MainActivity activity;

    public GetJSONTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... params) {
        Log.i("Async", "doInBackground");
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(JSON_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        Log.i("Async", "onPostExecute");
        activity.parseJsonResponse(result);
    }
}
