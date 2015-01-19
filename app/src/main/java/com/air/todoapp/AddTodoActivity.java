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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddTodoActivity extends ActionBarActivity {

    private ListView lvTodoItems;
    private EditText etAddTodo;
    private TodoDBHelper mydb;

    private List<String> items;
    private ArrayAdapter<String> itemsAdaptor;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        mydb = new TodoDBHelper(this);
        etAddTodo = (EditText) findViewById(R.id.etAddTodo);
        lvTodoItems = (ListView) findViewById(R.id.lvTodoItems);
        items = mydb.getAllTodoItem();
        //readItems();
        itemsAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvTodoItems.setAdapter(itemsAdaptor);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvTodoItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdaptor.notifyDataSetChanged();
                        mydb.deleteTodoItem(pos+1);
                        //writeItems();
                        return true;
                    }
                }
        );

        lvTodoItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                               View item, int pos, long id) {
                        String todoItem = items.get(pos);
                        Intent i = new Intent(AddTodoActivity.this, EditTodoActivity.class);
                        i.putExtra("todoItem", todoItem);
                        i.putExtra("listPos", pos);
                        startActivityForResult(i, REQUEST_CODE);
                    }
                }
        );
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch(IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        }catch(IOException e) {
            e.printStackTrace();
        }
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
                items.add(todoItem);
                mydb.insertTodoItem(todoItem);
            } else {
                items.set(pos, todoItem);
                mydb.updateTodoItem(pos+1, todoItem);
            }
            itemsAdaptor.notifyDataSetChanged();
            //writeItems();
        }
    }

    public void addTodoItem(View view) {
        String todoItem = etAddTodo.getText().toString();
        itemsAdaptor.add(todoItem);
        etAddTodo.setText("");
        mydb.insertTodoItem(todoItem);
        //writeItems();
    }
}
