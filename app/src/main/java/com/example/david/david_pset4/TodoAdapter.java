
package com.example.david.david_pset4;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class TodoAdapter extends ResourceCursorAdapter {

    public TodoAdapter(Context context, Cursor cursor) {

        super(context, R.layout.row_todo, cursor, 0);
        System.out.println("askjdalsd");

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        System.out.println("heloooooooo");
        TextView entryText = view.findViewById(R.id.entryText);
        CheckBox completed = view.findViewById(R.id.checkBox);
        Integer title_id = cursor.getColumnIndex("title");
        Integer completed_id = cursor.getColumnIndex("completed");

        String title_val = cursor.getString(title_id);
        Integer completed_val = cursor.getInt(completed_id);

        entryText.setText(title_val);

        if (completed_val == 0){
            completed.setChecked(false);
        } else {
            completed.setChecked(true);
        }
    }
}
