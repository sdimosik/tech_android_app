package android.technopolis.films.repository

import android.technopolis.films.api.tmdb.TmdbClientGenerator
import android.technopolis.films.api.trakt.model.media.MediaType
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TmdbRepository {
    private val client = TmdbClientGenerator.getClient()

    private lateinit var load: RequestCreator

    fun bindImage(imageView: ImageView, type: MediaType, mediaId: Int, url: MutableLiveData<String>) {
        GlobalScope.launch(Dispatchers.IO) {
            url.postValue(client.getImageUrl(type, mediaId.toString(), null))
//            load = Picasso.get().load(imageUrl)
        }
    }

}