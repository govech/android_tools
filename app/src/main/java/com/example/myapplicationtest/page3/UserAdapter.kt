package com.example.myapplicationtest.page3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtest.R
import com.example.myapplicationtest.bean.ArticleBean

class UserAdapter() :
    PagingDataAdapter<ArticleBean, UserAdapter.UserViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cusview, parent, false)

        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.textView.text = item?.title
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view as TextView
    }


    //    diffCallback: DiffUtil.ItemCallback<ArticleBean>
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ArticleBean>() {
            override fun areItemsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
                return oldItem == newItem
            }
        }
    }

}