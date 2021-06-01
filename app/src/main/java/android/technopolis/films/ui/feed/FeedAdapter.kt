package android.technopolis.films.ui.feed

import android.technopolis.films.databinding.FeedAdapterBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class Holder(
    private val itemBinding: FeedAdapterBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bindTo(item: FeedItemModel) {
        itemView.apply {
            itemBinding.name.text = item.name
            itemBinding.someInfo.text = item.someInfo
        }
    }
}

private val differCallback = object :
    DiffUtil.ItemCallback<FeedItemModel>() {
    override fun areItemsTheSame(
        oldItem: FeedItemModel,
        newItem: FeedItemModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: FeedItemModel,
        newItem: FeedItemModel
    ): Boolean {
        return oldItem == newItem
    }
}

class FeedAdapter : ListAdapter<FeedItemModel, Holder>(differCallback) {
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            FeedAdapterBinding.inflate(
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
