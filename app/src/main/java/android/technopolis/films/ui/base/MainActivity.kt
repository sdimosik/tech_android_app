package android.technopolis.films.ui.base

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.ActivityMainBinding
import android.technopolis.films.repository.MainRepository
import android.technopolis.films.repository.Repository
import android.technopolis.films.ui.calendar.CalendarFragment
import android.technopolis.films.ui.calendar.CalendarViewModel
import android.technopolis.films.ui.calendar.CalendarViewModelFactory
import android.technopolis.films.ui.feed.FeedFragment
import android.technopolis.films.ui.feed.FeedViewModel
import android.technopolis.films.ui.feed.FeedViewModelFactory
import android.technopolis.films.ui.history.HistoryFragment
import android.technopolis.films.ui.history.HistoryViewModel
import android.technopolis.films.ui.history.HistoryViewModelFactory
import android.technopolis.films.ui.profile.ProfileFragment
import android.technopolis.films.ui.profile.ProfileViewModel
import android.technopolis.films.ui.profile.ProfileViewModelFactory
import android.technopolis.films.ui.watch.WatchFragment
import android.technopolis.films.ui.watch.WatchViewModel
import android.technopolis.films.ui.watch.WatchViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    private val watchFragment = WatchFragment()
    private val calendarFragment = CalendarFragment()
    private val feedFragment = FeedFragment()
    private val historyFragment = HistoryFragment()
    private val profileFragment = ProfileFragment()

    val repository: Repository
        get() = MainRepository()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setUpViewModels()
        setUpNavController()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment).commit()
    }

    private fun setUpNavController() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_watch,
                R.id.navigation_calendar,
                R.id.navigation_feed,
                R.id.navigation_history,
                R.id.navigation_profile
            )
        )

        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        showFragment(FeedFragment())
        binding.navView.menu.findItem(R.id.navigation_feed).isChecked = true

        binding.navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_watch -> {
                    showFragment(watchFragment)
                    true
                }
                R.id.navigation_calendar -> {
                    showFragment(calendarFragment)
                    true
                }
                R.id.navigation_feed -> {
                    showFragment(feedFragment)
                    true
                }
                R.id.navigation_history -> {
                    showFragment(historyFragment)
                    true
                }
                R.id.navigation_profile -> {
                    showFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
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
}