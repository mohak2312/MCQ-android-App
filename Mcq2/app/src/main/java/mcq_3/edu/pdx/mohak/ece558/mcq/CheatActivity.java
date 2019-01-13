//unique package for MCQ app
package mcq_3.edu.pdx.mohak.ece558.mcq;
//Built in library
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Cheat activity to show the correct answer
 */
public class CheatActivity extends AppCompatActivity {

    //variable  for output
    private String T_Answer;
    private TextView Answer;

    private static final String EXTRA_ANSWER_IS_TRUE = "Mark_true";
    private static final String EXTRA_ANSWER_SHOWN = "Mark_shown";
    private static final String KEY_INDEX_CHEAT = "Mark_cheat";
    private static final String KEY_CORRECT_ANS = "Mark_ans";
    private static final String TAG = "Mark-1";
    private boolean Cheater_Status = false;

    /**
     * Start the intent
     * by paring the correct answer and key
     * @param packageContext
     * @param T_Answer
     * @return
     */

    public static Intent newIntent(Context packageContext, String T_Answer) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, T_Answer );
        return intent;
    }

    /**
     * OnCreate is called when the quiz_activity
     *  is started.
     *  super.xxx method whenever we override a method
      */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // calls the super.onCreate() method and inflates
        // the layout with an ID of R.id.layout.activity_cheat
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        //  Get intent from quiz activity
        // get the true answer  and set it in the textview
        T_Answer = getIntent().getStringExtra(EXTRA_ANSWER_IS_TRUE);
        Answer = (TextView) findViewById(R.id.answer_c);
        //restore the savedinstances
        if(savedInstanceState != null) {
            Cheater_Status = savedInstanceState.getBoolean(KEY_INDEX_CHEAT);
        }
        Cheater_Status = true;
            Answer.setText(T_Answer);
            setAnswerShownResult(Cheater_Status);
    }

    /**
     * intent for get the result in the quiz activity
     * @param isAnswerShown
     */
    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    /**
     * savedInstanceState - variable of type bundle,
     * pairing the instance var with key
     * @param savedInstanceState
     */

    //
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_INDEX_CHEAT,Cheater_Status);
        savedInstanceState.putString(KEY_CORRECT_ANS, T_Answer);
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
}
