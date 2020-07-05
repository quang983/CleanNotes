package com.example.cleannotes.ui.groups.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleannotes.R
import com.example.cleannotes.ui.interfaces.OnItemClicked
import com.example.domain.model.Group
import kotlinx.android.synthetic.main.group_card.view.*

class GroupListAdapter(
    private val listener: OnItemClicked
): RecyclerView.Adapter<GroupsViewHolder>() {

    private val groups = mutableListOf<Group>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.group_card, parent, false
        )
        return GroupsViewHolder(view = view)
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        val group = groups[position]
        holder.bind(group = group, listener = listener)
    }

    fun updateList(newGroups: List<Group>) {
        groups.clear()
        groups.addAll(newGroups)
        notifyDataSetChanged()
    }
}

class GroupsViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    fun bind(group: Group, listener: OnItemClicked) {
        view.groupCardTitle.text = group.name
        view.setOnClickListener {
            listener.onClick(id = group.id!!)
        }
    }
}