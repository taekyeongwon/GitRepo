package com.emgram.kr.tmaas.myapp.core.custom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emgram.kr.tmaas.myapp.R
import kotlinx.android.synthetic.main.item_test.view.*

class TestAdapter: RecyclerView.Adapter<TestAdapter.TestViewHolder>() {
    private val testArray = ArrayList<String>()

    fun setText(list: ArrayList<String>) {
        testArray.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false))
    }

    override fun getItemCount(): Int = testArray.size

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        with(holder.itemView) {
            test_tv.text = testArray[position]
        }
    }

    inner class TestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            with(itemView) {

            }
        }
    }
}