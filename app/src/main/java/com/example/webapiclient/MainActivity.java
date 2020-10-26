package com.example.webapiclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener{

    ListView listView;
    Button btnGetAll,btnAdd;
    RestService restService;
    TextView student_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestService();
        setContentView(R.layout.activity_main);
        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd= (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //This function will call when the screen is activate
    @Override
    public void onResume() {

        super.onResume();
        refreshScreen();
    }

    @Override
    public void onClick(View v) {
        if (v== findViewById(R.id.btnAdd)){

            Intent intent = new Intent(this,StudentDetailActivity.class);
            intent.putExtra("student_Id",0);
            startActivity(intent);

        }else {
            // You should use refreshScreen() instead, just show you an easier method only :P
            refreshScreen_SimpleWay();
        }
    }

    private void refreshScreen(){

        //Call to server to grab list of student records. this is a asyn
        restService.getService().getStudent(new Callback<List<Student>>() {
            @Override
            public void success(List<Student> students, Response response) {
                ListView lv = (ListView) findViewById(R.id.listView);

                CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, R.layout.view_student_entry, students);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        student_Id = (TextView) view.findViewById(R.id.student_Id);
                        String studentId = student_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(), StudentDetailActivity.class);
                        objIndent.putExtra("student_Id", Integer.parseInt(studentId));
                        startActivity(objIndent);
                    }
                });
                lv.setAdapter(customAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    //I Don't like the idea of loop the List<Student> and put data into ArrayList, but I DON'T WANT
    //made a complicated tutorial also:D, So I showed you this simple way but is not encourage
    //just to make you easy to understand without knowing the CustomAdapter method
    private void refreshScreen_SimpleWay(){

        restService.getService().getStudent(new Callback<List<Student>>() {
            @Override
            public void success(List<Student> students, Response response) {
                ListView lv = (ListView) findViewById(R.id.listView);


                ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

                for (int i = 0; i < students.size(); i++) {
                    HashMap<String, String> student = new HashMap<String, String>();
                    student.put("id", String.valueOf(students.get(i).Id));
                    student.put("name", String.valueOf(students.get(i).Name));

                    studentList.add(student);
                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        student_Id = (TextView) view.findViewById(R.id.student_Id);
                        String studentId = student_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(),StudentDetailActivity.class);
                        objIndent.putExtra("student_Id", Integer.parseInt( studentId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter(MainActivity.this, studentList, R.layout.view_student_entry, new String[]{"id", "name"}, new int[]{R.id.student_Id, R.id.student_name});
                lv.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }
}
