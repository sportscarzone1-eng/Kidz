package com.example.model

enum class Language(val displayName: String, val code: String) {
  ENGLISH("English", "en"),
  URDU("اردو", "ur")
}

enum class Subject(
  val id: String,
  val titleEn: String,
  val titleUr: String,
  val subtitleEn: String,
  val subtitleUr: String,
  val emoji: String,
  val primaryColorHex: Long,
  val secondaryColorHex: Long
) {
  ENGLISH(
    id = "english",
    titleEn = "English",
    titleUr = "انگریزی",
    subtitleEn = "Alphabet, Phonics & Words",
    subtitleUr = "حروفِ تہجی اور الفاظ",
    emoji = "🔤",
    primaryColorHex = 0xFFEF4444,
    secondaryColorHex = 0xFFFEE2E2
  ),
  URDU(
    id = "urdu",
    titleEn = "Urdu",
    titleUr = "اردو",
    subtitleEn = "Letters, Words & Reading",
    subtitleUr = "آؤ اردو سیکھیں الف سے ے",
    emoji = "📖",
    primaryColorHex = 0xFF059669,
    secondaryColorHex = 0xFFD1FAE5
  ),
  MATHEMATICS(
    id = "math",
    titleEn = "Mathematics",
    titleUr = "ریاضی",
    subtitleEn = "Numbers, Counting & Shapes",
    subtitleUr = "گنتی، جمع اور اشکال",
    emoji = "🔢",
    primaryColorHex = 0xFF0284C7,
    secondaryColorHex = 0xFFE0F2FE
  ),
  SCIENCE(
    id = "science",
    titleEn = "Science",
    titleUr = "سائنس",
    subtitleEn = "Nature, Animals & Space",
    subtitleUr = "قدرت، جاندار اور نظام شمسی",
    emoji = "🔬",
    primaryColorHex = 0xFF7C3AED,
    secondaryColorHex = 0xFFEDE9FE
  )
}

data class AlphabetItem(
  val uppercase: String,
  val lowercase: String,
  val wordEn: String,
  val wordUr: String,
  val phonics: String,
  val emoji: String,
  val sentenceEn: String,
  val sentenceUr: String,
  val colorHex: Long
)

data class UrduAlphabetItem(
  val letter: String,
  val letterNameUr: String,
  val letterNameEn: String,
  val wordUr: String,
  val wordEn: String,
  val emoji: String,
  val sentenceUr: String,
  val sentenceEn: String,
  val colorHex: Long
)

data class MathNumberItem(
  val number: Int,
  val wordEn: String,
  val wordUr: String,
  val digitUr: String,
  val emoji: String
)

data class MathProblem(
  val id: String,
  val questionEn: String,
  val questionUr: String,
  val visualItem: String,
  val count1: Int,
  val count2: Int,
  val operation: String, // "+", "-", "×"
  val answer: Int,
  val options: List<Int>
)

data class ShapeItem(
  val nameEn: String,
  val nameUr: String,
  val emoji: String,
  val sidesCountEn: String,
  val sidesCountUr: String,
  val realWorldExamplesEn: String,
  val realWorldExamplesUr: String
)

data class ScienceLesson(
  val id: String,
  val titleEn: String,
  val titleUr: String,
  val categoryEn: String,
  val categoryUr: String,
  val iconEmoji: String,
  val factPointsEn: List<String>,
  val factPointsUr: List<String>,
  val quizQuestionEn: String,
  val quizQuestionUr: String,
  val quizOptionsEn: List<String>,
  val quizOptionsUr: List<String>,
  val correctOptionIndex: Int,
  val funTakeawayEn: String,
  val funTakeawayUr: String
)

data class BadgeItem(
  val id: String,
  val titleEn: String,
  val titleUr: String,
  val descEn: String,
  val descUr: String,
  val emoji: String,
  val requiredStars: Int
)

data class DailyGoalProgress(
  val targetActivities: Int = 5,
  val completedActivities: Int = 0,
  val currentStreakDays: Int = 1
)
