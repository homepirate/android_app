package com.example.lab1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CharacterAdapter(private val characters: List<Character>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val character_name: TextView = itemView.findViewById(R.id.character_name)
        val character_status: TextView = itemView.findViewById(R.id.character_status)
        val character_species: TextView = itemView.findViewById(R.id.character_species)
        val character_image: ImageView = itemView.findViewById(R.id.character_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.character_name.text = character.name
        holder.character_status.text = character.status
        holder.character_species.text = character.species
        Glide.with(holder.itemView.context).load(character.image).into(holder.character_image)
    }

    override fun getItemCount() = characters.size
}