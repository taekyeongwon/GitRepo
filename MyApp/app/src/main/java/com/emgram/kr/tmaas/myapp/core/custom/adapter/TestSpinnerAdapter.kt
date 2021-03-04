package com.emgram.kr.tmaas.myapp.core.custom.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.emgram.kr.tmaas.myapp.R

class TestSpinnerAdapter(context: Context, val resource: Int, var objects: List<String>, title: String): ArrayAdapter<String>(context, resource, objects), SpinnerAdapter {
    private var dropRes: Int = -1

//    init {
//        objects += title
//    }

    override fun getCount(): Int = if(objects.isNotEmpty()) objects.size - 1 else 0

    override fun getItem(position: Int): String? {
        return objects[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: createTopView(position, parent)

        return view
    }

    override fun setDropDownViewResource(resource: Int) {
        super.setDropDownViewResource(resource)
        dropRes = resource
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: createDropView(position, parent)

        return view
    }

    private fun createDropView(position: Int, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(dropRes, parent, false)
        val text: TextView = view.findViewById(R.id.test_tv)
        text.text = objects[position]
        return view
    }

    private fun createTopView(position: Int, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resource, parent, false)
        val text: TextView = view.findViewById(R.id.text_sp_tv)

        Log.d("Adapter", "createTopView Position ::"+position)
        if(position == count) {
            text.text = ""
            text.hint = objects[position]
        } else {
            text.text = objects[position]
        }
        return view
    }
}