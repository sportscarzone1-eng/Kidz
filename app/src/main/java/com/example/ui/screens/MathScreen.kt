package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundEffects
import com.example.audio.SpeechManager
import com.example.data.LearningCurriculum
import com.example.model.Language
import com.example.model.MathNumberItem
import com.example.model.MathProblem
import com.example.ui.theme.*

@Composable
fun MathScreen(
  language: Language,
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onAwardStars: (Int) -> Unit,
  onLessonCompleted: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) }

  val tabs = listOf(
    if (language == Language.ENGLISH) "123 Count" else "گنتی ۱ تا ۲۰",
    if (language == Language.ENGLISH) "Addition" else "جمع (+)",
    if (language == Language.ENGLISH) "Subtraction" else "تفریق (-)",
    if (language == Language.ENGLISH) "Multiply" else "ضرب (×)",
    if (language == Language.ENGLISH) "Shapes" else "اشکال"
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("math_screen")
  ) {
    ScrollableTabRow(
      selectedTabIndex = selectedTab,
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = MathSky,
      edgePadding = 12.dp
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
          modifier = Modifier.testTag("math_tab_$index")
        )
      }
    }

    when (selectedTab) {
      0 -> NumberCountingTab(
        language = language,
        speechManager = speechManager,
        soundEffects = soundEffects,
        onNumberClicked = {
          onAwardStars(1)
          onLessonCompleted("math_num_${it.number}")
        }
      )
      1 -> MathOperationTab(
        problems = LearningCurriculum.mathAdditionProblems,
        operationSymbol = "+",
        language = language,
        speechManager = speechManager,
        soundEffects = soundEffects,
        onCorrect = {
          onAwardStars(2)
          onLessonCompleted("math_add")
        }
      )
      2 -> MathOperationTab(
        problems = LearningCurriculum.mathSubtractionProblems,
        operationSymbol = "-",
        language = language,
        speechManager = speechManager,
        soundEffects = soundEffects,
        onCorrect = {
          onAwardStars(2)
          onLessonCompleted("math_sub")
        }
      )
      3 -> MathOperationTab(
        problems = LearningCurriculum.mathMultiplicationProblems,
        operationSymbol = "×",
        language = language,
        speechManager = speechManager,
        soundEffects = soundEffects,
        onCorrect = {
          onAwardStars(3)
          onLessonCompleted("math_mul")
        }
      )
      4 -> ShapesTab(
        language = language,
        speechManager = speechManager,
        soundEffects = soundEffects,
        onShapeClicked = {
          onAwardStars(1)
          onLessonCompleted("math_shape_${it.nameEn}")
        }
      )
    }
  }
}

