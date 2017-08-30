package com.apriluziknaver.projectmypets;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Locale;

public class AlarmNoteDetailsActivity extends AppCompatActivity {


    EditText title;


    Intent intent;
    TimePicker timePicker;
    DatePicker datePicker;
    Spinner cycle_num;
    Spinner cycle_days;
    TextView dpDate;
    ImageView dpCalendar;

    ArrayAdapter<CharSequence> adCycleNum;
    ArrayAdapter<CharSequence> adCycleDays;


    DBHelper helper;
    String mtitle;
    String repeat;
    String repeat1;
    int date;
    int hm;
    Calendar time;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_note_details2);
        intent = getIntent();
        setTitle("Alarm Setting");

        time = Calendar.getInstance();

        helper=new DBHelper(this,"pets",null,1);

        dpDate = (TextView)findViewById(R.id.date_display);
        dpCalendar = (ImageView)findViewById(R.id.calendar);
        title = (EditText)findViewById(R.id.alarm_edit_title);


        timePicker = (TimePicker)findViewById(R.id.timepicker);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hm=i*100+i1;


            }
        });




        cycle_num = (Spinner)findViewById(R.id.cycle_sipnner1);
        cycle_days = (Spinner)findViewById(R.id.cycle_sipnner2);
        adCycleNum = ArrayAdapter.createFromResource(this,R.array.spinner_cycle_num,R.layout.support_simple_spinner_dropdown_item);
        adCycleDays = ArrayAdapter.createFromResource(this,R.array.spinner_cycle_days,R.layout.support_simple_spinner_dropdown_item);

        cycle_num.setAdapter(adCycleNum);
        cycle_days.setAdapter(adCycleDays);
        cycle_num.setOnItemSelectedListener(sListener1);
        cycle_days.setOnItemSelectedListener(sListener1);


    }


    DatePickerDialog.OnDateSetListener eDateSetListener = new DatePickerDialog.OnDateSetListener() {
        //달력에서 값얻어와서 Time변수 메소드 이용 값설정
        @Override
        public void onDateSet(DatePicker view, int year, int monthofYear, int dayOfMonth) {

//            Time.set(Calendar.YEAR, year);
//            Time.set(Calendar.MONTH, monthofYear);
//            Time.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            updateLabel();

        }

    };




    public void saveAlarm(View v){
        mtitle=title.getText().toString();
        String s=String.format("%d%02d%d",datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
        helper.insultOrUpdateDB(mtitle,date+"",repeat+repeat1,s+hm,1);



        finish();
    }

    public void cancelAlarm(View view){

        finish();

    }

   AdapterView.OnItemSelectedListener sListener1 = new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getCount()){

            case 30:
                repeat="";
                repeat+=adCycleNum.getItem(i).toString();
                break;

            case 4:
                repeat1="";
                repeat1+=adCycleDays.getItem(i).toString();
                break;
        }


       }

       @Override
       public void onNothingSelected(AdapterView<?> adapterView) {

       }
   };


    AdapterView.OnItemSelectedListener sListener2 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public void viewCalendar(View view){
        CalendarView calendarView = new CalendarView(this);

        // TODO: 2017-08-30 calendar 로 값 받아오기 

//        time.get(Calendar.YEAR)
//        time.get(Calendar.MONTH)
//        time.get(Calendar.DAY_OF_MONTH)

//        new DatePickerDialog(AlarmNoteDetailsActivity.this,
//                eDateSetListener, Time.get(Calendar.YEAR), Time.get(Calendar.MONTH), Time.get(Calendar.DAY_OF_MONTH)).show();

        calendarView.setOnDateChangeListener((CalendarView.OnDateChangeListener) eDateSetListener);



    }


}
