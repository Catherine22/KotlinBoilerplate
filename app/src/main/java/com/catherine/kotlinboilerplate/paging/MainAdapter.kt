package com.catherine.kotlinboilerplate.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.catherine.kotlinboilerplate.delegation.OnItemClickListener

class MainAdapter(
    private val myDataset: Array<String>,
    private val onItemClickListener: OnItemClickListener?
) :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    class MyAdapter(textView: TextView) : RecyclerView.ViewHolder(textView)

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return MyViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = myDataset[position]
        holder.itemView.setOnClickListener {
            onItemClickListener?.onClick(it, position)
        }
    }

    override fun getItemCount() = myDataset.size
}