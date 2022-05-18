package andr.legacy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import andr.legacy.db.Weather;

public class WeatherAdapter extends ArrayAdapter<Weather> {
    public WeatherAdapter(@NonNull Context context, List<Weather> weathers) {
        super(context, 0, weathers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Weather item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_weather, parent, false);
        }

        FormUtils.setTextValue(convertView.findViewById(R.id.txt_city), item.getCity());
        FormUtils.setTextValue(convertView.findViewById(R.id.txt_note), item.getNote());
        FormUtils.setTextValue(convertView.findViewById(R.id.txt_temp), item.getTemperature() + " C");
        FormUtils.setTextValue(convertView.findViewById(R.id.txt_weather), item.getWeather());
        return convertView;
    }
}
