//unique package for MCQ app
package mcq_3.edu.pdx.mohak.ece558.mcq;

//Built in library
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
//Library for json
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//library to handle quiz files
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import static android.widget.Toast.makeText;

/**
 * Quiz Activity for user to play
 * quiz
 */

public class QuizActivity extends AppCompatActivity {

    // variable for question and radio buttons
    private TextView question;
    private RadioButton first_button;
    private RadioButton second_button;
    private RadioButton third_button;
    private RadioButton fourth_button;

    // variable for cheat button and next button
    private Button cheat_button;
    private Button next_button;

    // variable for radiogroup
    private RadioGroup rbgroup;

    //variable for gesture
    private GestureDetectorCompat gobj;

    //Globle variable
    private int number_of_correct_ans = 0;
    private int currentindex = 0;
    private Boolean Cheater = false;
    private int number;
    private int nocheats = 0;

    private static final String TAG = "Mark-1";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String KEY_INDEX = "Mark_index";
    private static final String KEY_CHEATER_STATUS = "Mark_status";
    private static final String KEY_ANSWER_STATUS = "0";
    private static final String EXTRA_QUIZ_NAME = "Mark_name";

    //variable for quiz file handling
    private String fileName;
    private String fileContent = "";

    //Array variable to store the quiz content
    List<String> questions = new ArrayList<>();
    List<String> option_A = new ArrayList<>();
    List<String> option_B = new ArrayList<>();
    List<String> option_C = new ArrayList<>();
    List<String> option_D = new ArrayList<>();
    List<Integer> rightAnswer = new ArrayList<>();
    List<String> answer = new ArrayList<>();
    int[] status = new int[20];

    /**
     * Start the intent
     * by paring the correct answer and key
     * @param packageContext
     * @param s
     * @return
     */
    public static Intent newIntent(Context packageContext, String s) {
        Intent intent = new Intent(packageContext, QuizActivity.class);
        intent.putExtra(EXTRA_QUIZ_NAME, s );
        return intent;
    }

    /**
     *  OnCreate is called when the quiz_activity
     *  is started.
     *  super.xxx method whenever we override a method
      */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // calls the super.onCreate() method and inflates
        // the layout with an ID of R.id.layout.activity_quiz
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // Get intent from start activity
        //get the file name for the quiz
        Intent i=getIntent();
        fileName= i.getStringExtra(StartActivity.EXTRA_NAME);

        //create the object for radiogroup and radio button
        //wiring up the widgets
        rbgroup=(RadioGroup) findViewById(R.id.radio_group);
        first_button = (RadioButton) findViewById(R.id.first_ans);
        second_button = (RadioButton) findViewById(R.id.second_ans);
        third_button = (RadioButton) findViewById(R.id.third_ans);
        fourth_button = (RadioButton)findViewById(R.id.fourth_ans);
        question = (TextView)findViewById(R.id.question);
        cheat_button = (Button) findViewById(R.id.cheat);
        next_button = (Button) findViewById(R.id.next);

        //restore the savedinstances
        if(savedInstanceState != null) {
            currentindex = savedInstanceState.getInt(KEY_INDEX, 0);
            Cheater = savedInstanceState.getBoolean(KEY_CHEATER_STATUS, false);
            status[currentindex] = savedInstanceState.getInt(KEY_ANSWER_STATUS, 0);
        }

