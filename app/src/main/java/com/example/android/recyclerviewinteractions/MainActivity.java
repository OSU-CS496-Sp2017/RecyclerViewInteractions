package com.example.android.recyclerviewinteractions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayDeque;

public class MainActivity extends AppCompatActivity {

    // The layout element representing our list of TODOs.
    private RecyclerView mTodoListRecyclerView;

    // The layout element representing the text entry box where the user enters a TODO.
    private EditText mTodoEntryEditText;

    // A list of all the TODOs the user has entered (we'll represent them as an adapter).
    private TodoAdapter mTodoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Use IDs to grab references to the layout items for the TextView representing our TODO
         * list and the EditText representing the TODO text entry box.
         */
        mTodoEntryEditText = (EditText)findViewById(R.id.et_todo_entry_box);
        mTodoListRecyclerView = (RecyclerView)findViewById(R.id.rv_todo_list);

        mTodoListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTodoListRecyclerView.setHasFixedSize(true);

        mTodoAdapter = new TodoAdapter();
        mTodoListRecyclerView.setAdapter(mTodoAdapter);

        // Use ID to grab a reference to the layout element for the button to add a TODO.
        Button addTodoButton = (Button)findViewById(R.id.btn_add_todo);

        /*
         * Create an anonymous class implementing the View.OnClickListener interface to handle
         * clicks on the button to add TODOs.  The onClick() method is implemented to describe
         * how to respond to clicks on the button.
         */
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Extract the text the user entered into the text entry box.
                String todoText = mTodoEntryEditText.getText().toString();

                /*
                 * If the user-entered text is not empty, push it onto the stack of TODO strings
                 * and then display all the TODOs on the stack within our TextView in order from
                 * most-recently added to least-recently added.  Once all of the TODOs are
                 * displayed, clear the text in the text entry box.
                 */
                if (!TextUtils.isEmpty(todoText)) {
                    mTodoAdapter.addTodo(todoText);
                    mTodoEntryEditText.setText("");
                }
            }
        });
    }
}
