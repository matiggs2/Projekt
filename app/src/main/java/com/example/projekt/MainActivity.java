package com.example.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.projekt.MESSAGE";
    CalendarView calendarView;
    TextView txtdate;
    Button btnNotes;
    //metoda otwierająca aktywność z dodawaniem notatek i przekazanie wybranej daty
    public void OpenCreateNote (View view)
    {
        Intent intent = new Intent(this, CreateNote.class);
        TextView txt = (TextView) findViewById(R.id.date);
        String message = txt.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotes = (Button) findViewById(R.id.notes);

        //kalendarz
        calendarView = (CalendarView) findViewById(R.id.calendar);
        txtdate = (TextView) findViewById(R.id.date);

        //zmiana zawartości mark date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                String data = dayOfMonth + "/" + (month+1) + "/" + year;
                txtdate.setText(data);
            }
        });
        //otwarcie aktywności z listą notek
        btnNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListNotes.class);
                startActivity(intent);
            }
        });
    }

}
