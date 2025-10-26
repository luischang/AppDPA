package dev.luischang.appdpa.presentation.apifootball

import android.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.luischang.appdpa.data.remote.apifootball.Country
import dev.luischang.appdpa.data.remote.apifootball.RetrofitInstance
import dev.luischang.appdpa.data.remote.apifootball.TeamWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApiFootballViewModel: ViewModel() {
    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries
    private val _selecteCountry = MutableStateFlow<Country?>(null)
    val selectedCountry: StateFlow<Country?> =_selecteCountry
    private val _teams = MutableStateFlow<List<TeamWrapper>>(emptyList())
    val teams: StateFlow<List<TeamWrapper>> = _teams
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadCountries() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getCountries()
                _countries.value = response.response.sortedBy { it.name }
                _errorMessage.value = null
            } catch (e: Exception){
                _errorMessage.value = "Error al obtener los países: ${e.message}"
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun onCountrySelected(country: Country){
        _selecteCountry.value = country
        loadTeamsByCountry(country.name)
    }

    private fun loadTeamsByCountry(countryName: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getTeamsByCountry(countryName)
                _teams.value = response.response
                _errorMessage.value = null
            } catch (e: Exception){
                _errorMessage.value = "Error al obtener los equipos por país: ${e.message}"
                _teams.value = emptyList()
            }finally {
                _isLoading.value = false
            }
        }
    }
}