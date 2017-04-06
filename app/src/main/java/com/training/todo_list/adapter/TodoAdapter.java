package com.training.todo_list.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.training.todo_list.R;
import com.training.todo_list.activities.todo_list.AddTodo;
import com.training.todo_list.model.models.Todo;

import java.util.List;

/**
 * Created by stuart on 4/4/17.
 */

public class TodoAdapter extends BaseAdapter {

    LayoutInflater tLIinflater;

    Context tCcontext;
    List<Todo> mDataTodo;
    Activity tAact;

    public TodoAdapter(Context pCcontext, List<Todo> pDataTodo, Activity pAact) {
        this.tCcontext = pCcontext;
        this.mDataTodo = pDataTodo;
        this.tAact = pAact;
    }

    @Override
    public int getCount() { return mDataTodo.size(); }

    @Override
    public Object getItem(int pIi) { return mDataTodo.get(pIi); }

    @Override
    public long getItemId(int pIi) {
        return mDataTodo.indexOf(getItem(pIi));
    }

    @Override
    public View getView(final int pIi, View pVview, ViewGroup pVGview) {
        View rVview = pVview;
        TodoHolder mHolder = new TodoHolder();
        if (rVview == null) {
            tLIinflater = (LayoutInflater) tCcontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            rVview = tLIinflater.inflate(R.layout.lt_act_todo_content, null);
            mHolder.txtDescription = (TextView) rVview.findViewById(R.id.txt_description);
            mHolder.txtDone = (TextView) rVview.findViewById(R.id.txt_done);
            mHolder.txtDate = (TextView) rVview.findViewById(R.id.txt_date);
            mHolder.rlRow = (RelativeLayout) rVview.findViewById(R.id.rl_row);
            rVview.setTag(mHolder);
        } else {
            mHolder = (TodoHolder) rVview.getTag();
        }

        mHolder.txtDescription.setText(mDataTodo.get(pIi).description());
        mHolder.txtDone.setText(mDataTodo.get(pIi).isDone() + "");
        mHolder.txtDate.setText(mDataTodo.get(pIi).timeCreation() + "");

        mHolder.rlRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                Intent tINAddTodo = new Intent(tCcontext, AddTodo.class);
                tINAddTodo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                tINAddTodo.putExtra("id", mDataTodo.get(pIi).id() + "");
                tCcontext.startActivity(tINAddTodo);
            }
        });
        return rVview;
    }

    class TodoHolder {
        TextView txtDescription, txtDone, txtDate;
        RelativeLayout rlRow;
    }
}
