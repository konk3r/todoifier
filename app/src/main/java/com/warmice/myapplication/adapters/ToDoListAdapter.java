package com.warmice.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warmice.myapplication.R;
import com.warmice.myapplication.dataaccess.ToDoManager;
import com.warmice.myapplication.model.ToDoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel on 4/26/17.
 */
public class ToDoListAdapter extends RecyclerView.Adapter {
    private final ToDoManager manager;
    private List<ToDoItem> list = new ArrayList<>();

    public ToDoListAdapter(ToDoManager manager) {
        this.manager = manager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_to_do, parent, false);
        return new ToDoViewHolder(manager, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ToDoViewHolder toDoHolder = (ToDoViewHolder) holder;
        ToDoItem item = getItemAtPosition(position);
        toDoHolder.bind(item);
    }

    private ToDoItem getItemAtPosition(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ToDoItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
