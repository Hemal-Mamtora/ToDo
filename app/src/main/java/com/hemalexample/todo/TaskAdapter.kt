package com.hemalexample.todo

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.content.DialogInterface


class TaskAdapter(val mctx: Context, val layoutResId : Int, val taskList : List<Task>)
    : ArrayAdapter<Task>(mctx, layoutResId, taskList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context);
        val view : View = layoutInflater.inflate(layoutResId, null);
        val tvTask = view.findViewById<TextView>(R.id.tvTask);

        tvTask.setOnClickListener {
            showUpdateDialog()
        }
        val task = taskList[position]
        tvTask.text = task.task
        return view;
    }

    fun showUpdateDialog(){
        val builder = AlertDialog.Builder(mctx)
        builder.setTitle("Update Task")
        val inflater = LayoutInflater.from(mctx)
        val view = inflater.inflate(R.layout.update_task, null)
        val et = view.findViewById<EditText>(R.id.etNewItem)
        //add other fields to modify

        builder.setView(view)

        builder.setPositiveButton("Update") { p0, p1 ->

        }

        builder.setNegativeButton("No") { p0, p1 ->

        }

        val alert = builder.create()
        alert.show()
    }


}