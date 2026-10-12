package com.example.data

import com.example.model.*

object LearningCurriculum {

  val englishAlphabet = listOf(
    AlphabetItem("A", "a", "Apple", "سیب", "æ - Ap-ple", "🍎", "A is for Apple! Crisp and sweet.", "الف سے سیب! میٹھا اور مزیدار۔", 0xFFEF4444),
    AlphabetItem("B", "b", "Ball", "گیند", "b - Ball", "⚽", "B is for Ball! Bouncing high.", "بے سے گیند! اونچی اچھلتی ہے۔", 0xFF3B82F6),
    AlphabetItem("C", "c", "Cat", "بلی", "k - Cat", "🐱", "C is for Cat! Soft and playful.", "سی سے بلی! پیاری اور چنچل۔", 0xFFF59E0B),
    AlphabetItem("D", "d", "Dog", "کتا", "d - Dog", "🐶", "D is for Dog! A faithful friend.", "ڈی سے کتا! وفادار دوست۔", 0xFF10B981),
    AlphabetItem("E", "e", "Elephant", "ہاتھی", "e - El-e-phant", "🐘", "E is for Elephant! Gentle giant.", "ای سے ہاتھی! بڑا اور طاقتور۔", 0xFF8B5CF6),
    AlphabetItem("F", "f", "Fish", "مچھلی", "f - Fish", "🐟", "F is for Fish! Swimming in water.", "ایف سے مچھلی! پانی کی رانی۔", 0xFF06B6D4),
    AlphabetItem("G", "g", "Giraffe", "زرافہ", "dʒ - Gi-raffe", "🦒", "G is for Giraffe! Tallest of all.", "جی سے زرافہ! لمبی گردن والا۔", 0xFFF97316),
    AlphabetItem("H", "h", "Hat", "ٹوپی", "h - Hat", "🎩", "H is for Hat! Wear it on your head.", "ایچ سے ٹوپی! سر پر سجائیں۔", 0xFFEC4899),
    AlphabetItem("I", "i", "Ice Cream", "آئس کریم", "aɪ - Ice-cream", "🍦", "I is for Ice Cream! Cool and sweet.", "آئی سے آئس کریم! ٹھنڈی میٹھی۔", 0xFF6366F1),
    AlphabetItem("J", "j", "Juice", "شربت", "dʒ - Juice", "🧃", "J is for Juice! Fresh and fruity.", "جے سے جوس! صحت بخش اور لذیذ۔", 0xFF14B8A6),
    AlphabetItem("K", "k", "Kite", "پتنگ", "k - Kite", "🪁", "K is for Kite! Flying in the sky.", "کے سے پتنگ! ہوا میں اڑتی ہوئی۔", 0xFFEAB308),
    AlphabetItem("L", "l", "Lion", "شیر", "l - Li-on", "🦁", "L is for Lion! King of the jungle.", "ایل سے شیر! جنگل کا بادشاہ۔", 0xFFF59E0B),
    AlphabetItem("M", "m", "Monkey", "بندر", "m - Mon-key", "🐒", "M is for Monkey! Jumping on trees.", "ایم سے بندر! درختوں پر اچھلتا۔", 0xFF84CC16),
    AlphabetItem("N", "n", "Nest", "گھونسلہ", "n - Nest", "🪹", "N is for Nest! Cozy home for birds.", "این سے گھونسلہ! چڑیوں کا پیارا گھر۔", 0xFF10B981),
    AlphabetItem("O", "o", "Orange", "مالٹا", "ɒ - Or-ange", "🍊", "O is for Orange! Full of Vitamin C.", "او سے مالٹا! ترش و شیریں پھل۔", 0xFFF97316),
    AlphabetItem("P", "p", "Penguin", "پینگوئن", "p - Pen-guin", "🐧", "P is for Penguin! Waddling on snow.", "پی سے پینگوئن! برف پر چلنے والا۔", 0xFF3B82F6),
    AlphabetItem("Q", "q", "Queen", "ملکہ", "kw - Queen", "👑", "Q is for Queen! Wearing a crown.", "کیو سے ملکہ! سر پر تاج پہنے۔", 0xFF8B5CF6),
    AlphabetItem("R", "r", "Rainbow", "دھنک", "r - Rain-bow", "🌈", "R is for Rainbow! Seven shining colors.", "آر سے دھنک! سات رنگوں والی قوس قزح۔", 0xFFEC4899),
    AlphabetItem("S", "s", "Star", "ستارہ", "s - Star", "⭐", "S is for Star! Twinkling in night sky.", "ایس سے ستارہ! رات کو چمکتا ہوا۔", 0xFFFBBF24),
    AlphabetItem("T", "t", "Tiger", "چیتا", "t - Ti-ger", "🐯", "T is for Tiger! Fast and brave.", "ٹی سے چیتا! تیز اور نڈر۔", 0xFFEA580C),
    AlphabetItem("U", "u", "Umbrella", "چھتری", "ʌ - Um-brel-la", "☂️", "U is for Umbrella! Keeps off rain.", "یو سے چھتری! بارش سے بچاتی ہے۔", 0xFF0284C7),
    AlphabetItem("V", "v", "Violin", "سارنگی", "v - Vi-o-lin", "🎻", "V is for Violin! Playing sweet music.", "وی سے وائلن! میٹھی دھن سناتا ہے۔", 0xFF7C3AED),
    AlphabetItem("W", "w", "Whale", "وہیل", "w - Whale", "🐋", "W is for Whale! Swimming in the deep blue ocean.", "ڈبلیو سے وہیل! سمندر کی سب سے بڑی مچھلی۔", 0xFF0284C7),
    AlphabetItem("X", "x", "Xylophone", "زائلوفون", "z - Xy-lo-phone", "🎼", "X is for Xylophone! Chiming happy notes.", "ایکس سے زائلوفون! رنگ برنگا ساز۔", 0xFF10B981),
    AlphabetItem("Y", "y", "Yacht", "کشتی", "j - Yacht", "⛵", "Y is for Yacht! Sailing on waves.", "وائی سے یاٹ! پانی پر تیرتی کشتی۔", 0xFFF59E0B),
    AlphabetItem("Z", "z", "Zebra", "زیبرا", "z - Ze-bra", "🦓", "Z is for Zebra! Black and white stripes.", "زیڈ سے زیبرا! کالی اور سفید دھاریوں والا۔", 0xFF475569)
  )

