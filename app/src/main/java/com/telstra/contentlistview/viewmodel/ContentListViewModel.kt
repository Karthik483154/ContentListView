package com.telstra.contentlistview.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.telstra.contentlistview.service.model.UserContent
import com.telstra.contentlistview.service.repository.UserContentRepository
import org.jetbrains.annotations.TestOnly

/**
 * Created by Karthikeyan 06/07/2020
 *
 * @author Karthikeyan
 * @version 1.0
 *
 *  This is View Model Component that expose the LiveData user content query so the UI can observe it
 */

class ContentListViewModel(application: Application) : AndroidViewModel(application) {

    private val contentListObservable: LiveData<UserContent> = UserContentRepository.instance?.getUserContentDetails() as LiveData<UserContent>

    private lateinit var isLoading: MutableLiveData<UserContent>
    private lateinit var isShowError: MutableLiveData<UserContent>

    /**
     * This is method expose the LiveData user content query so the UI can observe it
     */
    fun getContentListObservable(): LiveData<UserContent> {
        return contentListObservable
    }

    @TestOnly
    fun getIsLoading(): LiveData<UserContent> {
        if (!::isLoading.isInitialized) {
            isLoading = MutableLiveData()
            isLoading.value = contentListObservable.value
        }
        return isLoading
    }

    @TestOnly
    fun showError(): LiveData<UserContent> {
        if (!::isShowError.isInitialized) {
            isShowError = MutableLiveData()
            isShowError.value = null
        }
        return isShowError
    }

    @TestOnly
    fun loadUserContent() {
        try {
            isLoading.value = contentListObservable.value
        } catch (e: Exception) {
            isShowError.value = null
            e.printStackTrace()
        } finally {
            isLoading.value = contentListObservable.value
        }
    }
}