package com.gtappdevelopers.noteapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NoteRVAdapter(
    val context: Context,
    val noteClickDeleteInterface: NoteClickDeleteInterface,
    val noteClickInterface: NoteClickInterface,
    var isImage1Displayed: Boolean = false
) :
    RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    //on below line we are creating a variable for our all notes list.
    val allNotes = ArrayList<Note>()


    //on below line we are creating a view holder class.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //on below line we are creating an initializing all our variables which we have added in layout file.
        val noteTV = itemView.findViewById<TextView>(R.id.idTVNote)
        val dateTV = itemView.findViewById<TextView>(R.id.idTVDate)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
        val cardView = itemView.findViewById<CardView>(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_rv_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //on below line we are setting data to item of recycler view.
        holder.noteTV.text = allNotes.get(position).noteTitle
        holder.dateTV.text = "Last Updated : " + allNotes.get(position).timeStamp
        holder.cardView.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.recycler_anim
            )
        )

        //on below line we are adding click listner to our delete image view icon.
        holder.deleteIV.setOnClickListener {
            //on below line we are calling a note click interface and we are passing a position to it.
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
        }


        //on below line we are adding click listner to our recycler view item.
        holder.itemView.setOnClickListener {
            //on below line we are calling a note click interface and we are passing a position to it.
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        //on below line we are returning our list size.
        return allNotes.size
    }

    //below method is use to update our list of notes.
    fun updateList(newList: List<Note>) {
        //on below line we are clearing our notes array list/
        allNotes.clear()
        //on below line we are adding a new list to our all notes list.
        allNotes.addAll(newList)
        //on below line we are calling notify data change method to notify our adapter.
        notifyDataSetChanged()
    }

    fun setFilteredList(filteredNotes: List<Note>) {
        this.allNotes.clear()
        this.allNotes.addAll(filteredNotes)
        notifyDataSetChanged()
    }

}

interface NoteClickDeleteInterface {
    //creating a method for click action on delete image view.
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface {
    //creating a method for click action on recycler view item for updating it.
    fun onNoteClick(note: Note)
}