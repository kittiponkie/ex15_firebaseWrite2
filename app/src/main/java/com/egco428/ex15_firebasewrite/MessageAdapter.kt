package com.egco428.ex15_firebasewrite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MessageAdapter(val mContext:Context, val layoutResId:Int, val messageList:List<Message>):ArrayAdapter<Message>(mContext,layoutResId,messageList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
        val view:View = layoutInflater.inflate(layoutResId,null)
        val msgTextView = view.findViewById<TextView>(R.id.msgView)
        val rateTextView = view.findViewById<TextView>(R.id.rateView)
        val msg = messageList[position]
        msgTextView.text = msg.message
        rateTextView.text = msg.rating.toString()
        return view
    }
}