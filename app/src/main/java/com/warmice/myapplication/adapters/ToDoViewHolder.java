package com.warmice.myapplication.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.warmice.myapplication.R;
import com.warmice.myapplication.dataaccess.ToDoManager;
import com.warmice.myapplication.model.ToDoItem;

/**
 * Created by gabriel on 4/26/17.
 */
class ToDoViewHolder extends RecyclerView.ViewHolder {
    private final ToDoManager toDoManager;
    private final View finishedImage;
    private final TextView name;
    private ToDoItem item;

    public ToDoViewHolder(ToDoManager toDoManager, View itemView) {
        super(itemView);
        this.toDoManager = toDoManager;
        setClickListeners();
        finishedImage = itemView.findViewById(R.id.list_item_to_do_is_finished);
        name = (TextView) itemView.findViewById(R.id.list_item_to_do_name);
    }

    private void setClickListeners() {
        itemView.setOnClickListener(view -> toggleCompletedState());
        itemView.setOnLongClickListener(v -> displayOptions());
    }

    private void toggleCompletedState() {
        item.toggleIsComplete();
        toDoManager.notifyDataChange();
    }

    private boolean displayOptions() {
        Context context = itemView.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_to_do, null);
        final EditText input = (EditText) view.findViewById(R.id.dialog_to_do_input_text);
        input.setText(item.text);

        builder.setView(view)
                .setTitle(R.string.add_item_title)
                .setPositiveButton(R.string.update_item_button, (dialog, which) -> {
                    item.text = input.getText().toString();
                    toDoManager.notifyDataChange();
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.delete_item_button, (dialog, which) -> {
                    toDoManager.delete(item);
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        return true;
    }

    public void bind(ToDoItem item) {
        this.item = item;
        if (this.item.isComplete) {
            finishedImage.setVisibility(View.VISIBLE);
        } else {
            finishedImage.setVisibility(View.GONE);
        }

        name.setText(item.text);
    }
}
