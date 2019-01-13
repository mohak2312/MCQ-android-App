//unique package
package mcq_3.edu.pdx.mohak.ece558.mcq;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Splash activity to show the splash screen for 3 seconds
 */

public class Splash extends AppCompatActivity {
    /** OnCreate is called when the splash activity
    * is started.
    * super.xxx method whenever we override a method
    */
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        // calls the super.onCreate() method and inflates
        // the layout with an ID of R.id.layout.activity_splash
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 3;
        /**
         * Handler to handle the splash screen,
         *Start another activity after 3 seconds,
         *Finish the splash activity,
         */
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, StartActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }
}
