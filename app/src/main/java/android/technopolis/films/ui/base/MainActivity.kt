package android.technopolis.films.ui.base

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.api.ApiConfig
import android.technopolis.films.api.TraktClientGenerator
import android.technopolis.films.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
                if (ApiConfig.token != null) {
                    break
                }
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setUpNavController()
    }

    private fun setUpNavController() {
        navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
    }

    companion object {
        private const val TRAKT_API_PREFERENCES = "trakt_api_preferences"
    }
}