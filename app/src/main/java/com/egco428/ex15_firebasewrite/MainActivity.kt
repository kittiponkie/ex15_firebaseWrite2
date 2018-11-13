package com.egco428.ex15_firebasewrite

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener




class MainActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    lateinit var msgList: MutableList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Write a message to the database

        /*val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("course")

        myRef.setValue("egco428")*/

        msgList = mutableListOf()
        database = FirebaseDatabase.getInstance().getReference("dataMsg")
        database.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    msgList.clear()
                    for(i in p0.children){
                        val message = i.getValue(Message::class.java)
                        msgList.add(message!!)
                    }
                    val adapter = MessageAdapter(applicationContext, R.layout.messages, msgList)
                    listView.adapter = adapter
                }
            }
        })

        submitBtn.setOnClickListener{
            saveData()
        }

        readBtn.setOnClickListener{
            readData()
        }
    }

    private fun saveData(){
        val msg = editText.text.toString()
        if(msg.isEmpty()){
            editText.error = "Please enter a message"
            return
        }
        val database2 = FirebaseDatabase.getInstance().getReference("dataMsg")
        val messageId = database2.push().key
        val messageData = Message(messageId, msg, ratingBar.rating.toInt())
        database2.child(messageId).setValue(messageData).addOnCompleteListener ({
            Toast.makeText(applicationContext, "Message saved successfully", Toast.LENGTH_SHORT).show()
        })
    }

    private fun readData(){
        // Read from the database
        val myRef = FirebaseDatabase.getInstance().getReference("message")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                textView.text = value
                //Log.d(FragmentActivity.TAG, "Value is: " + value!!)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })
    }
}
