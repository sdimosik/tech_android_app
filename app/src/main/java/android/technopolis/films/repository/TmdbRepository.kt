package android.technopolis.films.repository

import android.technopolis.films.api.tmdb.TmdbClientGenerator
import android.technopolis.films.api.trakt.model.media.MediaType
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TmdbRepository {
    private val client = TmdbClientGenerator.getClient()

    fun bindImage(imageView: ImageView, type: MediaType, mediaId: Int) {
        MainScope().launch(Dispatchers.IO) {
            val imageUrl = client.getImageUrl(type, mediaId.toString(), null)
            Picasso.get().load(imageUrl).into(imageView)
        }
    }

}