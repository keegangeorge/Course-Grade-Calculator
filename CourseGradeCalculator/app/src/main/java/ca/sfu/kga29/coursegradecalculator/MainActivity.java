package ca.sfu.kga29.coursegradecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * MainActivity class for applications primary activity
 *
 * @author Keegan
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    // FIELDS //
    private static final String DEBUG_TAG = "CourseGrades";
    private static final String ASSIGNMENTS = "ASSIGNMENTS";
    private static final String PARTICIPATION = "PARTICIPATION";
    private static final String PROJECTS = "PROJECTS";
    private static final String QUIZZES = "QUIZZES";
    private static final String EXAM = "EXAM";

    private EditText assignmentEditText;
    private double assignmentMark;

    private EditText participationEditText;
    private double participationMark;

    private EditText projectsEditText;
    private double projectMark;

    private EditText quizzesEditText;
    private double quizzesMark;

    private EditText examEditText;
    private double examMark;
    private SeekBar examSeekBar;

    private TextView finalMarkTextView;

    private Button resetButton;

    /**
     * Method that is called when the activity is created
     *
     * @param savedInstanceState Bundle information that is saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignmentEditText = (EditText) findViewById(R.id.etAssignment);
        assignmentEditText.addTextChangedListener(this);

        participationEditText = (EditText) findViewById(R.id.etParticipation);
        participationEditText.addTextChangedListener(this);

        projectsEditText = (EditText) findViewById(R.id.etProjects);
        projectsEditText.addTextChangedListener(this);

        quizzesEditText = (EditText) findViewById(R.id.etQuizzes);
        quizzesEditText.addTextChangedListener(this);

        examEditText = (EditText) findViewById(R.id.etExam);
        examEditText.addTextChangedListener(this);

        finalMarkTextView = (TextView) findViewById(R.id.etFinalMark);

        examSeekBar = (SeekBar) findViewById(R.id.examSeekBar);
        examSeekBar.setOnSeekBarChangeListener(examSeekBarListener);
        examSeekBar.setProgress(80);

        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // TODO Auto-generated method stub
    }

    /**
     * Saves text information when interface is updated by user
     *
     * @param charSequence value entered by user
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (assignmentEditText.isFocused()) {
            try {
                assignmentMark = Double.parseDouble(charSequence.toString());
            } catch (NumberFormatException e) {
                assignmentMark = 0.0;
            }
        }

        if (participationEditText.isFocused()) {
            try {
                participationMark = Double.parseDouble(charSequence.toString());
            } catch (NumberFormatException e) {
                participationMark = 0.0;
            }
        }

        if (projectsEditText.isFocused()) {
            try {
                projectMark = Double.parseDouble(charSequence.toString());
            } catch (NumberFormatException e) {
                projectMark = 0.0;
            }
        }

        if (quizzesEditText.isFocused()) {
            try {
                quizzesMark = Double.parseDouble(charSequence.toString());
            } catch (NumberFormatException e) {
                quizzesMark = 0.0;
            }
        }

        // Update finalMark
        updateFinalMark();
    }


    @Override
    public void afterTextChanged(Editable editable) {
        // TODO Auto-generated method stub
    }

    /**
     * Method responsible for what occurs on reset-button click
     * Upon button click, default values will be restored
     *
     * @param view current view item (button)
     */
    @Override
    public void onClick(View view) {
        participationMark = 0.0;
        assignmentMark = 0.0;
        projectMark = 0.0;
        quizzesMark = 0.0;

        assignmentEditText.setText("");
        participationEditText.setText("");
        projectsEditText.setText("");
        quizzesEditText.setText("");
        examEditText.setText("");
        examSeekBar.setProgress(80);
    }

    @Override
    public void onProgressChanged(SeekBar seekbar, int i, boolean b) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    /**
     * Responsible for updating the final mark depending upon the values entered for other grades
     */
    private void updateFinalMark() {
        // calculates the final mark
        double finalMark = assignmentMark * 15 / 100 + participationMark * 15 / 100 + projectMark * 20 / 100 + quizzesMark * 20 / 100 + examMark * 30 / 100;

        // sets the text in finalMarkEditText to finalMark
        this.finalMarkTextView.setText(String.format("%.02f", finalMark));
    }

    /**
     * Responsible for updating the exam mark based on position of seekbar
     */
    private void updateExam() {
        // matches edit text on exam mark to position of seekbar
        examEditText.setText(String.format("%d", (int) examMark));
    }

    /**
     * Saves the state of marks entered currently
     *
     * @param outState the Bundle for saving key/value info
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(ASSIGNMENTS, assignmentMark);
        outState.putDouble(PARTICIPATION, participationMark);
        outState.putDouble(PROJECTS, projectMark);
        outState.putDouble(QUIZZES, quizzesMark);
        outState.putDouble(EXAM, examMark);
    }

    /**
     * Responsible for restoring the information from the last saved input
     *
     * @param inState the Bundle with the previously saved key/value info
     */
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        assignmentMark = inState.getDouble(ASSIGNMENTS);
        participationMark = inState.getDouble(PARTICIPATION);
        projectMark = inState.getDouble(PROJECTS);
        quizzesMark = inState.getDouble(QUIZZES);
        examMark = inState.getDouble(EXAM);
        updateFinalMark();
    }

    /**
     * Anonymous inner-class for updating exam mark when user changes position of the seek bar
     */
    private SeekBar.OnSeekBarChangeListener examSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        /**
         * Sets the exam value to the current position of the seekbar
         * @param seekBar reference to the seekbar
         * @param progress current progress of seekbar
         * @param fromUser true/false from user
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            examMark = seekBar.getProgress();
            // Log.i(DEBUG_TAG, "examMark" + examMark);
            updateExam();
            updateFinalMark();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };


} // MainActivity Class End



