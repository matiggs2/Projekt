package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateNote extends AppCompatActivity {

    private static final String TAG = "CreateNote";

    DatabaseHelper mDatabaseHelper;
    private Button btnCreate, btnNotes;
    private EditText editText;

    Animation animRotate;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.create_note);
            //dla animacji
            btnCreate = (Button) findViewById(R.id.create);
            animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

            // Pobranie i wyświetlenie wiadomości od intencji
        Intent intent = getIntent();
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.txtdate);
        textView.setText(message);

        editText= (EditText) findViewById(R.id.note);
        btnCreate = (Button) findViewById(R.id.create);
        btnNotes = (Button) findViewById(R.id.notes);
        mDatabaseHelper = new DatabaseHelper(this);

        //stworzenie rekordu z dodaniem daty
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = message + " " + editText.getText().toString();
                if (editText.length() != 0) {
                    AddData(newEntry);
                    btnCreate.startAnimation(animRotate);
                    editText.setText("");
                } else {
                    toastMessage("Write some note");
                }
            }
        });
        //otwarcie aktywności z listą notek
        btnNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View v) {
                Intent intent = new Intent(CreateNote.this, ListNotes.class);
                startActivity(intent);
            }
        });
        }

    public void AddData(String newEntry){
        boolean insertData = mDatabaseHelper.addData(newEntry); //tworzony jest boolean ponieważ w klasie DatabaseHelper jest on zwracany
        if (insertData) {
            toastMessage("Data inserted");
        } else {
            toastMessage("Something went wrong");
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
