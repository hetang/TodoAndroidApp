package com.air.todoapp.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.air.todoapp.R;
import com.air.todoapp.model.TodoItem;

import java.util.List;

/**
 * Created by hetashah on 1/20/15.
 */
public class TodoAdaptor extends ArrayAdapter<TodoItem> {

    public TodoAdaptor(Context context, List<TodoItem> todoItems) {
        super(context, 0, todoItems);
    }

    public void removeTodoItem(int pos) {
        remove(getItem(pos));
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }

        TextView tvTodoItem = (TextView) convertView.findViewById(R.id.tvTodoItem);
        tvTodoItem.setText(item.getItem());

        return convertView;
    }
}
