package dev.luischang.appdpa.presentation.apifootball

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiFootballScreen(viewModel: ApiFootballViewModel = viewModel()){

    val countries by viewModel.countries.collectAsState()
    val selectedCountry by viewModel.selectedCountry.collectAsState()
    val teams by viewModel.teams.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var expanded by remember {mutableStateOf(false)}
    val context = LocalContext.current

    Column (modifier = Modifier.fillMaxSize().padding(16.dp))
    {
        Text("Equipos de fútbol por país"
            , style = MaterialTheme.typography.titleLarge)

        Spacer(modifier= Modifier.height(16.dp))

        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedCountry?.name ?: "Seleccionar país")
            }
            DropdownMenu(expanded = expanded
                , onDismissRequest = {expanded= false}){

                countries.forEach { country ->
                    DropdownMenuItem(
                        text = {Text(country.name)},
                        onClick = {
                            expanded = false
                            viewModel.onCountrySelected(country)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            if(isLoading){
                CircularProgressIndicator()
            }else if(errorMessage!=null){
                Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp))
                {
                    items(teams) { wrapper ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    val query = wrapper.team.name
                                    val intent =
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://www.google.com/search?q=$query")
                                        )
                                    context.startActivity(intent)
                                }
                        ) {
                            Row(modifier = Modifier.padding(12.dp))
                            {
                                Image(painter=
                                    rememberAsyncImagePainter(wrapper.team.logo),
                                    contentDescription = wrapper.team.name,
                                    modifier = Modifier.size(64.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column{
                                    Text(wrapper.team.name, style = MaterialTheme.typography.titleMedium)
                                    Text("Fundado el: ${wrapper.team.founded ?: "Desconocido"}")
                                }
                            }
                        }
                    }
                }
            }



        }

    }


}