package com.example.simpleroomretroapp.view_models

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simpleroomretroapp.data.Repository
import com.example.simpleroomretroapp.data.database.entities.UserEntity
import com.example.simpleroomretroapp.models.UserData
import com.example.simpleroomretroapp.utilities.UserTypeConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {

    //livedata
    val savedUsers = MutableLiveData<List<UserData>>()
    val usersNetResponse = MutableLiveData<List<UserData>>()

    var localUserUpdated = false

    //room database
    fun getLocalUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val users = repository.local.getAllUsers()
            users.collect {entityList ->
                Log.d("MainViewModel", "getLocalUsers INFO loaded users from db: $entityList")
                if (entityList.isNotEmpty()) {
                    val userList = arrayListOf<UserData>()
                    entityList.forEach{userList.add(UserTypeConverter.userEntityToUserData(it))}
                    savedUsers.postValue(userList)
                } else {
                    savedUsers.postValue(arrayListOf())
                }
            }
            //reverting updated boolean to false
            localUserUpdated = false
        }
    }

    fun deleteAllLocalUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllUsers()
        }
    }

    fun deleteLocalUser(userData: UserData) {
        Log.d("MainViewModel", "deleteLocalUser INFO deleting user: $userData")
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteUser(UserTypeConverter.userDataToUserEntity(userData))
        }
    }

    private fun saveUserList(userList: List<UserData>) {
        Log.d("MainViewModel", "saveUserList INFO saving userList to DB: $userList")
        viewModelScope.launch(Dispatchers.IO) {
            val userEntityList = arrayListOf<UserEntity>()
            userList.forEach {userData ->
                userEntityList.add(UserTypeConverter.userDataToUserEntity(userData))
            }
            repository.local.insertUserList(userEntityList)
        }
    }

    fun updateUser(userData: UserData) {
        Log.d("MainViewModel", "updateUser INFO userUpdate: $userData")
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertUser(UserTypeConverter.userDataToUserEntity(userData))
            localUserUpdated = true
        }
    }

    //retrofit
    fun getRemoteUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (hasInternetConnection()){
                    val response = repository.remote.getUsers(2)
                    if (response.isSuccessful) {
                        usersNetResponse.postValue(response.body()?.data)
                        saveUserList(response.body()?.data!!)
                    }
                } else {
                    usersNetResponse.postValue(arrayListOf())
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "getRemoteUserList ERROR ${e.message} ${e.stackTrace}")
            }
        }
    }


    /**
     * checking internet connection before each network call
     */
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}