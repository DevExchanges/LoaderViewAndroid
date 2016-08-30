package info.devexchanges.loaderview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> strings;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list_view);
        strings = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            strings.add("");
        }

        adapter = new ListViewAdapter(this, R.layout.item_listview, strings, false);
        listView.setAdapter(adapter);
    }

    private void loadJSONDataFromURL() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               new GetJSONTask(MainActivity.this).execute();
            }
        }, 1000);
        Log.i("Main", "load data");
    }

    //parsing json after getting from Internet
    public void parseJsonResponse(String result) {
        strings.clear();
        try {
            JSONObject json = new JSONObject(result);
            JSONArray jArray = new JSONArray(json.getString("message"));
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                strings.add(jObject.getString("name"));
            }

            adapter.notifyDataSetChanged();
            Log.i("Main", "finish load data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.load) {
            adapter = new ListViewAdapter(this, R.layout.item_listview, strings, true);
            listView.setAdapter(adapter);
            loadJSONDataFromURL();
        }
        return super.onOptionsItemSelected(item);
    }
}
