package android.technopolis.films.ui.profile.favorite

import android.technopolis.films.databinding.FavoriteInProfileAdapterBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class FavoriteInProfileAdapter : RecyclerView.Adapter<FavoriteInProfileAdapter.FilmsHolder>() {

    private var binding: FavoriteInProfileAdapterBinding? = null

    inner class FilmsHolder(itemBinding: FavoriteInProfileAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object :
        DiffUtil.ItemCallback<FavoriteInProfileItemModel>() {
        override fun areItemsTheSame(
            oldItem: FavoriteInProfileItemModel,
            newItem: FavoriteInProfileItemModel
        ): Boolean {
            return oldItem.someInfo == newItem.someInfo
        }

        override fun areContentsTheSame(
            oldItem: FavoriteInProfileItemModel,
            newItem: FavoriteInProfileItemModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsHolder {
        binding = FavoriteInProfileAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return FilmsHolder(binding!!)
    }

    override fun onBindViewHolder(holder: FilmsHolder, position: Int) {
        val current = differ.currentList[position]

        holder.itemView.apply {
            binding?.someInfoItem?.text = current.someInfo.toString()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}