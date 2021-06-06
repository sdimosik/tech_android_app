package android.technopolis.films.ui.profile

import android.technopolis.films.repository.MainRepository
import android.technopolis.films.ui.profile.favorite.FavoriteInProfileItemModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _text = MutableStateFlow("This is profile Fragment")
    val text: StateFlow<String> = _text.asStateFlow()

    private val tempList = MutableStateFlow(
        listOf(
            FavoriteInProfileItemModel(1),
            FavoriteInProfileItemModel(2),
            FavoriteInProfileItemModel(3),
            FavoriteInProfileItemModel(4),
            FavoriteInProfileItemModel(5),
            FavoriteInProfileItemModel(6),
            FavoriteInProfileItemModel(7),
            FavoriteInProfileItemModel(8),
            FavoriteInProfileItemModel(9),
            FavoriteInProfileItemModel(10),
            FavoriteInProfileItemModel(11),
            FavoriteInProfileItemModel(12),
            FavoriteInProfileItemModel(13),
            FavoriteInProfileItemModel(14),
            FavoriteInProfileItemModel(15),
            FavoriteInProfileItemModel(16),
            FavoriteInProfileItemModel(17),
            FavoriteInProfileItemModel(18)
        )
    )

    fun observeFavoriteList(): StateFlow<List<FavoriteInProfileItemModel>> {
        return tempList
    }

    fun updateUserSetting() = mainRepository.getUserSettings()

    fun getUserSetting() = mainRepository.userSettings
}
