# ElectroEdge

**ElectroEdge** is an interactive Android learning application designed to help students and enthusiasts master the fundamentals of **Electrical and Electronic Engineering** through structured PDF study modules, interactive visual flashcards, progressive quizzes, and real-time performance tracking.

---

## Features

- Personalized User Onboarding
  - Custom nickname setup stored locally via `SharedPreferences`.
  - Easy profile switching ("Change Nickname") from the navigation drawer.

- Progressive Study Modules (PDF Reader)
  - Built-in PDF topic viewer covering 8 core chapters of electronics.
  - **Mastery-Based Unlocking:** Chapter 1 starts unlocked; subsequent chapters unlock automatically when you score **80% or higher** on the preceding chapter's quiz.

- Interactive Visual Flashcards
  - Organized by topic sets to reinforce component symbols, formulas, and definitions.
  - Tap-to-flip card animations (`flip_in` / `flip_out`) and left/right swipe gestures to navigate between cards.

- Chapter Quizzes
  - 8 multiple-choice quizzes aligned with each curriculum chapter.
  - Progressive unlocking tied to quiz mastery (80%+ threshold).
  - Automatic high-score persistence using **Room Database** (`SQLite`).

- Performance Dashboard
  - Visualizes quiz scores and completion percentages across all 8 chapters.
  - Custom circular progress indicators (`ProgressCircleView`) with checkmark badges upon achieving 100%.

---

##  Curriculum Chapters

1. **Chapter 1:** Difference between Electrical and Electronic Circuits
2. **Chapter 2:** Fundamentals of Electronic Circuits
3. **Chapter 3:** Resistors
4. **Chapter 4:** Scientific Notations and Metric System
5. **Chapter 5:** Electronic Components
6. **Chapter 6:** Semiconductors
7. **Chapter 7:** Transistors and Amplification
8. **Chapter 8:** Power Supplies

---

##  Tech Stack

| Category | Technology |
| :--- | :--- |
| **Language** | Java (Java 11 compatibility) |
| **Platform** | Android SDK (Min SDK 27 / Target & Compile SDK 36) |
| **UI Framework** | AndroidX AppCompat, Material Design Components, ConstraintLayout, DrawerLayout |
| **Local Storage** | Room Persistence Library (`2.6.1`), AndroidX Preference / `SharedPreferences` |
| **Image Loading** | Glide (`4.16.0`) |
| **Document Viewing** | Android PDF Viewer |
| **Build System** | Gradle (Kotlin DSL `build.gradle.kts`) |

---

##  Project Structure

```text
ElectroEdge/
├── app/
│   ├── src/main/
│   │   ├── assets/                  # Chapter 1–8 PDF learning modules
│   │   ├── java/com/example/project1/
│   │   │   ├── User_startup.java        # Launcher & nickname setup screen
│   │   │   ├── Welcome.java             # Welcome splash/intro screen
│   │   │   ├── BaseActivity.java        # Shared bottom navigation controller
│   │   │   ├── Dashboard.java           # Main home screen & navigation drawer
│   │   │   ├── TopicList.java           # Chapter list with 80% unlock logic
│   │   │   ├── topicviewer.java         # Built-in PDF chapter viewer
│   │   │   ├── FlashcardList.java       # Flashcard deck selection screen
│   │   │   ├── FlashcardsActivity.java  # Flip & swipe flashcard study view
│   │   │   ├── QuizList.java            # Quiz selection list with lock status
│   │   │   ├── QuizActivity.java        # Multiple-choice quiz engine
│   │   │   ├── PerformanceActivity.java # Visual score & progress tracker
│   │   │   ├── ProgressCircleView.java  # Custom circular progress view
│   │   │   ├── AppDatabase.java         # Room database configuration
│   │   │   ├── Score.java               # Room entity for quiz scores
│   │   │   └── ScoreDao.java            # DAO for querying & saving scores
│   │   ├── res/                     # Layouts, drawables, animations, and themes
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
└── settings.gradle.kts
```

---

##  Getting Started

### Prerequisites

- **Android Studio** (Ladybug or newer recommended)
- **JDK 11** or higher
- **Android SDK 36** installed via SDK Manager
- An Android device or emulator running **Android 8.1 (API 27)** or higher

### Installation & Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/justlanee/electroedge.git
   cd electroedge
   ```

2. **Open in Android Studio:**
   - Launch Android Studio and select **Open**.
   - Select the `ElectroEdge` project directory and wait for Gradle sync to complete.

3. **Build and Run:**
   - Connect an Android device (with USB debugging enabled) or start an Android Virtual Device (AVD).
   - Click **Run ▶** (`Shift + F10`) in Android Studio, or build from the terminal:
     ```bash
     ./gradlew assembleDebug
     ```

---

## 📄 License

This project is developed for educational purposes.
