package org.sjhstudio.lostark.di

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ManagerModule {

    @Provides
    @ActivityScoped
    fun provideInputMethodManager(activity: Activity): InputMethodManager {
        return activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
}