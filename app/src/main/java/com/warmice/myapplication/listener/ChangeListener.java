package com.warmice.myapplication.listener;

/**
 * Created by gabriel on 4/26/17.
 */
public interface ChangeListener<T> {
    void onChange(T updatedItem);
}
