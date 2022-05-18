package andr.legacy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import andr.legacy.db.MainDatabase;
import andr.legacy.db.Weather;
import andr.legacy.db.WeatherDao;

public class MainActivity extends NavigableActivity {

    private WeatherAdapter adapter;
    private WeatherDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = MainDatabase.getDbInstance(this).weatherDao();
        adapter = new WeatherAdapter(this, new ArrayList<>());
        ListView listHistory = findViewById(R.id.list_weather);
        listHistory.setAdapter(adapter);
        registerForContextMenu(listHistory);

        // show context menu on single click
        listHistory.setOnItemClickListener((parent, view, position, id) -> listHistory.showContextMenuForChild(view));
    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.weather_menu, menu);

        int pos = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        Weather weather = adapter.getItem(pos);
        menu.setHeaderTitle(String.format(Locale.ENGLISH, "%s (%d)", weather.getCity(), weather.getId()));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        int pos = ((AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo()).position;
        Weather item = adapter.getItem(pos);

        if (id == R.id.item_edit) {
            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtra("id", item.getId());
            startActivity(intent);
            finish();
        }

        if (id == R.id.item_delete) {
            new AlertDialog.Builder(this)
                    .setMessage("Bạn có chắc muốn xóa thời tiết này")
                    .setPositiveButton("Chắc", (dialog, which) -> {
                        Executors.newSingleThreadExecutor().execute(() -> {
                            dao.delete(item);
                            runOnUiThread(() -> {
                                adapter.remove(item);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(this, String.format(Locale.ENGLISH, "Xóa %s (%d)", item.getCity(), item.getId()), Toast.LENGTH_SHORT).show();
                            });
                        });
                    })
                    .setNegativeButton("Trở lại", (dialog, which) -> Toast.makeText(this, "Hủy", Toast.LENGTH_SHORT).show())
                    .show();
        }

        return true;
    }

    private void loadData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Weather> weathers = dao.getAll();
            runOnUiThread(() -> {
                adapter.clear();
                adapter.addAll(weathers);
                adapter.notifyDataSetChanged();
            });
        });
    }
}