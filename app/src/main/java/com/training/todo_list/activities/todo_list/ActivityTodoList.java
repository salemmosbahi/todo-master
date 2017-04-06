package com.training.todo_list.activities.todo_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.training.todo_list.R;
import com.training.todo_list.adapter.TodoAdapter;
import com.training.todo_list.model.managers.TodoManager;

public class ActivityTodoList extends Activity {

    private ListView tLTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lt_act_todo_list);

        tLTodo = (ListView) findViewById(R.id.lv_todo);
        TodoAdapter mTodo = new TodoAdapter(getApplicationContext(), TodoManager.all(), ActivityTodoList.this);
        tLTodo.setAdapter(mTodo);
    }


    public void askAddTodo(View pView) {
        /*Toast.makeText(this, "Ask add todo", Toast.LENGTH_SHORT).show();*/
        Intent tINAddTodo = new Intent(ActivityTodoList.this, AddTodo.class);
        startActivity(tINAddTodo);
    }


    public void askSurprise(View pView) {
        AlertDialog.Builder tBuilder = new AlertDialog.Builder(this);
        tBuilder.setTitle(R.string.dialog_title_surprise);
        tBuilder.setMessage(R.string.dialog_message_surprise);
        tBuilder.setPositiveButton(R.string.confirm, null);
        tBuilder.show();
    }
}
