package com.example.kotlinearthquakemonitor

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinearthquakemonitor.databinding.EqListItemBinding
import kotlin.math.log

private val TAG = EqAdapter::class.java.simpleName

class EqAdapter: ListAdapter<Earthquake, EqAdapter.EqViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Earthquake>(){
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem
        }
    }

    lateinit var onItemClickListener: (Earthquake) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqAdapter.EqViewHolder {

        val binding = EqListItemBinding.inflate(LayoutInflater.from(parent.context))

        //val view = LayoutInflater.from(parent.context).inflate(R.layout.eq_list_item, parent, false)
        return EqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EqAdapter.EqViewHolder, position: Int) {
        val earthquake = getItem(position)
        //holder.magnitudeText.text = earthquake.magnitude.toString()
        //holder.placeText.text = earthquake.place
        holder.bind(earthquake)
    }

    inner class EqViewHolder(private val binding: EqListItemBinding): RecyclerView.ViewHolder(binding.root) {
        //val magnitudeText = view.findViewById<TextView>(R.id.eq_magnitude_text)
        //val placeText = view.findViewById<TextView>(R.id.eq_place_text)

        fun bind(earthquake: Earthquake){
            binding.eqMagnitudeText.text = earthquake.magnitude.toString()
            binding.eqPlaceText.text = earthquake.place

            binding.root.setOnClickListener{
                    if(::onItemClickListener.isInitialized){
                        onItemClickListener(earthquake)
                    } else {
                        Log.e("TAG","onItemClickListener not initialized")
                    }

            }
        }
    }
}