package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.audio.SoundEffects
import com.example.audio.SpeechManager
import com.example.data.LearningCurriculum
import com.example.model.Language
import com.example.model.UrduAlphabetItem
import com.example.ui.theme.*

@Composable
fun UrduScreen(
  language: Language,
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onAwardStars: (Int) -> Unit,
  onLessonCompleted: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  // Always render the Urdu learning module in RTL layout for genuine Arabic/Urdu typography flow
  CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    var selectedTab by remember { mutableStateOf(0) }
    var selectedLetter by remember { mutableStateOf<UrduAlphabetItem?>(null) }

    val tabs = listOf(
      "حروفِ تہجی (ا سے ے)",
      "تصویر اور حرف ملائیں",
      "صحیح حرف پہچانیں"
    )

    Column(
      modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .testTag("urdu_screen")
    ) {
      TabRow(
        selectedTabIndex = selectedTab,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = UrduGreen,
        modifier = Modifier.padding(horizontal = 8.dp)
      ) {
        tabs.forEachIndexed { index, title ->
          Tab(
            selected = selectedTab == index,
            onClick = {
              soundEffects.playTap()
              selectedTab = index
            },
            text = {
              Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
            },
            modifier = Modifier.testTag("urdu_tab_$index")
          )
        }
      }

      when (selectedTab) {
        0 -> UrduAlphabetGrid(
          letters = LearningCurriculum.urduAlphabet,
          onLetterClick = { item ->
            soundEffects.playTap()
            speechManager.speakUrdu("${item.letterNameUr}، ${item.wordUr}", "${item.letterNameEn}, ${item.wordEn}")
            selectedLetter = item
            onAwardStars(1)
            onLessonCompleted("ur_${item.letter}")
          }
        )
        1 -> UrduPictureMatchQuiz(
          speechManager = speechManager,
          soundEffects = soundEffects,
          onCorrect = {
            onAwardStars(2)
            onLessonCompleted("ur_quiz_match")
          }
        )
        2 -> UrduIdentifyLetterQuiz(
          speechManager = speechManager,
          soundEffects = soundEffects,
          onCorrect = {
            onAwardStars(2)
            onLessonCompleted("ur_quiz_identify")
          }
        )
      }

      selectedLetter?.let { item ->
        UrduLetterDetailDialog(
          item = item,
          speechManager = speechManager,
          soundEffects = soundEffects,
          onDismiss = { selectedLetter = null }
        )
      }
    }
  }
}

@Composable
private fun UrduAlphabetGrid(
  letters: List<UrduAlphabetItem>,
  onLetterClick: (UrduAlphabetItem) -> Unit
) {
  Column(modifier = Modifier.fillMaxSize()) {
    Text(
      text = "کسی بھی حرف کو چھوئیں، تلفظ سنیں اور تصویر دیکھیں! 🔊",
      style = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = FontWeight.Medium
      ),
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    )

    LazyVerticalGrid(
      columns = GridCells.Fixed(3),
      contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 24.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      items(letters) { item ->
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder(),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .aspectRatio(0.95f)
            .clickable { onLetterClick(item) }
            .testTag("urdu_letter_card_${item.letter}")
        ) {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
          ) {
            Text(
              text = item.letter,
              style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = UrduGreen,
                fontSize = 38.sp
              )
            )

            Text(text = item.emoji, fontSize = 28.sp)

            Text(
              text = item.wordUr,
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = TextDark,
                fontSize = 17.sp
              )
            )
          }
        }
      }
    }
  }
}

@Composable
private fun UrduLetterDetailDialog(
  item: UrduAlphabetItem,
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(26.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
        .testTag("urdu_detail_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = UrduGreenLight,
          modifier = Modifier.size(110.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Text(
              text = item.emoji,
              fontSize = 62.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Text(
            text = item.letter,
            style = MaterialTheme.typography.displayLarge.copy(
              fontWeight = FontWeight.ExtraBold,
              color = UrduGreen,
              fontSize = 48.sp
            )
          )
          Spacer(modifier = Modifier.width(10.dp))
          IconButton(
            onClick = {
              soundEffects.playTap()
              speechManager.speakUrdu("${item.letterNameUr}، ${item.sentenceUr}", "${item.letterNameEn}. ${item.sentenceEn}")
            },
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(KidsTertiaryContainer)
              .testTag("urdu_dialog_speaker")
          ) {
            Icon(
              imageVector = Icons.Default.VolumeUp,
              contentDescription = "Urdu Pronunciation",
              tint = UrduGreen
            )
          }
        }

        Text(
          text = "${item.letterNameUr} (${item.letterNameEn})",
          style = MaterialTheme.typography.titleMedium.copy(
            color = KidsSecondary,
            fontWeight = FontWeight.Bold
          )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "${item.wordUr} — ${item.wordEn}",
          style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
          )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFFF0FDF4),
          border = CardDefaults.outlinedCardBorder(),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = item.sentenceUr,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = UrduGreen,
                lineHeight = 28.sp
              ),
              textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = item.sentenceEn,
              style = MaterialTheme.typography.bodySmall.copy(
                color = TextMuted,
                fontWeight = FontWeight.Medium
              ),
              textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth()
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = UrduGreen),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("سمجھ گیا! ⭐", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
      }
    }
  }
}

