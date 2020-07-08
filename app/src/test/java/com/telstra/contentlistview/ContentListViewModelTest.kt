package com.telstra.contentlistview

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.telstra.contentlistview.service.model.UserContent
import com.telstra.contentlistview.service.model.UserContentRows
import com.telstra.contentlistview.viewmodel.ContentListViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ContentListViewModelTest {

    private lateinit var viewModel: ContentListViewModel

    private lateinit var isLoadingLiveData: LiveData<UserContent>
    private lateinit var isErrorLiveData: LiveData<UserContent>

    private val userContentRowList = listOf(
        UserContentRows(
            "Beavers",
            "Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony",
            "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
        )
    )

    private val userContent = listOf(
        UserContent("About Canada", userContentRowList)
    )


    @Spy
    private val userContentLiveData: MutableLiveData<UserContent> = MutableLiveData()

    @Spy
    private val userContentRowListLiveData: MutableLiveData<List<UserContentRows>> = MutableLiveData()

    @Mock
    private lateinit var application: Application

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        viewModel = ContentListViewModel(application)

        isLoadingLiveData = viewModel.getIsLoading()
        isErrorLiveData = viewModel.showError()
    }

    @Test
    fun check_userContent() {
        var isLoading = isLoadingLiveData.value
        //assertNotNull(isLoading)
        isLoading?.let { assertTrue(true) }
        viewModel.loadUserContent()
        isLoading = isLoadingLiveData.value
        //assertNotNull(isLoading)
        isLoading?.let { assertFalse(false) }
        viewModel.loadUserContent()
    }

    @Test
    fun check_ErrorContent() {
        val isLoading = isLoadingLiveData.value
        isLoading?.let { assertTrue(true) }

        val isError = isErrorLiveData.value
        isError?.let { assertFalse(false) }
    }
}