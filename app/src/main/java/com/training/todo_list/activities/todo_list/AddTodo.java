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

import com.training.todo_list.R;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.model.models.TodoType;

import java.util.Calendar;
import java.util.Date;
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

    TodoManager mDataTodo;
    TodoTypeManager mDataType;
    private String[] tSAType = new String[3];
    private ArrayAdapter<String> tASType;
    private int tIYear, tIMonth, tIDay, tIHour, tIMinute;
    private String tSId = "";

    private String mSDescription, mNameTodoType;
    private Date mTimeCreation;
    private boolean mBIsDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_add);

        etxtDescription = (EditText) findViewById(R.id.etxt_description);
        txtDate = (TextView) findViewById(R.id.txt_date);
        spnType = (Spinner) findViewById(R.id.spn_type);
        switDone = (Switch) findViewById(R.id.swit_done);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setText("Add");

        Intent tINAddTodo = getIntent();
        Bundle tBDExtras = tINAddTodo.getExtras();
        if (tBDExtras != null) {
            tSId = (String) tBDExtras.get("id");
        }

        for (int i = 0; i < mDataType.all().size(); i++) {
            tSAType[i] = mDataType.all().get(i).name();
        }

        tASType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tSAType);
        tASType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnType.setAdapter(tASType);

        Calendar tCDCalend = Calendar.getInstance();
        tIYear = tCDCalend.get(Calendar.YEAR);
        tIMonth  = tCDCalend.get(Calendar.MONTH);
        tIDay  = tCDCalend.get(Calendar.DAY_OF_MONTH);
        tIHour = tCDCalend.get(Calendar.HOUR_OF_DAY);
        tIMinute = tCDCalend.get(Calendar.MINUTE);

        txtDate.setText(new StringBuilder().append(tIYear).append("/").append(tIMonth + 1).append("/").append(tIDay).append(" ").append(tIHour).append(":").append(tIMinute));
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTodo.this, dateSetListener, tIYear, tIMonth, tIDay).show();
            }
        });

        if (!tSId.equals("")) {

            btnAdd.setText("Edit");

            Todo mTodo = mDataTodo.todoFor(UUID.fromString(tSId));
            mSDescription = mTodo.description();
            mTimeCreation = mTodo.timeCreation();
            TodoType mTodoType = mDataType.todoTypeFor(mTodo.idTodoType());
            mNameTodoType = mTodoType.name();
            mBIsDone = mTodo.isDone();

            etxtDescription.setText(mSDescription);
            txtDate.setText(mTimeCreation + "");
            spnType.setSelection(tASType.getPosition(mNameTodoType));
            switDone.setChecked(mBIsDone);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!tSId.equals("")) {
                    editTodo(UUID.fromString(tSId));
                } else {
                    addTodo();
                }
            }
        });
    }

    private void editTodo(UUID pUid) {

        UUID tUUIdType = UUID.randomUUID();
        TodoType mTodoType = mDataType.todoTypeForName(spnType.getSelectedItem().toString());
        tUUIdType = mTodoType.id();

        if (!etxtDescription.getText().equals("")) {
            Todo mEditTodo = new TodoManager().todoFor(pUid);
            mEditTodo.setmSDescription(etxtDescription.getText().toString());
            mEditTodo.setmTimeCreation(new Date(txtDate.getText().toString()));
            mEditTodo.setmIdTodoType(tUUIdType);
            mEditTodo.setmBIsDone(switDone.isChecked());

            Intent tINListTodo = new Intent(AddTodo.this, ActivityTodoList.class);
            startActivity(tINListTodo);
        }
    }

    private void addTodo() {

        UUID tUUIdType = UUID.randomUUID();
        TodoType mTodoType = mDataType.todoTypeForName(spnType.getSelectedItem().toString());
        tUUIdType = mTodoType.id();

        if (!etxtDescription.getText().equals("")) {
            Todo mTodo = new Todo(etxtDescription.getText().toString(), new Date(txtDate.getText().toString()), tUUIdType, switDone.isChecked(), UUID.randomUUID());
            TodoManager mTodoManager = new TodoManager();
            mTodoManager.save(mTodo);

            Intent tINListTodo = new Intent(AddTodo.this, ActivityTodoList.class);
            startActivity(tINListTodo);
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker pView, int pYear, int pMonth, int pDay) {
            tIYear = pYear;
            tIMonth = pMonth;
            tIDay = pDay;
            new TimePickerDialog(AddTodo.this, timeSetListener, tIHour, tIMinute, true).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker pView, int pHour, int pMinute) {
            tIHour = pHour;
            tIMinute = pMinute;
            txtDate.setText(new StringBuilder().append(tIYear).append("/").append(tIMonth + 1).append("/").append(tIDay).append(" ").append(tIHour).append(":").append(tIMinute));
        }
    };
}
