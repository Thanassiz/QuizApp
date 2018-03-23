package com.example.android.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    // Key vars, helps to save variables in Bundles (savedInstanceState, outState)
    private static final String STATE_KEY_NICKNAME = "profileNickname";
    private static final String STATE_KEY_IS_PROFILE_PRESSED = "isProfilePressed";
    private static final String STATE_KEY_SCORE = "score";
    private static final String STATE_KEY_IS_RESULTS_PRESSED = "isResultsPressed";
    private static final String STATE_KEY_STATEMODEL = "stateModel";
    // Helper var to see if profile button is pressed, same for score button
    private boolean isProfilePressed = false;
    public static boolean isResultsPressed = false;
    // Views
    private ImageView profileImage;
    private EditText profileEditText;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private Button setProfileButton;
    private TextView welcomeTextView;
    private TextView scoreTextView;
    private Button scoreButton;
    private ListView listView;
    // Adapter of question model
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //region Initialize Variables
        profileImage = findViewById(R.id.profile_image_id);
        profileEditText = findViewById(R.id.profile_nickname_id);
        radioMale = findViewById(R.id.profile_male_radio_id);
        radioFemale = findViewById(R.id.profile_female_radio_id);
        setProfileButton = findViewById(R.id.set_profile_button_id);
        welcomeTextView = findViewById(R.id.welcome_text_id);
        scoreTextView = findViewById(R.id.quiz_score_text_id);
        scoreButton = findViewById(R.id.score_button_id);
        //endregion
        /*
        Create a list of questions
        The last value of Question which is added in the list is the question type.
        Type 1 is question with checkboxes, can have multiple answers
        Type 2 is question with radiobuttons, can have only one answer
        Type 3 is question with images, can have only one answer
        Type 4 is question with an edittext, can have only one answer
        */
        final ArrayList<Question> questions = new ArrayList<>();
        // Add questions in arraylist
        questions.add(new Question(getString(R.string.question_1_main_text), getString(R.string.question_1_answer_1), getString(R.string.question_1_answer_2), getString(R.string.question_1_answer_3), getString(R.string.question_1_answer_4), 1));
        questions.add(new Question(getString(R.string.question_2_main_text), getString(R.string.question_2_answer_1), getString(R.string.question_2_answer_2), getString(R.string.question_2_answer_3), getString(R.string.question_2_answer_4), 2));
        questions.add(new Question(getString(R.string.question_3_main_text), R.drawable.img_q_greece, R.drawable.img_q_london, R.drawable.img_q_paris, R.drawable.img_q_rome, 3));
        questions.add(new Question(getString(R.string.question_4_main_text), getString(R.string.question_4_answer_1), 4));
        questions.add(new Question(getString(R.string.question_5_main_text), getString(R.string.question_5_answer_1), getString(R.string.question_5_answer_2), getString(R.string.question_5_answer_3), getString(R.string.question_5_answer_4), 2));
        // Create a QuestionAdapter, whose data source is a list of Questions. The adapter knows
        // how to create list questions for each question in the list
        adapter = new QuestionAdapter(this, questions);
        //Find the ListView object from the activity_quiz with its' id caller.
        listView = findViewById(R.id.question_list_view_id);
        // Connects the adapter to the listView so it can display questions for each Question in the list
        listView.setAdapter(adapter);
        // Lock selected details of profile or clear selected details
        setProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isProfilePressed) {
                    resetProfile();
                } else {
                    setProfile();
                }
            }
        });

        // Show results of total score also clears total score and quiz
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isResultsPressed) {
                    isResultsPressed = false;
                    adapter.setScore(0);
                    adapter.clearViews(0);
                    adapter.clearViews(1);
                    adapter.clearViews(2);
                    adapter.clearViews(3);
                    adapter.clearViews(4);
                    listView.setAdapter(null);
                    listView.setAdapter(adapter);
                    scoreTextView.setText(getString(R.string.quiz_score_text));
                    scoreButton.setText(getString(R.string.score_button_text));
                } else {
                    //Displays score and answers and locks the questions
                    isResultsPressed = true;
                    int score = 0;
                    score = adapter.countScore(0);
                    score = adapter.countScore(1);
                    score = adapter.countScore(2);
                    score = adapter.countScore(3);
                    score = adapter.countScore(4);
                    scoreTextView.setText(Integer.toString(score) + getString(R.string.out_of_five));
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_score_text)
                            + Integer.toString(score) + getString(R.string.out_of_five), Toast.LENGTH_SHORT).show();
                    scoreButton.setText(getString(R.string.reset_score_button_text));
                }
            }
        });
    }

    // Display profile image depended on gender selection and locks profile
    private void setProfile() {
        // Checks if EditText or Radiobutton fields are selected else it throws a message to fill them
        if (TextUtils.isEmpty(profileEditText.getText())
                || (!radioMale.isChecked() && !radioFemale.isChecked())) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.set_profile_validation_text), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        } else {
            if (radioMale.isChecked()) {
                profileImage.setImageResource(R.drawable.prof_img_mal);
                welcomeTextView.setText(getString(R.string.welcome_male_text) + profileEditText.getText());

            }
            if (radioFemale.isChecked()) {
                profileImage.setImageResource(R.drawable.prof_img_fem);
                welcomeTextView.setText(getString(R.string.welcome_female_text) + profileEditText.getText());
            }
            //region Lock Profile
            profileEditText.setFocusable(false);
            profileEditText.setInputType(InputType.TYPE_NULL);
            radioMale.setClickable(false);
            radioFemale.setClickable(false);
            setProfileButton.setText(getString(R.string.reset_profile_button_text));
            //endregion
            isProfilePressed = true;
        }
    }

    // Reset profile setting default settings and unlocks it
    private void resetProfile() {
        //region Unlock Profile
        profileEditText.setFocusable(true);
        profileEditText.setFocusableInTouchMode(true);
        profileEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        profileEditText.setText("");
        radioMale.setClickable(true);
        radioFemale.setClickable(true);
        //endregion
        //region Set Default Views
        profileImage.setImageResource(R.drawable.profile_img);
        radioMale.setChecked(false);
        radioMale.clearFocus();
        radioFemale.setChecked(false);
        radioFemale.clearFocus();
        welcomeTextView.setText(getString(R.string.welcome_text));
        setProfileButton.setText(R.string.set_profile_button_text);
        isProfilePressed = false;
        //endregion
    }

    // This method is used to save vars (in Bundle) before the Activity is destroyed.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String profileNickname = profileEditText.getText().toString();
        String score = scoreTextView.getText().toString();
        outState.putString(STATE_KEY_NICKNAME, profileNickname);
        outState.putBoolean(STATE_KEY_IS_PROFILE_PRESSED, isProfilePressed);
        outState.putString(STATE_KEY_SCORE, score);
        outState.putBoolean(STATE_KEY_IS_RESULTS_PRESSED, isResultsPressed);
        Serializable stateModel = adapter.getmCheckStates();
        outState.putSerializable(STATE_KEY_STATEMODEL, stateModel);
    }

    // Repopulates the values that were saved in onSavedInstaceState's Bundle.
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String profileNickname = savedInstanceState.getString(STATE_KEY_NICKNAME);
        String score = savedInstanceState.getString(STATE_KEY_SCORE);
        ArrayList stateModel = (ArrayList) savedInstanceState.getSerializable(STATE_KEY_STATEMODEL);
        isProfilePressed = savedInstanceState.getBoolean(STATE_KEY_IS_PROFILE_PRESSED);
        isResultsPressed = savedInstanceState.getBoolean(STATE_KEY_IS_RESULTS_PRESSED);
        adapter.setmCheckStates(stateModel);

        // The code bellow (displaying Views) should be called onResume method,
        // hence some variables are local cause of Bundle, it's written here.
        // PROFILE LAYOUT
        if (isProfilePressed) {
            setProfile();
        } else {
            resetProfile();
        }
        if (radioMale.isChecked()) {
            profileImage.setImageResource(R.drawable.prof_img_mal);
            welcomeTextView.setText(getString(R.string.welcome_male_text) + profileNickname);
        }
        if (radioFemale.isChecked()) {
            profileImage.setImageResource(R.drawable.prof_img_fem);
            welcomeTextView.setText(getString(R.string.welcome_female_text) + profileNickname);
        }

        // SCORE LAYOUT
        if (isResultsPressed) {
            scoreTextView.setText(score);
            scoreButton.setText(getString(R.string.reset_score_button_text));
        } else {
            isResultsPressed = false;
            scoreTextView.setText(getString(R.string.quiz_score_text));
            scoreButton.setText(getString(R.string.score_button_text));
        }
    }

}
