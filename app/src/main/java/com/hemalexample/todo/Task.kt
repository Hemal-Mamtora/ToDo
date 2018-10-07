package com.hemalexample.todo

import java.util.*

class Task(val id: String, val task : String, val detail :String, val done : Boolean, val date: Date, val priority : Int){
    constructor():this("","","", false, Date(), 0) {

    }
}