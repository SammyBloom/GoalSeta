package com.example.goalseta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {

    private EditText editTextTitle, editTextDesc, editTextDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTitle = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextDueDate = findViewById(R.id.editTextFinishBy);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        final String taskTitle = editTextTitle.getText().toString().trim();
        final String taskDesc = editTextDesc.getText().toString().trim();
        final String taskDueDate = editTextDueDate.getText().toString().trim();

        if (taskTitle.isEmpty()) {
            editTextTitle.setError("Task required");
            editTextTitle.requestFocus();
            return;
        }

        if (taskDesc.isEmpty()) {
            editTextDesc.setError("Desc required");
            editTextDesc.requestFocus();
            return;
        }

        if (taskDueDate.isEmpty()) {
            editTextDueDate.setError("Finish by required");
            editTextDueDate.requestFocus();
            return;
        }

        class SaveTask extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {

//                Create a new Task
                Task task = new Task();
                task.setTask(taskTitle);
                task.setDescription(taskDesc);
                task.setDueDate(taskDueDate);
                task.setCompleted(false);

//                Adding to datasabase
                DatabaseClient.getInstance(getApplicationContext()).getDatabase()
                        .taskDao()
                        .insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Task Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask saveTask = new SaveTask();
        saveTask.execute();
    }
}
