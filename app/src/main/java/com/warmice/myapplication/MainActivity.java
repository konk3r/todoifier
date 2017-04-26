package com.warmice.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.warmice.myapplication.adapters.ToDoListAdapter;
import com.warmice.myapplication.dataaccess.ToDoManager;
import com.warmice.myapplication.model.ToDoItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private RecyclerView list;
    private ToDoManager toDoManager;
    private ToDoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDataAccessObjects();
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void createDataAccessObjects() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        toDoManager = new ToDoManager(sharedPrefs);
        toDoManager.setChangeListener(list -> updateList(list));
    }

    private void updateList(List<ToDoItem> list) {
        if (adapter != null) {
            adapter.setList(list);
        }
    }

    private void setupViews() {
        findViews();
        setSupportActionBar(toolbar);
        createList();
        fab.setOnClickListener(view -> addNewItem());
    }

    private void createList() {
        adapter = new ToDoListAdapter(toDoManager);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        toDoManager.load();
    }

    private void addNewItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_to_do, null);
        final EditText input = (EditText) view.findViewById(R.id.dialog_to_do_input_text);

        builder.setView(view)
                .setTitle(R.string.add_item_title)
                .setPositiveButton(R.string.add_item_button, (dialog, which) -> {
                    String text = input.getText().toString();
                    toDoManager.add(new ToDoItem(text));
                    dialog.dismiss();
                })
        .setNegativeButton(R.string.cancel_item_button, (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        list = (RecyclerView) findViewById(R.id.list);
    }
}
