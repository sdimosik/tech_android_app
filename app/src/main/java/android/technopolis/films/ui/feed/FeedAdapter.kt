package android.technopolis.films.ui.feed

import android.technopolis.films.api.trakt.model.media.CommonMediaItem
import android.technopolis.films.databinding.FeedSubAdapterItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class FeedAdapterHolder(
    private val itemBinding: FeedSubAdapterItemBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bindTo(item: CommonMediaItem) {
        itemView.apply {
            if (true) {
                itemBinding.name.text = item.title
                itemBinding.someInfo.text = item.year.toString()
            } else {
                itemBinding.name.text = item.title
                itemBinding.someInfo.text = item.year.toString()
            }
        }
    }
}

private val differCallback = object :
    DiffUtil.ItemCallback<CommonMediaItem>() {
    override fun areItemsTheSame(
        oldItem: CommonMediaItem,
        newItem: CommonMediaItem,
    ): Boolean {
        return oldItem.ids.trakt == newItem.ids.trakt
    }

    override fun areContentsTheSame(
        oldItem: CommonMediaItem,
        newItem: CommonMediaItem,
    ): Boolean {
        return oldItem == newItem
    }
}

class FeedAdapter : ListAdapter<CommonMediaItem, FeedAdapterHolder>(differCallback) {

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapterHolder {
        return FeedAdapterHolder(
            FeedSubAdapterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(feedAdapterHolder: FeedAdapterHolder, position: Int) {
        val current = differ.currentList[position]
        feedAdapterHolder.bindTo(current)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
