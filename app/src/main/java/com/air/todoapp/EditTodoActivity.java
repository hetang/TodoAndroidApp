package com.air.todoapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditTodoActivity extends ActionBarActivity {

    private EditText etEditTodoItem;
    private int listPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        setTitle(getResources().getString(R.string.edit_view_title));
        String todoItem = getIntent().getStringExtra("todoItem");
        listPos = getIntent().getIntExtra("listPos", 0);

        etEditTodoItem = (EditText) findViewById(R.id.etEditTodoItem);
        etEditTodoItem.setText(todoItem);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_todo, menu);
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

    public void saveItem(View view) {
        Intent data = new Intent();
        data.putExtra("todoItem", etEditTodoItem.getText().toString());
        data.putExtra("listPos", listPos);
        setResult(RESULT_OK, data);
        finish();
    }
}