@Composable
private fun NumberCountingTab(
  language: Language,
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onNumberClicked: (MathNumberItem) -> Unit
) {
  val numbers = LearningCurriculum.mathNumbers

  Column(modifier = Modifier.fillMaxSize()) {
    Text(
      text = if (language == Language.ENGLISH) "Tap a number to count and listen! 🔊" else "گنتی سیکھنے کے لیے کسی نمبر پر ٹیپ کریں! 🔊",
      style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )

    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 24.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      items(numbers) { item ->
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder(),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .clickable {
              soundEffects.playTap()
              if (language == Language.ENGLISH) {
                speechManager.speakEnglish("Number ${item.number}. ${item.wordEn}")
              } else {
                speechManager.speakUrdu("نمبر ${item.wordUr}", "Number ${item.wordEn}")
              }
              onNumberClicked(item)
            }
            .testTag("math_number_card_${item.number}")
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Text(
                text = "${item.number}",
                style = MaterialTheme.typography.displayMedium.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = MathSky
                )
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "(${item.digitUr})",
                style = MaterialTheme.typography.headlineSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = UrduGreen
                )
              )
            }

            Text(
              text = if (language == Language.ENGLISH) item.wordEn else item.wordUr,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Visual items count
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center
            ) {
              val displayCount = minOf(item.number, 5)
              val repeatEmoji = item.emoji.repeat(displayCount)
              Text(text = repeatEmoji, fontSize = 18.sp, maxLines = 1)
              if (item.number > 5) {
                Text(text = " +${item.number - 5}", style = MaterialTheme.typography.labelSmall)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun MathOperationTab(
  problems: List<MathProblem>,
  operationSymbol: String,
  language: Language,
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onCorrect: () -> Unit
) {
  var problemIndex by remember { mutableStateOf(0) }
  var feedbackState by remember { mutableStateOf<Boolean?>(null) }
  val problem = problems[problemIndex % problems.size]

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    item {
      Text(
        text = if (language == Language.ENGLISH) problem.questionEn else problem.questionUr,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Visual items representation card
      Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MathSkyLight),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            // Group 1
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = problem.visualItem.repeat(problem.count1), fontSize = 28.sp)
              Text(
                text = "${problem.count1}",
                style = MaterialTheme.typography.headlineSmall.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = MathSky
                )
              )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
              text = operationSymbol,
              style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = EnglishCoral
              )
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Group 2
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = problem.visualItem.repeat(problem.count2), fontSize = 28.sp)
              Text(
                text = "${problem.count2}",
                style = MaterialTheme.typography.headlineSmall.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = MathSky
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = "${problem.count1} $operationSymbol ${problem.count2} = ?",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = TextDark
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = if (language == Language.ENGLISH) "Choose the correct answer:" else "صحیح جواب منتخب کریں:",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        problem.options.forEach { option ->
          Button(
            onClick = {
              if (option == problem.answer) {
                feedbackState = true
                soundEffects.playCorrect()
                speechManager.speakEnglish("Correct! $option is the answer!")
                onCorrect()
              } else {
                feedbackState = false
                soundEffects.playTryAgain()
                speechManager.speakEnglish("Try again! Count the items carefully.")
              }
            },
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MathSky),
            modifier = Modifier
              .weight(1f)
              .height(64.dp)
              .testTag("math_option_$option")
          ) {
            Text(
              text = "$option",
              style = MaterialTheme.typography.headlineSmall.copy(
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
          text = if (language == Language.ENGLISH) "🎉 Correct! Super counting skills!" else "🎉 بالکل درست! شاباش ریاضی کے چیمپئن!",
          color = SuccessGreen,
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
          onClick = {
            feedbackState = null
            problemIndex++
          },
          colors = ButtonDefaults.buttonColors(containerColor = MathSky),
          shape = RoundedCornerShape(14.dp)
        ) {
          Text("Next Problem ➡️", fontWeight = FontWeight.Bold)
        }
      } else if (feedbackState == false) {
        Text(
          text = if (language == Language.ENGLISH) "Not quite! Try counting again!" else "دوبارہ گن کر کوشش کریں!",
          color = ErrorSoftRed,
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      }
    }
  }
}

@Composable
private fun ShapesTab(
  language: Language,
  speechManager: SpeechManager,
  soundEffects: SoundEffects,
  onShapeClicked: (com.example.model.ShapeItem) -> Unit
) {
  val shapes = LearningCurriculum.shapes

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    item {
      Text(
        text = if (language == Language.ENGLISH) "Explore 2D Geometric Shapes! Tap to learn:" else "مختلف اشکال کی پہچان اور خصوصیات:",
        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
      )
    }

    items(shapes.size) { index ->
      val shape = shapes[index]
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier
          .fillMaxWidth()
          .clickable {
            soundEffects.playTap()
            if (language == Language.ENGLISH) {
              speechManager.speakEnglish("${shape.nameEn}. Has ${shape.sidesCountEn}. Real examples: ${shape.realWorldExamplesEn}")
            } else {
              speechManager.speakUrdu("${shape.nameUr}۔ ${shape.sidesCountUr}", "${shape.nameEn}")
            }
            onShapeClicked(shape)
          }
          .testTag("shape_item_${shape.nameEn}")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = MathSkyLight,
            modifier = Modifier.size(56.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(text = shape.emoji, fontSize = 32.sp)
            }
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = if (language == Language.ENGLISH) shape.nameEn else "${shape.nameUr} (${shape.nameEn})",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
            )
            Text(
              text = if (language == Language.ENGLISH) "Sides: ${shape.sidesCountEn}" else "اضلاع: ${shape.sidesCountUr}",
              style = MaterialTheme.typography.labelMedium.copy(color = MathSky, fontWeight = FontWeight.SemiBold)
            )
            Text(
              text = if (language == Language.ENGLISH) "Examples: ${shape.realWorldExamplesEn}" else "مثالیں: ${shape.realWorldExamplesUr}",
              style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
            )
          }

          IconButton(
            onClick = {
              soundEffects.playTap()
              speechManager.speakEnglish(shape.nameEn)
            }
          ) {
            Icon(
              imageVector = Icons.Default.VolumeUp,
              contentDescription = "Speak shape",
              tint = MathSky
            )
          }
        }
      }
    }
  }
}
