package com.telstra.contentlistview

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.telstra.contentlistview.service.model.UserContent
import com.telstra.contentlistview.service.model.UserContentRows
import com.telstra.contentlistview.view.ui.MainActivity
import com.telstra.contentlistview.viewmodel.ContentListViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    private lateinit var activity: MainActivity

    private lateinit var activityController: ActivityController<MainActivity>

    @Mock
    private lateinit var viewModel: ContentListViewModel

    @Mock
    private lateinit var userContentData: LiveData<UserContent>

    @Mock
    private lateinit var isLoadingLiveData: LiveData<Boolean>

    @Mock
    private lateinit var isErrorLiveData: LiveData<Boolean>

    @Mock
    private lateinit var contentObserverCaptor: ArgumentCaptor<Observer<UserContent>>

    @Mock
    private lateinit var isLoadingObserverCaptor: ArgumentCaptor<Observer<Boolean>>

    @Mock
    private lateinit var isErrorObserverCaptor: ArgumentCaptor<Observer<Boolean>>

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        activityController = Robolectric.buildActivity(MainActivity::class.java)
        activity = activityController.get()

        activityController.create()
        activity.setTestViewModel(viewModel)
        activityController.start()
        verify(userContentData).observe(ArgumentMatchers.any(LifecycleOwner::class.java), contentObserverCaptor.capture())
        verify(isLoadingLiveData).observe(ArgumentMatchers.any(LifecycleOwner::class.java), isLoadingObserverCaptor.capture())
        verify(isErrorLiveData).observe(ArgumentMatchers.any(LifecycleOwner::class.java), isErrorObserverCaptor.capture())
    }

    @Test
    fun testActNotNull() {
        val activity = Robolectric.buildActivity(MainActivity::class.java)
        assertNotNull(activity)
    }

    @Test
    fun has_visible_loading_view_on_create() {
        assertEquals(View.VISIBLE, activity.progressBar.visibility)
    }

    @Test
    fun has_hidden_recycler_view_error_view_on_create() {
        assertEquals(View.GONE, activity.swipeContentView.visibility)
        assertEquals(View.GONE, activity.contentErrorMessage.visibility)
    }

    @Test
    fun displays_content_list_when_available() {
        val userContentRowList = listOf(
            UserContentRows(
                "Beavers",
                "Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony",
                "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
            )
        )

        isLoadingObserverCaptor.value.onChanged(false)
        isErrorObserverCaptor.value.onChanged(false)

        assertEquals(View.VISIBLE, activity.contentRecyclerView.visibility)
        assertEquals(View.GONE, activity.progressBar.visibility)
        assertEquals(View.GONE, activity.contentErrorMessage.visibility)
    }

    @Test
    fun displays_error_view() {
        isLoadingObserverCaptor.value.onChanged(false)
        isErrorObserverCaptor.value.onChanged(true)

        assertEquals(View.GONE, activity.contentRecyclerView.visibility)
        assertEquals(View.GONE, activity.progressBar.visibility)
        assertEquals(View.VISIBLE, activity.contentErrorMessage.visibility)
    }
}