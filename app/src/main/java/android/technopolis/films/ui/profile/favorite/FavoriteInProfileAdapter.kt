package android.technopolis.films.ui.profile.favorite

import android.technopolis.films.databinding.FavoriteInProfileAdapterBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class Holder(
    private val itemBinding: FavoriteInProfileAdapterBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bindTo(item: FavoriteInProfileItemModel) {
        itemView.apply {
            itemBinding.someInfoItem.text = item.someInfo.toString()
        }
    }
}

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

class FavoriteInProfileAdapter : ListAdapter<FavoriteInProfileItemModel, Holder>(differCallback) {
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            FavoriteInProfileAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val current = differ.currentList[position]
        holder.bindTo(current)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
