package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.audio.SoundEffects
import com.example.audio.SpeechManager
import com.example.data.LearningCurriculum
import com.example.model.Language
import com.example.model.ScienceLesson
import com.example.ui.theme.*

@Composable
fun ScienceScreen(
  language: Language,
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onAwardStars: (Int) -> Unit,
  onLessonCompleted: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var activeLesson by remember { mutableStateOf<ScienceLesson?>(null) }
  val lessons = LearningCurriculum.scienceLessons

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("science_screen")
  ) {
    Text(
      text = if (language == Language.ENGLISH)
        "Explore the Wonders of Nature & Science! 🔬"
      else
        "قدرت، جاندار اور سائنس کی خوبصورت دنیا دریافت کریں! 🔬",
      style = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
      ),
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )

    LazyColumn(
      contentPadding = PaddingValues(start = 14.dp, end = 14.dp, bottom = 28.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      items(lessons) { lesson ->
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder(),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              soundEffects.playTap()
              activeLesson = lesson
            }
            .testTag("science_lesson_card_${lesson.id}")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(18.dp),
              color = ScienceVioletLight,
              modifier = Modifier.size(62.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(text = lesson.iconEmoji, fontSize = 34.sp)
              }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = if (language == Language.ENGLISH) lesson.categoryEn else lesson.categoryUr,
                style = MaterialTheme.typography.labelSmall.copy(
                  color = ScienceViolet,
                  fontWeight = FontWeight.Bold
                )
              )
              Text(
                text = if (language == Language.ENGLISH) lesson.titleEn else lesson.titleUr,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = TextDark
                )
              )
              Text(
                text = if (language == Language.ENGLISH) "Read, listen & take quiz" else "پڑھیں، سنیں اور کوئز کھیلیں",
                style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
              )
            }

            Icon(
              imageVector = Icons.Default.ChevronRight,
              contentDescription = "Open Lesson",
              tint = ScienceViolet
            )
          }
        }
      }
    }

    activeLesson?.let { lesson ->
      ScienceLessonDetailDialog(
        lesson = lesson,
        language = language,
        speechManager = speechManager,
        soundEffects = soundEffects,
        onQuizPassed = {
          onAwardStars(3)
          onLessonCompleted("sci_${lesson.id}")
        },
        onDismiss = { activeLesson = null }
      )
    }
  }
}

@Composable
private fun ScienceLessonDetailDialog(
  lesson: ScienceLesson,
  language: Language,
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onQuizPassed: () -> Unit,
  onDismiss: () -> Unit
) {
  var selectedQuizOption by remember { mutableStateOf<Int?>(null) }
  var isQuizSubmitted by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(26.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.92f)
        .padding(vertical = 8.dp)
        .testTag("science_lesson_dialog")
    ) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = lesson.iconEmoji,
              fontSize = 36.sp
            )

            IconButton(
              onClick = {
                soundEffects.playTap()
                val narration = if (language == Language.ENGLISH) {
                  "${lesson.titleEn}. " + lesson.factPointsEn.joinToString(" ") + " " + lesson.funTakeawayEn
                } else {
                  "${lesson.titleUr}۔ " + lesson.factPointsUr.joinToString(" ")
                }
                if (language == Language.ENGLISH) {
                  speechManager.speakEnglish(narration)
                } else {
                  speechManager.speakUrdu(narration, lesson.titleEn)
                }
              },
              modifier = Modifier
                .clip(CircleShape)
                .background(ScienceVioletLight)
                .testTag("science_narration_button")
            ) {
              Icon(
                imageVector = Icons.Default.VolumeUp,
                contentDescription = "Read Lesson Out Loud",
                tint = ScienceViolet
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = if (language == Language.ENGLISH) lesson.titleEn else lesson.titleUr,
            style = MaterialTheme.typography.headlineSmall.copy(
              fontWeight = FontWeight.ExtraBold,
              color = ScienceViolet
            ),
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Facts List
          val facts = if (language == Language.ENGLISH) lesson.factPointsEn else lesson.factPointsUr
          facts.forEach { fact ->
            Surface(
              shape = RoundedCornerShape(14.dp),
              color = Color(0xFFF8FAFC),
              border = CardDefaults.outlinedCardBorder(),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.Top
              ) {
                Text(text = "🌱", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = fact,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = TextDark
                  )
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Fun Takeaway Card
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = KidsSecondaryContainer,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text(
                text = if (language == Language.ENGLISH) "💡 Fun Little Fact:" else "💡 دلچسپ بات:",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF78350F)
                )
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = if (language == Language.ENGLISH) lesson.funTakeawayEn else lesson.funTakeawayUr,
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Medium,
                  color = TextDark
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          // Mini Quiz Section
          Text(
            text = if (language == Language.ENGLISH) "Quick Quiz Challenge ⭐" else "چھوٹا کوئز چیلنج ⭐",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = if (language == Language.ENGLISH) lesson.quizQuestionEn else lesson.quizQuestionUr,
            style = MaterialTheme.typography.bodyLarge.copy(
              fontWeight = FontWeight.SemiBold,
              color = TextDark
            ),
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(12.dp))

          val options = if (language == Language.ENGLISH) lesson.quizOptionsEn else lesson.quizOptionsUr
          options.forEachIndexed { index, option ->
            val isSelected = selectedQuizOption == index
            val isCorrect = index == lesson.correctOptionIndex

            val buttonColor = when {
              isQuizSubmitted && isCorrect -> SuccessGreen
              isQuizSubmitted && isSelected && !isCorrect -> ErrorSoftRed
              isSelected -> ScienceViolet
              else -> Color(0xFFF1F5F9)
            }

            Surface(
              shape = RoundedCornerShape(14.dp),
              color = buttonColor,
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable(enabled = !isQuizSubmitted) {
                  soundEffects.playTap()
                  selectedQuizOption = index
                }
                .testTag("science_quiz_option_$index")
            ) {
              Text(
                text = option,
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected || (isQuizSubmitted && isCorrect)) Color.White else TextDark
                ),
                modifier = Modifier.padding(14.dp),
                textAlign = TextAlign.Center
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          if (!isQuizSubmitted) {
            Button(
              onClick = {
                if (selectedQuizOption != null) {
                  isQuizSubmitted = true
                  if (selectedQuizOption == lesson.correctOptionIndex) {
                    soundEffects.playCorrect()
                    speechManager.speakEnglish("Awesome! You got it right!")
                    onQuizPassed()
                  } else {
                    soundEffects.playTryAgain()
                  }
                }
              },
              enabled = selectedQuizOption != null,
              colors = ButtonDefaults.buttonColors(containerColor = ScienceViolet),
              shape = RoundedCornerShape(14.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("submit_science_quiz_button")
            ) {
              Text(if (language == Language.ENGLISH) "Check Answer" else "جواب چیک کریں", fontWeight = FontWeight.Bold)
            }
          } else {
            Button(
              onClick = onDismiss,
              colors = ButtonDefaults.buttonColors(containerColor = ScienceViolet),
              shape = RoundedCornerShape(14.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(if (language == Language.ENGLISH) "Done & Close" else "مکمل ہو گیا", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}
