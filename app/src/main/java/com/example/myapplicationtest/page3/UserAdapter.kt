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
import com.example.myapplicationtest.databinding.ItemCusviewBinding

//class UserAdapter : PagingDataAdapter<ArticleBean, UserAdapter.UserViewHolder>(diffCallback) {
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): UserViewHolder {
//
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cusview, parent, false)
//
//        return UserViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.textView.text = item?.title
//    }
//
//    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val textView = view as TextView
//    }
//
//
//    //    diffCallback: DiffUtil.ItemCallback<ArticleBean>
//    companion object {
//        private val diffCallback = object : DiffUtil.ItemCallback<ArticleBean>() {
//            override fun areItemsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//
//}


//class UserAdapter : PagingDataAdapter<ArticleBean, UserAdapter.UserViewHolder>(diffCallback) {
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): UserViewHolder {
//        val binding = ItemCusviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return UserViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)
//    }
//
//    inner class UserViewHolder(private val binding: ItemCusviewBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: ArticleBean?) {
//        }
//    }
//
//    companion object {
//        private val diffCallback = object : DiffUtil.ItemCallback<ArticleBean>() {
//            override fun areItemsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//}

class QuickPageAdapter<T : Any>(
    private val itemLayoutRes: Int,
    private val bindView: (View, T) -> Unit,
    private val itemCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, QuickPageAdapter<T>.PageViewHolder>(itemCallback) {

    private var onItemClickListener: ((View, T) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(itemLayoutRes, parent, false)
        return PageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    inner class PageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: T) {
            bindView(view, item)
            itemView.setOnClickListener {
                onItemClickListener?.invoke(view, item)
            }
        }
    }


}

