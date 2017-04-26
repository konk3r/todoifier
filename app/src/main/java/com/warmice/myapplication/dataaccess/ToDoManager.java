package com.warmice.myapplication.dataaccess;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.warmice.myapplication.listener.ChangeListener;
import com.warmice.myapplication.model.ToDoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel on 4/26/17.
 */
public class ToDoManager {
    private static final String KEY_TO_DO_LIST = "to_do_list";

    private final SharedPreferences preferences;
    List<ToDoItem> toDoList = new ArrayList<>();
    ChangeListener<List<ToDoItem>> changeListener;

    public ToDoManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void add(ToDoItem toDoItem) {
        toDoList.add(toDoItem);
        notifyChange();
        storeChanges();
    }

    public List<ToDoItem> getList() {
        return toDoList;
    }

    public void delete(ToDoItem item) {
        toDoList.remove(item);
        notifyChange();
        storeChanges();
    }

    private void notifyChange() {
        if (changeListener != null) {
            changeListener.onChange(toDoList);
        }
    }

    public void setChangeListener(ChangeListener<List<ToDoItem>> listener) {
        this.changeListener = listener;
    }

    private void storeChanges() {
        String json = new Gson().toJson(toDoList);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_TO_DO_LIST, json);
        editor.apply();
    }

    public void load() {
        String json = preferences.getString(KEY_TO_DO_LIST, null);
        if (json == null) {
            return;
        }

        toDoList = new Gson().fromJson(json, new TypeToken<List<ToDoItem>>(){}.getType());
        notifyChange();
    }

    public void notifyDataChange() {
        notifyChange();
        storeChanges();
    }
}
