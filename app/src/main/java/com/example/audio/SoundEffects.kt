package com.example.audio

import android.media.AudioManager
import android.media.ToneGenerator
import android.util.Log

class SoundEffects(private val isSoundEnabled: () -> Boolean) {

  private var toneGen: ToneGenerator? = null

  init {
    try {
      toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 85)
    } catch (e: Exception) {
      Log.w("SoundEffects", "ToneGenerator could not be initialized", e)
    }
  }

  fun playCorrect() {
    if (!isSoundEnabled()) return
    try {
      toneGen?.startTone(ToneGenerator.TONE_PROP_BEEP2, 180)
    } catch (e: Exception) {
      Log.e("SoundEffects", "Error playing tone", e)
    }
  }

  fun playTryAgain() {
    if (!isSoundEnabled()) return
    try {
      toneGen?.startTone(ToneGenerator.TONE_PROP_NACK, 220)
    } catch (e: Exception) {
      Log.e("SoundEffects", "Error playing tone", e)
    }
  }

  fun playTap() {
    if (!isSoundEnabled()) return
    try {
      toneGen?.startTone(ToneGenerator.TONE_PROP_BEEP, 70)
    } catch (e: Exception) {
      Log.e("SoundEffects", "Error playing tone", e)
    }
  }

  fun playFanfare() {
    if (!isSoundEnabled()) return
    try {
      toneGen?.startTone(ToneGenerator.TONE_PROP_PROMPT, 350)
    } catch (e: Exception) {
      Log.e("SoundEffects", "Error playing fanfare", e)
    }
  }

  fun release() {
    try {
      toneGen?.release()
      toneGen = null
    } catch (e: Exception) {
      // ignore
    }
  }
}
