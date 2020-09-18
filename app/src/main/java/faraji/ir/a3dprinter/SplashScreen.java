package faraji.ir.a3dprinter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo);
        logo.startAnimation(AnimationUtils.loadAnimation(this , R.anim.fade_in));

        appName = findViewById(R.id.appName);
        appName.startAnimation(AnimationUtils.loadAnimation(this , R.anim.fade_in));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this , LoginActivity.class));finish();

            }
        }, 2500);

    }
}