package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.model.Language
import com.example.ui.theme.*

@Composable
fun KidsTopBar(
  title: String,
  starsCount: Int,
  currentLanguage: Language,
  isSoundEnabled: Boolean,
  onBackClick: (() -> Unit)? = null,
  onLanguageToggle: () -> Unit,
  onSoundToggle: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .testTag("kids_top_bar"),
    color = MaterialTheme.colorScheme.surface,
    tonalElevation = 2.dp,
    shadowElevation = 3.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        if (onBackClick != null) {
          IconButton(
            onClick = onBackClick,
            modifier = Modifier
              .size(44.dp)
              .testTag("back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = MaterialTheme.colorScheme.primary
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
        }

        Text(
          text = title,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          ),
          maxLines = 1
        )
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Stars pill
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = KidsSecondaryContainer,
          modifier = Modifier.testTag("stars_pill")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = "Stars",
              tint = StarGold,
              modifier = Modifier.size(20.dp)
            )
            Text(
              text = "$starsCount",
              style = MaterialTheme.typography.labelLarge.copy(
                color = Color(0xFF92400E),
                fontWeight = FontWeight.ExtraBold
              )
            )
          }
        }

        // Sound toggle
        IconButton(
          onClick = onSoundToggle,
          modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(KidsPrimaryContainer)
            .testTag("sound_toggle_button")
        ) {
          Icon(
            imageVector = if (isSoundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeMute,
            contentDescription = if (isSoundEnabled) "Mute Sound" else "Enable Sound",
            tint = KidsPrimary,
            modifier = Modifier.size(20.dp)
          )
        }

        // Language selector pill
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = KidsPrimary,
          modifier = Modifier
            .clickable(onClick = onLanguageToggle)
            .testTag("language_toggle_pill")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = if (currentLanguage == Language.ENGLISH) "🌐 اردو" else "🌐 EN",
              style = MaterialTheme.typography.labelMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
              )
            )
          }
        }
      }
    }
  }
}

@Composable
fun CelebrationDialog(
  starsEarned: Int,
  language: Language,
  onDismiss: () -> Unit
) {
  val infiniteTransition = rememberInfiniteTransition(label = "star_pulse")
  val starScale by infiniteTransition.animateFloat(
    initialValue = 0.95f,
    targetValue = 1.15f,
    animationSpec = infiniteRepeatable(
      animation = tween(600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "star_scale"
  )

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(28.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("celebration_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Mascot badge avatar
        Box(
          modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(KidsPrimaryContainer)
            .border(3.dp, StarGold, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Image(
            painter = painterResource(id = R.drawable.mascot_avatar),
            contentDescription = "Mascot",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = if (language == Language.ENGLISH) "Fantastic Job!" else "زبردست کام!",
          style = MaterialTheme.typography.headlineMedium.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold
          ),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center,
          modifier = Modifier.scale(starScale)
        ) {
          repeat(minOf(3, starsEarned)) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = "Star",
              tint = StarGold,
              modifier = Modifier.size(36.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = if (language == Language.ENGLISH)
            "+$starsEarned Stars Earned! Keep shining, Little Genius!"
          else
            "+$starsEarned تارے حاصل کیے! شاباش میرے پیارے بچے!",
          style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
          ),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(20.dp),
          colors = ButtonDefaults.buttonColors(containerColor = KidsTertiary),
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("celebration_continue_button")
        ) {
          Text(
            text = if (language == Language.ENGLISH) "Continue Learning ✨" else "اگلا سبق شروع کریں ✨",
            style = MaterialTheme.typography.titleMedium.copy(
              color = Color.White,
              fontWeight = FontWeight.Bold
            )
          )
        }
      }
    }
  }
}

@Composable
fun ParentGateDialog(
  language: Language,
  onAccessGranted: () -> Unit,
  onDismiss: () -> Unit
) {
  val num1 = remember { (6..9).random() }
  val num2 = remember { (4..8).random() }
  val correctAnswer = remember { num1 + num2 }
  var inputAnswer by remember { mutableStateOf("") }
  var isError by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("parent_gate_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = if (language == Language.ENGLISH) "Grown-Ups Only 🔒" else "صرف والدین کے لیے 🔒",
          style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = if (language == Language.ENGLISH)
            "To keep kids safe, please solve this math question:"
          else
            "بچوں کی حفاظت کے لیے، برائے مہربانی یہ سوال حل کریں:",
          style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = KidsPrimaryContainer,
          modifier = Modifier.padding(horizontal = 8.dp)
        ) {
          Text(
            text = "$num1 + $num2 = ?",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = KidsPrimary
            ),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
          value = inputAnswer,
          onValueChange = {
            inputAnswer = it.filter { char -> char.isDigit() }
            isError = false
          },
          label = { Text(if (language == Language.ENGLISH) "Enter Answer" else "جواب درج کریں") },
          singleLine = true,
          isError = isError,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("parent_gate_input")
        )

        if (isError) {
          Text(
            text = if (language == Language.ENGLISH) "Incorrect answer, try again." else "غلط جواب، دوبارہ کوشش کریں۔",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 4.dp)
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = onDismiss,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text(if (language == Language.ENGLISH) "Cancel" else "منسوخ")
          }

          Button(
            onClick = {
              if (inputAnswer.trim() == correctAnswer.toString()) {
                onAccessGranted()
              } else {
                isError = true
              }
            },
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = KidsPrimary),
            modifier = Modifier
              .weight(1f)
              .testTag("parent_gate_submit_button")
          ) {
            Text(if (language == Language.ENGLISH) "Open" else "کھولیں")
          }
        }
      }
    }
  }
}
