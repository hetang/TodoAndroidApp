package com.air.todoapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.air.todoapp.adaptor.TodoAdaptor;
import com.air.todoapp.model.TodoItem;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddTodoActivity extends ActionBarActivity {

    private ListView lvTodoItems;
    private EditText etAddTodo;
    private TodoDBHelper mydb;

    private List<TodoItem> items;
    private TodoAdaptor todoAdapter;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        mydb = new TodoDBHelper(this);
        etAddTodo = (EditText) findViewById(R.id.etAddTodo);
        lvTodoItems = (ListView) findViewById(R.id.lvTodoItems);
        items = mydb.getAllTodoItem();
        todoAdapter = new TodoAdaptor(this, items);
        lvTodoItems.setAdapter(todoAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvTodoItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        items.remove(pos);
                        todoAdapter.notifyDataSetChanged();
                        mydb.deleteTodoItem(pos+1);
                        return true;
                    }
                }
        );

        lvTodoItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                               View item, int pos, long id) {
                        TodoItem todoItem = items.get(pos);
                        Intent i = new Intent(AddTodoActivity.this, EditTodoActivity.class);
                        i.putExtra("todoItem", todoItem.getItem());
                        i.putExtra("listPos", pos);
                        startActivityForResult(i, REQUEST_CODE);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String todoItem = data.getExtras().getString("todoItem");
            int pos = data.getExtras().getInt("listPos", -1);
            if(pos == -1) {
                items.add(new TodoItem(todoItem));
                mydb.insertTodoItem(todoItem);
            } else {
                items.set(pos, new TodoItem(todoItem));
                mydb.updateTodoItem(pos+1, todoItem);
            }
            todoAdapter.notifyDataSetChanged();
        }
    }

    public void addTodoItem(View view) {
        String todoItem = etAddTodo.getText().toString();
        todoAdapter.add(new TodoItem(todoItem));
        etAddTodo.setText("");
        mydb.insertTodoItem(todoItem);
    }
}
