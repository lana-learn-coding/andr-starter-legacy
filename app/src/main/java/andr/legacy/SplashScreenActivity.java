package andr.legacy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Executors.newSingleThreadScheduledExecutor().schedule(() -> runOnUiThread(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }), 3, TimeUnit.SECONDS);
    }
}
