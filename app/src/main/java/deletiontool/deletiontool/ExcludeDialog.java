package deletiontool.deletiontool;/*
 * *
 *  * Created by Husayn on 22/10/2021, 5:04 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 22/10/2021, 2:29 PM
 *
 */

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import deletiontool.deletiontool.premium.R;

public class ExcludeDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button insertBtn;
    public RecyclerView recyclerView;
    public MyRecyclerViewAdapter adapter;
    public EditText edit_data;

    public ExcludeDialog(Activity c, MyRecyclerViewAdapter adapter) {
        super(c);
        this.c = c;
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.exc_layout);

        edit_data = findViewById(R.id.edit_data);
        insertBtn = findViewById(R.id.insertText);
        insertBtn.setOnClickListener(this);
        recyclerView = findViewById(R.id.excludeRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        String edited = edit_data.getText().toString().trim();

        if (view.getId() == R.id.insertText) {
            if (!edited.equals("")){
                int insertIndex = 0;
                adapter.getmData().add(insertIndex, edited);
                adapter.notifyItemInserted(insertIndex);
                edit_data.getText().clear();
                Toast.makeText(c, "Successfully added.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(c, "Please insert word.", Toast.LENGTH_SHORT).show();

            }

        }

    }


    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {

    }

}
