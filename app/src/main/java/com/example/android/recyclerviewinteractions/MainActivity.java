package com.example.android.recyclerviewinteractions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayDeque;

public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTodoCheckedChangeListener {

    // The layout element representing our list of TODOs.
    private RecyclerView mTodoListRecyclerView;

    // The layout element representing the text entry box where the user enters a TODO.
    private EditText mTodoEntryEditText;

    // A list of all the TODOs the user has entered (we'll represent them as an adapter).
    private TodoAdapter mTodoAdapter;

    private Toast mTodoToast;

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

        mTodoListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mTodoToast = null;

        mTodoAdapter = new TodoAdapter(this);
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
                    mTodoListRecyclerView.scrollToPosition(0);
                    mTodoAdapter.addTodo(todoText);
                    mTodoEntryEditText.setText("");
                }
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                ((TodoAdapter.TodoViewHolder)viewHolder).removeFromList();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mTodoListRecyclerView);
    }

    @Override
    public void onTodoCheckedChange(String todo, boolean isChecked) {
        if (mTodoToast != null) {
            mTodoToast.cancel();
        }
        String statusMessage = isChecked ? "COMPLETED" : "MARKED INCOMPLETE";
        mTodoToast = Toast.makeText(this, statusMessage + ": " + todo, Toast.LENGTH_LONG);
        mTodoToast.show();
    }
}
