package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.ProgressManager
import com.example.model.Language
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Little Genius", appName)
  }

  @Test
  fun `test progress manager persists stars and language`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val progressManager = ProgressManager(context)
    val initialStars = progressManager.starsFlow.value
    progressManager.addStars(5)
    assertEquals(initialStars + 5, progressManager.starsFlow.value)

    progressManager.setLanguage(Language.URDU)
    assertEquals(Language.URDU, progressManager.languageFlow.value)

    progressManager.setLanguage(Language.ENGLISH)
    assertEquals(Language.ENGLISH, progressManager.languageFlow.value)
  }
}
