//unique package for MCQ app
package mcq_3.edu.pdx.mohak.ece558.mcq;

//Built i library
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Start Activity  for tarting the app,
 * Start button for starting quiz,
 * spinner to select the quiz,
 */
public class StartActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    //Create the variable for inputs and output
    private Button start;
    private String quiz;
    private TextView user_name;

    public static final String EXTRA_NAME="mcq_3.edu.pdx.mohak.ece558.mcq_3";
    private static final String TAG="Mark-1";

    /**
     * OnCreate is called when the Start_activity
     * is started.
     * super.xxx method whenever we override a method
      */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // calls the super.onCreate() method and inflates
        // the layout with an ID of R.id.layout.activity_start
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // create object for preveious username and
        //get the previous user name using sharedpreference
        // and print he username on the start_page
        user_name =(TextView) findViewById(R.id.Prev_User_name);
        SharedPreferences sharedPref = getSharedPreferences(GradeActivity.SHARED_PREF, GradeActivity.MODE_PRIVATE);
        String previous_username=sharedPref.getString(GradeActivity.USER_NAME,"User");
        user_name.setText("Welcome " + previous_username +" !");

        //create a object for spinner box
        Spinner spinner= (Spinner)findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.spinner_array, android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Handle the start Button
        // by getting the start button object
        // The onClick() method listens for a "button clicked"
        // event for the "start" button. When
        // the event occurs, we start the quiz activity
        start=(Button) findViewById(R.id.start_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start new quizActivity
                // and send the selected quiz name from spinner to the quiz activity
                Intent i=new Intent(StartActivity.this, QuizActivity.class);
                i.putExtra(EXTRA_NAME,quiz+".json" );
                startActivity(i);

            }
        });
    }

    /**
     * function for spinner to get the quiz name
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        quiz = parent.getItemAtPosition(position).toString();
    }

    /**
     * set if nothing is selected from the spinner
     * @param parent
     */
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // Override the Activity lifecycle callbacks for logging
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }


    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    /**
     *  Override the on back pressed activity if back button is pressed,
     *  Exit the app by calling finishAffinity
     *
     * @return null
     */
    @Override
    public void onBackPressed(){
        finishAffinity();
    }
}
