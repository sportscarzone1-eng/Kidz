package com.example

import com.example.data.LearningCurriculum
import com.example.model.Language
import com.example.model.Subject
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun testEnglishAlphabetCompleteness() {
    val alphabet = LearningCurriculum.englishAlphabet
    assertEquals("Should contain 26 English letters", 26, alphabet.size)
    assertEquals("A", alphabet.first().uppercase)
    assertEquals("Z", alphabet.last().uppercase)
  }

  @Test
  fun testUrduAlphabetCompleteness() {
    val urduAlphabet = LearningCurriculum.urduAlphabet
    assertTrue("Should contain at least 36 Urdu letters", urduAlphabet.size >= 36)
    assertEquals("ا", urduAlphabet.first().letter)
    assertEquals("ے", urduAlphabet.last().letter)
  }

  @Test
  fun testMathProblemsAnswers() {
    LearningCurriculum.mathAdditionProblems.forEach { problem ->
      assertEquals(problem.count1 + problem.count2, problem.answer)
      assertTrue(problem.options.contains(problem.answer))
    }

    LearningCurriculum.mathSubtractionProblems.forEach { problem ->
      assertEquals(problem.count1 - problem.count2, problem.answer)
      assertTrue(problem.options.contains(problem.answer))
    }

    LearningCurriculum.mathMultiplicationProblems.forEach { problem ->
      assertEquals(problem.count1 * problem.count2, problem.answer)
      assertTrue(problem.options.contains(problem.answer))
    }
  }

  @Test
  fun testScienceLessonsValidQuiz() {
    LearningCurriculum.scienceLessons.forEach { lesson ->
      assertTrue(lesson.correctOptionIndex in 0 until lesson.quizOptionsEn.size)
      assertEquals(lesson.quizOptionsEn.size, lesson.quizOptionsUr.size)
    }
  }

  @Test
  fun testSubjectConfigurations() {
    val subjects = Subject.values()
    assertEquals(4, subjects.size)
    val ids = subjects.map { it.id }.toSet()
    assertTrue(ids.contains("english"))
    assertTrue(ids.contains("urdu"))
    assertTrue(ids.contains("math"))
    assertTrue(ids.contains("science"))
  }
}
