package com.example.android.recyclerviewinteractions;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by hessro on 4/18/17.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private ArrayList<String> mTodoList;
    private OnTodoCheckedChangeListener mCheckedChangeListener;

    public TodoAdapter(OnTodoCheckedChangeListener checkChangedListener) {
        mTodoList = new ArrayList<String>();
        mCheckedChangeListener = checkChangedListener;
    }

    public void addTodo(String todo) {
        mTodoList.add(todo);
//        notifyDataSetChanged();
        notifyItemInserted(0);
    }

    private int adapterPositionToArrayIndex(int adapterPosition) {
        return mTodoList.size() - adapterPosition - 1;
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.todo_list_item, parent, false);
        TodoViewHolder viewHolder = new TodoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        String todo = mTodoList.get(adapterPositionToArrayIndex(position));
        holder.bind(todo);
    }

    public interface OnTodoCheckedChangeListener {
        void onTodoCheckedChange(String todo, boolean isChecked);
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        private TextView mTodoTextView;

        public TodoViewHolder(final View itemView) {
            super(itemView);
            mTodoTextView = (TextView)itemView.findViewById(R.id.tv_todo_text);

            CheckBox checkBox = (CheckBox)itemView.findViewById(R.id.todo_checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String todo = mTodoList.get(adapterPositionToArrayIndex(getAdapterPosition()));
                    mCheckedChangeListener.onTodoCheckedChange(todo, isChecked);
                }
            });
        }

        public void removeFromList() {
            int position = getAdapterPosition();
            mTodoList.remove(adapterPositionToArrayIndex(position));
            notifyItemRemoved(position);
        }

        public void bind(String todo) {
            mTodoTextView.setText(todo);
        }
    }
}