  val urduAlphabet = listOf(
    UrduAlphabetItem("ا", "الف", "Alif", "انار", "Pomegranate", "🫐", "الف سے انار، میٹھا اور مزیدار!", "Alif is for Anaar (Pomegranate)!", 0xFFEF4444),
    UrduAlphabetItem("ب", "بے", "Bay", "بلی", "Cat", "🐱", "بے سے بلی، پیاری اور چنچل۔", "Bay is for Billi (Cat)!", 0xFF3B82F6),
    UrduAlphabetItem("پ", "پے", "Pay", "پتنگ", "Kite", "🪁", "پے سے پتنگ، ہوا میں اڑتی ہوئی!", "Pay is for Patang (Kite)!", 0xFFF59E0B),
    UrduAlphabetItem("ت", "تے", "Tay", "تتلی", "Butterfly", "🦋", "تے سے تتلی، رنگ برنگی خوبصورت۔", "Tay is for Titli (Butterfly)!", 0xFFEC4899),
    UrduAlphabetItem("ٹ", "ٹے", "Ttay", "ٹماٹر", "Tomato", "🍅", "ٹے سے ٹماٹر، لال اور رس بھرا۔", "Ttay is for Tamatar (Tomato)!", 0xFFDC2626),
    UrduAlphabetItem("ث", "ثے", "Say", "ثمر", "Fruit", "🍇", "ثے سے ثمر، صحت کے لیے فائدہ مند پھل۔", "Say is for Samar (Fruit)!", 0xFF10B981),
    UrduAlphabetItem("ج", "جیم", "Jeem", "جہاز", "Airplane", "✈️", "جیم سے جہاز، بادلوں کے اوپر سفر کرے۔", "Jeem is for Jahaz (Airplane)!", 0xFF0284C7),
    UrduAlphabetItem("چ", "چے", "Chay", "چاند", "Moon", "🌙", "چے سے چاند، رات کو چمکتا ہوا پیارا چاند۔", "Chay is for Chaand (Moon)!", 0xFFFBBF24),
    UrduAlphabetItem("ح", "حے", "Hay", "حوض", "Pond / Pool", "🏞️", "حے سے حوض، ٹھنڈے اور صاف پانی کا حوض۔", "Hay is for Houz (Pond)!", 0xFF06B6D4),
    UrduAlphabetItem("خ", "خے", "Khay", "خرگوش", "Rabbit", "🐰", "خے سے خرگوش، تیز دوڑنے والا پیارا جانور۔", "Khay is for Khargosh (Rabbit)!", 0xFF8B5CF6),
    UrduAlphabetItem("د", "دال", "Daal", "درخت", "Tree", "🌳", "دال سے درخت، گھنی چھاؤں دینے والا درخت۔", "Daal is for Darakht (Tree)!", 0xFF059669),
    UrduAlphabetItem("ڈ", "ڈال", "Ddaal", "ڈولفن", "Dolphin", "🐬", "ڈال سے ڈولفن، سمندر میں کرتب دکھاتی ہوئی۔", "Ddaal is for Dolphin!", 0xFF0284C7),
    UrduAlphabetItem("ذ", "ذال", "Zaal", "ذخیرہ", "Treasury", "💎", "ذال سے ذخیرہ، علم اور حکمت کا خزانہ۔", "Zaal is for Zakheera (Treasure)!", 0xFFEAB308),
    UrduAlphabetItem("ر", "رے", "Ray", "ریل گاڑی", "Train", "🚂", "رے سے ریل گاڑی، چھک چھک چلتی ہوئی!", "Ray is for Rail Gaadi (Train)!", 0xFFEF4444),
    UrduAlphabetItem("ڑ", "ڑے", "Rray", "پہاڑ", "Mountain", "⛰️", "ڑے سے پہاڑ، بلند و بالا چوٹیاں۔", "Rray in Pahaad (Mountain)!", 0xFF64748B),
    UrduAlphabetItem("ز", "زے", "Zay", "زرافہ", "Giraffe", "🦒", "زے سے زرافہ، سب سے لمبی گردن والا۔", "Zay is for Zarafa (Giraffe)!", 0xFFF59E0B),
    UrduAlphabetItem("ژ", "ژے", "Zhay", "ژالہ باری", "Hailstorm", "🌨️", "ژے سے ژالہ باری، برف کے سفید گولے۔", "Zhay is for Zhala Baari (Hail)!", 0xFF60A5FA),
    UrduAlphabetItem("س", "سین", "Seen", "سورج", "Sun", "☀️", "سین سے سورج، چمکتا دھمکتا سورج۔", "Seen is for Sooraj (Sun)!", 0xFFF59E0B),
    UrduAlphabetItem("ش", "شین", "Sheen", "شیر", "Lion", "🦁", "شین سے شیر، طاقتور اور نڈر۔", "Sheen is for Shair (Lion)!", 0xFFEA580C),
    UrduAlphabetItem("ص", "صاد", "Saad", "صابن", "Soap", "🧼", "صاد سے صابن، ہاتھ صاف رکھنے کے لیے۔", "Saad is for Saban (Soap)!", 0xFF14B8A6),
    UrduAlphabetItem("ض", "ضاد", "Zaad", "ضعیف", "Elderly", "👴", "ضاد سے ضعیف، بزرگوں کی عزت کریں۔", "Zaad is for Zaeef (Elder)!", 0xFF8B5CF6),
    UrduAlphabetItem("ط", "طوئے", "Toy", "طوطا", "Parrot", "🦜", "طوئے سے طوطا، میٹھی باتیں کرتا ہوا طوطا!", "Toy is for Tota (Parrot)!", 0xFF10B981),
    UrduAlphabetItem("ظ", "ظوئے", "Zoy", "ظروف", "Utensils", "🏺", "ظوئے سے ظروف، مٹی کے خوبصورت برتن۔", "Zoy is for Zuroof (Pots)!", 0xFFB45309),
    UrduAlphabetItem("ع", "عین", "Ain", "عینک", "Glasses", "👓", "عین سے عینک، چیزیں واضح دیکھنے کے لیے۔", "Ain is for Ainak (Glasses)!", 0xFF3B82F6),
    UrduAlphabetItem("غ", "غین", "Ghain", "غبارہ", "Balloon", "🎈", "غین سے غبارہ، رنگ برنگا ہوا میں اڑتا۔", "Ghain is for Ghubara (Balloon)!", 0xFFEC4899),
    UrduAlphabetItem("ف", "فے", "Fay", "فوارہ", "Fountain", "⛲", "فے سے فوارہ، پانی اچھالتا خوبصورت فوارہ۔", "Fay is for Fawara (Fountain)!", 0xFF06B6D4),
    UrduAlphabetItem("ق", "قاف", "Qaaf", "قلم", "Pen", "✒️", "قاف سے قلم، علم اور تعلیم کی پہچان۔", "Qaaf is for Qalam (Pen)!", 0xFF6366F1),
    UrduAlphabetItem("ک", "کاف", "Kaaf", "کتاب", "Book", "📚", "کاف سے کتاب، نئی نئی کہانیاں سیکھیں۔", "Kaaf is for Kitaab (Book)!", 0xFF10B981),
    UrduAlphabetItem("گ", "گاف", "Gaaf", "گائے", "Cow", "🐄", "گاف سے گائے، میٹھا دودھ دینے والی گائے۔", "Gaaf is for Gaaye (Cow)!", 0xFFF97316),
    UrduAlphabetItem("ل", "لام", "Laam", "لومڑی", "Fox", "🦊", "لام سے لومڑی، ہوشیار اور چست۔", "Laam is for Loomri (Fox)!", 0xFFEA580C),
    UrduAlphabetItem("م", "میم", "Meem", "مور", "Peacock", "🦚", "میم سے مور، خوبصورت پروں والا مور۔", "Meem is for Mor (Peacock)!", 0xFF0284C7),
    UrduAlphabetItem("ن", "نون", "Noon", "نلکا", "Tap", "🚰", "نون سے نلکا، پانی کبھی ضائع نہ کریں۔", "Noon is for Nalka (Water Tap)!", 0xFF06B6D4),
    UrduAlphabetItem("و", "واؤ", "Wao", "ورزش", "Exercise", "🏃", "واؤ سے ورزش، روزانہ صبح کی ورزش صحت مند رکھے۔", "Wao is for Warzish (Exercise)!", 0xFF10B981),
    UrduAlphabetItem("ہ", "ہے", "Chhoti Hay", "ہاتھی", "Elephant", "🐘", "ہے سے ہاتھی، لمبی سونڈ والا پیارا ہاتھی۔", "Hay is for Haathi (Elephant)!", 0xFF8B5CF6),
    UrduAlphabetItem("ء", "ہمزہ", "Hamza", "چائے", "Tea", "☕", "ہمزہ درمیان میں آنے والا خاص حرف۔", "Hamza (Glottal mark)!", 0xFFF59E0B),
    UrduAlphabetItem("ی", "چھوٹی یے", "Chhoti Yay", "یکہ", "Cart", "🐎", "چھوٹی یے سے یکہ، گھوڑا گاڑی۔", "Yay is for Yakkah (Horse Carriage)!", 0xFF3B82F6),
    UrduAlphabetItem("ے", "بڑی یے", "Badi Yay", "طوطے", "Parrots", "🦜", "بڑی یے لفظ کے آخر میں آتی ہے، جیسے طوطے اور تارے۔", "Badi Yay (Plural vowel)!", 0xFF10B981)
  )

