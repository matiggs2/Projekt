package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditNotes extends AppCompatActivity {

    private static final String TAG = "EditNotes";

    private Button btnSave, btnDelete;
    private EditText editable_item;

    DatabaseHelper mDatabaseHelper;

    private String selectedNote;
    private int selectedID;

    Animation animFade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_notes);

        btnSave = (Button) findViewById(R.id.save);
        btnDelete = (Button) findViewById(R.id.delete);
        editable_item = (EditText) findViewById(R.id.editable_item);
        mDatabaseHelper = new DatabaseHelper(this);

        animFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);

        //pobiera extra intent z klasy ListNotes
        Intent receivedIntent = getIntent();

        //teraz pobiera itemID podany jako extra
        selectedID =receivedIntent.getIntExtra("id",-1);

        //i to samo tylko z notatką
        selectedNote =receivedIntent.getStringExtra("note");

        //ustawia tekst pokazując wybraną notatkę
        editable_item.setText(selectedNote);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                if(!item.equals("")){
                    mDatabaseHelper.updateNote(item, selectedID, selectedNote);
                    btnSave.startAnimation(animFade);
                }else{
                    toastMessage("Enter some note");
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteNote(selectedID,selectedNote);
                editable_item.setText("");
                btnDelete.startAnimation(animFade);
                toastMessage("removed from database");
            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
