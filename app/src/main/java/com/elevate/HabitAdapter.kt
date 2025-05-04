package com.elevate

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class HabitAdapter(
    private val habits: List<Habit>,
    private val onHabitSelected: (Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    inner class HabitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val habitImage: ImageView = view.findViewById(R.id.habitImage)
        val habitTitle: TextView = view.findViewById(R.id.habitTitle)
        val cardView: MaterialCardView = view as MaterialCardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]
        holder.habitImage.setImageResource(habit.imageResId)
        holder.habitTitle.text = habit.title

        if (habit.isSelected) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FCE4EC")) // Light pink
            holder.cardView.strokeWidth = 4
            holder.cardView.strokeColor = Color.parseColor("#D77FA1") // Border color
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE)
            holder.cardView.strokeWidth = 0
        }

        holder.itemView.setOnClickListener {
            habit.isSelected = !habit.isSelected
            notifyItemChanged(position)
            onHabitSelected(habit)
        }
    }

    override fun getItemCount(): Int = habits.size
}