  val mathNumbers = listOf(
    MathNumberItem(1, "One", "ایک", "۱", "🍎"),
    MathNumberItem(2, "Two", "دو", "۲", "🎈"),
    MathNumberItem(3, "Three", "تین", "۳", "⭐"),
    MathNumberItem(4, "Four", "چار", "۴", "🧸"),
    MathNumberItem(5, "Five", "پانچ", "۵", "🚗"),
    MathNumberItem(6, "Six", "چھ", "۶", "🍓"),
    MathNumberItem(7, "Seven", "سات", "۷", "🦋"),
    MathNumberItem(8, "Eight", "آٹھ", "۸", "🌸"),
    MathNumberItem(9, "Nine", "نو", "۹", "⚽"),
    MathNumberItem(10, "Ten", "دس", "۱۰", "🌟"),
    MathNumberItem(11, "Eleven", "گیارہ", "۱۱", "🍬"),
    MathNumberItem(12, "Twelve", "بارہ", "۱۲", "🦆"),
    MathNumberItem(13, "Thirteen", "تیرہ", "۱۳", "🍌"),
    MathNumberItem(14, "Fourteen", "چودہ", "۱۴", "🚀"),
    MathNumberItem(15, "Fifteen", "پندرہ", "۱۵", "🧁"),
    MathNumberItem(16, "Sixteen", "سولہ", "۱۶", "🐟"),
    MathNumberItem(17, "Seventeen", "سترہ", "۱۷", "🎁"),
    MathNumberItem(18, "Eighteen", "اٹھارہ", "۱۸", "🥕"),
    MathNumberItem(19, "Nineteen", "انیس", "۱۹", "🍩"),
    MathNumberItem(20, "Twenty", "بیس", "۲۰", "🏆")
  )

