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
import androidx.compose.material.icons.filled.Refresh
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
import com.example.audio.SoundEffects
import com.example.audio.SpeechManager
import com.example.model.Language
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun GamesHubScreen(
  language: Language,
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onAwardStars: (Int) -> Unit,
  onLessonCompleted: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedGameTab by remember { mutableStateOf(0) }

  val tabs = listOf(
    if (language == Language.ENGLISH) "Memory Match 🃏" else "یادداشت کارڈز 🃏",
    if (language == Language.ENGLISH) "Count & Catch 🍎" else "گنیں اور پکڑیں 🍎",
    if (language == Language.ENGLISH) "Word Match 🔤" else "الفاظ کی جوڑ 🔤"
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("games_hub_screen")
  ) {
    TabRow(
      selectedTabIndex = selectedGameTab,
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = Color(0xFFD946EF),
      modifier = Modifier.padding(horizontal = 8.dp)
    ) {
      tabs.forEachIndexed { index, title ->
        Tab(
          selected = selectedGameTab == index,
          onClick = {
            soundEffects.playTap()
            selectedGameTab = index
          },
          text = {
            Text(
              text = title,
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
          },
          modifier = Modifier.testTag("game_tab_$index")
        )
      }
    }

    when (selectedGameTab) {
      0 -> MemoryCardGame(
        language = language,
        soundEffects = soundEffects,
        speechManager = speechManager,
        onGameWon = {
          onAwardStars(5)
          onLessonCompleted("game_memory")
        }
      )
      1 -> CountCatchGame(
        language = language,
        soundEffects = soundEffects,
        speechManager = speechManager,
        onCorrect = {
          onAwardStars(2)
          onLessonCompleted("game_count")
        }
      )
      2 -> WordPictureMatchGame(
        language = language,
        soundEffects = soundEffects,
        speechManager = speechManager,
        onCorrect = {
          onAwardStars(2)
          onLessonCompleted("game_word_match")
        }
      )
    }
  }
}

data class MemoryCard(
  val id: Int,
  val content: String,
  val name: String,
  var isFlipped: Boolean = false,
  var isMatched: Boolean = false
)

@Composable
private fun MemoryCardGame(
  language: Language,
  soundEffects: SoundEffects,
  speechManager: SpeechManager,
  onGameWon: () -> Unit
) {
  val baseItems = listOf(
    Pair("🦁", "Lion"),
    Pair("🍎", "Apple"),
    Pair("⭐", "Star"),
    Pair("🚀", "Rocket"),
    Pair("🐬", "Dolphin"),
    Pair("🦋", "Butterfly")
  )

  var cards by remember {
    mutableStateOf(
      (baseItems + baseItems).shuffled().mapIndexed { index, pair ->
        MemoryCard(id = index, content = pair.first, name = pair.second)
      }
    )
  }

  var flippedCards by remember { mutableStateOf<List<Int>>(emptyList()) }
  var movesCount by remember { mutableStateOf(0) }
  var isWon by remember { mutableStateOf(false) }

  // Check matching logic
  LaunchedEffect(flippedCards) {
    if (flippedCards.size == 2) {
      movesCount++
      val first = cards[flippedCards[0]]
      val second = cards[flippedCards[1]]
      if (first.content == second.content) {
        soundEffects.playCorrect()
        cards = cards.map {
          if (it.id == first.id || it.id == second.id) it.copy(isMatched = true) else it
        }
        flippedCards = emptyList()
        if (cards.all { it.isMatched }) {
          isWon = true
          soundEffects.playFanfare()
          speechManager.speakEnglish("You won the Memory Game! Outstanding job!")
          onGameWon()
        }
      } else {
        soundEffects.playTryAgain()
        delay(850)
        cards = cards.map {
          if (it.id == first.id || it.id == second.id) it.copy(isFlipped = false) else it
        }
        flippedCards = emptyList()
      }
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = if (language == Language.ENGLISH) "Find the matching pairs! 🃏" else "ایک جیسے کارڈز کے جوڑے ملائیں! 🃏",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )

      IconButton(
        onClick = {
          soundEffects.playTap()
          movesCount = 0
          isWon = false
          flippedCards = emptyList()
          cards = (baseItems + baseItems).shuffled().mapIndexed { index, pair ->
            MemoryCard(id = index, content = pair.first, name = pair.second)
          }
        },
        modifier = Modifier.testTag("reset_memory_game_button")
      ) {
        Icon(
          imageVector = Icons.Default.Refresh,
          contentDescription = "Restart Game",
          tint = MaterialTheme.colorScheme.primary
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    LazyVerticalGrid(
      columns = GridCells.Fixed(3),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
    ) {
      items(cards) { card ->
        val canClick = !card.isMatched && !card.isFlipped && flippedCards.size < 2

        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (card.isFlipped || card.isMatched) Color.White else KidsPrimary
          ),
          border = CardDefaults.outlinedCardBorder(),
          elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
          modifier = Modifier
            .aspectRatio(1f)
            .clickable(enabled = canClick) {
              soundEffects.playTap()
              cards = cards.map { if (it.id == card.id) it.copy(isFlipped = true) else it }
              flippedCards = flippedCards + card.id
            }
            .testTag("memory_card_${card.id}")
        ) {
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
          ) {
            if (card.isFlipped || card.isMatched) {
              Text(text = card.content, fontSize = 40.sp)
            } else {
              Text(text = "❓", fontSize = 28.sp)
            }
          }
        }
      }
    }

    if (isWon) {
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = KidsTertiaryContainer,
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = if (language == Language.ENGLISH) "🏆 Fantastic! All pairs matched!" else "🏆 شاندار! آپ نے تمام کارڈز ملا لیے!",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = KidsTertiary
            )
          )
          Text(
            text = "+5 Stars Awarded! ⭐⭐⭐⭐⭐",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Bold,
              color = Color(0xFF065F46)
            )
          )
        }
      }
    }
  }
}

