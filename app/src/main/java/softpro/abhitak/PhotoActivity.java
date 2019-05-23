package softpro.abhitak;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoActivity extends AppCompatActivity {

    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        photoView = findViewById(R.id.photo_view);
        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {

            }
        });
        setTitle("Bijnor");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bijnor:
                photoView.setImageResource(R.drawable.bijnor);
                setTitle("Bijnor");
                break;
            case R.id.action_amroha:
                photoView.setImageResource(R.drawable.amroha);
                setTitle("Amroha");
                break;
            case R.id.action_moradabad:
                photoView.setImageResource(R.drawable.moradabad);
                setTitle("Moradabad");
                break;
            case R.id.action_rampur:
                photoView.setImageResource(R.drawable.rampur);
                setTitle("Rampur");
                break;
            case R.id.action_sambhal:
                photoView.setImageResource(R.drawable.sambhal);
                setTitle("Sambhal");
                break;
        }
        return true;
    }

}
