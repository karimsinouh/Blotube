package com.example.blotube.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blotube.R
import com.example.blotube.api.database.Database
import com.example.blotube.util.NightMode
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Settings(
    db: Database
    ){

    val readLaterState=remember{ mutableStateOf(false) }
    val watchLaterState=remember{ mutableStateOf(false) }

    val c= LocalContext.current

    val scope= rememberCoroutineScope()

    val switchColor=SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colors.primary)

    val nightMode=NightMode.isEnabled(c).collectAsState(initial = false)

    Column(Modifier.fillMaxSize()) {

        ListItem (
            icon= { Icon(imageVector = Icons.Outlined.DarkMode, contentDescription = "") }
        ){
            Row {
                Text(stringResource(R.string.night_mode),
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
            icon= { Icon(imageVector = Icons.Outlined.Book, contentDescription = "") },
            modifier = Modifier.clickable {
                readLaterState.value=true
            }) {
            Text(stringResource(R.string.clear_read_later))
        }


        Divider()
        ListItem(
            icon= { Icon(imageVector = Icons.Outlined.WatchLater, contentDescription = "") },
            modifier = Modifier.clickable {
                watchLaterState.value=true
            }
        ) {
            Text(stringResource(R.string.clear_watch_later))
        }

        Divider()
        ListItem(
            icon= { Icon(imageVector = Icons.Outlined.Feedback, contentDescription = "") }
            ) {
            Text(text = stringResource(R.string.feedback))
        }
    }


    //dialogs

    if (watchLaterState.value)
        ConfirmDialog(
            stringResource(R.string.clear_watch_later),
            text = stringResource(R.string.clear_watch_later_text) ,
            onDismiss = { watchLaterState.value=false })
        {
            scope.launch {
                db.videos().deleteAll()
            }
        }


    if (readLaterState.value)
        ConfirmDialog(
            stringResource(R.string.clear_read_later),
            text = stringResource(R.string.clear_read_later_text) ,
            onDismiss = { readLaterState.value=false })
        {
            scope.launch {
                db.posts().deleteAll()
            }
        }
    
}

@Composable
fun ConfirmDialog(
    title:String,
    text:String,
    onDismiss:()->Unit,
    onConfirm:()->Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text= { Text(text) },
        buttons = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.CenterEnd
            ){
                TextButton(onClick = onConfirm) {
                    Text(stringResource(R.string.confirm))
                }
            }
        }
    )
}