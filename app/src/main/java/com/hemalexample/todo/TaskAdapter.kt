package com.hemalexample.todo

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class TaskAdapter(val mctx: Context, val layoutResId : Int, val taskList : List<Task>)
    : ArrayAdapter<Task>(mctx, layoutResId, taskList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context);
        val view : View = layoutInflater.inflate(layoutResId, null);
        val tvTask = view.findViewById<TextView>(R.id.tvTask);

        val task = taskList[position]
        tvTask.text = task.task

        tvTask.setOnClickListener {
            showUpdateDialog(task)
        }


        return view;
    }

    fun showUpdateDialog(task: Task) {
        val builder = AlertDialog.Builder(mctx)
        builder.setTitle("Update Task")
        val inflater = LayoutInflater.from(mctx)
        val view = inflater.inflate(R.layout.update_task, null)

        val et = view.findViewById<EditText>(R.id.etNewItem)
        et.setText(task.task)

        val etDetails = view.findViewById<EditText>(R.id.etDetails)
        etDetails.setText(task.detail)


        val etDate = view.findViewById<EditText>(R.id.etDate)
        val myDate = Date(task.date.time)
        etDate.setText(myDate.toString())

        val etPriority = view.findViewById<EditText>(R.id.etPriority)
        etPriority.setText(task.priority.toString())

        val cbDone = view.findViewById<CheckBox>(R.id.cbDone)
        cbDone.isChecked = task.done

        //add other fields to modify

        builder.setView(view)

        builder.setPositiveButton("Update") { p0, p1 ->
            val dbTask = FirebaseDatabase.getInstance().getReference("tasks")
            val myTask = et.text.toString().trim()
            val myDetail = etDetails.text.toString().trim()
            val myDone = cbDone.isChecked
            val myDate = etDate.text.toString().trim()
            val myPriority = etPriority.text.toString().trim().toInt()

            if(myTask.isEmpty()){
                et.error = "Please enter a task"
                et.requestFocus()
                return@setPositiveButton
            }

            if(myDetail.isEmpty()){
                etDetails.error = "Please enter a task"
                etDetails.requestFocus()
                return@setPositiveButton
            }

            val newtask = Task(task.id, myTask, myDetail, myDone, task.date, myPriority)
            dbTask.child(task.id).setValue(newtask)
            Toast.makeText(mctx, "Task Updated", Toast.LENGTH_LONG).show()
        }

        builder.setNegativeButton("Delete") { p0, p1 ->
            val dbTask = FirebaseDatabase.getInstance().getReference("tasks")
            dbTask.child(task.id).removeValue()
            Toast.makeText(mctx, "Task Deleted", Toast.LENGTH_LONG).show()
        }

        val alert = builder.create()
        alert.show()
    }


}
