package softpro.abhitak;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        container = findViewById(R.id.container);
        Element element = new Element();
        element.setTitle("Made With \u2764 In India");
        element.setGravity(Gravity.CENTER_HORIZONTAL);
        Element tanmay = new Element("Tanmay", R.drawable.ic_person_black_24dp);
        Element naseem = new Element("Naseem", R.drawable.ic_person_black_24dp);
        tanmay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/tanmay-srivastava-1a9279129/"));
                startActivity(browserIntent);
            }
        });
        naseem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/thisismenaseem"));
                startActivity(browserIntent);
            }
        });
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.abhi_tak)
                .setDescription(getResources().getString(R.string.large_text))
                .addGroup("Connect with us")
                .addEmail("tanmays32@gmail.com")
                .addGroup("Developers")
                .addItem(tanmay)
                .addItem(naseem)
                .addItem(element)
                .create();

        container.addView(aboutPage, 0);
    }
}
