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
import androidx.compose.material.icons.filled.CheckCircle
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
import com.example.model.AlphabetItem
import com.example.model.Language
import com.example.ui.theme.*

@Composable
fun EnglishScreen(
  language: Language,
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onAwardStars: (Int) -> Unit,
  onLessonCompleted: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) }
  var selectedLetter by remember { mutableStateOf<AlphabetItem?>(null) }

  val tabs = listOf(
    if (language == Language.ENGLISH) "Letters A-Z" else "حروف A تا Z",
    if (language == Language.ENGLISH) "Letter Match" else "حرف جوڑیں",
    if (language == Language.ENGLISH) "Missing Letter" else "خالی جگہ پُر کریں"
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("english_screen")
  ) {
    // Mode selector tabs
    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = EnglishCoral,
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
          modifier = Modifier.testTag("english_tab_$index")
        )
      }
    }

    when (selectedTab) {
      0 -> AlphabetGridTab(
        letters = LearningCurriculum.englishAlphabet,
        language = language,
        onLetterClick = { letter ->
          soundEffects.playTap()
          speechManager.speakEnglish("${letter.uppercase}. ${letter.wordEn}")
          selectedLetter = letter
          onAwardStars(1)
          onLessonCompleted("en_${letter.uppercase}")
        }
      )
      1 -> LetterMatchQuizTab(
        language = language,
        speechManager = speechManager,
        soundEffects = soundEffects,
        onCorrect = {
          onAwardStars(2)
          onLessonCompleted("en_quiz_match")
        }
      )
      2 -> MissingLetterTab(
        language = language,
        speechManager = speechManager,
        soundEffects = soundEffects,
        onCorrect = {
          onAwardStars(2)
          onLessonCompleted("en_quiz_missing")
        }
      )
    }

    // Detail dialog when letter is tapped
    selectedLetter?.let { letter ->
      LetterDetailDialog(
        item = letter,
        language = language,
        speechManager = speechManager,
        soundEffects = soundEffects,
        onDismiss = { selectedLetter = null }
      )
    }
  }
}

@Composable
private fun AlphabetGridTab(
  letters: List<AlphabetItem>,
  language: Language,
  onLetterClick: (AlphabetItem) -> Unit
) {
  Column(modifier = Modifier.fillMaxSize()) {
    Text(
      text = if (language == Language.ENGLISH) "Tap a letter to see picture and hear sound! 🔊" else "حرف کو چھوئیں، تصویر دیکھیں اور آواز سنیں! 🔊",
      style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
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
            .testTag("letter_card_${item.uppercase}")
        ) {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
          ) {
            Row(verticalAlignment = Alignment.Bottom) {
              Text(
                text = item.uppercase,
                style = MaterialTheme.typography.headlineLarge.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = EnglishCoral
                )
              )
              Text(
                text = item.lowercase,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = TextMuted
                )
              )
            }

            Text(text = item.emoji, fontSize = 28.sp)

            Text(
              text = item.wordEn,
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextDark
              ),
              maxLines = 1
            )
          }
        }
      }
    }
  }
}

