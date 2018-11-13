package com.egco428.ex15_firebasewrite

class Message(val id:String, val message: String, val rating: Int){
    constructor(): this("","",0)
}