  val mathAdditionProblems = listOf(
    MathProblem("add1", "How many apples altogether?", "کل کتنے سیب ہیں؟", "🍎", 2, 3, "+", 5, listOf(4, 5, 6)),
    MathProblem("add2", "Count the stars combined!", "تاروں کو ملا کر گنیں!", "⭐", 3, 4, "+", 7, listOf(6, 7, 8)),
    MathProblem("add3", "How many teddy bears in all?", "کل کتنے کھلونے بنے؟", "🧸", 4, 2, "+", 6, listOf(5, 6, 7)),
    MathProblem("add4", "Add the colorful balloons!", "غباروں کو جمع کریں!", "🎈", 5, 3, "+", 8, listOf(7, 8, 9)),
    MathProblem("add5", "Count the toy cars together!", "گاڑیوں کو ملا کر گنیں!", "🚗", 6, 4, "+", 10, listOf(9, 10, 11))
  )

  val mathSubtractionProblems = listOf(
    MathProblem("sub1", "5 apples minus 2 apples, how many remain?", "۵ سیب میں سے ۲ نکالیں تو کتنے بچے؟", "🍎", 5, 2, "-", 3, listOf(2, 3, 4)),
    MathProblem("sub2", "6 balloons fly, 2 pop! How many left?", "۶ میں سے ۲ غبارے پھٹ گئے، کتنے بچے؟", "🎈", 6, 2, "-", 4, listOf(3, 4, 5)),
    MathProblem("sub3", "8 shiny stars, take away 3 stars!", "۸ میں سے ۳ تارے ہٹائیں!", "⭐", 8, 3, "-", 5, listOf(4, 5, 6)),
    MathProblem("sub4", "7 sweet strawberries minus 4!", "۷ میں سے ۴ اسٹرابیری کم کریں!", "🍓", 7, 4, "-", 3, listOf(2, 3, 4)),
    MathProblem("sub5", "10 toy cars, 5 drive away!", "۱۰ میں سے ۵ گاڑیاں چلی گئیں!", "🚗", 10, 5, "-", 5, listOf(4, 5, 6))
  )

