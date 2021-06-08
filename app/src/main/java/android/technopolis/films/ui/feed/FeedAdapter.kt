package android.technopolis.films.ui.feed

import android.technopolis.films.api.trakt.model.media.CommonMediaItem
import android.technopolis.films.databinding.FeedSubAdapterItemBinding
import android.technopolis.films.databinding.MediaHolderBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FeedAdapterHolder(
    private val itemBinding: MediaHolderBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {
    private val ivImage: AppCompatImageView = itemBinding.mediaHolderMediaImage
    private val tvName: AppCompatTextView = itemBinding.mediaHolderMediaName
    private val tvDescription: AppCompatTextView = itemBinding.mediaHolderMediaDescription

    fun bindTo(item: CommonMediaItem) {
        itemView.apply {
            if (true) {
                tvName.text = item.title
                tvDescription.text = item.year.toString()
            } else {
                tvName.text = item.title
                tvDescription.text = item.year.toString()
            }
            Picasso.get().load(item.mediaUrl).into(ivImage)
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
            MediaHolderBinding.inflate(
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
