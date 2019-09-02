package com.example.goalseta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    private Context context;
    private List<Task> taskList;

    public TasksAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task task = taskList.get(position);

        holder.textViewTask.setText(task.getTask());
        holder.textViewDesc.setText(task.getDescription());
        holder.textViewDueTime.setText(task.getDueDate());
        if (task.isCompleted()){
            holder.textViewStatus.setText("Completed");
            holder.textViewStatus.setBackgroundColor(Color.parseColor("#356f19"));
        } else {
            holder.textViewStatus.setText("Not Completed");
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewTask, textViewDesc, textViewDueTime, textViewStatus;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewDueTime = itemView.findViewById(R.id.textViewFinishBy);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Task task = taskList.get(getAdapterPosition());

            Intent intent = new Intent(context, UpdateTaskActivity.class);
            intent.putExtra("task", task);

            context.startActivity(intent);
        }
    }
}
