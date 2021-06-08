package android.technopolis.films.ui.watch.rvMediaHolder

import android.technopolis.films.api.trakt.model.media.Media
import android.technopolis.films.api.trakt.model.media.MediaTypeResponse
import android.technopolis.films.databinding.MediaHolderBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

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
    binding: MediaHolderBinding,
) : ViewHolder(binding.root) {
    private val ivImage: AppCompatImageView = binding.mediaHolderMediaImage
    private val tvName: AppCompatTextView = binding.mediaHolderMediaName
    private val tvDescription: AppCompatTextView = binding.mediaHolderMediaDescription

    fun bind(media: Media) {
        when (media.type) {
            MediaTypeResponse.movie -> {
                val movie = media.movie
                tvName.text = movie?.title
                tvDescription.text = movie?.year.toString()
            }
            MediaTypeResponse.show -> {
                val show = media.show
                tvName.text = show?.title
                tvDescription.text = show?.year.toString()
            }
        }
        Picasso.get().load(media.get()?.mediaUrl).into(ivImage)
    }
}

object MediaDiff : DiffUtil.ItemCallback<Media>() {
    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
        return oldItem.equalsTo(newItem)
    }
}
