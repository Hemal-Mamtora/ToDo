package com.hemalexample.todo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var etNewItem : EditText
    lateinit var btnAddItem : Button
    lateinit var lvItems : ListView
    lateinit var btnViewCalendar : Button

    lateinit var ref : DatabaseReference
    lateinit var taskList : MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("tasks")

        etNewItem = findViewById(R.id.etNewItem)
        btnAddItem = findViewById(R.id.btnAddItem)
        lvItems = findViewById(R.id.lvItems)
        btnViewCalendar = findViewById(R.id.btnViewCalendar)

        btnAddItem.setOnClickListener {
            saveItem()
        }

        btnViewCalendar.setOnClickListener {
            val intent = Intent(this@MainActivity, Calendar::class.java)
            startActivity(intent)
        }

        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    taskList.clear()
                    for(h in p0.children){
                        val task = h.getValue(Task::class.java)
                        taskList.add(task!!)
                    }
                }

                val adapter = TaskAdapter(this@MainActivity, R.layout.tasks, taskList)
                lvItems.adapter = adapter
            }

        })
    }

    private fun saveItem(){
        val item = etNewItem.text.toString().trim()
        if(item.isEmpty()){
            etNewItem.error = "Please enter a task"
            return
        }

        val taskId = ref.push().key
        val task = Task(taskId.toString(), item, "sample", false, Date(), 0)
        ref.child(taskId.toString()).setValue(task).addOnCompleteListener {
            Toast.makeText(applicationContext, "Task Saved Successfully", Toast.LENGTH_LONG).show()
        }
    }
}
