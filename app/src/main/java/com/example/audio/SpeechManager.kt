package com.example.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

class SpeechManager(
  private val context: Context,
  private val isVoiceEnabled: () -> Boolean
) : TextToSpeech.OnInitListener {

  private var tts: TextToSpeech? = null
  private var isInitialized = false
  private var isUrduAvailable = false

  init {
    try {
      tts = TextToSpeech(context.applicationContext, this)
    } catch (e: Exception) {
      Log.e("SpeechManager", "Failed to construct TextToSpeech", e)
    }
  }

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      isInitialized = true
      tts?.setPitch(1.15f) // Slightly higher friendly tone for kids
      tts?.setSpeechRate(0.85f) // Gentle, clear speed for early learners

      // Test Urdu locale availability
      val urduLocale = Locale("ur", "PK")
      val urduRes = tts?.isLanguageAvailable(urduLocale) ?: TextToSpeech.LANG_NOT_SUPPORTED
      isUrduAvailable = (urduRes >= TextToSpeech.LANG_AVAILABLE)
      Log.d("SpeechManager", "TTS initialized. Urdu available: $isUrduAvailable")
    } else {
      Log.w("SpeechManager", "TTS init returned status $status")
    }
  }

  fun speakEnglish(text: String) {
    if (!isVoiceEnabled() || !isInitialized || tts == null) return
    try {
      tts?.language = Locale.US
      tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utterance_en_${System.currentTimeMillis()}")
    } catch (e: Exception) {
      Log.e("SpeechManager", "Error speaking English text", e)
    }
  }

  fun speakUrdu(urduText: String, fallbackPhonetic: String = "") {
    if (!isVoiceEnabled() || !isInitialized || tts == null) return
    try {
      if (isUrduAvailable) {
        tts?.language = Locale("ur", "PK")
        tts?.speak(urduText, TextToSpeech.QUEUE_FLUSH, null, "utterance_ur_${System.currentTimeMillis()}")
      } else {
        // Fallback to English phonetic voice so the child still hears the pronunciation!
        val speech = if (fallbackPhonetic.isNotBlank()) fallbackPhonetic else urduText
        tts?.language = Locale.US
        tts?.speak(speech, TextToSpeech.QUEUE_FLUSH, null, "utterance_fallback_${System.currentTimeMillis()}")
      }
    } catch (e: Exception) {
      Log.e("SpeechManager", "Error speaking Urdu text", e)
    }
  }

  fun stop() {
    try {
      tts?.stop()
    } catch (e: Exception) {
      // ignore
    }
  }

  fun release() {
    try {
      tts?.stop()
      tts?.shutdown()
      tts = null
      isInitialized = false
    } catch (e: Exception) {
      // ignore
    }
  }
}
