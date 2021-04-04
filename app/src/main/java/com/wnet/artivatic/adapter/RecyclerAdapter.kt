package com.wnet.artivatic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wnet.artivatic.R
import com.wnet.artivatic.data.api_model.Row
import kotlinx.android.synthetic.main.single_item_list.view.*

class RecyclerAdapter(val list: ArrayList<Row>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(
            R.layout.single_item_list, parent, false
        )
        context = itemView.context
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MyViewHolder, position: Int) {

        //if title == null then hide the title
        if (list.get(position).title != null) {
            holder.title.text = list.get(position).title
        } else {
            holder.title.visibility = View.GONE
        }

        //if description == null then hide the description
        if (list.get(position).description != null) {
            holder.description.text = list.get(position).description
        } else {
            holder.description.visibility = View.GONE
        }

        //if imageURL == null then hide the image
        //also setting the default image if the URL is not valid
        if (list.get(position).imageHref != null) {
            //Using Glide for Lazy loading of the image
            Glide.with(context)
                .load(list.get(position).imageHref)
                .placeholder(R.drawable.default_image)
                .dontAnimate()
                .into(holder.image)
        }else{
            holder.image.visibility = View.GONE
        }

    }

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }

    fun addupdates(upgradeModel: List<Row>) {
        list.clear()
        list.addAll(upgradeModel)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val title = itemView.findViewById<TextView>(R.id.title)
        val description = itemView.findViewById<TextView>(R.id.description)
    }
}