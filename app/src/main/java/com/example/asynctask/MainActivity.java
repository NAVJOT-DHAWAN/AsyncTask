package com.example.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TEXT_STATE = "currentText";

    private TextView mTextView;
    private ProgressBar progressbar;
    static int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView1);
        progressbar = findViewById(R.id.progressBar);
        progressbar.setMax(10);

        if (savedInstanceState != null) {
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }
    }

    public void startTask(View view) {
        mTextView.setText(R.string.napping);
        //  new SimpleAsyncTask(mTextView).execute();
        count = 1;
        progressbar.setVisibility(View.VISIBLE);
        progressbar.setProgress(0);
        switch (view.getId()) {
            case R.id.button:
                new SimpleAsyncTask(mTextView).execute();
                new MyTask().execute(10);
                break;
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the state of the TextView
        outState.putString(TEXT_STATE, mTextView.getText().toString());
    }

    class MyTask extends AsyncTask<Integer,Integer,String>{

        @Override
        protected String doInBackground(Integer... params) {
            for (; count <= params[0]; count++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(count);
                    Log.e("count_found:::","" +count);
                    //startTask(progressbar);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
            progressbar.setVisibility(View.GONE);
          //  txt.setText(result);
           // btn.setText("Restart");
        }
        @Override
        protected void onPreExecute() {
            //txt.setText("Task Starting...");
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            //txt.setText("Running..."+ values[0]);
            progressbar.setProgress(values[0]);
        }
    }

    }

