package com.warmice.myapplication.model;

/**
 * Created by gabriel on 4/26/17.
 */
public class ToDoItem {
    public boolean isComplete;
    public String text;

    public ToDoItem(String text) {
        this.text = text;
    }

    public void toggleIsComplete() {
        isComplete = !isComplete;
    }
}
