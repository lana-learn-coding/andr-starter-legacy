package andr.legacy;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Executors;

import andr.legacy.db.MainDatabase;
import andr.legacy.db.Weather;
import andr.legacy.db.WeatherDao;

public class FormActivity extends NavigableActivity {
    private WeatherDao dao;
    int updateId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        dao = MainDatabase.getDbInstance(this).weatherDao();
        findViewById(R.id.btn_save).setOnClickListener(this::saveForm);
        findViewById(R.id.btn_update).setOnClickListener(this::updateForm);

        MainApplication application = (MainApplication) getApplicationContext();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, application.getWeathers());
        AutoCompleteTextView select = findViewById(R.id.select_weather);
        select.setAdapter(adapter);
        select.setOnClickListener((v) -> select.showDropDown());
    }

    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.btn_update).setVisibility(View.GONE);
        Bundle extras = ObjectUtils.defaultIfNull(getIntent().getExtras(), new Bundle());
        updateId = extras.getInt("id", -1);
        if (updateId < 0) return;

        findViewById(R.id.btn_update).setVisibility(View.VISIBLE);
        Executors.newSingleThreadExecutor().execute(() -> {
            Weather item = dao.getOne(updateId);
            if (item == null) {
                runOnUiThread(() -> {
                    navigate(this, MainActivity.class);
                    Toast.makeText(this, "Không tìm thấy bản ghi với id " + updateId, Toast.LENGTH_SHORT).show();
                });
                return;
            }

            // set value to form on update
            runOnUiThread(() -> {
                FormUtils.setTextValue(findViewById(R.id.edit_txt_city), item.getCity());
                FormUtils.setTextValue(findViewById(R.id.edit_txt_note), item.getNote());
                FormUtils.setTextValue(findViewById(R.id.edit_txt_temp), String.valueOf(ObjectUtils.defaultIfNull(item.getTemperature(), 0)));
                ((AutoCompleteTextView) findViewById(R.id.select_weather)).setText(item.getWeather(), false);
            });
        });
    }

    private void saveForm(View view) {
        Weather weather = getFormData();

        Executors.newSingleThreadExecutor().execute(() -> {
            dao.insert(weather);
            runOnUiThread(() -> {
                navigate(this, MainActivity.class);
                Toast.makeText(this, "Thêm mới thời tiết thành công", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void updateForm(View view) {
        if (updateId < 0) {
            Toast.makeText(this, "Không thể cập nhật - chưa chọn bản ghi", Toast.LENGTH_SHORT).show();
            return;
        }
        Weather weather = getFormData();
        weather.setId(updateId);

        Executors.newSingleThreadExecutor().execute(() -> {
            dao.update(weather);
            runOnUiThread(() -> {
                navigate(this, MainActivity.class);
                Toast.makeText(this, "Cập nhật thời tiết thành công", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private Weather getFormData() {
        Weather weather = new Weather();
        weather.setCity(FormUtils.getTextValue(findViewById(R.id.edit_txt_city)));
        weather.setNote(FormUtils.getTextValue(findViewById(R.id.edit_txt_note)));
        weather.setTemperature(Integer.parseInt(StringUtils.defaultIfBlank(FormUtils.getTextValue(findViewById(R.id.edit_txt_temp)), "0")));
        weather.setWeather(FormUtils.getTextValue(findViewById(R.id.select_weather)));
        return weather;
    }
}