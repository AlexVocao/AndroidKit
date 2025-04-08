package com.example.appb


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "B: Received ACTION_A", Toast.LENGTH_SHORT).show()

        val intent = Intent()
        intent.setAction("ACTION_B")
        intent.setPackage("com.example.appa")
        context?.sendBroadcast(intent)
        Toast.makeText(context, "B: Send ACTION_B", Toast.LENGTH_SHORT).show()
    }
}