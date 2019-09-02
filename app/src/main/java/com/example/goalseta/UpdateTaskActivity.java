package com.example.goalseta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Delete;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateTaskActivity extends AppCompatActivity {

    private EditText editTextTask, editTextDesc, editTextDueTime;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        editTextTask = findViewById(R.id.update_task);
        editTextDesc = findViewById(R.id.update_Desc);
        editTextDueTime = findViewById(R.id.update_due_date);
        checkBox = findViewById(R.id.checkBoxFinished);

        final Task task = (Task) getIntent().getSerializableExtra("task");
        loadTask(task);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setCompleted(checkBox.isChecked());
                updateTask(task);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context;
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTask(task);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void deleteTask(final Task task) {
        class DeleteTask extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext())
                        .getDatabase()
                        .taskDao()
                        .delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
            }
        }
        DeleteTask deleteTask = new DeleteTask();
        deleteTask.execute();
    }

    private void updateTask(final Task task) {

        final String updateTask = editTextTask.getText().toString().trim();
        final String updtateDesc = editTextDesc.getText().toString().trim();
        final String updateDueDate = editTextDueTime.getText().toString().trim();

        if (updateTask.isEmpty()) {
            editTextTask.setError("Task required");
            editTextTask.requestFocus();
            return;
        }

        if (updtateDesc.isEmpty()) {
            editTextDesc.setError("Desc required");
            editTextDesc.requestFocus();
            return;
        }

        if (updateDueDate.isEmpty()) {
            editTextDueTime.setError("Finish by required");
            editTextDueTime.requestFocus();
            return;
        }

        class UpdateTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                task.setTask(updateTask);
                task.setDescription(updtateDesc);
                task.setDueDate(updateDueDate);
                DatabaseClient.getInstance(getApplicationContext()).getDatabase()
                        .taskDao()
                        .update(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
            }
        }
        UpdateTask ut = new UpdateTask();
        ut.execute();
    }

    private void loadTask(Task task) {
        editTextTask.setText(task.getTask());
        editTextDesc.setText(task.getDescription());
        editTextDueTime.setText(task.getDueDate());
        checkBox.setChecked(task.isCompleted());
    }
}