@Composable
private fun UrduPictureMatchQuiz(
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onCorrect: () -> Unit
) {
  val alphabet = LearningCurriculum.urduAlphabet
  var currentIndex by remember { mutableStateOf(0) }
  var feedbackState by remember { mutableStateOf<Boolean?>(null) }

  val currentItem = alphabet[currentIndex % alphabet.size]
  val options = remember(currentIndex) {
    val wrong = alphabet.filter { it != currentItem }.shuffled().take(2)
    (wrong + currentItem).shuffled()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "اس حرف کی مناسب تصویر منتخب کریں:",
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Surface(
      shape = RoundedCornerShape(24.dp),
      color = UrduGreenLight,
      modifier = Modifier.size(110.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(
          text = currentItem.letter,
          style = MaterialTheme.typography.displayLarge.copy(
            fontWeight = FontWeight.ExtraBold,
            color = UrduGreen,
            fontSize = 54.sp
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      options.forEach { option ->
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder(),
          modifier = Modifier
            .weight(1f)
            .height(125.dp)
            .clickable {
              if (option == currentItem) {
                feedbackState = true
                soundEffects.playCorrect()
                speechManager.speakUrdu("شاباش! ${option.letterNameUr} سے ${option.wordUr}!", "Correct! ${option.letterNameEn} is for ${option.wordEn}!")
                onCorrect()
              } else {
                feedbackState = false
                soundEffects.playTryAgain()
                speechManager.speakUrdu("دوبارہ کوشش کریں، ${currentItem.wordUr} تلاش کریں!", "Try again!")
              }
            }
            .testTag("urdu_match_option_${option.letter}")
        ) {
          Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Text(text = option.emoji, fontSize = 42.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = option.wordUr,
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    if (feedbackState == true) {
      Text(
        text = "🌟 شاندار! بالکل درست جواب!",
        color = SuccessGreen,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )

      Spacer(modifier = Modifier.height(12.dp))

      Button(
        onClick = {
          feedbackState = null
          currentIndex++
        },
        colors = ButtonDefaults.buttonColors(containerColor = UrduGreen),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.testTag("next_urdu_match_button")
      ) {
        Text("اگلا حرف ⬅️", fontWeight = FontWeight.Bold)
      }
    } else if (feedbackState == false) {
      Text(
        text = "دوبارہ کوشش کریں!",
        color = ErrorSoftRed,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }
  }
}

@Composable
private fun UrduIdentifyLetterQuiz(
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onCorrect: () -> Unit
) {
  val alphabet = LearningCurriculum.urduAlphabet
  var currentIndex by remember { mutableStateOf(2) }
  var feedbackState by remember { mutableStateOf<Boolean?>(null) }

  val currentItem = alphabet[currentIndex % alphabet.size]
  val options = remember(currentIndex) {
    val wrong = alphabet.filter { it != currentItem }.shuffled().take(2)
    (wrong + currentItem).shuffled()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "اس لفظ کا پہلا حرف کون سا ہے؟",
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color(0xFFFEF3C7),
      modifier = Modifier.size(110.dp)
    ) {
      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(text = currentItem.emoji, fontSize = 48.sp)
        Text(
          text = currentItem.wordUr,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = Color(0xFF78350F)
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      options.forEach { option ->
        Button(
          onClick = {
            if (option == currentItem) {
              feedbackState = true
              soundEffects.playCorrect()
              speechManager.speakUrdu("بہت خوب! ${option.letterNameUr} سے ${option.wordUr}!", "Well done!")
              onCorrect()
            } else {
              feedbackState = false
              soundEffects.playTryAgain()
              speechManager.speakUrdu("غلط جواب، دوبارہ کوشش کریں!", "Try again!")
            }
          },
          shape = RoundedCornerShape(18.dp),
          colors = ButtonDefaults.buttonColors(containerColor = UrduGreen),
          modifier = Modifier
            .weight(1f)
            .height(76.dp)
            .testTag("urdu_identify_choice_${option.letter}")
        ) {
          Text(
            text = option.letter,
            style = MaterialTheme.typography.displayMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = Color.White,
              fontSize = 38.sp
            )
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    if (feedbackState == true) {
      Text(
        text = "🎉 بہت اچھے! اگلا سوال حل کریں!",
        color = SuccessGreen,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )

      Spacer(modifier = Modifier.height(12.dp))

      Button(
        onClick = {
          feedbackState = null
          currentIndex++
        },
        colors = ButtonDefaults.buttonColors(containerColor = UrduGreen),
        shape = RoundedCornerShape(16.dp)
      ) {
        Text("اگلا سوال ⬅️", fontWeight = FontWeight.Bold)
      }
    } else if (feedbackState == false) {
      Text(
        text = "دوبارہ کوشش کریں!",
        color = ErrorSoftRed,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }
  }
}
