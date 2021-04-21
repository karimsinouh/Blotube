package com.example.blotube.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.blotube.api.database.Database
import com.example.blotube.util.NightMode
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Settings(
    db: Database
    ){

    val c= LocalContext.current

    val scope= rememberCoroutineScope()

    val scrollState= rememberScrollState()

    val switchColor=SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colors.primary)

    val nightMode=NightMode.isEnabled(c).collectAsState(initial = false)

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        ListItem (
            icon= { Icon(imageVector = Icons.Outlined.DarkMode, contentDescription = "") }
        ){
            Row {
                Text(text = "Dark Theme",
                    Modifier
                        .fillMaxWidth()
                        .weight(0.9f))

                Switch(
                    checked = nightMode.value,
                    onCheckedChange = {
                        scope.launch {
                            NightMode.setEnabled(c,it)
                        } },
                    colors = switchColor)
            }
        }

        Divider()
        ListItem(
            icon= { Icon(imageVector = Icons.Outlined.Book, contentDescription = "") }
        ) {
            Text(text = "Clear Read Later")
        }


        Divider()
        ListItem(
            icon= { Icon(imageVector = Icons.Outlined.WatchLater, contentDescription = "") }

        ) {
            Text(text = "Clear Watch Later")
        }

        Divider()
        ListItem(
            icon= { Icon(imageVector = Icons.Outlined.Feedback, contentDescription = "") }
        ) {
            Text(text = "Feedback")
        }
    }
    
}