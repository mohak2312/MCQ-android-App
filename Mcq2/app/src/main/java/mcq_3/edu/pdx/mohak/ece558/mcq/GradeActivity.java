//unique packe name
package mcq_3.edu.pdx.mohak.ece558.mcq;
//builtin library
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Grade activity to show the results of attempted quiz
 */

public class GradeActivity extends AppCompatActivity {
    //Variables for output
    private int c_answers;
    private int no_questions;
    private int no_cheats;
    private String user;

    private TextView Correct_Ans;
    private TextView Cheated;
    private TextView t_question;
    private EditText user_name;

    //variable for buttons
    private Button play_again;
    private Button quit;

    private static final String TAG = "Mark-1";
    public static final String USER_NAME="Mark-2";
    public static final String SHARED_PREF="user_name";
    private static final String EXTRA_ANSWER_IS_TRUE = "Mark1_true";
    private static final String EXTRA_NO_OF_Q ="no_of_q";
    private static final String EXTRA_NO_OF_CHEATS ="no_of_cheats";

    /**
     * Start the intent and
     * pairing the correct answer with key
     * @param packageContext
     * @param mnumber_of_correct_ans
     * @param numberOfQuestions
     * @param no_cheats
     * @return
     */

    public static Intent newIntent(Context packageContext, int mnumber_of_correct_ans, int numberOfQuestions, int no_cheats) {
        Intent intent = new Intent(packageContext, GradeActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, mnumber_of_correct_ans );
        intent.putExtra(EXTRA_NO_OF_Q,numberOfQuestions);
        intent.putExtra(EXTRA_NO_OF_CHEATS, no_cheats);
        return intent;
    }

    /**
     *  OnCreate is called when the quiz_activity
     *  is started.
     *  super.xxx method whenever we override a method
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // calls the super.onCreate() method and inflates
        // the layout with an ID of R.id.layout.activity_grade
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        //  Get intent from quiz activity
        Bundle extras = getIntent().getExtras();
        c_answers = extras.getInt(EXTRA_ANSWER_IS_TRUE, 0);
        no_questions = extras.getInt(EXTRA_NO_OF_Q, 0);
        no_cheats = extras.getInt(EXTRA_NO_OF_CHEATS, 0);

        Correct_Ans = (TextView)findViewById(R.id.correct);
        Cheated = (TextView)findViewById(R.id.cheated);
        t_question = (TextView)findViewById(R.id.num_of_que);
        user_name=(EditText)findViewById(R.id.user_name);

        // Handle the play again Button
        // by getting the play again button object
        // The onClick() method listens for a "button clicked"
        // event for the "play again" button. When
        // the event occurs, we save the user name and again start the startactivity
        play_again= (Button)findViewById(R.id.play_again);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_data();
                onBackPressed();

            }
        });

        // Handle the quit Button
        // by getting the quit button object
        // The onClick() method listens for a "button clicked"
        // event for the "quit" button. When
        // the event occurs, we save the user name and exit the app
        quit=(Button)findViewById(R.id.quit);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_data();
                finishAffinity();

            }
        });

        //set the textview according to the rsults
        Correct_Ans.setText(""+c_answers);
        t_question.setText(""+no_questions);
        Cheated.setText(""+no_cheats);


    }

    /**
     * save the user name using sharedprefereneces
     */
    public void save_data(){
        
        user=user_name.getText().toString();
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF,GradeActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_NAME, user);
        editor.apply();
    }

    /**
     * override for onbackpressed method,
     * if pressed then start the startactivity again
     */
    @Override
    public void onBackPressed(){
        Intent i = new Intent(GradeActivity.this,StartActivity.class );
        startActivity(i);
    }

    /**
     * override methode for dispatch the touch event when
     * user touch out of edit box
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    user_name.setFocusable(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
    //log callbacks
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    public void onResume(){
        super.onResume();
        Log.d(TAG, "GA_onResume() called");
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


}
