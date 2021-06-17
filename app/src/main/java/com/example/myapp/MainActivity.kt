package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapp.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var database = FirebaseDatabase.getInstance().reference

        // Adding data to Firebase
        binding.btnadd.setOnClickListener {
            var etnum = binding.etnum.text.toString().toInt()
            var etname = binding.etname.text.toString()
            var etsalary = binding.etsalary.text.toString().toInt()
//            database.setValue(Personal(etnum, etname, etsalary))
            database.child(etnum.toString()).setValue(Personal(etname, etsalary))
        }

        //Reading data from Firebase
        var getdata = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()
                for (i in snapshot.children) {
                    var name = i.child("pname").getValue()
                    var salary = i.child("psalary").getValue()
                    sb.append("${i.key} $name $salary \n")
                }
                binding.tvresult.setText(sb)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
    }
}