package com.training.todo_list;

import android.app.Application;

import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.model.models.TodoType;

import java.util.Date;
import java.util.UUID;

public class TodoListApplication extends Application {

    /**
     * Initialize application with some dumb Todo & TodoType.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        TodoType tTypeEmergency = new TodoType("Emergency", "#b94a48", UUID.randomUUID());
        TodoType tTypeBeforeYesterday = new TodoType("Before yesterday", "#f89406", UUID.randomUUID());
        TodoType tTypeNormal = new TodoType("Normal", "#3a87ad", UUID.randomUUID());

        Todo tTodo1 = new Todo("Buy milk and eggs",
                new Date(1420106400000L), tTypeNormal.id(), true, UUID.randomUUID());
        Todo tTodo2 = new Todo("Call Superman to repair the internet",
                new Date(1443693600000L), tTypeEmergency.id(), false, UUID.randomUUID());

        TodoTypeManager tTodoTypeManager = new TodoTypeManager();
        TodoManager tTodoManager = new TodoManager();
        tTodoTypeManager.save(tTypeEmergency);
        tTodoTypeManager.save(tTypeBeforeYesterday);
        tTodoTypeManager.save(tTypeNormal);
        tTodoManager.save(tTodo1);
        tTodoManager.save(tTodo2);
    }
}
