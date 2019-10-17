package com.cyberwith.createdatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDatabaseHelper myDatabaseHelper;
    private EditText nameEditText, ageEditText, genderEditText, idEditText;
    private Button saveButton, displayButton,updateButton,deleteButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.editNameID);
        ageEditText = findViewById(R.id.editAgeID);
        genderEditText = findViewById(R.id.editGenderID);
        idEditText = findViewById(R.id.editID);
        saveButton = findViewById(R.id.saveButtonID);
        displayButton = findViewById(R.id.displayButtonID);
        updateButton = findViewById(R.id.updateButtonID);
        deleteButton = findViewById(R.id.deleteButtonID);
        textView = findViewById(R.id.textID);

        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();

        saveButton.setOnClickListener(this);
        displayButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();
        String id = idEditText.getText().toString();

        if (v.getId() == R.id.saveButtonID) {
            long rowID = myDatabaseHelper.insertData(name, age, gender);
            if (rowID == -1) {
                Toast.makeText(getApplicationContext(), " Row unsuccessful ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), " Row " + rowID + " is successfully inserted ", Toast.LENGTH_LONG).show();
            }
        }
        else if (v.getId() == R.id.displayButtonID) {
            Cursor cursor = myDatabaseHelper.displayData();

            if (cursor.getCount() == 0) {
                showData("Error"," Data not found ");
                Toast.makeText(getApplicationContext(), " Row not find out ",Toast.LENGTH_LONG).show();
                return;
            }

            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext()){
                stringBuffer.append("ID "+cursor.getString(0)+"\n");
                stringBuffer.append("Name "+cursor.getString(1)+"\n");
                stringBuffer.append("Age "+cursor.getString(2)+"\n");
                stringBuffer.append("Gender "+cursor.getString(3)+"\n\n");
            }
            showData("ResultSet", stringBuffer.toString());
            textView.setText(stringBuffer);
        }
        else if (v.getId() == R.id.updateButtonID){
            boolean updated = myDatabaseHelper.updateData(id, name, age, gender);
            if(updated == true){
                Toast.makeText(getApplicationContext(), " Data is updated ",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), " Update is failed ",Toast.LENGTH_LONG).show();
            }
        }
        else if (v.getId() == R.id.deleteButtonID) {
            myDatabaseHelper.deleteData(id);
            Toast.makeText(getApplicationContext(), " data is deleted ",Toast.LENGTH_LONG).show();
        }
    }

    public void showData (String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("Cancel",null);
        builder.show();
    }
}
