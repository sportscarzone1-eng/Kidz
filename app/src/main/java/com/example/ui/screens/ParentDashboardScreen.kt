package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ProgressManager
import com.example.model.Language
import com.example.ui.theme.*

@Composable
fun ParentDashboardScreen(
  language: Language,
  progressManager: ProgressManager,
  isSoundEnabled: Boolean,
  isVoiceEnabled: Boolean,
  selectedAgeGroup: String,
  completedLessonsCount: Int,
  totalStars: Int,
  modifier: Modifier = Modifier
) {
  var showResetConfirm by remember { mutableStateOf(false) }
  val quizStats = remember(totalStars) { progressManager.getQuizStats() }
  val learningMinutes = remember(totalStars) { progressManager.getEstimatedLearningMinutes() }

  val accuracyPercent = if (quizStats.second > 0) {
    ((quizStats.first.toFloat() / quizStats.second.toFloat()) * 100).toInt()
  } else 100

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(horizontal = 16.dp)
      .testTag("parent_dashboard_screen"),
    contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Title Header
    item {
      Column {
        Text(
          text = if (language == Language.ENGLISH) "Parent Dashboard 👨‍👩‍👧" else "والدین کا ڈیش بورڈ 👨‍👩‍👧",
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
          )
        )
        Text(
          text = if (language == Language.ENGLISH)
            "Insights, learning performance, and controls for your child"
          else
            "بچے کی تعلیمی کارکردگی اور ترتیبات کا جائزہ",
          style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
      }
    }

    // 2. High-Level Summary Stats Grid
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        StatCard(
          title = if (language == Language.ENGLISH) "Total Stars" else "کل تارے",
          value = "$totalStars ⭐",
          color = KidsSecondary,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          title = if (language == Language.ENGLISH) "Quiz Accuracy" else "کوئز درستگی",
          value = "$accuracyPercent%",
          color = KidsTertiary,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          title = if (language == Language.ENGLISH) "Est. Time" else "سیکھنے کا وقت",
          value = "${learningMinutes}m",
          color = KidsPrimary,
          modifier = Modifier.weight(1f)
        )
      }
    }

    // 3. Subject Progress Overview
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = if (language == Language.ENGLISH) "Curriculum Progress by Subject" else "مضامین کی پیش رفت",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )

          Spacer(modifier = Modifier.height(14.dp))

          SubjectProgressBar(
            subjectName = if (language == Language.ENGLISH) "English Alphabet & Words" else "انگریزی حروف اور الفاظ",
            progress = 0.65f,
            color = EnglishCoral
          )

          Spacer(modifier = Modifier.height(10.dp))

          SubjectProgressBar(
            subjectName = if (language == Language.ENGLISH) "Urdu Letters & Reading" else "اردو حروفِ تہجی اور پڑھائی",
            progress = 0.55f,
            color = UrduGreen
          )

          Spacer(modifier = Modifier.height(10.dp))

          SubjectProgressBar(
            subjectName = if (language == Language.ENGLISH) "Mathematics & Shapes" else "ریاضی اور اشکال",
            progress = 0.70f,
            color = MathSky
          )

          Spacer(modifier = Modifier.height(10.dp))

          SubjectProgressBar(
            subjectName = if (language == Language.ENGLISH) "Science & Discovery" else "سائنس اور فطرت",
            progress = 0.50f,
            color = ScienceViolet
          )
        }
      }
    }

    // 4. Age Mode Recommendation
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = if (language == Language.ENGLISH) "Child's Age Group" else "بچے کی عمر کا انتخاب",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = if (language == Language.ENGLISH)
              "Adapts problem difficulty and visual hints"
            else
              "سوالات کی مشکل اور رہنمائی بچے کی عمر کے مطابق ہوتی ہے",
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            FilterChip(
              selected = selectedAgeGroup == "4-5",
              onClick = { progressManager.setAgeGroup("4-5") },
              label = { Text("Ages 4–5 (Pre-K / Kinder)") },
              leadingIcon = if (selectedAgeGroup == "4-5") {
                { Icon(Icons.Default.Check, "Selected", modifier = Modifier.size(16.dp)) }
              } else null,
              modifier = Modifier.weight(1f)
            )

            FilterChip(
              selected = selectedAgeGroup == "6-8",
              onClick = { progressManager.setAgeGroup("6-8") },
              label = { Text("Ages 6–8 (Elementary)") },
              leadingIcon = if (selectedAgeGroup == "6-8") {
                { Icon(Icons.Default.Check, "Selected", modifier = Modifier.size(16.dp)) }
              } else null,
              modifier = Modifier.weight(1f)
            )
          }
        }
      }
    }

    // 5. Audio & Voice Settings
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = if (language == Language.ENGLISH) "Audio & Narration Settings" else "آواز اور تلفظ کی ترتیبات",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text(
                text = if (language == Language.ENGLISH) "Sound Effects & Fanfare" else "گیم کی آوازیں اور دھنیں",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
              )
              Text(
                text = if (language == Language.ENGLISH) "Gentle beeps, taps, and cheers" else "درست جواب پر حوصلہ افزا آواز",
                style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
              )
            }
            Switch(
              checked = isSoundEnabled,
              onCheckedChange = { progressManager.setSoundEnabled(it) }
            )
          }

          Divider(modifier = Modifier.padding(vertical = 10.dp), color = CardBorder)

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text(
                text = if (language == Language.ENGLISH) "Spoken Voice Pronunciation" else "الفاظ اور حروف کا تلفظ",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
              )
              Text(
                text = if (language == Language.ENGLISH) "Real speech for letters & words" else "حروف اور جملے بول کر سنانا",
                style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
              )
            }
            Switch(
              checked = isVoiceEnabled,
              onCheckedChange = { progressManager.setVoiceEnabled(it) }
            )
          }
        }
      }
    }

    // 6. Child Safety & Privacy Card
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
        border = CardDefaults.outlinedCardBorder()
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.Top
        ) {
          Icon(
            imageVector = Icons.Default.Shield,
            contentDescription = "Safety Shield",
            tint = KidsTertiary,
            modifier = Modifier.size(28.dp)
          )
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = if (language == Language.ENGLISH) "Child Safety & Privacy Pledge" else "بچوں کی حفاظت اور رازداری کا وعدہ",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF065F46)
              )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = if (language == Language.ENGLISH)
                "• 100% on-device learning\n• Zero ads or sponsored popups\n• No personal data collected or shared\n• Fully compliant with Google Play Families Policies"
              else
                "• ۱۰۰ فیصد محفوظ اور آف لائن\n• کوئی اشتہار یا پاپ اپ نہیں\n• بچے کی کوئی ذاتی معلومات محفوظ نہیں کی جاتی\n• گوگل پلے فیملی پالیسی کے عین مطابق",
              style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF047857), lineHeight = 18.sp)
            )
          }
        }
      }
    }

    // 7. Reset Progress Button
    item {
      OutlinedButton(
        onClick = { showResetConfirm = true },
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorSoftRed),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("reset_progress_button")
      ) {
        Icon(
          imageVector = Icons.Default.Warning,
          contentDescription = null,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(if (language == Language.ENGLISH) "Reset Learning Progress" else "پیش رفت دوبارہ شروع کریں")
      }
    }
  }

  if (showResetConfirm) {
    AlertDialog(
      onDismissRequest = { showResetConfirm = false },
      title = { Text(if (language == Language.ENGLISH) "Reset All Progress?" else "کیا آپ تمام پیش رفت مٹانا چاہتے ہیں؟") },
      text = {
        Text(
          if (language == Language.ENGLISH)
            "This will reset earned stars, badges, and completed lesson history."
          else
            "اس عمل سے تمام حاصل کردہ تارے اور اسباق کی تاریخ دوبارہ شروع ہو جائے گی۔"
        )
      },
      confirmButton = {
        TextButton(
          onClick = {
            progressManager.resetAllProgress()
            showResetConfirm = false
          }
        ) {
          Text(if (language == Language.ENGLISH) "Yes, Reset" else "ہاں، مٹا دیں", color = ErrorSoftRed)
        }
      },
      dismissButton = {
        TextButton(onClick = { showResetConfirm = false }) {
          Text(if (language == Language.ENGLISH) "Cancel" else "منسوخ")
        }
      }
    )
  }
}

@Composable
private fun StatCard(
  title: String,
  value: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = CardDefaults.outlinedCardBorder()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = value,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.ExtraBold,
          color = color
        )
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(color = TextMuted),
        maxLines = 1
      )
    }
  }
}

@Composable
private fun SubjectProgressBar(
  subjectName: String,
  progress: Float,
  color: Color
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        text = subjectName,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = TextDark)
      )
      Text(
        text = "${(progress * 100).toInt()}%",
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = color)
      )
    }

    Spacer(modifier = Modifier.height(6.dp))

    LinearProgressIndicator(
      progress = { progress },
      modifier = Modifier
        .fillMaxWidth()
        .height(8.dp)
        .clip(RoundedCornerShape(4.dp)),
      color = color,
      trackColor = Color(0xFFF1F5F9)
    )
  }
}
