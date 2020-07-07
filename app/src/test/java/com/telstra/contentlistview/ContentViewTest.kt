package com.telstra.contentlistview

import com.telstra.contentlistview.view.MainActivity
import com.telstra.contentlistview.viewmodels.ContentViewModel
import org.junit.Test
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.spy

class ContentViewTest {

    @Test
    fun test_onCreate() {
        val mainActivity = spy(MainActivity::class.java)
        doNothing().`when`(mainActivity).initView()
        doNothing().`when`(mainActivity).setContentView(R.layout.activity_main)
        doNothing().`when`(mainActivity).loadDataModel()
    }

    @Test
    fun test_checkContentData() {
        val model = spy(ContentViewModel::class.java)
        doNothing().`when`(model).loadUserContentData()
    }
}