        //read the jason file and prase it
        readFile();
        //update the question and options
        updateQuestionOptions();

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Listner for radiobuttons, if any one radiobutton is clicked then
        // it will set that radio button as answer
        // and checked that the answer is correct or not
        first_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                checkAnswer(1);
            }
        });
        second_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                checkAnswer(2);

            }
        });
        third_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                checkAnswer(3);
            }
        });
        fourth_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                checkAnswer(4);
            }
        });

        // Handle the cheat Button
        // by getting the cheat button object
        // The onClick() method listens for a "button clicked"
        // event for the "cheat" button. When
        // the event occurs, we start the cheat activity
        cheat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correctAnswer = answer.get(currentindex);
                Intent intent = CheatActivity.newIntent(QuizActivity.this, correctAnswer);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
        // Handle the cheat gesture control
        // by getting the gesture object
        // The onClick() method listens for a "gesture recoginazation"
        // event for the gesture. When
        // the event occurs, we switch to the next question
        gobj= new GestureDetectorCompat(this,new LearnGeasture());

        // Handle the next Button
        // by getting the next button object
        // The onClick() method listens for a "button clicked"
        // event for the "next" button. When
        // the event occurs,we switch to the next question
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //It will keep getting the data for 8 questions
                if(currentindex != 7) {

                    //clear the previous radio button
                    rbgroup.clearCheck();

                    // if status of the question is not zero means if any option is selected then
                    // update the question and set
                    // cheater status false
                    if(status[currentindex]== 1) {
                        currentindex = (currentindex + 1);
                        updateQuestionOptions();
                        Cheater = false;
                    }

                    //else if option is not selected then
                    // toast the message the select the option
                    else{
                        Toast toast = makeText(QuizActivity.this, "select an option", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
                        toast.show();
                    }
                }
                //if all question is finished then start the grade activity
                else
                {
                    grade();
                }

            }
        }

        );

    }

    /**
     * Preparing restult to return
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data ){
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            Cheater = true;

        }
    }

    /**
     *  ontouch to check any event occur or not using gesture
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.gobj.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * Gesture listener for any event occured
     */
    class LearnGeasture extends GestureDetector.SimpleOnGestureListener{
        /**
         * onfling to check for any gesture
         * @param event1
         * @param event2
         * @param velocityX
         * @param velocityY
         * @return
         */
        @Override
        public boolean onFling(MotionEvent event1,MotionEvent event2,float velocityX,float velocityY)
        {
            //if any slides done by user from right to left then
            //it will update the new question
            if(event2.getX()<event1.getX())
            {

                if(currentindex != 7) {
                    rbgroup.clearCheck();
                    // if status of the question is not zero means if any option is selected then
                    // update the question and set
                    // cheater status false
                    if(status[currentindex]== 1) {
                        currentindex = (currentindex + 1);
                        updateQuestionOptions();
                        Cheater = false;

                    }
                    //else if option is not selected then
                    // toast the message the select the option
                    else{
                        Toast toast = makeText(QuizActivity.this, "select an option", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
                        toast.show();
                    }
                }
                //else show the results
                else
                {
                    grade();
                }
            }
            return true;
        }
    }

    /**
     *  read jason file from assets by opening it,
     *  open the files and read all the data and stored it
     *  in one list and in the end close that file
     */
    private void readFile() {
        String json = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            fileContent = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();

        }

        //PARSING
        try {
            //creating JSONobject
            JSONObject jsonObjMain = new JSONObject(fileContent);
            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.getJSONArray("Moh@k");
            // JSONArray has five JSONObject
            for (number = 0; number < jsonArray.length(); number++) {
                //Creating JSONObject from JSONArray
                JSONObject jsonObj = jsonArray.getJSONObject(number);
                // Getting data from individual JSONObject
                questions.add(jsonObj.getString("question"));
                option_A.add(jsonObj.getString("optionA"));
                option_B.add(jsonObj.getString("optionB"));
                option_C.add(jsonObj.getString("optionC"));
                option_D.add(jsonObj.getString("optionD"));
                rightAnswer.add(jsonObj.getInt("correctAnswer"));
                answer.add(jsonObj.getString("cheat"));

            }

        } catch (JSONException e) {
            // if file not read then exception occure
            e.printStackTrace();
        }

    }

    /**
     *  check the pressed answer is coorect or not,
     *   if it is correct and user is not cheated then
     *   increment the count for correct answer
     * @param userpressed
     */

    private void checkAnswer(int userpressed) {
        //retrive the correct answer fom jason file
        int correct_ans  = rightAnswer.get(currentindex);
        //check that user is cheated or not
        // if cheate then incremant the count for cheat
        //else incremnt the count for correct ans
        if (Cheater) {
            nocheats+=1;
        } else {
            if (userpressed == correct_ans) {
                number_of_correct_ans += 1;
            }
        }
        //set status for radiobutton is 1
        status[currentindex] = 1;

    }

    /**
     *update the question and options
     */

    private void updateQuestionOptions(){
        question.setText(questions.get(currentindex));
        first_button.setText(option_A.get(currentindex));
        second_button.setText(option_B.get(currentindex));
        third_button.setText(option_C.get(currentindex));
        fourth_button.setText(option_D.get(currentindex));
    }

    /**
     * Set the intent and start the grade activity with total correct answer, cheated and , total question
     */

    private void grade(){
        Intent intent = GradeActivity.newIntent(QuizActivity.this, number_of_correct_ans, number, nocheats);
        startActivityForResult(intent, REQUEST_CODE_CHEAT);
    }

    /**
     * savedInstanceState - variable of type bundle,
     * pairing the instance var with key
     * @param savedInstanceState
     */
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX,currentindex);
        savedInstanceState.putBoolean(KEY_CHEATER_STATUS, Cheater);
        savedInstanceState.putInt(KEY_ANSWER_STATUS, status[currentindex]);
    }


    //log callbacks
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