@Composable
private fun LetterDetailDialog(
  item: AlphabetItem,
  language: Language,
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
        .testTag("letter_detail_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = EnglishCoralLight,
          modifier = Modifier.size(110.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Text(
              text = item.emoji,
              fontSize = 60.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "${item.uppercase} ${item.lowercase}",
            style = MaterialTheme.typography.displayMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = EnglishCoral
            )
          )
          Spacer(modifier = Modifier.width(8.dp))
          IconButton(
            onClick = {
              soundEffects.playTap()
              speechManager.speakEnglish("${item.uppercase} is for ${item.wordEn}. ${item.sentenceEn}")
            },
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(KidsPrimaryContainer)
              .testTag("dialog_speech_button")
          ) {
            Icon(
              imageVector = Icons.Default.VolumeUp,
              contentDescription = "Pronounce Letter",
              tint = KidsPrimary
            )
          }
        }

        Text(
          text = item.wordEn,
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
        )

        Text(
          text = item.phonics,
          style = MaterialTheme.typography.titleSmall.copy(
            color = KidsSecondary,
            fontWeight = FontWeight.SemiBold
          )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFFF8FAFC),
          border = CardDefaults.outlinedCardBorder(),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(
              text = item.sentenceEn,
              style = MaterialTheme.typography.bodyMedium.copy(
                color = TextDark,
                fontWeight = FontWeight.Medium
              ),
              textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = item.sentenceUr,
              style = MaterialTheme.typography.bodyMedium.copy(
                color = UrduGreen,
                fontWeight = FontWeight.Bold
              ),
              textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth()
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = EnglishCoral),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Got it! ⭐", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
private fun LetterMatchQuizTab(
  language: Language,
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onCorrect: () -> Unit
) {
  val alphabet = LearningCurriculum.englishAlphabet
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
      text = if (language == Language.ENGLISH) "Find the picture for letter:" else "اس حرف کی تصویر تلاش کریں:",
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
    )

    Spacer(modifier = Modifier.height(14.dp))

    Surface(
      shape = RoundedCornerShape(24.dp),
      color = EnglishCoralLight,
      modifier = Modifier.size(110.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(
          text = "${currentItem.uppercase} ${currentItem.lowercase}",
          style = MaterialTheme.typography.displayMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            color = EnglishCoral
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
            .height(120.dp)
            .clickable {
              if (option == currentItem) {
                feedbackState = true
                soundEffects.playCorrect()
                speechManager.speakEnglish("Correct! ${option.uppercase} is for ${option.wordEn}!")
                onCorrect()
              } else {
                feedbackState = false
                soundEffects.playTryAgain()
                speechManager.speakEnglish("Try again! Look for ${currentItem.wordEn}.")
              }
            }
            .testTag("match_option_${option.uppercase}")
        ) {
          Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Text(text = option.emoji, fontSize = 42.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = option.wordEn,
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    if (feedbackState == true) {
      Text(
        text = if (language == Language.ENGLISH) "🌟 Excellent! That's correct!" else "🌟 شاباش! بالکل درست جواب!",
        color = SuccessGreen,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )

      Spacer(modifier = Modifier.height(12.dp))

      Button(
        onClick = {
          feedbackState = null
          currentIndex++
        },
        colors = ButtonDefaults.buttonColors(containerColor = EnglishCoral),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.testTag("next_letter_match_button")
      ) {
        Text("Next Letter ➡️", fontWeight = FontWeight.Bold)
      }
    } else if (feedbackState == false) {
      Text(
        text = if (language == Language.ENGLISH) "Oops! Give it another try!" else "دوبارہ کوشش کریں!",
        color = ErrorSoftRed,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }
  }
}

@Composable
private fun MissingLetterTab(
  language: Language,
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onCorrect: () -> Unit
) {
  val puzzleList = remember {
    listOf(
      Triple("A P P L E", "A _ P L E", 'P'),
      Triple("B A L L", "B _ L L", 'A'),
      Triple("C A T", "C _ T", 'A'),
      Triple("D O G", "D _ G", 'O'),
      Triple("S T A R", "S T _ R", 'A'),
      Triple("F I S H", "F _ S H", 'I')
    )
  }

  var puzzleIndex by remember { mutableStateOf(0) }
  var feedbackState by remember { mutableStateOf<Boolean?>(null) }
  val puzzle = puzzleList[puzzleIndex % puzzleList.size]

  val choices = remember(puzzleIndex) {
    val vowels = listOf('A', 'E', 'I', 'O', 'U', 'P', 'T', 'S').filter { it != puzzle.third }
    (vowels.shuffled().take(2) + puzzle.third).shuffled()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = if (language == Language.ENGLISH) "Fill the missing letter in the word!" else "لفظ میں غائب حرف تلاش کر کے مکمل کریں!",
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
    )

    Spacer(modifier = Modifier.height(20.dp))

    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color(0xFFFEF2F2),
      border = CardDefaults.outlinedCardBorder(),
      modifier = Modifier.padding(16.dp)
    ) {
      Text(
        text = puzzle.second,
        style = MaterialTheme.typography.displayMedium.copy(
          fontWeight = FontWeight.ExtraBold,
          letterSpacing = 6.sp,
          color = EnglishCoral
        ),
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 18.dp)
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
      choices.forEach { char ->
        Button(
          onClick = {
            if (char == puzzle.third) {
              feedbackState = true
              soundEffects.playCorrect()
              speechManager.speakEnglish("Great! The word is ${puzzle.first}!")
              onCorrect()
            } else {
              feedbackState = false
              soundEffects.playTryAgain()
              speechManager.speakEnglish("Not quite, try another letter!")
            }
          },
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = EnglishCoral),
          modifier = Modifier
            .size(70.dp)
            .testTag("missing_letter_choice_$char")
        ) {
          Text(
            text = "$char",
            style = MaterialTheme.typography.headlineLarge.copy(
              fontWeight = FontWeight.ExtraBold,
              color = Color.White
            )
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    if (feedbackState == true) {
      Text(
        text = if (language == Language.ENGLISH) "✨ Perfect! You spelled it right!" else "✨ زبردست! بالکل درست ہجے!",
        color = SuccessGreen,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )

      Spacer(modifier = Modifier.height(12.dp))

      Button(
        onClick = {
          feedbackState = null
          puzzleIndex++
        },
        colors = ButtonDefaults.buttonColors(containerColor = EnglishCoral),
        shape = RoundedCornerShape(14.dp)
      ) {
        Text("Next Word ➡️", fontWeight = FontWeight.Bold)
      }
    } else if (feedbackState == false) {
      Text(
        text = if (language == Language.ENGLISH) "Try again!" else "دوبارہ کوشش کریں!",
        color = ErrorSoftRed,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }
  }
}
