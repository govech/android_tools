package com.example.myapplicationtest

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ApkListAdapter(val context: Context, private val appInfoList: MutableList<AppInfoData>) :
    RecyclerView.Adapter<ApkListAdapter.ViewHolder>() {


    private var ItemClickListener: ((View, position: Int) -> Unit)? = null
    fun setClickListener(listener: (View, position: Int) -> Unit) {
        this.ItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appinfo, parent, false)
        val holder = ViewHolder(view)
        view.setOnClickListener {
            ItemClickListener?.invoke(it, holder.adapterPosition)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return appInfoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            nameTv.text = appInfoList[position].appName
            Glide.with(context).load(appInfoList[position].icon).into(iconImg)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTv: TextView
        val iconImg: ImageView

        init {
            nameTv = itemView.findViewById(R.id.tv_name)
            iconImg = itemView.findViewById(R.id.img_icon)
        }

    }
}