  val mathMultiplicationProblems = listOf(
    MathProblem("mul1", "2 groups of 2 stars:", "۲ تارے دو بار:", "⭐", 2, 2, "×", 4, listOf(3, 4, 5)),
    MathProblem("mul2", "3 groups of 2 apples:", "۲ سیب تین بار:", "🍎", 3, 2, "×", 6, listOf(5, 6, 7)),
    MathProblem("mul3", "2 groups of 4 balloons:", "۴ غبارے دو بار:", "🎈", 2, 4, "×", 8, listOf(6, 8, 9)),
    MathProblem("mul4", "3 groups of 3 strawberries:", "۳ اسٹرابیری تین بار:", "🍓", 3, 3, "×", 9, listOf(7, 8, 9))
  )

  val shapes = listOf(
    ShapeItem("Circle", "دائرہ", "🔴", "0 sides (round)", "کوئی کونا نہیں، بالکل گول", "Clock, Coin, Plate, Sun", "گھڑی، سکہ، پلیٹ، سورج"),
    ShapeItem("Square", "مربع", "🟦", "4 equal sides", "۴ برابر لمبائی کے اضلاع", "Window, Chessboard, Box", "کھڑکی، ڈبہ، شطرنج کا تختہ"),
    ShapeItem("Triangle", "مثلث", "🔺", "3 straight sides", "۳ کونے اور ۳ اضلاع", "Pizza Slice, Mountain, Roof", "پیزا کا ٹکڑا، پہاڑ، چھت"),
    ShapeItem("Rectangle", "مستطیل", "🚪", "4 sides (opposites equal)", "۴ کونے (آمنے سامنے برابر)", "Door, Book, Smartphone", "دروازہ، کتاب، موبائل فون"),
    ShapeItem("Star", "ستارہ", "⭐", "5 points", "۵ کونے", "Night sky stars, Starfish", "آسمان کے تارے، اسٹار فش"),
    ShapeItem("Diamond", "ہیرا (لوزینج)", "🔷", "4 angled sides", "۴ ترچھے اضلاع", "Kite, Playing cards diamond", "پتنگ، تاش کا ہیرا")
  )

