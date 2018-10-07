package com.hemalexample.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class TaskAdapter(val mctx: Context, val layoutResId : Int, val taskList : List<Task>)
    : ArrayAdapter<Task>(mctx, layoutResId, taskList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context);
        val view : View = layoutInflater.inflate(layoutResId, null);
        val tvTask = view.findViewById<TextView>(R.id.tvTask);
        val task = taskList[position]
        tvTask.text = task.task
        return view;
    }


}