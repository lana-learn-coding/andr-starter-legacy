package andr.legacy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class NavigableActivity extends AppCompatActivity {
    protected void navigate(Context context, Class<?> target) {
        if (getClass().equals(target)) return;
        startActivity(new Intent(context, target));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuitem_create) {
            navigate(this, FormActivity.class);
            return true;
        }

        if (id == R.id.menuitem_home) {
            navigate(this, MainActivity.class);
            return true;
        }
        return false;
    }
}
