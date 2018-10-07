package com.hemalexample.todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var etNewItem : EditText
    lateinit var btnAddItem : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNewItem = findViewById(R.id.etNewItem)
        btnAddItem = findViewById(R.id.btnAddItem)

        btnAddItem.setOnClickListener {
            saveItem()
        }
    }

    private fun saveItem(){
        val item = etNewItem.text.toString().trim()
        if(item.isEmpty()){
            etNewItem.error = "Please enter a task"
            return
        }



        val ref = FirebaseDatabase.getInstance().getReference("tasks")
        val taskId = ref.push().key
        val task = Task(taskId.toString(), item, "sample", false, Date(), 0)
        ref.child(taskId.toString()).setValue(task).addOnCompleteListener {
            Toast.makeText(applicationContext, "Task Saved Successfully", Toast.LENGTH_LONG).show()
        }
    }
}
