package com.example.android.quizapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.quizapp.QuizActivity.isResultsPressed;


/**
 * Created by Thanassis on 25/2/2018.
 */
public class QuestionAdapter extends ArrayAdapter<Question> {

    int score = 0;
    // Identifies the class or activity where the log call occurs
    private static final String LOG_TAG = QuestionAdapter.class.getSimpleName();
    // The View that adapter is attached.
    private View listQuestionView;
    // The ViewHolders that contains all the views
    private ViewHolder viewHolder;
    private ViewHolderType2 viewHolderType2;
    private ViewHolderType3 viewHolderType3;
    private ViewHolderType4 viewHolderType4;
    // Array that saves the states (positions and attributes of Views) helps for displaying correctly
    private ArrayList<DataStateModel> mCheckStates;

    // Constructor
    public QuestionAdapter(@NonNull Context context, @NonNull List<Question> questions) {
        super(context, 0, questions);
        mCheckStates = new ArrayList<>();
        for (Question currentQuestion : questions) {
            mCheckStates.add(new DataStateModel(currentQuestion));
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("Position: " + position, " , getView: " + convertView);
        // Initialize listQuestionView, will be shown as list.
        listQuestionView = convertView;
        // Get the data item type for this position
        int type = getItemViewType(position);
        // Check if the existing listQuestionView is being re-used, otherwise inflate the view
        if (listQuestionView == null || (listQuestionView.getClass() != LinearLayout.class)) {
            if (type == 1) {
                viewHolder = new ViewHolder();
            } else if (type == 2) {
                viewHolderType2 = new ViewHolderType2();
            } else if (type == 3) {
                viewHolderType3 = new ViewHolderType3();
            } else if (type == 4) {
                viewHolderType4 = new ViewHolderType4();
            }
            // Inflate XML layout based on the type
            listQuestionView = getInflatedLayoutForType(type, parent);
            // Saves all objects displayed in a row of listQuestionView to viewHolder and stores em to memory (pool)
            if (type == 1) {
                initializeListType1();
                listQuestionView.setTag(viewHolder);
            } else if (type == 2) {
                initializeListType2();
                listQuestionView.setTag(viewHolderType2);
            } else if (type == 3) {
                initializeListType3();
                listQuestionView.setTag(viewHolderType3);
            } else if (type == 4) {
                initializeListType4();
                listQuestionView.setTag(viewHolderType4);
            }
        } else {
            // Get objects from listQuestionView and let viewHolder handle em
            Object tag = listQuestionView.getTag();
            if (type == 1) {
                if (tag.getClass() == ViewHolder.class) {
                    viewHolder = (ViewHolder) tag;
                } else {
                    viewHolder = new ViewHolder();
                    listQuestionView = getInflatedLayoutForType(type, parent);
                    initializeListType1();
                    listQuestionView.setTag(viewHolder);
                }
            } else if (type == 2) {
                if (tag.getClass() == ViewHolderType2.class) {
                    viewHolderType2 = (ViewHolderType2) tag;
                } else {
                    viewHolderType2 = new ViewHolderType2();
                    listQuestionView = getInflatedLayoutForType(type, parent);
                    initializeListType2();
                    listQuestionView.setTag(viewHolderType2);
                }
            } else if (type == 3) {
                if (tag.getClass() == ViewHolderType3.class) {
                    viewHolderType3 = (ViewHolderType3) tag;
                } else {
                    viewHolderType3 = new ViewHolderType3();
                    listQuestionView = getInflatedLayoutForType(type, parent);
                    initializeListType3();
                    listQuestionView.setTag(viewHolderType3);
                }
            } else if (type == 4) {
                if (tag.getClass() == ViewHolderType4.class) {
                    viewHolderType4 = (ViewHolderType4) tag;
                } else {
                    viewHolderType4 = new ViewHolderType4();
                    listQuestionView = getInflatedLayoutForType(type, parent);
                    initializeListType4();
                    listQuestionView.setTag(viewHolderType4);
                }
            }
        }

        // Fetch data to ViewHolder and Initialize onCheckChangeListeners
        if (type == 1) {
            //--- TYPE 1 ---
            setViewHolderType1(position);
            InitializeCheckListeners(position);
        } else if (type == 2) {
            // --- TYPE 2 ---
            setViewHolderType2(position);
            InitializeRadioListeners(position);
        } else if (type == 3) {
            // --- TYPE 3 ---
            setViewHolderType3(position);
            InitializeImageListeners(position);
        } else if (type == 4) {
            // --- TYPE 4 ---
            setViewHolderType4(position);
            InitializeEditTextListeners(position);
        }
        // This IF is called here only for CORRECTLY displaying/disabling answers
        if (isResultsPressed) {
            countScore(position);
        }
        return this.listQuestionView;
    }

    // Initialize views from list_question_type_1.xml
    private void initializeListType1() {
        // Find the TextView in the list_question_type_1.xml layout with the main_question_id
        viewHolder.mainQuestionTextView = listQuestionView.findViewById(R.id.main_question_type1_id);
        // Find the CheckBoxes in the list_question_type_1.xml layout with their corresponding_id
        viewHolder.answer1Checkbox = listQuestionView.findViewById(R.id.checkbox_answer_1);
        viewHolder.answer2Checkbox = listQuestionView.findViewById(R.id.checkbox_answer_2);
        viewHolder.answer3Checkbox = listQuestionView.findViewById(R.id.checkbox_answer_3);
        viewHolder.answer4Checkbox = listQuestionView.findViewById(R.id.checkbox_answer_4);
    }

    // Initialize views from list_question_type_2.xml
    private void initializeListType2() {
        // Find the TextView in the list_question_type_2.xml layout with the main_question_id
        viewHolderType2.mainQuestionTextView = listQuestionView.findViewById(R.id.main_question_type2_id);
        // Find the CheckBoxes in the list_question_type_1.xml layout with their corresponding_id
        viewHolderType2.answerRadioGroup = listQuestionView.findViewById(R.id.radio_group_answerlist);
        viewHolderType2.answer1Radiobutton = listQuestionView.findViewById(R.id.radio_answer_1);
        viewHolderType2.answer2Radiobutton = listQuestionView.findViewById(R.id.radio_answer_2);
        viewHolderType2.answer3Radiobutton = listQuestionView.findViewById(R.id.radio_answer_3);
        viewHolderType2.answer4Radiobutton = listQuestionView.findViewById(R.id.radio_answer_4);
    }

    // Initialize views from list_question_type_3.xml
    private void initializeListType3() {
        // Find the TextView in the list_question_type_3.xml layout with the main_question_id
        viewHolderType3.mainQuestionTextView = listQuestionView.findViewById(R.id.main_question_type3_id);
        // Find the ImageViews in the list_question_type_3.xml layout with their corresponding_id
        viewHolderType3.answer1Image = listQuestionView.findViewById(R.id.image_answer_1);
        viewHolderType3.answer2Image = listQuestionView.findViewById(R.id.image_answer_2);
        viewHolderType3.answer3Image = listQuestionView.findViewById(R.id.image_answer_3);
        viewHolderType3.answer4Image = listQuestionView.findViewById(R.id.image_answer_4);
    }

    // Initialize views from list_question_type_4.xml
    private void initializeListType4() {
        // Find the TextView in the list_question_type_4.xml layout with the main_question_id
        viewHolderType4.mainQuestionTextView = listQuestionView.findViewById(R.id.main_question_type4_id);
        // Find the EditText in the list_question_type_4.xml layout with its corresponding_id
        viewHolderType4.answer1EditText = listQuestionView.findViewById(R.id.edittext_answer1);
    }

    // Fetch the ViewHolder with data
    private void setViewHolderType1(int position) {
        // Match the DataStateModel with mCheckStates array
        DataStateModel currentModel = mCheckStates.get(position);
        // Get the main question from DataStateModel object and set it to the TextView of ViewHolder
        viewHolder.mainQuestionTextView.setText(currentModel.question.getmMainQuestion());
        // Get the answers from DataStateModel object and set it to the CheckBoxes of ViewHolder
        viewHolder.answer1Checkbox.setText(currentModel.question.getmAnswer1());
        viewHolder.answer2Checkbox.setText(currentModel.question.getmAnswer2());
        viewHolder.answer3Checkbox.setText(currentModel.question.getmAnswer3());
        viewHolder.answer4Checkbox.setText(currentModel.question.getmAnswer4());
        // Set state to Checkboxes of ViewHolder (depending if they are pressed or not)
        // Also disabling/removing the setOnCheckedChangeListeners by setting em to null. This is the proper way to handle onChecked events.
        viewHolder.answer1Checkbox.setOnCheckedChangeListener(null);
        viewHolder.answer1Checkbox.setChecked(currentModel.checkbox1);
        viewHolder.answer2Checkbox.setOnCheckedChangeListener(null);
        viewHolder.answer2Checkbox.setChecked(currentModel.checkbox2);
        viewHolder.answer3Checkbox.setOnCheckedChangeListener(null);
        viewHolder.answer3Checkbox.setChecked(currentModel.checkbox3);
        viewHolder.answer4Checkbox.setOnCheckedChangeListener(null);
        viewHolder.answer4Checkbox.setChecked(currentModel.checkbox4);
    }

    // Fetch the ViewHolder with data
    private void setViewHolderType2(int position) {
        // Match the DataStateModel with mCheckStates array
        DataStateModel currentModel = mCheckStates.get(position);
        // Get the main question from DataStateModel object and set it to the TextView of ViewHolder
        viewHolderType2.mainQuestionTextView.setText(currentModel.question.getmMainQuestion());
        // Get the answers from DataStateModel object and set it to the RadioButtons of ViewHolder
        viewHolderType2.answer1Radiobutton.setText(currentModel.question.getmAnswer1());
        viewHolderType2.answer2Radiobutton.setText(currentModel.question.getmAnswer2());
        viewHolderType2.answer3Radiobutton.setText(currentModel.question.getmAnswer3());
        viewHolderType2.answer4Radiobutton.setText(currentModel.question.getmAnswer4());
        // Clears all RadioButtons in the RadioGroup of ViewHolder
        // Also disabling/removing the setOnCheckedChangeListeners by setting to null. This is the proper way to handle onChecked events.
        viewHolderType2.answerRadioGroup.setOnCheckedChangeListener(null);
        viewHolderType2.answerRadioGroup.clearCheck();
        // Set the Model to hold the answer the user picked
        if (currentModel.currentRadioPos == 0) {
            viewHolderType2.answerRadioGroup.check(R.id.radio_answer_1);
        } else if (currentModel.currentRadioPos == 1) {
            viewHolderType2.answerRadioGroup.check(R.id.radio_answer_2);
        } else if (currentModel.currentRadioPos == 2) {
            viewHolderType2.answerRadioGroup.check(R.id.radio_answer_3);
        } else if (currentModel.currentRadioPos == 3) {
            viewHolderType2.answerRadioGroup.check(R.id.radio_answer_4);
        }
    }

    // Fetch the ViewHolder with data
    private void setViewHolderType3(int position) {
        // Match the DataStateModel with mCheckStates array
        DataStateModel currentModel = mCheckStates.get(position);
        // Get the main question from DataStateModel object and set it to the TextView of ViewHolder
        viewHolderType3.mainQuestionTextView.setText(currentModel.question.getmMainQuestion());
        // Clear Image
        viewHolderType3.answer1Image.setImageResource(R.drawable.clear_image_boarder);
        viewHolderType3.answer2Image.setImageResource(R.drawable.clear_image_boarder);
        viewHolderType3.answer3Image.setImageResource(R.drawable.clear_image_boarder);
        viewHolderType3.answer4Image.setImageResource(R.drawable.clear_image_boarder);
        // Get the images from DataStateModel object and set it to the ImageViews of ViewHolder
        if (viewHolderType3.answer1Image != null) {
            viewHolderType3.answer1Image.setImageResource(currentModel.question.getmImage1());
        } else {
            Log.e(LOG_TAG, "Image 1: = NULL");
        }
        if (viewHolderType3.answer2Image != null) {
            viewHolderType3.answer2Image.setImageResource(currentModel.question.getmImage2());
        } else {
            Log.e(LOG_TAG, "Image 2: = NULL");
        }
        if (viewHolderType3.answer3Image != null) {
            viewHolderType3.answer3Image.setImageResource(currentModel.question.getmImage3());
        } else {
            Log.e(LOG_TAG, "Image 3: = NULL");
        }
        if (viewHolderType3.answer4Image != null) {
            viewHolderType3.answer4Image.setImageResource(currentModel.question.getmImage4());
        } else {
            Log.e(LOG_TAG, "Image 4: = NULL");
        }

        // Set state to Checkboxes of ViewHolder (depending if they are pressed or not)
        // Also disabling/removing the setOnClickListeners by setting em to null. This is the proper way to handle onClick events.
        viewHolderType3.answer1Image.setOnClickListener(null);
        viewHolderType3.answer2Image.setOnClickListener(null);
        viewHolderType3.answer3Image.setOnClickListener(null);
        viewHolderType3.answer4Image.setOnClickListener(null);
        if (viewHolderType3.answer1Image.getId() == currentModel.isImageSelected) {
            viewHolderType3.answer1Image.setBackgroundResource(R.drawable.border_selected_style);
        } else {
            viewHolderType3.answer1Image.setBackgroundResource(R.drawable.clear_image_boarder);
        }
        if (viewHolderType3.answer2Image.getId() == currentModel.isImageSelected) {
            viewHolderType3.answer2Image.setBackgroundResource(R.drawable.border_selected_style);
        } else {
            viewHolderType3.answer2Image.setBackgroundResource(R.drawable.clear_image_boarder);
        }
        if (viewHolderType3.answer3Image.getId() == currentModel.isImageSelected) {
            viewHolderType3.answer3Image.setBackgroundResource(R.drawable.border_selected_style);
        } else {
            viewHolderType3.answer3Image.setBackgroundResource(R.drawable.clear_image_boarder);
        }
        if (viewHolderType3.answer4Image.getId() == currentModel.isImageSelected) {
            viewHolderType3.answer4Image.setBackgroundResource(R.drawable.border_selected_style);
        } else {
            viewHolderType3.answer4Image.setBackgroundResource(R.drawable.clear_image_boarder);
        }
    }

    // Fetch the ViewHolder with data
    private void setViewHolderType4(int position) {
        // Match the DataStateModel with mCheckStates array
        final DataStateModel currentModel = mCheckStates.get(position);
        // Get the main question from DataStateModel object and set it to the TextView of ViewHolder
        viewHolderType4.mainQuestionTextView.setText(currentModel.question.getmMainQuestion());
        // Also disabling/removing the setOnFocusChangeListener by setting to null. This is the proper way to handle events.
        viewHolderType4.answer1EditText.setOnFocusChangeListener(null);
        viewHolderType4.answer1EditText.clearFocus();
        // Get the answers from DataStateModel object and set it to the EditText of ViewHolder
        viewHolderType4.answer1EditText.setText(currentModel.edittextCaption);
        viewHolderType4.answer1EditText.setId(currentModel.edittextId);
    }

    // Initialize onCheckedChangedListeners for CheckBoxes
    private void InitializeCheckListeners(final int position) {
        viewHolder.answer1Checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    mCheckStates.get(position).checkbox1 = true;
                    System.out.println("CheckBox 1 with ID: " + buttonView.getId());
                } else {
                    mCheckStates.get(position).checkbox1 = false;
                }
            }
        });
        viewHolder.answer2Checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    mCheckStates.get(position).checkbox2 = true;
                    System.out.println("CheckBox 2 with ID: " + buttonView.getId());
                } else {
                    mCheckStates.get(position).checkbox2 = false;
                }
            }
        });
        viewHolder.answer3Checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    mCheckStates.get(position).checkbox3 = true;
                    System.out.println("CheckBox 3 with ID: " + buttonView.getId());
                } else {
                    mCheckStates.get(position).checkbox3 = false;
                }
            }
        });
        viewHolder.answer4Checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    mCheckStates.get(position).checkbox4 = true;
                    System.out.println("CheckBox 4 with ID: " + buttonView.getId());
                } else {
                    mCheckStates.get(position).checkbox4 = false;
                }
            }
        });
    }

    // Initialize onCheckedChangedListeners for RadioButtons
    private void InitializeRadioListeners(final int position) {
        final DataStateModel currentModel = mCheckStates.get(position);
        viewHolderType2.answerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // group.getCheckedRadioButtonId() is the selected radiobutton object.
                Log.e("val", "position of list: " + position + ", checked radiobutton " + group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())) + ", with object id " + group.getCheckedRadioButtonId());
                if (checkedId > -1) {
                    // Holds the checked id of the radiobutton which is pressed finding it by it's view object
                    currentModel.currentRadioPos = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId()));
                }
            }
        });
    }

    // Initialize onClickListeners for Images
    private void InitializeImageListeners(final int position) {
        viewHolderType3.answer1Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckStates.get(position).isImageSelected = v.getId();
                System.out.println(" IMAGE 1: CLICKED, with ID = " + v.getId());
                notifyDataSetChanged();
            }
        });
        viewHolderType3.answer2Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckStates.get(position).isImageSelected = v.getId();
                System.out.println(" IMAGE 2: CLICKED, with ID = " + v.getId());
                notifyDataSetChanged();
            }
        });
        viewHolderType3.answer3Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckStates.get(position).isImageSelected = v.getId();
                System.out.println(" IMAGE 3: CLICKED, with ID = " + v.getId());
                notifyDataSetChanged();
            }
        });
        viewHolderType3.answer4Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckStates.get(position).isImageSelected = v.getId();
                System.out.println(" IMAGE 4: CLICKED, with ID = " + v.getId());
                notifyDataSetChanged();
            }
        });
    }

    // Initialize onFocusChangeListeners for EditText
    private void InitializeEditTextListeners(final int position) {
        viewHolderType4.answer1EditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mCheckStates.get(position).edittextCaption = ((EditText) v).getText().toString();
                    mCheckStates.get(position).edittextId = v.getId();
                }
            }
        });
    }

    // Get the type of View that will be created by getView(int, View, ViewGroup) for the specified item.
    @Override
    public int getItemViewType(int position) {
        return getItem(position).getmQuestionType();
    }

    // Given the item type, responsible for returning the correct inflated XML layout file
    private View getInflatedLayoutForType(int type, ViewGroup parent) {
        if (type == 1) {
            return LayoutInflater.from(getContext()).inflate(R.layout.list_question_type_1, parent, false);
        } else if (type == 2) {
            return LayoutInflater.from(getContext()).inflate(R.layout.list_question_type_2, parent, false);
        } else if (type == 3) {
            return LayoutInflater.from(getContext()).inflate(R.layout.list_question_type_3, parent, false);
        } else if (type == 4) {
            return LayoutInflater.from(getContext()).inflate(R.layout.list_question_type_4, parent, false);
        } else {
            return null;
        }
    }

    private class ViewHolder {
        private TextView mainQuestionTextView;
        private CheckBox answer1Checkbox;
        private CheckBox answer2Checkbox;
        private CheckBox answer3Checkbox;
        private CheckBox answer4Checkbox;
    }

    private class ViewHolderType2 {
        private TextView mainQuestionTextView;
        private RadioGroup answerRadioGroup;
        private RadioButton answer1Radiobutton;
        private RadioButton answer2Radiobutton;
        private RadioButton answer3Radiobutton;
        private RadioButton answer4Radiobutton;
    }

    private class ViewHolderType3 {
        private TextView mainQuestionTextView;
        private ImageView answer1Image;
        private ImageView answer2Image;
        private ImageView answer3Image;
        private ImageView answer4Image;
    }

    private class ViewHolderType4 {
        private TextView mainQuestionTextView;
        private EditText answer1EditText;

    }

    // Class for keeping track of view positioning
    public class DataStateModel {
        private DataStateModel(Question question) {
            this.question = question;
        }

        private Question question;
        // Checkboxes are for type 1 questions
        private boolean checkbox1 = false;
        private boolean checkbox2 = false;
        private boolean checkbox3 = false;
        private boolean checkbox4 = false;
        // This is radioButton helper to track their id
        // For type 2 questions
        int currentRadioPos = -1;
        // Images are for Type 3 questions
        private int isImageSelected = -1;
        // EditText are for Type 4 questions
        private String edittextCaption = "";
        private int edittextId = -1;
    }

    // Getter method used to save adapters instance
    public ArrayList<DataStateModel> getmCheckStates() {
        return mCheckStates;
    }

    // Setter method used to save adapters instance
    public void setmCheckStates(ArrayList<DataStateModel> checkStates) {
        mCheckStates = checkStates;
    }

    // Shows score and displays (correct/wrong) answers
    public int countScore(int position) {
        // Updates the View
        notifyDataSetChanged();
        //region Score Count
        // Question no.1
        if (position == 0 && viewHolder != null && (mCheckStates.get(position).checkbox1 && mCheckStates.get(position).checkbox4)
                && (!mCheckStates.get(position).checkbox2 && !mCheckStates.get(position).checkbox3)) {
            score++;
        }
        // Question no.2
        if (position == 1 && viewHolderType2 != null && mCheckStates.get(position).currentRadioPos == 2) {
            score++;
        }
        // Question no.3
        if (position == 2 && viewHolderType3 != null && mCheckStates.get(position).isImageSelected == viewHolderType3.answer1Image.getId()) {
            score++;
        }
        // Question no.4
        if (position == 3 && viewHolderType4 != null && mCheckStates.get(position).edittextCaption.contentEquals("MADRID")) {
            score++;
        }
        // Question no.5
        if (position == 4 && viewHolderType2 != null && mCheckStates.get(position).currentRadioPos == 1) {
            score++;
        }
        //endregion
        //region Display correct and wrong answers
        // Question no.1
        if (viewHolder != null && position == 0) {
            viewHolder.answer1Checkbox.setTextColor(Color.GREEN);
            viewHolder.answer2Checkbox.setTextColor(Color.RED);
            viewHolder.answer3Checkbox.setTextColor(Color.RED);
            viewHolder.answer4Checkbox.setTextColor(Color.GREEN);
        }
        // Question no.2
        if (viewHolderType2 != null && position == 1) {
            viewHolderType2.answer1Radiobutton.setTextColor(Color.RED);
            viewHolderType2.answer2Radiobutton.setTextColor(Color.RED);
            viewHolderType2.answer3Radiobutton.setTextColor(Color.GREEN);
            viewHolderType2.answer4Radiobutton.setTextColor(Color.RED);
        }
        // Question no.3
        if (viewHolderType3 != null && position == 2) {
            viewHolderType3.answer1Image.setBackgroundResource(R.drawable.border_green_style);
            viewHolderType3.answer2Image.setBackgroundResource(R.drawable.border_red_style);
            viewHolderType3.answer3Image.setBackgroundResource(R.drawable.border_red_style);
            viewHolderType3.answer4Image.setBackgroundResource(R.drawable.border_red_style);
        }
        // Question no.4
        if (viewHolderType4 != null && position == 3) {
            if (viewHolderType4.answer1EditText.getText().toString().contentEquals("MADRID")) {
                viewHolderType4.answer1EditText.setTextColor(Color.GREEN);
            } else {
                viewHolderType4.answer1EditText.setTextColor(Color.RED);
            }
        }
        // Question no.5
        if (viewHolderType2 != null && position == 4) {
            viewHolderType2.answer1Radiobutton.setTextColor(Color.RED);
            viewHolderType2.answer2Radiobutton.setTextColor(Color.GREEN);
            viewHolderType2.answer3Radiobutton.setTextColor(Color.RED);
            viewHolderType2.answer4Radiobutton.setTextColor(Color.RED);
        }
        //endregion
        //region Disable ViewHolders
        // Question no.1
        if (viewHolder != null && position == 0) {
            viewHolder.answer1Checkbox.setEnabled(false);
            viewHolder.answer2Checkbox.setEnabled(false);
            viewHolder.answer3Checkbox.setEnabled(false);
            viewHolder.answer4Checkbox.setEnabled(false);
        }
        // Question no.2
        if (viewHolderType2 != null && position == 1) {
            viewHolderType2.answer1Radiobutton.setEnabled(false);
            viewHolderType2.answer2Radiobutton.setEnabled(false);
            viewHolderType2.answer3Radiobutton.setEnabled(false);
            viewHolderType2.answer4Radiobutton.setEnabled(false);
        }
        // Question no.3
        if (viewHolderType3 != null && position == 2) {
            viewHolderType3.answer1Image.setEnabled(false);
            viewHolderType3.answer2Image.setEnabled(false);
            viewHolderType3.answer3Image.setEnabled(false);
            viewHolderType3.answer4Image.setEnabled(false);
        }
        // Question no.4
        if (viewHolderType4 != null && position == 3) {
            if (viewHolderType4.answer1EditText.getText().toString().contentEquals("MADRID")) {
                viewHolderType4.answer1EditText.setEnabled(false);
            } else {
                viewHolderType4.answer1EditText.setEnabled(false);
            }
        }
        // Question no.5
        if (viewHolderType2 != null && position == 4) {
            viewHolderType2.answer1Radiobutton.setEnabled(false);
            viewHolderType2.answer2Radiobutton.setEnabled(false);
            viewHolderType2.answer3Radiobutton.setEnabled(false);
            viewHolderType2.answer4Radiobutton.setEnabled(false);
        }
        //endregion
        return score;
    }

    // Set score is used when reset_score button is pressed
    public void setScore(int score) {
        this.score = score;
    }

    // Clear Views current positions when reset_score button is pressed
    public void clearViews(int position) {
        //region Clear ViewHolders
        notifyDataSetChanged();
        // Question no.1
        if (viewHolder != null && position == 0) {
            mCheckStates.get(position).checkbox1 = false;
            mCheckStates.get(position).checkbox2 = false;
            mCheckStates.get(position).checkbox3 = false;
            mCheckStates.get(position).checkbox4 = false;
        }
        // Question no.2
        if (viewHolderType2 != null && position == 1) {
            mCheckStates.get(position).currentRadioPos = -1;
        }
        // Question no.3
        if (viewHolderType3 != null && position == 2) {
            mCheckStates.get(position).isImageSelected = -1;
        }
        // Question no.4
        if (viewHolderType4 != null && position == 3) {
            mCheckStates.get(position).edittextCaption = "";
        }
        // Question no.5
        if (viewHolderType2 != null && position == 4) {
            mCheckStates.get(position).currentRadioPos = -1;
        }
        //endregion
    }

}
