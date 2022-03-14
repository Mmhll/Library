package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BookRecycler(var context : Context, var data : Array<BookEntity>) : RecyclerView.Adapter<BookRecycler.VH>() {

    private lateinit var myListener: onItemClickListener
    private lateinit var myLongListener: onLongItemClickListener

    interface onItemClickListener{
        fun onItemClick(position : Int)
    }

    interface onLongItemClickListener{
        fun onLongItemClick(position: Int) : Boolean
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        myListener = listener
    }

    fun setOnLongItemClickListener(listener: onLongItemClickListener){
        myLongListener = listener
    }


    class VH(itemView: View, listener: onItemClickListener, longListener : onLongItemClickListener) : RecyclerView.ViewHolder(itemView){
        var imageBook : ImageView = itemView.findViewById(R.id.imageBook)
        var titleBook : TextView = itemView.findViewById(R.id.titleBook)
        var yearBook : TextView = itemView.findViewById(R.id.yearBook)
        var authorBook : TextView = itemView.findViewById(R.id.authorBook)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            itemView.setOnLongClickListener {
                longListener.onLongItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(context).inflate(R.layout.one_book_item, parent, false), myListener, myLongListener)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(context).load(data[position].uri).into(holder.imageBook)
        holder.titleBook.text = data[position].title
        holder.yearBook.text = data[position].year
        holder.authorBook.text = data[position].author
    }

    override fun getItemCount(): Int {
        return data.size
    }

}