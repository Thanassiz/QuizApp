package com.example.android.quizapp;

/**
 * @author Thanassis
 * @since 24/2/2018.
 */
public class Question {

 /*   public enum questionTypes{
        TYPE1(1), TYPE2(2), TYPE3(3);
        private int value;
        private questionTypes(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }*/

    // Main question
    private String mMainQuestion;
    // Answers for question
    private String mAnswer1;
    private String mAnswer2;
    private String mAnswer3;
    private String mAnswer4;
    // Answers for question as images
    private int mImage1;
    private int mImage2;
    private int mImage3;
    private int mImage4;
    // Helper variable to find which constructor (Question) has been called (to change display in adapter)
    private int mQuestionType = 0;

    // Question with 4 answers (RadioBoxes or CheckBoxes)
    public Question(String mainQuestion, String answer1, String answer2, String answer3, String answer4, int questionType) {
        this.mMainQuestion = mainQuestion;
        this.mAnswer1 = answer1;
        this.mAnswer2 = answer2;
        this.mAnswer3 = answer3;
        this.mAnswer4 = answer4;
        this.mQuestionType = questionType;
    }

    // Question with 4 image answers
    public Question(String mainQuestion, int image1, int image2, int image3, int image4, int questionType) {
        this.mMainQuestion = mainQuestion;
        this.mImage1 = image1;
        this.mImage2 = image2;
        this.mImage3 = image3;
        this.mImage4 = image4;
        this.mQuestionType = questionType;
    }

    // Question with a display image and 4 answers (Radio buttons)
    public Question(String mainQuestion, int image1, String answer1, String answer2, String answer3, String answer4, int questionType) {
        this.mMainQuestion = mainQuestion;
        this.mImage1 = image1;
        this.mAnswer1 = answer1;
        this.mAnswer2 = answer2;
        this.mAnswer3 = answer3;
        this.mAnswer4 = answer4;
        this.mQuestionType = questionType;
    }

    // Question with 1 answer (Edit Text)
    public Question(String mainQuestion, String answer1, int questionType) {
        this.mMainQuestion = mainQuestion;
        this.mAnswer1 = answer1;
        this.mQuestionType = questionType;
    }

    // Get main question
    public String getmMainQuestion() {
        return mMainQuestion;
    }

    // Get first answer
    public String getmAnswer1() {
        return mAnswer1;
    }

    // Set first answer (Only used in Edit Text)
    public void setmAnswer1(String answer1) {
        this.mAnswer1 = answer1;
    }

    // Get second answer
    public String getmAnswer2() {
        return mAnswer2;
    }

    // Get third answer
    public String getmAnswer3() {
        return mAnswer3;
    }

    // Get forth answer
    public String getmAnswer4() {
        return mAnswer4;
    }

    // Get first image
    public int getmImage1() {
        return mImage1;
    }

    // Get second image
    public int getmImage2() {
        return mImage2;
    }

    // Get third image
    public int getmImage3() {
        return mImage3;
    }

    // Get forth image
    public int getmImage4() {
        return mImage4;
    }

    // Get question type
    public int getmQuestionType() {
        return mQuestionType;
    }
}
