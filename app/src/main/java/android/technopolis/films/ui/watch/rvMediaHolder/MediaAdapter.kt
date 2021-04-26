package android.technopolis.films.ui.watch.rvMediaHolder

import android.technopolis.films.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MediaAdapter : ListAdapter<Media, MediaViewHolder>(MediaDiff){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.media_holder, parent, false)
        return MediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MediaViewHolder(itemView: View) : ViewHolder(itemView) {
    private val ivImage: ImageView = itemView.findViewById(R.id.media_holder__media_image)
    private val tvName: TextView = itemView.findViewById(R.id.media_holder__media_name)
    private val tvDescription: TextView =
        itemView.findViewById(R.id.media_holder__media_description)

    fun bind(media: Media) {
        ivImage.setBackgroundColor(0x00FF00)
        tvName.text = "Just Media Name"
        tvDescription.text = "Just Media Description"
    }
}

object MediaDiff: DiffUtil.ItemCallback<Media>() {
    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
        return false
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
        return false
        TODO("Not yet implemented")
    }
}
