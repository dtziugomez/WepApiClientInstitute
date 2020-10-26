package com.example.webapiclient;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Student> {

    public CustomAdapter(Context context, int resource, List<Student> student) {
        super(context, resource, student);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.view_student_entry, parent, false);
        }

        Student student = getItem(position);

        if (student != null) {
            TextView tvStudentId = (TextView) v.findViewById(R.id.student_Id);
            TextView tvStudentName = (TextView) v.findViewById(R.id.student_name);
            tvStudentId.setText( Integer.toString(student.Id));
            tvStudentName.setText(student.Name);
        }

        return v;
    }
}
