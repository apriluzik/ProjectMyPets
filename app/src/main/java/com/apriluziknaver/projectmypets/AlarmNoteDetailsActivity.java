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
    TextView saveBtn;
    TextView cancelBtn;

    ArrayAdapter<CharSequence> adCycleNum;
    ArrayAdapter<CharSequence> adCycleDays;

    Calendar calendar;
    DBHelper helper;

    String mtitle;
    String repeat;
    String repeat1;
    String totalDate ;
    int hm;
    int date;
    int theYear;
    int theMonth;
    int theDayOfMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_note_details2);
        intent = getIntent();
        setTitle("Alarm Setting");

        calendar = Calendar.getInstance(Locale.KOREA);
        theDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        theMonth = calendar.get(Calendar.MONTH);
        theYear = calendar.get(Calendar.YEAR);

        saveBtn = (TextView) findViewById(R.id.savebtn);
        cancelBtn = (TextView) findViewById(R.id.cancelbtn);
        dpDate = (TextView)findViewById(R.id.date_display);
        dpCalendar = (ImageView)findViewById(R.id.calendar);
        title = (EditText)findViewById(R.id.alarm_edit_title);


        helper=new DBHelper(this,"pets",null,1);

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
        cycle_num.setOnItemSelectedListener(selectedListener);
        cycle_days.setOnItemSelectedListener(selectedListener);


    }

//save버튼
    public void saveAlarm(View v){
        
        mtitle=title.getText().toString();
        helper.insultOrUpdateDB(mtitle,date+"",repeat+repeat1,totalDate+hm,1);


        Log.d("totalData","언제:"+hm+" 어디서:"+totalDate+" 무엇을:"+mtitle+" 어떻게:"+repeat+repeat1);

        finish();
    }

//cancel버튼
    public void cancelAlarm(View view){

        finish();

    }




//날짜
    DatePickerDialog.OnDateSetListener eDateSetListener = new DatePickerDialog.OnDateSetListener() {
        //달력에서 값얻어와서 Time변수 메소드 이용 값설정
        @Override
        public void onDateSet(DatePicker view, int year, int monthofYear, int dayOfMonth) {

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthofYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            view.updateDate(year,monthofYear,dayOfMonth);

            dpDate.setText(String.format("%04d / %02d / %02d",year,monthofYear+1,dayOfMonth));
            totalDate=String.format("%d%02d%d",year,monthofYear+1,dayOfMonth);

            Log.d("DateChange","TOTALDATE ::::"+totalDate);
            Log.d("DateChange","Listender() :"+year+" "+(monthofYear+1)+" "+dayOfMonth);


        }

    };



   AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getCount()){

            case 30:
                repeat="";
                Log.d("timeChange",repeat+=adCycleNum.getItem(i).toString());
                break;

            case 4:
                repeat1="";
                Log.d("timeChange",repeat+=adCycleNum.getItem(i).toString());
                break;
        }


       }

       @Override
       public void onNothingSelected(AdapterView<?> adapterView) {

       }
   };



    public void viewCalendar(View view){


        // TODO: 2017-08-30 calendar 로 값 받아오기 

        DatePickerDialog pickerDialog = new DatePickerDialog(this,eDateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.setTitle("DatePickerDialog");
               pickerDialog.show();

        String total = String.format("%04d / %02d / %02d",theYear,theMonth,theDayOfMonth);


        Log.d("DateChange","viewCalendar()  :"+total);


    }




}
