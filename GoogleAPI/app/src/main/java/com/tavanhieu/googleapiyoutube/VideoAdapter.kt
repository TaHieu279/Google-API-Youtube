package com.tavanhieu.googleapiyoutube

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class VideoAdapter (var context: Context, var layout: Int, var arr: ArrayList<Video>): BaseAdapter() {
    override fun getCount(): Int {
        return arr.size
    }

    override fun getItem(position: Int): Any {
        return arr[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ViewHolder() {
        lateinit var imgAnhVideo: ImageView
        lateinit var txtTitle: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        var holder = ViewHolder()
        if(view == null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, null)
            holder.imgAnhVideo  = view.findViewById(R.id.imgAnhVideo)
            holder.txtTitle     = view.findViewById(R.id.txtTenVideo)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val res = arr[position]
        holder.txtTitle.text = res.title
        Picasso.get().load(res.picture).placeholder(R.drawable.loadingbar).error(R.drawable.error).into(holder.imgAnhVideo)

        return view
    }
}