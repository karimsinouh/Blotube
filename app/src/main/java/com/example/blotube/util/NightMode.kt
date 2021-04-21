package com.example.blotube.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

object NightMode {

    private val NIGHT_MODE= booleanPreferencesKey("night_mode")
    private val Context.nightDataStore:DataStore<Preferences> by preferencesDataStore(name="night_mode")

    fun isEnabled(c:Context):Flow<Boolean>{
        return c.nightDataStore.data.map {
            it[NIGHT_MODE] ?: false
        }
    }

    suspend fun setEnabled(c:Context,value:Boolean){
        c.nightDataStore.edit {
            it[NIGHT_MODE]=value
        }
    }

}