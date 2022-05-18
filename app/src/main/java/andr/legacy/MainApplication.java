package andr.legacy;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application {
    private final List<String> weathers = new ArrayList<String>() {{
        add("Nắng");
        add("Mưa");
        add("Tuyết");
        add("Bão");
        add("Mây Mù");
        add("Râm");
    }};

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public List<String> getWeathers() {
        return weathers;
    }
}
