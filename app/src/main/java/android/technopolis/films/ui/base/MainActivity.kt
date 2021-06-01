package android.technopolis.films.ui.base

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.api.ApiConfig
import android.technopolis.films.api.TraktClientGenerator
import android.technopolis.films.databinding.ActivityMainBinding
import android.technopolis.films.repository.MainRepository
import android.technopolis.films.ui.calendar.CalendarViewModel
import android.technopolis.films.ui.calendar.CalendarViewModelFactory
import android.technopolis.films.ui.feed.FeedViewModel
import android.technopolis.films.ui.feed.FeedViewModelFactory
import android.technopolis.films.ui.history.HistoryViewModel
import android.technopolis.films.ui.history.HistoryViewModelFactory
import android.technopolis.films.ui.profile.ProfileViewModel
import android.technopolis.films.ui.profile.ProfileViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var calendarViewModel: CalendarViewModel
    lateinit var feedViewModel: FeedViewModel
    lateinit var historyViewModel: HistoryViewModel
    lateinit var profileViewModel: ProfileViewModel

    private lateinit var navController: NavController
    private val destinations = setOf(
        R.id.navigation_watch,
        R.id.navigation_calendar,
        R.id.navigation_feed,
        R.id.navigation_history,
        R.id.navigation_profile
    )

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
                if (ApiConfig.token != null) {
                    break
                }
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setUpViewModels()
        setUpNavController()
    }

    private fun setUpNavController() {
        val appBarConfiguration = AppBarConfiguration(destinations)

        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    private fun setUpViewModels() {
        val mainRepository = MainRepository()

        setUpViewModelCalendar(mainRepository)
        setUpViewModelFeed(mainRepository)
        setUpViewModelHistory(mainRepository)
        setUpViewModelProfile(mainRepository)
    }

    private fun setUpViewModelCalendar(mainRepository: MainRepository) {

        val calendarViewModelProviderFactory =
            CalendarViewModelFactory(
                mainRepository
            )

        calendarViewModel = ViewModelProvider(
            this,
            calendarViewModelProviderFactory
        ).get(CalendarViewModel::class.java)
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

    private fun setUpViewModelHistory(mainRepository: MainRepository) {

        val historyViewModelProviderFactory =
            HistoryViewModelFactory(
                mainRepository
            )

        historyViewModel = ViewModelProvider(
            this,
            historyViewModelProviderFactory
        ).get(HistoryViewModel::class.java)
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

    companion object {
        private const val TRAKT_API_PREFERENCES = "trakt_api_preferences"
    }
}