package android.technopolis.films.ui.base

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.api.trakt.TraktApiConfig
import android.technopolis.films.api.trakt.TraktClientGenerator
import android.technopolis.films.databinding.ActivityMainBinding
import android.technopolis.films.db.UserSettingDatabase
import android.technopolis.films.repository.MainRepository
import android.technopolis.films.ui.feed.FeedViewModel
import android.technopolis.films.ui.feed.FeedViewModelFactory
import android.technopolis.films.ui.profile.ProfileViewModel
import android.technopolis.films.ui.profile.ProfileViewModelFactory
import android.technopolis.films.ui.watch.WatchViewModel
import android.technopolis.films.ui.watch.WatchViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var watchViewModel: WatchViewModel
    lateinit var feedViewModel: FeedViewModel
    lateinit var profileViewModel: ProfileViewModel

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            TraktClientGenerator.preferences =
                getSharedPreferences(TRAKT_API_PREFERENCES, MODE_PRIVATE)
        } catch (ex: ExceptionInInitializerError) {
            println(ex)
            //do login
            val loginUrl = TraktClientGenerator.loginUrl
            //redirect user on this login url, obtain code
            val code = ""//todo
            TraktClientGenerator.doLogin(code)
            while (true) {
                if (TraktApiConfig.token != null) {
                    break
                }
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setUpViewModel()
        setUpNavController()
    }

    private fun setUpViewModel() {

        val mainRepository = MainRepository(
            UserSettingDatabase(this)
        )

        setUpViewModelFeed(mainRepository)
        setUpViewModelProfile(mainRepository)
        setUpViewModelWatch(mainRepository)
    }

    private fun setUpViewModelWatch(mainRepository: MainRepository) {
        val watchViewModelProviderFactory =
            WatchViewModelFactory(
                mainRepository
            )

        watchViewModel = ViewModelProvider(
            this,
            watchViewModelProviderFactory
        ).get(WatchViewModel::class.java)
    }

    private fun setUpViewModelFeed(mainRepository: MainRepository) {

        val feedViewModelProviderFactory =
            FeedViewModelFactory(
                mainRepository
            )

        feedViewModel = ViewModelProvider(
            this,
            feedViewModelProviderFactory
        ).get(FeedViewModel::class.java)
    }

    private fun setUpViewModelProfile(mainRepository: MainRepository) {

        val profileViewModelProviderFactory =
            ProfileViewModelFactory(
                mainRepository
            )

        profileViewModel = ViewModelProvider(
            this,
            profileViewModelProviderFactory
        ).get(ProfileViewModel::class.java)
    }


    private fun setUpNavController() {
        navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
    }

    companion object {
        private const val TRAKT_API_PREFERENCES = "trakt_api_preferences"
    }
}