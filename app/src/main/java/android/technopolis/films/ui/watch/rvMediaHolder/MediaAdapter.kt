package android.technopolis.films.ui.watch.rvMediaHolder

import android.technopolis.films.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MediaAdapter(
    private val _media: List<Media>
) : RecyclerView.Adapter<MediaAdapter.MediaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.media_holder, parent, false)
        return MediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(_media[0])
    }

    override fun getItemCount(): Int {
        return _media.size
    }

    class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val _ivImage: ImageView = itemView.findViewById(R.id.media_holder__media_image)
        private val _tvName: TextView = itemView.findViewById(R.id.media_holder__media_name)
        private val _tvDescription: TextView =
            itemView.findViewById(R.id.media_holder__media_description)

        fun bind(media: Media) {
            _ivImage.setBackgroundColor(0x00FF00)
            _tvName.text = "Just Media Name"
            _tvDescription.text = "Just Media Description"
        }
    }

}