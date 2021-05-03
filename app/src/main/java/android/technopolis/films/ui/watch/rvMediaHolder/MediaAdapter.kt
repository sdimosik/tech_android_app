package android.technopolis.films.ui.watch.rvMediaHolder

import android.technopolis.films.databinding.MediaHolderBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MediaAdapter : ListAdapter<Media, MediaViewHolder>(MediaDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = MediaHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MediaViewHolder(
    binding: MediaHolderBinding
) : ViewHolder(binding.root) {
    private val ivImage: AppCompatImageView = binding.mediaHolderMediaImage
    private val tvName: AppCompatTextView = binding.mediaHolderMediaName
    private val tvDescription: AppCompatTextView = binding.mediaHolderMediaDescription

    fun bind(media: Media) {
        ivImage.setBackgroundColor(0x00FF00)
        tvName.text = "Just Media Name"
        tvDescription.text = "Just Media Description"
    }
}

object MediaDiff : DiffUtil.ItemCallback<Media>() {
    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
        return false
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
        return false
        TODO("Not yet implemented")
    }
}
