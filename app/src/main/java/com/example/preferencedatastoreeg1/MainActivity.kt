package com.example.preferencedatastoreeg1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.preferencedatastoreeg1.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val Context.datastorePref: DataStore<Preferences> by preferencesDataStore(name = "sample_file")
    private val key = stringPreferencesKey("sample_key")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            lifecycleScope.launch {
                saveValue("sample_value")
            }
        }

        binding.btn2.setOnClickListener {
            lifecycleScope.launch {
                getValue().collectLatest {
                    binding.textValue.text = it
                }
            }
        }





    }


    fun getValue():Flow<String>{
        return datastorePref.data.map { it[key] ?:"" }
    }

    suspend fun saveValue(value:String){
        datastorePref.edit {
            it[key] = value
        }
    }

}