package com.emgram.kr.tmaas.myapp.core.custom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter

class TestSpinnerAdapter(context: Context, val resource: Int, objects: List<String>, title: String): ArrayAdapter<String>(context, resource, objects), SpinnerAdapter {
    private var dropRes: Int = -1
    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getItem(position: Int): String? {
        return super.getItem(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
    }

    override fun setDropDownViewResource(resource: Int) {
        super.setDropDownViewResource(resource)
        dropRes = resource
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createDropView(parent)
    }

    private fun createDropView(parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(dropRes, parent, false)
    }

    private fun createTopView(parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resource, parent, false)

    }
}