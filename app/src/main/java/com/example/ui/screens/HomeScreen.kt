package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.LearningCurriculum
import com.example.model.DailyGoalProgress
import com.example.model.Language
import com.example.model.Subject
import com.example.ui.theme.*

@Composable
fun HomeScreen(
  language: Language,
  starsCount: Int,
  dailyGoal: DailyGoalProgress,
  completedLessonsCount: Int,
  onSubjectClick: (Subject) -> Unit,
  onGamesClick: () -> Unit,
  onParentDashboardClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("home_screen"),
    contentPadding = PaddingValues(bottom = 32.dp)
  ) {
    // 1. Hero Mascot Banner
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 10.dp)
          .testTag("hero_mascot_card"),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
      ) {
        Box(modifier = Modifier.fillMaxWidth().height(170.dp)) {
          Image(
            painter = painterResource(id = R.drawable.mascot_banner),
            contentDescription = "Little Genius Mascot",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )

          // Soft gradient overlay at bottom
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(
                Brush.verticalGradient(
                  colors = listOf(Color.Transparent, Color(0x99000000)),
                  startY = 180f
                )
              )
          )

          Column(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(16.dp)
          ) {
            Text(
              text = if (language == Language.ENGLISH) "Welcome, Little Genius! 🌟" else "خوش آمدید، لٹل جینیئس! 🌟",
              style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
              )
            )
            Text(
              text = if (language == Language.ENGLISH) "Ready for today's magical learning journey?" else "آج کا دلچسپ تعلیمی سفر شروع کریں؟",
              style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFFFEF08A),
                fontWeight = FontWeight.Medium
              )
            )
          }
        }
      }
    }

    // 2. Daily Learning Goal Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp)
          .testTag("daily_goal_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "🎯 ",
                fontSize = 20.sp
              )
              Text(
                text = if (language == Language.ENGLISH) "Daily Learning Goal" else "روزانہ کا تعلیمی ہدف",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            val progressRatio = (dailyGoal.completedActivities.toFloat() / dailyGoal.targetActivities.toFloat())
              .coerceIn(0f, 1f)

            LinearProgressIndicator(
              progress = { progressRatio },
              modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp)),
              color = KidsSecondary,
              trackColor = KidsSecondaryContainer
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = if (language == Language.ENGLISH)
                "${dailyGoal.completedActivities} of ${dailyGoal.targetActivities} activities completed"
              else
                "${dailyGoal.targetActivities} میں سے ${dailyGoal.completedActivities} سرگرمیاں مکمل",
              style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
          }

          Spacer(modifier = Modifier.width(16.dp))

          Surface(
            shape = RoundedCornerShape(16.dp),
            color = KidsTertiaryContainer,
            modifier = Modifier.size(56.dp)
          ) {
            Column(
              modifier = Modifier.fillMaxSize(),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Text("🔥", fontSize = 18.sp)
              Text(
                text = "${dailyGoal.currentStreakDays}d",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = KidsTertiary
                )
              )
            }
          }
        }
      }
    }

    // 3. Section Title: Choose Subject
    item {
      Text(
        text = if (language == Language.ENGLISH) "Pick a Subject to Learn" else "پڑھنے کے لیے مضمون منتخب کریں",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier.padding(start = 18.dp, top = 14.dp, bottom = 8.dp, end = 18.dp)
      )
    }

    // 4. Four Big Subject Cards
    val subjectPairs = Subject.values().toList().chunked(2)
    items(subjectPairs) { pair ->
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        pair.forEach { subject ->
          SubjectCard(
            subject = subject,
            language = language,
            onClick = { onSubjectClick(subject) },
            modifier = Modifier
              .weight(1f)
              .testTag("subject_card_${subject.id}")
          )
        }
      }
    }

    // 5. Fun Games Banner
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 10.dp)
          .clickable { onGamesClick() }
          .testTag("games_banner_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF4FF)),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(KidsPrimary, Color(0xFFEC4899)))),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(0xFFFAE8FF)),
              contentAlignment = Alignment.Center
            ) {
              Text("🎮", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
              Text(
                text = if (language == Language.ENGLISH) "Play & Learn Games" else "کھیلیں اور سیکھیں",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF86198F)
                )
              )
              Text(
                text = if (language == Language.ENGLISH)
                  "Memory match, letter challenge & quizzes"
                else
                  "یادداشت کارڈز، حروف اور کوئز کھیلیں",
                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFA21CAF))
              )
            }
          }

          FilledIconButton(
            onClick = onGamesClick,
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFFD946EF)),
            modifier = Modifier.size(40.dp)
          ) {
            Icon(
              imageVector = Icons.Default.PlayArrow,
              contentDescription = "Play Games",
              tint = Color.White
            )
          }
        }
      }
    }

    // 6. Badges Ribbon
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp)
          .testTag("badges_ribbon_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.WorkspacePremium,
                contentDescription = "Badges",
                tint = StarGold,
                modifier = Modifier.size(22.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (language == Language.ENGLISH) "Your Learning Badges" else "آپ کے حاصل کردہ بیجز",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
            }

            Text(
              text = "$starsCount ⭐",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = KidsSecondary
              )
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            items(LearningCurriculum.badges) { badge ->
              val isUnlocked = starsCount >= badge.requiredStars
              Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isUnlocked) KidsSecondaryContainer else Color(0xFFF1F5F9),
                modifier = Modifier
                  .width(96.dp)
                  .testTag("badge_item_${badge.id}")
              ) {
                Column(
                  modifier = Modifier.padding(10.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  Text(
                    text = if (isUnlocked) badge.emoji else "🔒",
                    fontSize = 26.sp
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = if (language == Language.ENGLISH) badge.titleEn else badge.titleUr,
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = if (isUnlocked) Color(0xFF78350F) else TextMuted
                    ),
                    maxLines = 1
                  )
                  Text(
                    text = "${badge.requiredStars} ⭐",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = TextMuted)
                  )
                }
              }
            }
          }
        }
      }
    }

    // 7. Parent Dashboard Access Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, end = 16.dp, top = 8.dp)
          .clickable { onParentDashboardClick() }
          .testTag("parent_zone_button"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = "Parent Gate",
              tint = TextMuted,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = if (language == Language.ENGLISH) "Parents Area" else "والدین کا سیکشن",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = TextDark
                )
              )
              Text(
                text = if (language == Language.ENGLISH) "Progress stats, age modes & sound settings" else "پڑھائی کی رپورٹ، ترتیبات اور عمر کا انتخاب",
                style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
              )
            }
          }

          Text(
            text = "🔒",
            fontSize = 18.sp
          )
        }
      }
    }
  }
}

@Composable
private fun SubjectCard(
  subject: Subject,
  language: Language,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val primaryColor = Color(subject.primaryColorHex)
  val secondaryColor = Color(subject.secondaryColorHex)

  Card(
    modifier = modifier
      .height(150.dp)
      .clickable(onClick = onClick),
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = secondaryColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(14.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Box(
          modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(Color.White),
          contentAlignment = Alignment.Center
        ) {
          Text(text = subject.emoji, fontSize = 24.sp)
        }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = primaryColor
        ) {
          Text(
            text = "★",
            color = Color.White,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            fontSize = 12.sp
          )
        }
      }

      Column {
        Text(
          text = if (language == Language.ENGLISH) subject.titleEn else subject.titleUr,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            color = primaryColor
          )
        )
        Text(
          text = if (language == Language.ENGLISH) subject.subtitleEn else subject.subtitleUr,
          style = MaterialTheme.typography.labelSmall.copy(
            color = primaryColor.copy(alpha = 0.85f),
            fontWeight = FontWeight.Medium
          ),
          maxLines = 2
        )
      }
    }
  }
}
