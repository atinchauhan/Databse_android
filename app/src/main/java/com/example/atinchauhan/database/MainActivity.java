package com.example.atinchauhan.database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    EditText etName;
    EditText etCell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName=findViewById(R.id.etName);
        etCell=findViewById(R.id.etCell);

    }
    public void btnSave(View view)
    {
        String name=etName.getText().toString().trim();
        String cell=etCell.getText().toString().trim();
        try
        {
        ContactsDB db=new ContactsDB(this);
        db.open();
        db.createEntry(name,cell);
        db.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            etName.setText("");
            etCell.setText("");
        }

        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }
    public void btnEdit(View view){
        try{
    ContactsDB db=new ContactsDB(this);
    db.open();
    db.updateEntry("1","Zayn","9991114400");
    db.close();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }
    public void btnDelete(View view){
        try{
            ContactsDB db=new ContactsDB(this);
            db.open();
            db.deleteEntry("2");
            db.close();
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }
    public void btnShow(View view){
        startActivity(new Intent(this,Data.class));

    }
}
