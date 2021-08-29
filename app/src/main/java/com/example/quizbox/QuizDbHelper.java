package com.example.quizbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.quizbox.QuizContract.*;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz,db";
    private static final int DATABASE_VERSION = 2;

    private SQLiteDatabase db;

    public QuizDbHelper(@Nullable Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE ="CREATE TABLE "+ QuizContract.QuestionsTable.TABLE_NAME + " ( "
                + QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);

    }
    private void fillQuestionsTable() {
        Question q1 = new Question("Easy: WWW stands for ", "World Wide Web", "Web World Wide",
                "Wide World Web", 1, Question.DIFFICULTY_EASY);
        addQuestion(q1);
        Question q2 = new Question("Easy: Which of the following are components of Central Processing Unit (CPU) ?",
                "Arithmetic logic unit, Mouse", "Arithmetic logic unit, Control unit", "Control Unit, Monitor",
                2, Question.DIFFICULTY_EASY);
        addQuestion(q2);
        Question q3 = new Question("Easy: Where is RAM located ?", "Expansion Board", "External Drive",
                "Mother Board", 3, Question.DIFFICULTY_EASY);
        addQuestion(q3);
        Question q4 = new Question("Easy: Full form of URL is ?",
                "Uniform Resource Locator", "Uniform Resource Link", "Uniform Registered Link",
                1, Question.DIFFICULTY_EASY);
        addQuestion(q4);
        Question q5 = new Question("Easy:If a computer provides database services to other, then it will be known as ?",
                "Web server", "Database server", "Application server", 2, Question.DIFFICULTY_EASY);
        addQuestion(q5);
        Question q6 = new Question("Medium: Name of the screen that recognizes touch input is :",
                "Recog screen", "Point Screen", "Touch Screen", 3, Question.DIFFICULTY_MEDIUM);
        addQuestion(q6);
        Question q7 = new Question("Medium: Identify the device through which data and instructions are entered into a computer",
                "Output device", "Software", "Input device",
                3, Question.DIFFICULTY_MEDIUM);
        addQuestion(q7);
        Question q8 = new Question("Medium: Computer Moniter is also known as :",
                "VDU", "UVD", "DVUB", 1,
                Question.DIFFICULTY_MEDIUM);
        addQuestion(q8);
        Question q9 = new Question("Medium: Arrange in ascending order the units of memory TB, KB, GB, MB",
                "MB>GB>TB>KB", "TB>GB>MB>KB", "TB>MB>GB>KB", 2,
                Question.DIFFICULTY_MEDIUM);
        addQuestion(q9);
        Question q10 = new Question("Medium: Which one of these stores more data than a DVD ?",
                "Floppy", "Blue Ray Disk", "CD Rom", 2,
                Question.DIFFICULTY_MEDIUM);
        addQuestion(q10);
        Question q11 = new Question("Hard: The output shown on the computer monitor is called",
                "VDU", "Soft Copy", "Hard Copy", 2,
                Question.DIFFICULTY_HARD);
        addQuestion(q11);
        Question q12 = new Question("Hard: Eight Bits make up a",
                "Megabyte", "Byte", "None", 2,
                Question.DIFFICULTY_HARD);
        addQuestion(q12);
        Question q13 = new Question("Hard: Which one is the result of the output given by a computer ?",
                "Istruction", "Information", "Data", 2,
                Question.DIFFICULTY_HARD);
        addQuestion(q13);
        Question q14 = new Question("Hard: Which one of these also known as read/write memory ?",
                "Floppy", "RAM", "CD Rom", 2,
                Question.DIFFICULTY_HARD);
        addQuestion(q14);
        Question q15 = new Question("Hard: The printed output from a computer is called",
                "Floppy", "Hard Copy", "CD Rom", 2,
                Question.DIFFICULTY_HARD);
        addQuestion(q15);
    }
    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions()
    {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
