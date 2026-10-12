package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.DailyGoalProgress
import com.example.model.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProgressManager(context: Context) {

  private val prefs: SharedPreferences =
    context.getSharedPreferences("little_genius_prefs", Context.MODE_PRIVATE)

  private val _starsFlow = MutableStateFlow(prefs.getInt(KEY_STARS, 12))
  val starsFlow: StateFlow<Int> = _starsFlow.asStateFlow()

  private val _languageFlow = MutableStateFlow(
    if (prefs.getString(KEY_LANGUAGE, "en") == "ur") Language.URDU else Language.ENGLISH
  )
  val languageFlow: StateFlow<Language> = _languageFlow.asStateFlow()

  private val _soundEnabledFlow = MutableStateFlow(prefs.getBoolean(KEY_SOUND, true))
  val soundEnabledFlow: StateFlow<Boolean> = _soundEnabledFlow.asStateFlow()

  private val _voiceEnabledFlow = MutableStateFlow(prefs.getBoolean(KEY_VOICE, true))
  val voiceEnabledFlow: StateFlow<Boolean> = _voiceEnabledFlow.asStateFlow()

  private val _dailyGoalFlow = MutableStateFlow(
    DailyGoalProgress(
      targetActivities = prefs.getInt(KEY_DAILY_TARGET, 5),
      completedActivities = prefs.getInt(KEY_DAILY_COMPLETED, 2),
      currentStreakDays = prefs.getInt(KEY_STREAK, 3)
    )
  )
  val dailyGoalFlow: StateFlow<DailyGoalProgress> = _dailyGoalFlow.asStateFlow()

  private val _ageGroupFlow = MutableStateFlow(prefs.getString(KEY_AGE_GROUP, "4-5") ?: "4-5")
  val ageGroupFlow: StateFlow<String> = _ageGroupFlow.asStateFlow()

  private val _completedLessonsFlow = MutableStateFlow(
    prefs.getStringSet(KEY_COMPLETED_LESSONS, setOf("en_A", "ur_ا", "math_1", "sci_animals")) ?: emptySet()
  )
  val completedLessonsFlow: StateFlow<Set<String>> = _completedLessonsFlow.asStateFlow()

  fun addStars(count: Int) {
    val newStars = _starsFlow.value + count
    _starsFlow.value = newStars
    prefs.edit().putInt(KEY_STARS, newStars).apply()

    // Increment daily completed
    val currentGoal = _dailyGoalFlow.value
    val updatedGoal = currentGoal.copy(
      completedActivities = minOf(currentGoal.completedActivities + 1, currentGoal.targetActivities)
    )
    _dailyGoalFlow.value = updatedGoal
    prefs.edit().putInt(KEY_DAILY_COMPLETED, updatedGoal.completedActivities).apply()

    // Record quiz accuracy
    val currentTotal = prefs.getInt(KEY_QUIZ_TOTAL, 8) + 1
    val currentCorrect = prefs.getInt(KEY_QUIZ_CORRECT, 7) + 1
    prefs.edit()
      .putInt(KEY_QUIZ_TOTAL, currentTotal)
      .putInt(KEY_QUIZ_CORRECT, currentCorrect)
      .apply()
  }

  fun recordIncorrectAttempt() {
    val currentTotal = prefs.getInt(KEY_QUIZ_TOTAL, 8) + 1
    prefs.edit().putInt(KEY_QUIZ_TOTAL, currentTotal).apply()
  }

  fun markLessonCompleted(lessonKey: String) {
    val updated = _completedLessonsFlow.value.toMutableSet()
    updated.add(lessonKey)
    _completedLessonsFlow.value = updated
    prefs.edit().putStringSet(KEY_COMPLETED_LESSONS, updated).apply()
  }

  fun setLanguage(language: Language) {
    _languageFlow.value = language
    prefs.edit().putString(KEY_LANGUAGE, language.code).apply()
  }

  fun toggleLanguage() {
    val next = if (_languageFlow.value == Language.ENGLISH) Language.URDU else Language.ENGLISH
    setLanguage(next)
  }

  fun setSoundEnabled(enabled: Boolean) {
    _soundEnabledFlow.value = enabled
    prefs.edit().putBoolean(KEY_SOUND, enabled).apply()
  }

  fun setVoiceEnabled(enabled: Boolean) {
    _voiceEnabledFlow.value = enabled
    prefs.edit().putBoolean(KEY_VOICE, enabled).apply()
  }

  fun setAgeGroup(age: String) {
    _ageGroupFlow.value = age
    prefs.edit().putString(KEY_AGE_GROUP, age).apply()
  }

  fun getQuizStats(): Pair<Int, Int> {
    val correct = prefs.getInt(KEY_QUIZ_CORRECT, 7)
    val total = prefs.getInt(KEY_QUIZ_TOTAL, 8)
    return Pair(correct, maxOf(correct, total))
  }

  fun getEstimatedLearningMinutes(): Int {
    return prefs.getInt(KEY_LEARNING_MINUTES, 35) + (_starsFlow.value / 3)
  }

  fun resetAllProgress() {
    prefs.edit().clear().apply()
    _starsFlow.value = 5
    _dailyGoalFlow.value = DailyGoalProgress(5, 0, 1)
    _completedLessonsFlow.value = emptySet()
  }

  companion object {
    private const val KEY_STARS = "stars_count"
    private const val KEY_LANGUAGE = "language_code"
    private const val KEY_SOUND = "sound_enabled"
    private const val KEY_VOICE = "voice_enabled"
    private const val KEY_DAILY_TARGET = "daily_target"
    private const val KEY_DAILY_COMPLETED = "daily_completed"
    private const val KEY_STREAK = "streak_days"
    private const val KEY_AGE_GROUP = "age_group"
    private const val KEY_COMPLETED_LESSONS = "completed_lessons"
    private const val KEY_QUIZ_CORRECT = "quiz_correct"
    private const val KEY_QUIZ_TOTAL = "quiz_total"
    private const val KEY_LEARNING_MINUTES = "learning_minutes"
  }
}
