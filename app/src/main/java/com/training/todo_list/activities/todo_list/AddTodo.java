package com.training.todo_list.activities.todo_list;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.training.todo_list.R;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by stuart on 4/5/17.
 */

public class AddTodo extends Activity {

    private EditText etxtDescription;
    private TextView txtDate;
    private Spinner spnType;
    private Switch switDone;
    private Button btnAdd;

    TodoTypeManager mDataType;
    private ArrayAdapter<String> tASType;
    private int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_add);

        etxtDescription = (EditText) findViewById(R.id.etxt_description);
        txtDate = (TextView) findViewById(R.id.txt_date);
        spnType = (Spinner) findViewById(R.id.spn_type);
        switDone = (Switch) findViewById(R.id.swit_done);
        btnAdd = (Button) findViewById(R.id.btn_add);

        Log.d("ss",mDataType.all().size() + "");
        for (int i = 0; i < mDataType.all().size(); i++) {
            tASType.add(mDataType.all().get(i).name());
        }

        /*tASType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tASType);
        tASType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        spnType.setAdapter(tASType);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month  = cal.get(Calendar.MONTH);
        day  = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);

        txtDate.setText(new StringBuilder().append(year).append("/").append(month + 1).append("/").append(day).append(" ").append(hour).append(":").append(minute));
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTodo.this, dateSetListener, year, month, day).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addTodo();
            }
        });
    }

    private void addTodo() {
        Todo tTodo1 = new Todo("hhhhh", new Date(1420106400000L), UUID.randomUUID(), true, UUID.randomUUID());
        TodoManager tTodoManager = new TodoManager();
        tTodoManager.save(tTodo1);

        Intent myIntent = new Intent(AddTodo.this, ActivityTodoList.class);
        startActivity(myIntent);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            new TimePickerDialog(AddTodo.this, timeSetListener, hour, minute, true).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int min) {
            hour = hourOfDay;
            minute = min;
            txtDate.setText(new StringBuilder().append(year).append("/").append(month + 1).append("/").append(day).append(" ").append(hour).append(":").append(minute));
        }
    };
}
