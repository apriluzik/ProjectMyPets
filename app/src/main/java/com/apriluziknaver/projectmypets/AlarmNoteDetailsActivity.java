package com.apriluziknaver.projectmypets;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    String name;
    String mtitle;
    String repeat;
    String repeat1;
    String totalDate ;
    String time;
    String hm;
    String date;
    int theYear;
    int theMonth;
    int theDayOfMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_note_setting);
        intent = getIntent();
        setTitle("Alarm Setting");
        name=intent.getStringExtra("ProfileName");

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
        date=new SimpleDateFormat("yyyyMMdd").format(new Date());

        timePicker = (TimePicker)findViewById(R.id.timepicker);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hm=String.format("%02d%02d",i,i1);
            }
        });





        dpDate.setText(new SimpleDateFormat("yyyy년MMMd일").format(new Date()));


        cycle_num = (Spinner)findViewById(R.id.cycle_sipnner1);
        cycle_days = (Spinner)findViewById(R.id.cycle_sipnner2);
        adCycleNum = ArrayAdapter.createFromResource(this,R.array.spinner_cycle_num,R.layout.support_simple_spinner_dropdown_item);
        adCycleDays = ArrayAdapter.createFromResource(this,R.array.spinner_cycle_days,R.layout.support_simple_spinner_dropdown_item);

        cycle_num.setAdapter(adCycleNum);
        cycle_days.setAdapter(adCycleDays);
        cycle_num.setOnItemSelectedListener(selectedListener);
        cycle_days.setOnItemSelectedListener(selectedListener);



        updateAlarm();


    }


    //알람수정
    public void updateAlarm(){
        if(intent.getParcelableExtra("item")==null)return;
        AlarmItem item=intent.getParcelableExtra("item");
        title.setText(item.alarmtitle.toString());


        timePicker.setHour(Integer.parseInt(item.alarmTime.substring(0,2)));
        timePicker.setMinute(Integer.parseInt(item.alarmTime.substring(2,4)));

        item.alarmdate.substring(3,5);

        int y=Integer.parseInt(item.alarmdate.substring(0,4));
        int m=Integer.parseInt(item.alarmdate.substring(4,6));
        int d=Integer.parseInt(item.alarmdate.substring(6,8));
        dpDate.setText(String.format("%04d / %02d / %02d",y,m,d));



//        item.alarmCycle.
//
//
//
//
//
//
//
//        Log.i("asd",item.alarmCycle);



    }

//알람저장
    public void saveAlarm(View v){

        mtitle=title.getText().toString();



//        intent.putExtra("Title",mtitle);
//        intent.putExtra("Date",date+"");
//        intent.putExtra("Time",hm+"");
//        intent.putExtra("Cycles",repeat+repeat1+"");

        AlarmItem item = new AlarmItem();

        if(mtitle.equals("")){
            mtitle=" Alarm ";
            item.alarmtitle=mtitle;
        }else {
            item.alarmtitle=mtitle;
        }

        if(hm==null){
            time =  new SimpleDateFormat("a hh:mm").format(new Date());
            item.alarmTime = time;

        }else { item.alarmTime = hm + ""; }

        if(totalDate==null){

            String da = new SimpleDateFormat("yyyy/MMM/d").format(new Date());
            item.alarmdate= da;

        }else{ item.alarmdate = totalDate;}


        item.alarmtitle = mtitle;
        item.alarmCycle = repeat + repeat1 + "";

        intent.putExtra("Alarm",item);
        setResult(RESULT_OK,intent);

        MyLog(item);



        hm=String.format("%02d%02d",timePicker.getHour(),timePicker.getMinute());



        Log.i("asd",mtitle+"     "+date+"    "+ repeat+repeat1+"     "+hm+"     ");

        Intent intent2= getIntent();

        if(intent2.getBooleanExtra("isadd",false)) {
            helper.UpdateDB(((AlarmItem)intent.getParcelableExtra("item")).id,mtitle,date,repeat+repeat1,hm+"",1,name);
        }

        else  helper.insultDB(mtitle,date,repeat+repeat1,hm+"",1,name);







        finish();
    }

//cancel버튼
    public void cancelAlarm(View view){ finish(); }




//날짜
    DatePickerDialog.OnDateSetListener eDateSetListener = new DatePickerDialog.OnDateSetListener() {
        //달력에서 값얻어와서 Time변수 메소드 이용 값설정
        @Override
        public void onDateSet(DatePicker view, int year, int monthofYear, int dayOfMonth) {

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthofYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            Log.i("aaaa",year+""+monthofYear+dayOfMonth);



            dpDate.setText(String.format("%04d / %02d / %02d",year,monthofYear+1,dayOfMonth));

            date=String.format("%d%02d%02d",year,monthofYear+1,dayOfMonth);
            totalDate=String.format("%d%02d%02d",year,monthofYear+1,dayOfMonth);

            dpDate.setText(totalDate);


        }

    };



   AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getCount()){

            case 30:
                repeat="";
                repeat=adCycleNum.getItem(i).toString();
                break;

            case 4:
                repeat1="";
                repeat1=adCycleDays.getItem(i).toString();
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

        String total = String.format("%04d%02d%02d",theYear,theMonth,theDayOfMonth);


        Log.d("DateChange","viewCalendar()  :"+total);


    }

    public void MyLog(AlarmItem item){
    Log.d("MyLog","언제:"+hm+" 어디서:"+totalDate+
            " 무엇을:"+mtitle+" 어떻게:"+repeat+repeat1);
    Log.d("MyLog","언제:"+item.alarmTime+" 어디서:"+item.alarmdate+
            " 무엇을:"+item.alarmtitle+" 어떻게:"+item.alarmCycle);

}

    public Calendar myAlram(String s,String repeat,String time){
        Calendar t= Calendar.getInstance();



        int year;
        int month;
        int day;
        int hour;
        int minute;

        year=Integer.parseInt(s.substring(0,4));
        month=Integer.parseInt(s.substring(4,6));
        day=Integer.parseInt(s.substring(6,8));

        hour=Integer.parseInt(time.substring(0,2));
        minute=Integer.parseInt(time.substring(2,4));



        t.set(Calendar.YEAR,year);
        t.set(Calendar.MONTH,month);
        t.set(Calendar.DAY_OF_MONTH,day);
        t.set(Calendar.HOUR_OF_DAY,hour);
        t.set(Calendar.MINUTE,minute);
        t.set(Calendar.SECOND,0);





        //20170912
        //1일
        //1645








        return t;

    }


}
