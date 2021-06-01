package android.technopolis.films.repository

import android.technopolis.films.api.Trakt
import android.technopolis.films.api.TraktClientGenerator

class MainRepository() : Repository {
    val client: Trakt = TraktClientGenerator.getClient()
}