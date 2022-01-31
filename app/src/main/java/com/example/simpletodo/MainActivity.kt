package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTask= mutableListOf<String>()
    lateinit var adaptor: TaskItemAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener =object : TaskItemAdaptor.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                listOfTask.removeAt(position)
                adaptor.notifyDataSetChanged()

                saveItems()

            }

        }

//        listOfTask.add("Do laundry")
//        listOfTask.add("Go for a walk")

loadItems()
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
         adaptor=TaskItemAdaptor(listOfTask,onLongClickListener)
        recyclerView.adapter=adaptor
        recyclerView.layoutManager=LinearLayoutManager(this)

        val inputTextField=findViewById<EditText>(R.id.addTaskField)


       findViewById<Button>(R.id.button).setOnClickListener {
           val userInputtedTask=inputTextField.text.toString()
           listOfTask.add(userInputtedTask)
           adaptor.notifyItemInserted(listOfTask.size-1)
           inputTextField.setText("")
           saveItems()

       }



    }
    fun getDataFile():File{
        return File(filesDir,"data.txt")
    }
    fun loadItems(){
        try {
            listOfTask=FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }


    }
    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listOfTask)
        } catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }
}