@Composable
private fun CountCatchGame(
  language: Language,
  soundEffects: SoundEffects,
  speechManager: SpeechManager,
  onCorrect: () -> Unit
) {
  var targetCount by remember { mutableStateOf((3..8).random()) }
  val targetEmoji = remember(targetCount) { listOf("🍎", "⭐", "🎈", "🧸", "🍓").random() }
  var feedbackState by remember { mutableStateOf<Boolean?>(null) }

  val options = remember(targetCount) {
    val wrong = listOf(targetCount - 1, targetCount + 1, targetCount + 2, targetCount - 2)
      .filter { it > 0 }
      .shuffled()
      .take(2)
    (wrong + targetCount).shuffled()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = if (language == Language.ENGLISH) "How many $targetEmoji do you count?" else "کتنے $targetEmoji نظر آ رہے ہیں؟",
      style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(18.dp))

    Card(
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
      modifier = Modifier
        .fillMaxWidth()
        .height(160.dp)
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        contentAlignment = Alignment.Center
      ) {
        val emojisText = targetEmoji.repeat(targetCount)
        Text(
          text = emojisText,
          fontSize = 32.sp,
          textAlign = TextAlign.Center,
          lineHeight = 44.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      options.forEach { option ->
        Button(
          onClick = {
            if (option == targetCount) {
              feedbackState = true
              soundEffects.playCorrect()
              speechManager.speakEnglish("Correct! Exactly $targetCount $targetEmoji!")
              onCorrect()
            } else {
              feedbackState = false
              soundEffects.playTryAgain()
              speechManager.speakEnglish("Try again! Touch and count each one.")
            }
          },
          shape = RoundedCornerShape(18.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MathSky),
          modifier = Modifier
            .weight(1f)
            .height(64.dp)
            .testTag("count_game_option_$option")
        ) {
          Text(
            text = "$option",
            style = MaterialTheme.typography.headlineMedium.copy(
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
        text = if (language == Language.ENGLISH) "🌟 Yes! Great counting!" else "🌟 شاباش! زبردست گنتی!",
        color = SuccessGreen,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )

      Spacer(modifier = Modifier.height(12.dp))

      Button(
        onClick = {
          feedbackState = null
          targetCount = (3..9).random()
        },
        colors = ButtonDefaults.buttonColors(containerColor = MathSky),
        shape = RoundedCornerShape(14.dp)
      ) {
        Text("Play Again ➡️", fontWeight = FontWeight.Bold)
      }
    } else if (feedbackState == false) {
      Text(
        text = if (language == Language.ENGLISH) "Count carefully and try again!" else "دھیان سے گن کر دوبارہ کوشش کریں!",
        color = ErrorSoftRed,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }
  }
}

@Composable
private fun WordPictureMatchGame(
  language: Language,
  soundEffects: SoundEffects,
  speechManager: SpeechManager,
  onCorrect: () -> Unit
) {
  val wordPairs = listOf(
    Pair("🍎", "Apple"),
    Pair("🐱", "Cat"),
    Pair("⚽", "Ball"),
    Pair("🚗", "Car"),
    Pair("☀️", "Sun"),
    Pair("🌙", "Moon")
  )

  var currentIndex by remember { mutableStateOf(0) }
  var feedbackState by remember { mutableStateOf<Boolean?>(null) }
  val currentPair = wordPairs[currentIndex % wordPairs.size]

  val choices = remember(currentIndex) {
    val wrong = wordPairs.filter { it != currentPair }.shuffled().take(2).map { it.second }
    (wrong + currentPair.second).shuffled()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = if (language == Language.ENGLISH) "Which word matches this picture?" else "اس تصویر کا صحیح نام کیا ہے؟",
      style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(20.dp))

    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color(0xFFEFF6FF),
      modifier = Modifier.size(130.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(text = currentPair.first, fontSize = 66.sp)
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      choices.forEach { choice ->
        Button(
          onClick = {
            if (choice == currentPair.second) {
              feedbackState = true
              soundEffects.playCorrect()
              speechManager.speakEnglish("Yes! That is a $choice!")
              onCorrect()
            } else {
              feedbackState = false
              soundEffects.playTryAgain()
            }
          },
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = EnglishCoral),
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("word_match_choice_$choice")
        ) {
          Text(
            text = choice,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    if (feedbackState == true) {
      Text(
        text = if (language == Language.ENGLISH) "✨ Splendid! You matched it!" else "✨ زبردست! بالکل صحیح!",
        color = SuccessGreen,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )

      Spacer(modifier = Modifier.height(10.dp))

      Button(
        onClick = {
          feedbackState = null
          currentIndex++
        },
        colors = ButtonDefaults.buttonColors(containerColor = EnglishCoral),
        shape = RoundedCornerShape(14.dp)
      ) {
        Text("Next Picture ➡️", fontWeight = FontWeight.Bold)
      }
    }
  }
}
