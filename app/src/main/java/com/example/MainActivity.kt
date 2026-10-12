package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.audio.SoundEffects
import com.example.audio.SpeechManager
import com.example.data.ProgressManager
import com.example.model.Language
import com.example.model.Subject
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.KidsTopBar
import com.example.ui.components.ParentGateDialog
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

sealed class AppScreen {
  object Home : AppScreen()
  object English : AppScreen()
  object Urdu : AppScreen()
  object Math : AppScreen()
  object Science : AppScreen()
  object Games : AppScreen()
  object ParentDashboard : AppScreen()
}

class MainActivity : ComponentActivity() {

  private lateinit var progressManager: ProgressManager
  private lateinit var soundEffects: SoundEffects
  private lateinit var speechManager: SpeechManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    progressManager = ProgressManager(this)
    soundEffects = SoundEffects { progressManager.soundEnabledFlow.value }
    speechManager = SpeechManager(this) { progressManager.voiceEnabledFlow.value }

    setContent {
      val currentLanguage by progressManager.languageFlow.collectAsStateWithLifecycle()
      val starsCount by progressManager.starsFlow.collectAsStateWithLifecycle()
      val soundEnabled by progressManager.soundEnabledFlow.collectAsStateWithLifecycle()
      val voiceEnabled by progressManager.voiceEnabledFlow.collectAsStateWithLifecycle()
      val dailyGoal by progressManager.dailyGoalFlow.collectAsStateWithLifecycle()
      val ageGroup by progressManager.ageGroupFlow.collectAsStateWithLifecycle()
      val completedLessons by progressManager.completedLessonsFlow.collectAsStateWithLifecycle()

      var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Home) }
      var showParentGate by remember { mutableStateOf(false) }
      var celebrationStars by remember { mutableStateOf<Int?>(null) }

      // Handle Android back button
      if (currentScreen != AppScreen.Home) {
        BackHandler {
          currentScreen = AppScreen.Home
        }
      }

      val screenTitle = when (currentScreen) {
        AppScreen.Home -> if (currentLanguage == Language.ENGLISH) "Little Genius" else "لٹل جینیئس"
        AppScreen.English -> if (currentLanguage == Language.ENGLISH) "English Learning" else "انگریزی تعلیم"
        AppScreen.Urdu -> if (currentLanguage == Language.ENGLISH) "Urdu Learning" else "اردو سیکھیں"
        AppScreen.Math -> if (currentLanguage == Language.ENGLISH) "Mathematics" else "ریاضی"
        AppScreen.Science -> if (currentLanguage == Language.ENGLISH) "Science" else "سائنس"
        AppScreen.Games -> if (currentLanguage == Language.ENGLISH) "Learning Games" else "تعلیمی کھیل"
        AppScreen.ParentDashboard -> if (currentLanguage == Language.ENGLISH) "Parent Zone" else "والدین سیکشن"
      }

      val layoutDir = if (currentLanguage == Language.URDU) LayoutDirection.Rtl else LayoutDirection.Ltr

      MyApplicationTheme {
        CompositionLocalProvider(LocalLayoutDirection provides layoutDir) {
          Scaffold(
            topBar = {
              KidsTopBar(
                title = screenTitle,
                starsCount = starsCount,
                currentLanguage = currentLanguage,
                isSoundEnabled = soundEnabled,
                onBackClick = if (currentScreen != AppScreen.Home) {
                  {
                    soundEffects.playTap()
                    currentScreen = AppScreen.Home
                  }
                } else null,
                onLanguageToggle = {
                  soundEffects.playTap()
                  progressManager.toggleLanguage()
                },
                onSoundToggle = {
                  soundEffects.playTap()
                  progressManager.setSoundEnabled(!soundEnabled)
                }
              )
            }
          ) { innerPadding ->
            Box(
              modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
            ) {
              when (currentScreen) {
                AppScreen.Home -> HomeScreen(
                  language = currentLanguage,
                  starsCount = starsCount,
                  dailyGoal = dailyGoal,
                  completedLessonsCount = completedLessons.size,
                  onSubjectClick = { subject ->
                    soundEffects.playTap()
                    currentScreen = when (subject) {
                      Subject.ENGLISH -> AppScreen.English
                      Subject.URDU -> AppScreen.Urdu
                      Subject.MATHEMATICS -> AppScreen.Math
                      Subject.SCIENCE -> AppScreen.Science
                    }
                  },
                  onGamesClick = {
                    soundEffects.playTap()
                    currentScreen = AppScreen.Games
                  },
                  onParentDashboardClick = {
                    soundEffects.playTap()
                    showParentGate = true
                  }
                )

                AppScreen.English -> EnglishScreen(
                  language = currentLanguage,
                  speechManager = speechManager,
                  soundEffects = soundEffects,
                  onAwardStars = { stars ->
                    progressManager.addStars(stars)
                    celebrationStars = stars
                  },
                  onLessonCompleted = { key ->
                    progressManager.markLessonCompleted(key)
                  }
                )

                AppScreen.Urdu -> UrduScreen(
                  language = currentLanguage,
                  speechManager = speechManager,
                  soundEffects = soundEffects,
                  onAwardStars = { stars ->
                    progressManager.addStars(stars)
                    celebrationStars = stars
                  },
                  onLessonCompleted = { key ->
                    progressManager.markLessonCompleted(key)
                  }
                )

                AppScreen.Math -> MathScreen(
                  language = currentLanguage,
                  speechManager = speechManager,
                  soundEffects = soundEffects,
                  onAwardStars = { stars ->
                    progressManager.addStars(stars)
                    celebrationStars = stars
                  },
                  onLessonCompleted = { key ->
                    progressManager.markLessonCompleted(key)
                  }
                )

                AppScreen.Science -> ScienceScreen(
                  language = currentLanguage,
                  speechManager = speechManager,
                  soundEffects = soundEffects,
                  onAwardStars = { stars ->
                    progressManager.addStars(stars)
                    celebrationStars = stars
                  },
                  onLessonCompleted = { key ->
                    progressManager.markLessonCompleted(key)
                  }
                )

                AppScreen.Games -> GamesHubScreen(
                  language = currentLanguage,
                  speechManager = speechManager,
                  soundEffects = soundEffects,
                  onAwardStars = { stars ->
                    progressManager.addStars(stars)
                    celebrationStars = stars
                  },
                  onLessonCompleted = { key ->
                    progressManager.markLessonCompleted(key)
                  }
                )

                AppScreen.ParentDashboard -> ParentDashboardScreen(
                  language = currentLanguage,
                  progressManager = progressManager,
                  isSoundEnabled = soundEnabled,
                  isVoiceEnabled = voiceEnabled,
                  selectedAgeGroup = ageGroup,
                  completedLessonsCount = completedLessons.size,
                  totalStars = starsCount
                )
              }
            }

            // Parental gate dialog
            if (showParentGate) {
              ParentGateDialog(
                language = currentLanguage,
                onAccessGranted = {
                  showParentGate = false
                  soundEffects.playTap()
                  currentScreen = AppScreen.ParentDashboard
                },
                onDismiss = { showParentGate = false }
              )
            }

            // Animated reward celebration dialog
            celebrationStars?.let { stars ->
              CelebrationDialog(
                starsEarned = stars,
                language = currentLanguage,
                onDismiss = { celebrationStars = null }
              )
            }
          }
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    speechManager.release()
    soundEffects.release()
  }
}