  val scienceLessons = listOf(
    ScienceLesson(
      id = "animals",
      titleEn = "Animals & Habitats",
      titleUr = "جانور اور ان کے مسکن",
      categoryEn = "Living Things",
      categoryUr = "جاندار چیزیں",
      iconEmoji = "🦁",
      factPointsEn = listOf(
        "Animals live where they find food, water, and shelter.",
        "Lions and elephants live in open grassy plains and forests.",
        "Fish and dolphins live in fresh rivers and vast oceans.",
        "Camels live in deserts because they can store water!"
      ),
      factPointsUr = listOf(
        "جانور ایسی جگہ رہتے ہیں جہاں انہیں خوراک، پانی اور پناہ ملے۔",
        "شیر اور ہاتھی جنگلات اور گھاس کے میدانوں میں رہتے ہیں۔",
        "مچھلیاں اور ڈولفن دریاؤں اور سمندروں میں تیرتی ہیں۔",
        "اونٹ صحرا میں رہتا ہے کیونکہ وہ پانی ذخیرہ کر سکتا ہے!"
      ),
      quizQuestionEn = "Where do fish and dolphins live?",
      quizQuestionUr = "مچھلیاں اور ڈولفن کہاں رہتی ہیں؟",
      quizOptionsEn = listOf("In trees", "In the ocean", "In the desert"),
      quizOptionsUr = listOf("درختوں پر", "سمندر اور پانی میں", "صحرا کی ریت میں"),
      correctOptionIndex = 1,
      funTakeawayEn = "Every animal has a special home that keeps it safe!",
      funTakeawayUr = "ہر جانور کا اپنا ایک پیارا اور محفوظ گھر ہوتا ہے!"
    ),
    ScienceLesson(
      id = "birds_insects",
      titleEn = "Birds & Busy Insects",
      titleUr = "پرندے اور کیڑے مکوڑے",
      categoryEn = "Nature",
      categoryUr = "قدرت",
      iconEmoji = "🦋",
      factPointsEn = listOf(
        "Birds have light feathers and wings to soar through the sky.",
        "Birds lay eggs in safe nests built from small twigs and leaves.",
        "Bees visit flowers to collect sweet nectar and make delicious honey.",
        "Butterflies start their life as tiny caterpillars before growing wings!"
      ),
      factPointsUr = listOf(
        "پرندوں کے خوبصورت پر ہوتے ہیں جن سے وہ ہوا میں اڑتے ہیں۔",
        "پرندے تنکوں سے بنے گھونسلوں میں انڈے دیتے ہیں۔",
        "شہد کی مکھیاں پھولوں سے رس چوس کر میٹھا شہد بناتی ہیں۔",
        "تتلیاں پہلے سنڈی کی شکل میں ہوتی ہیں اور پھر رنگ برنگی تتلی بنتی ہیں!"
      ),
      quizQuestionEn = "What do busy honeybees make from flower nectar?",
      quizQuestionUr = "شہد کی مکھیاں پھولوں کے رس سے کیا بناتی ہیں؟",
      quizOptionsEn = listOf("Sweet Honey", "Milk", "Ice Cream"),
      quizOptionsUr = listOf("میٹھا شہد", "دودھ", "آئس کریم"),
      correctOptionIndex = 0,
      funTakeawayEn = "Insects and birds help flowers and trees bloom every spring!",
      funTakeawayUr = "پرندے اور کیڑے مکوڑے باغوں کو شاداب رکھنے میں مدد کرتے ہیں!"
    ),
    ScienceLesson(
      id = "human_body",
      titleEn = "Our Amazing Body",
      titleUr = "ہمارا حیرت انگیز جسم",
      categoryEn = "Human Health",
      categoryUr = "انسانی صحت",
      iconEmoji = "🧠",
      factPointsEn = listOf(
        "Our brain controls everything we think, learn, and feel.",
        "The heart beats like a gentle pump sending blood to all body parts.",
        "Our eyes let us see, ears let us hear, and hands let us touch and build.",
        "Strong bones (our skeleton) give our body shape and allow us to run!"
      ),
      factPointsUr = listOf(
        "ہمارا دماغ ہر سوچ، پڑھائی اور عمل کو کنٹرول کرتا ہے۔",
        "دل ایک پمپ کی طرح پورے جسم کو خون پہنچاتا ہے۔",
        "آنکھوں سے ہم دیکھتے ہیں، کانوں سے سنتے ہیں اور ہاتھوں سے چھوتے ہیں۔",
        "مضبوط ہڈیاں ہمارے جسم کو سیدھا کھڑا رکھنے اور دوڑنے میں مدد دیتی ہیں!"
      ),
      quizQuestionEn = "Which organ inside our chest pumps blood all day?",
      quizQuestionUr = "ہمارے سینے میں کون سا عضو مسلسل خون پمپ کرتا ہے؟",
      quizOptionsEn = listOf("The Heart", "The Stomach", "The Elbow"),
      quizOptionsUr = listOf("دل", "معدہ", "کہنی"),
      correctOptionIndex = 0,
      funTakeawayEn = "Eating healthy fruits and drinking clean water keeps our body strong!",
      funTakeawayUr = "صاف پانی پینا اور پھل کھانا ہمارے جسم کو توانا رکھتا ہے!"
    ),
    ScienceLesson(
      id = "plants",
      titleEn = "How Plants Grow",
      titleUr = "پودے کیسے اگتے ہیں؟",
      categoryEn = "Botany",
      categoryUr = "نباتیات",
      iconEmoji = "🌱",
      factPointsEn = listOf(
        "A plant begins as a tiny seed tucked safely into soft soil.",
        "Plants need warm sunlight, fresh air, and cool water to grow.",
        "Green leaves use sunshine to make food for the whole plant.",
        "Flowers grow into delicious fruits that carry new seeds!"
      ),
      factPointsUr = listOf(
        "پودے کا آغاز مٹی میں بوئے گئے ایک چھوٹے سے بیج سے ہوتا ہے۔",
        "پودوں کو بڑھنے کے لیے سورج کی روشنی، ہوا اور پانی کی ضرورت ہوتی ہے۔",
        "ہرے پتے سورج کی روشنی سے پودے کے لیے خوراک بناتے ہیں۔",
        "پھولوں سے لذیذ پھل بنتے ہیں جن میں نئے بیج ہوتے ہیں!"
      ),
      quizQuestionEn = "What do green plants need to grow big and strong?",
      quizQuestionUr = "پودوں کو بڑھنے کے لیے کن چیزوں کی ضرورت ہوتی ہے؟",
      quizOptionsEn = listOf("Sunlight and Water", "Candy and Soda", "Toys and Books"),
      quizOptionsUr = listOf("دھوپ اور پانی", "ٹافیاں اور بوتل", "کھلونے اور کتابیں"),
      correctOptionIndex = 0,
      funTakeawayEn = "Planting trees cleans our air and keeps our world green!",
      funTakeawayUr = "درخت لگانے سے ہماری ہوا صاف اور ماحول خوشگوار رہتا ہے!"
    ),
    ScienceLesson(
      id = "weather_seasons",
      titleEn = "Weather & Four Seasons",
      titleUr = "موسم اور چاروں رتیں",
      categoryEn = "Earth Science",
      categoryUr = "موسمیات",
      iconEmoji = "🌈",
      factPointsEn = listOf(
        "Weather changes every day: it can be sunny, breezy, cloudy, or rainy.",
        "Spring brings new flowers and green leaves to every garden.",
        "Summer is warm and sunny with long, bright days.",
        "Autumn turns leaves orange and gold, and Winter brings cozy chilly snow!"
      ),
      factPointsUr = listOf(
        "موسم بدلتا رہتا ہے: کبھی دھوپ، کبھی بارش اور کبھی ٹھنڈی ہوا چلتی ہے۔",
        "بہار کے موسم میں ہر طرف نئے پھول اور ہریالی چھا جاتی ہے۔",
        "گرمیوں میں دھوپ تیز ہوتی ہے اور دن بڑے ہوتے ہیں۔",
        "خزاں میں پتے گرتے ہیں اور سردیوں میں خنک ہوائیں اور برف باری ہوتی ہے!"
      ),
      quizQuestionEn = "In which season do colorful new flowers bloom everywhere?",
      quizQuestionUr = "کس موسم میں ہر طرف رنگ برنگے نئے پھول کھلتے ہیں؟",
      quizOptionsEn = listOf("Spring", "Winter", "Desert storm"),
      quizOptionsUr = listOf("بہار کے موسم میں", "سردیوں کی ٹھنڈ میں", "آندھی میں"),
      correctOptionIndex = 0,
      funTakeawayEn = "A rainbow appears in the sky when sunshine shines through rain droplets!",
      funTakeawayUr = "جب بارش کے بعد دھوپ نکلتی ہے تو آسمان پر خوبصورت دھنک بنتی ہے!"
    ),
    ScienceLesson(
      id = "earth_space",
      titleEn = "Earth & The Solar System",
      titleUr = "زمین اور نظامِ شمسی",
      categoryEn = "Astronomy",
      categoryUr = "فلکیات",
      iconEmoji = "🪐",
      factPointsEn = listOf(
        "The Sun is a giant bright star that gives warmth and light to Earth.",
        "Earth is our home planet with blue oceans, land, and breathable air.",
        "The Moon orbits Earth and shines like a night light in the dark sky.",
        "There are 8 planets in our Solar System orbiting around the Sun!"
      ),
      factPointsUr = listOf(
        "سورج ایک بہت بڑا چمکتا ستارہ ہے جو زمین کو حرارت اور روشنی دیتا ہے۔",
        "زمین ہمارا پیارا سیارہ ہے جس پر پانی، ہوا اور خشکی موجود ہے۔",
        "چاند زمین کے گرد گھومتا ہے اور رات کے اندھیرے میں روشنی دیتا ہے۔",
        "ہمارے نظامِ شمسی میں سورج کے گرد ۸ بڑے سیارے چکر لگاتے ہیں!"
      ),
      quizQuestionEn = "What is the bright star at the center of our solar system?",
      quizQuestionUr = "ہمارے نظامِ شمسی کے مرکز میں کون سا بڑا ستارہ ہے؟",
      quizOptionsEn = listOf("The Sun", "The Moon", "A comet"),
      quizOptionsUr = listOf("سورج", "چاند", "دم دار ستارہ"),
      correctOptionIndex = 0,
      funTakeawayEn = "Astronauts travel in rocket ships to study space and the Moon!",
      funTakeawayUr = "خلا باز راکٹ میں بیٹھ کر خلا اور چاند کی سیر کرتے ہیں!"
    )
  )

  val badges = listOf(
    BadgeItem("b1", "First Steps", "پہلا قدم", "Completed your very first activity!", "آپ نے اپنی پہلی تعلیمی سرگرمی مکمل کی!", "🌟", 5),
    BadgeItem("b2", "Alphabet Star", "حروف کے چیمپئن", "Learned English letters and words!", "انگریزی کے الفاظ اور حروف سیکھے!", "🔤", 15),
    BadgeItem("b3", "Urdu Explorer", "اردو کے ہیرو", "Mastered Urdu letters and vocabulary!", "اردو حروف اور ذخیرہ الفاظ میں مہارت حاصل کی!", "📖", 30),
    BadgeItem("b4", "Number Wizard", "گنتی کے جادوگر", "Counted numbers and solved math puzzles!", "ریاضی کے سوالات اور گنتی حل کی!", "🔢", 50),
    BadgeItem("b5", "Science Star", "سائنسدان", "Explored animals, nature, and space!", "جانور، پودے اور نظام شمسی دریافت کیا!", "🔬", 75),
    BadgeItem("b6", "Super Genius", "سپر جینیئس", "Earned 100 stars across all subjects!", "تمام مضامین میں ۱۰۰ تارے حاصل کیے!", "👑", 100)
  )
}
