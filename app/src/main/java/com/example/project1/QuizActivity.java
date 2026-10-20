package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class QuizActivity extends AppCompatActivity {

    TextView quizTitleText, questionText, scoreText;
    Button optionA, optionB, optionC, optionD, btnBackToDashboard, btnToPerformance;

    int currentQuestion = 0;
    int score = 0;
    int quizNumber = 0;

    String[] questions;
    String[][] choices;
    String[] correctAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //  Find views
        quizTitleText = findViewById(R.id.quizTitleText);
        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        btnBackToDashboard = findViewById(R.id.btnBackToDashboard);
        btnToPerformance = findViewById(R.id.btnToPerformance);

        // Hide buttons at start
        btnBackToDashboard.setVisibility(View.GONE);
        btnToPerformance.setVisibility(View.GONE);
        scoreText.setVisibility(View.GONE);

        // Get quiz number & title passed from previous activity
        quizNumber = getIntent().getIntExtra("QUIZ_NUMBER", 0);
        String quizTitle = getIntent().getStringExtra("QUIZ_TITLE");
        if (quizTitle != null) {
            quizTitleText.setText(quizTitle);
        }

        // Load quiz data depending on quiz number (temporary until DB integration)
        if (quizNumber == 0) {
            loadQuiz1();
        } else if (quizNumber == 1) {
            loadQuiz2();
        } else if (quizNumber == 2) {
            loadQuiz3();
        } else if (quizNumber == 3) {
            loadQuiz4();
        } else if (quizNumber == 4) {
            loadQuiz5();
        } else if (quizNumber == 5) {
            loadQuiz6();
        } else if (quizNumber == 6) {
            loadQuiz7();
        } else if (quizNumber == 7) {
            loadQuiz8();
        }

        loadQuestion();

        //  Answer buttons logic
        View.OnClickListener answerClickListener = v -> {
            Button clickedButton = (Button) v;
            String selectedAnswer = clickedButton.getText().toString();

            if (selectedAnswer.equals(correctAnswers[currentQuestion])) {
                score++;
            }

            currentQuestion++;

            if (currentQuestion < questions.length) {
                loadQuestion();
            } else {
                //  End of quiz
                questionText.setText("Quiz Finished! Your Score: " + score + "/" + questions.length);
                optionA.setVisibility(View.GONE);
                optionB.setVisibility(View.GONE);
                optionC.setVisibility(View.GONE);
                optionD.setVisibility(View.GONE);

                //  Save the score (only keeps highest)
                saveQuizScore(quizNumber, score, questions.length);

                //  Show buttons
                btnBackToDashboard.setVisibility(View.VISIBLE);
                btnToPerformance.setVisibility(View.VISIBLE);
                scoreText.setVisibility(View.VISIBLE);
                scoreText.setText("Final Score: " + score);
            }
        };

        optionA.setOnClickListener(answerClickListener);
        optionB.setOnClickListener(answerClickListener);
        optionC.setOnClickListener(answerClickListener);
        optionD.setOnClickListener(answerClickListener);

        //  Back to Quiz list
        btnBackToDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(QuizActivity.this, QuizList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        //  Go to Performance screen
        btnToPerformance.setOnClickListener(v -> {
            Intent intent = new Intent(QuizActivity.this, PerformanceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadQuestion() {
        questionText.setText(questions[currentQuestion]);
        optionA.setText(choices[currentQuestion][0]);
        optionB.setText(choices[currentQuestion][1]);
        optionC.setText(choices[currentQuestion][2]);
        optionD.setText(choices[currentQuestion][3]);
        scoreText.setText("Score: " + score);
    }

    //  Save highest quiz score to Room Database
    private void saveQuizScore(int quizNumber, int newScore, int total) {
        AppDatabase db = AppDatabase.getInstance(this);
        ScoreDao dao = db.scoreDao();

        new Thread(() -> {
            Score existing = dao.getScoreByQuizNumber(quizNumber);

            if (existing == null) {
                //  First attempt
                dao.insert(new Score(quizNumber, newScore, total));
            } else if (newScore > existing.score) {
                //  Update only if new score is higher
                existing.score = newScore;
                existing.total = total;
                dao.update(existing);
            }
            //  Do nothing if score is lower
        }).start();
    }


//Quizzes

    private void loadQuiz1() {
        questions = new String[]{
                "What is the primary purpose of an electronic circuit?",
                "What component provides energy to a circuit?",
                "What is the function of a conductor?",
                "Which component uses electricity to perform work?",
                "A switch controls the flow of current by:",
                "For current to flow, a circuit must be:",
                "In a series circuit, components are connected:",
                "The total resistance in a series circuit equals:",
                "In a series circuit, if one component fails:",
                "In a parallel circuit, voltage is:",
                "In a parallel circuit, current:",
                "If one bulb in a parallel circuit burns out, the others:",
                "Formula for total resistance in a parallel circuit:",
                "Which statement best describes current in a series circuit?",
                "Which law defines the relationship between voltage, current, and resistance?",
                "According to Ohm’s Law, V = ?",
                "The unit of voltage is the:",
                "The unit of current is the:",
                "The unit of resistance is the:",
                "The unit of power is the:",
                "Power in a circuit is calculated by:",
                "Which of the following increases if voltage rises while resistance stays constant?",
                "A 10 V battery connected to a 5 Ω resistor produces current of:",
                "In series circuits, voltage:",
                "In parallel circuits, total resistance is always:",
                "Which statement about current flow is true?",
                "If resistance increases while voltage is constant, current will:",
                "In a simple DC circuit, energy is transferred from:",
                "Which circuit configuration provides redundancy if one component fails?",
                "The Ohm’s Law triangle helps to remember:"
        };

        choices = new String[][]{
                {"To store electricity", "To control and direct current flow", "To increase mechanical power", "To produce sound"},
                {"Load", "Power source", "Conductor", "Switch"},
                {"To block current", "To carry electric current", "To convert energy to heat", "To store energy"},
                {"Load", "Battery", "Conductor", "Switch"},
                {"Changing voltage", "Opening or closing the circuit", "Storing energy", "Dividing current"},
                {"Open", "Closed", "Broken", "Disconnected"},
                {"Side by side", "In a single path", "Across multiple branches", "Randomly"},
                {"The smallest resistor", "The product of all resistances", "The sum of all resistances", "Zero"},
                {"The others continue working", "Current increases", "The entire circuit stops", "Voltage doubles"},
                {"Divided among branches", "The same across all components", "Zero", "Dependent on current"},
                {"Remains constant in all branches", "Splits between branches", "Is zero", "Equals voltage divided by power"},
                {"Also go out", "Continue to light", "Flicker", "Lose half the brightness"},
                {"Rₜ = R₁ + R₂ + R₃", "1/Rₜ = 1/R₁ + 1/R₂ + 1/R₃ + …", "Rₜ = R₁ × R₂ × R₃", "Rₜ = R₁ / R₂"},
                {"Current splits between resistors", "Current is the same throughout", "Current increases at each resistor", "Current stops at each node"},
                {"Kirchhoff’s Law", "Ohm’s Law", "Newton’s Law", "Ampere’s Law"},
                {"I × R", "P × I", "R / I", "I / R"},
                {"Ampere", "Ohm", "Volt", "Watt"},
                {"Watt", "Ampere", "Volt", "Joule"},
                {"Volt", "Watt", "Ohm", "Coulomb"},
                {"Ohm", "Volt", "Watt", "Ampere"},
                {"P = I / V", "P = V × I", "P = V + I", "P = V² × R"},
                {"Current", "Power loss", "Inductance", "Capacitance"},
                {"0.5 A", "2 A", "10 A", "50 A"},
                {"Is divided among components", "Is constant across all", "Is zero", "Depends on capacitance"},
                {"Greater than the largest resistor", "Smaller than the smallest resistor", "Equal to average resistance", "Zero"},
                {"Electrons move from negative to positive", "Conventional current flows from negative to positive", "Current can flow in an open circuit", "Current increases without voltage"},
                {"Increase", "Decrease", "Stay the same", "Reverse direction"},
                {"Load to source", "Source to load", "Switch to battery", "Ground to conductor"},
                {"Series", "Parallel", "Open", "Shorted"},
                {"How to calculate power", "The relationship between V, I, and R", "The polarity of batteries", "The magnetic field direction"}
        };

        correctAnswers = new String[]{
                "To control and direct current flow",
                "Power source",
                "To carry electric current",
                "Load",
                "Opening or closing the circuit",
                "Closed",
                "In a single path",
                "The sum of all resistances",
                "The entire circuit stops",
                "The same across all components",
                "Splits between branches",
                "Continue to light",
                "1/Rₜ = 1/R₁ + 1/R₂ + 1/R₃ + …",
                "Current is the same throughout",
                "Ohm’s Law",
                "I × R",
                "Volt",
                "Ampere",
                "Ohm",
                "Watt",
                "P = V × I",
                "Current",
                "2 A",
                "Is divided among components",
                "Smaller than the smallest resistor",
                "Electrons move from negative to positive",
                "Decrease",
                "Source to load",
                "Parallel",
                "The relationship between V, I, and R"
        };
    }

    private void loadQuiz2() {
        questions = new String[]{
                "What is the primary purpose of an electronic circuit?",
                "What component provides energy to a circuit?",
                "What is the function of a conductor?",
                "Which component uses electricity to perform work?",
                "A switch controls the flow of current by:",
                "For current to flow, a circuit must be:",
                "In a series circuit, components are connected:",
                "The total resistance in a series circuit equals:",
                "In a series circuit, if one component fails:",
                "In a parallel circuit, voltage is:",
                "In a parallel circuit, current:",
                "If one bulb in a parallel circuit burns out, the others:",
                "Formula for total resistance in a parallel circuit:",
                "Which statement best describes current in a series circuit?",
                "Which law defines the relationship between voltage, current, and resistance?",
                "According to Ohm’s Law, V = ?",
                "The unit of voltage is the:",
                "The unit of current is the:",
                "The unit of resistance is the:",
                "The unit of power is the:",
                "Power in a circuit is calculated by:",
                "Which of the following increases if voltage rises while resistance stays constant?",
                "A 10 V battery connected to a 5 Ω resistor produces current of:",
                "In series circuits, voltage:",
                "In parallel circuits, total resistance is always:",
                "Which statement about current flow is true?",
                "If resistance increases while voltage is constant, current will:",
                "In a simple DC circuit, energy is transferred from:",
                "Which circuit configuration provides redundancy if one component fails?",
                "The Ohm’s Law triangle helps to remember:"
        };

        choices = new String[][]{
                {"To store electricity", "To control and direct current flow", "To increase mechanical power", "To produce sound"},
                {"Load", "Power source", "Conductor", "Switch"},
                {"To block current", "To carry electric current", "To convert energy to heat", "To store energy"},
                {"Load", "Battery", "Conductor", "Switch"},
                {"Changing voltage", "Opening or closing the circuit", "Storing energy", "Dividing current"},
                {"Open", "Closed", "Broken", "Disconnected"},
                {"Side by side", "In a single path", "Across multiple branches", "Randomly"},
                {"The smallest resistor", "The product of all resistances", "The sum of all resistances", "Zero"},
                {"The others continue working", "Current increases", "The entire circuit stops", "Voltage doubles"},
                {"Divided among branches", "The same across all components", "Zero", "Dependent on current"},
                {"Remains constant in all branches", "Splits between branches", "Is zero", "Equals voltage divided by power"},
                {"Also go out", "Continue to light", "Flicker", "Lose half brightness"},
                {"Rₜ = R₁ + R₂ + R₃", "1/Rₜ = 1/R₁ + 1/R₂ + 1/R₃ + …", "Rₜ = R₁ × R₂ × R₃", "Rₜ = R₁ / R₂"},
                {"Current splits between resistors", "Current is the same throughout", "Current increases at each resistor", "Current stops at each node"},
                {"Kirchhoff’s Law", "Ohm’s Law", "Newton’s Law", "Ampere’s Law"},
                {"I × R", "P × I", "R / I", "I / R"},
                {"Ampere", "Ohm", "Volt", "Watt"},
                {"Watt", "Ampere", "Volt", "Joule"},
                {"Volt", "Watt", "Ohm", "Coulomb"},
                {"Ohm", "Volt", "Watt", "Ampere"},
                {"P = I / V", "P = V × I", "P = V + I", "P = V² × R"},
                {"Current", "Power loss", "Inductance", "Capacitance"},
                {"0.5 A", "2 A", "10 A", "50 A"},
                {"Is divided among components", "Is constant across all", "Is zero", "Depends on capacitance"},
                {"Greater than the largest resistor", "Smaller than the smallest resistor", "Equal to average resistance", "Zero"},
                {"Electrons move from negative to positive", "Conventional current flows from negative to positive", "Current can flow in an open circuit", "Current increases without voltage"},
                {"Increase", "Decrease", "Stay the same", "Reverse direction"},
                {"Load to source", "Source to load", "Switch to battery", "Ground to conductor"},
                {"Series", "Parallel", "Open", "Shorted"},
                {"How to calculate power", "The relationship between V, I, and R", "The polarity of batteries", "The magnetic field direction"}
        };

        correctAnswers = new String[]{
                "To control and direct current flow",
                "Power source",
                "To carry electric current",
                "Load",
                "Opening or closing the circuit",
                "Closed",
                "In a single path",
                "The sum of all resistances",
                "The entire circuit stops",
                "The same across all components",
                "Splits between branches",
                "Continue to light",
                "1/Rₜ = 1/R₁ + 1/R₂ + 1/R₃ + …",
                "Current is the same throughout",
                "Ohm’s Law",
                "I × R",
                "Volt",
                "Ampere",
                "Ohm",
                "Watt",
                "P = V × I",
                "Current",
                "2 A",
                "Is divided among components",
                "Smaller than the smallest resistor",
                "Electrons move from negative to positive",
                "Decrease",
                "Source to load",
                "Parallel",
                "The relationship between V, I, and R"
        };
    }

    private void loadQuiz3() {
        questions = new String[]{
                "What is the main function of a resistor?",
                "What kind of component is a resistor?",
                "The unit of resistance is the:",
                "A resistor converts electrical energy into:",
                "In a circuit diagram, resistors are represented by:",
                "The value of a resistor is labeled in:",
                "Each resistor in a circuit is identified by:",
                "What is a fixed resistor?",
                "What is a variable resistor?",
                "A potentiometer functions as a:",
                "A rheostat is used to control:",
                "A trimmer (preset resistor) is used for:",
                "The resistor color code helps indicate:",
                "The first band in a 4-band resistor represents:",
                "The second band in a 4-band resistor represents:",
                "The third band in a 4-band resistor represents:",
                "The fourth band in a 4-band resistor represents:",
                "A 4-band resistor with colors Red, Violet, Orange, Gold has a value of:",
                "In a 5-band resistor, the first three bands represent:",
                "The fourth band in a 5-band resistor is the:",
                "The fifth band in a 5-band resistor represents:",
                "The resistor with colors Brown, Black, Black, Red, Brown equals:",
                "The tolerance band is usually:",
                "To verify a resistor’s actual value, use a:",
                "The color code for the digit '2' is:",
                "The color code for the digit '7' is:",
                "The color code for the multiplier ×1000 is:",
                "The color representing a ±5% tolerance is:",
                "Resistors are used to:",
                "Resistors protect sensitive components by:"
        };

        choices = new String[][]{
                {"Store charge", "Limit the flow of electric current", "Increase voltage", "Convert magnetic energy"},
                {"Active", "Passive", "Semiconductor", "Magnetic"},
                {"Ampere (A)", "Volt (V)", "Ohm (Ω)", "Watt (W)"},
                {"Sound", "Heat", "Light", "Magnetism"},
                {"Zigzag or rectangular symbols", "Circular symbols", "Arrows", "Dotted lines"},
                {"Amperes", "Volts", "Ohms", "Farads"},
                {"Its length", "Its resistance color", "A reference designator (e.g., R1, R2)", "Its voltage rating"},
                {"A resistor with a variable value", "A resistor with a permanent resistance value", "A resistor that stores charge", "A resistor that changes automatically"},
                {"A resistor that can adjust its resistance", "A resistor that is temperature-sensitive", "A resistor that has no tolerance", "A resistor that cannot change"},
                {"Voltage divider", "Current amplifier", "Heat sink", "Switch"},
                {"Voltage", "Current", "Frequency", "Inductance"},
                {"Tuning circuits during manufacturing", "Power supply regulation", "Storing data", "Generating voltage"},
                {"Size and weight", "Resistance and tolerance", "Material type", "Voltage polarity"},
                {"The multiplier", "The first digit", "The tolerance", "The resistance unit"},
                {"The second digit", "The multiplier", "The tolerance", "The first digit"},
                {"The multiplier", "The first digit", "The tolerance", "The temperature coefficient"},
                {"Voltage rating", "Tolerance", "Current", "Multiplier"},
                {"2.7 kΩ ±10%", "27 kΩ ±5%", "270 Ω ±5%", "2.7 MΩ ±10%"},
                {"Digits", "Tolerance", "Multiplier", "Material type"},
                {"First digit", "Multiplier", "Tolerance", "Second digit"},
                {"Voltage rating", "Tolerance", "Multiplier", "Power"},
                {"100 Ω ±5%", "10 kΩ ±1%", "1 kΩ ±10%", "100 kΩ ±2%"},
                {"Near the first color band", "Wider and spaced farther apart", "Black in color", "Always gold"},
                {"Voltmeter", "Multimeter", "Ammeter", "Capacitor"},
                {"Brown", "Red", "Orange", "Yellow"},
                {"Green", "Blue", "Violet", "Gray"},
                {"Red", "Orange", "Yellow", "Green"},
                {"Brown", "Silver", "Gold", "Red"},
                {"Control voltage and protect components", "Amplify signals", "Generate current", "Store electric charge"},
                {"Limiting current", "Increasing voltage", "Reversing polarity", "Changing frequency"}
        };

        correctAnswers = new String[]{
                "Limit the flow of electric current", "Passive", "Ohm (Ω)", "Heat", "Zigzag or rectangular symbols",
                "Ohms", "A reference designator (e.g., R1, R2)", "A resistor with a permanent resistance value",
                "A resistor that can adjust its resistance", "Voltage divider", "Current", "Tuning circuits during manufacturing",
                "Resistance and tolerance", "The first digit", "The second digit", "The multiplier", "Tolerance",
                "27 kΩ ±5%", "Digits", "Multiplier", "Tolerance", "10 kΩ ±1%", "Wider and spaced farther apart",
                "Multimeter", "Red", "Violet", "Orange", "Gold", "Control voltage and protect components", "Limiting current"
        };
    }

    private void loadQuiz4() {
        questions = new String[]{
                "Scientific notation expresses numbers using powers of:",
                "In (3,000), only which digit is significant?",
                "(3,005) has how many significant figures?",
                "Scientific notation of (6,250,000,000,000,000,000) is:",
                "(0.00000000000000000000000167) grams =",
                "In (N = a × 10^n), 'a' must be:",
                "A positive exponent in scientific notation means:",
                "A negative exponent in scientific notation means:",
                "(10^3 = )",
                "(10^-3 = )",
                "1 kilometer equals:",
                "1 millimeter equals:",
                "The prefix 'micro' (µ) means:",
                "The prefix 'nano' means:",
                "The prefix 'giga' represents:",
                "1 megahertz (MHz) equals:",
                "1 gigawatt (GW) =",
                "1 microfarad (µF) =",
                "1 nanosecond =",
                "1 picofarad (pF) =",
                "Scientific notation helps to:",
                "(3.000 × 10^3) has how many significant figures?",
                "1 kilogram =",
                "The base unit of the metric system is always represented by:",
                "(2.5 × 10^4) equals:",
                "(4.5 × 10^-3) equals:",
                "If the decimal moves right, exponent is:",
                "If the decimal moves left, exponent is:",
                "The metric system is based on:",
                "The main advantage of metric prefixes is:"
        };

        choices = new String[][]{
                {"Two", "Ten", "One hundred", "Thousand"},
                {"0", "3", "30", "None"},
                {"2", "3", "4", "5"},
                {"6.25 × 10^16", "6.25 × 10^18", "6.25 × 10^20", "6.25 × 10^22"},
                {"1.67 × 10^-18", "1.67 × 10^-24", "1.67 × 10^-26", "1.67 × 10^-20"},
                {"Between 0 and 1", "Between 1 and 10", "Between 10 and 100", "Greater than 100"},
                {"The number is small", "The number is large", "The number is negative", "None of these"},
                {"The number is small", "The number is large", "The number is zero", "It cannot be determined"},
                {"10", "100", "1,000", "10,000"},
                {"0.001", "1000", "0.1", "10"},
                {"10 m", "100 m", "1000 m", "10,000 m"},
                {"0.1 m", "0.01 m", "0.001 m", "0.0001 m"},
                {"10^-3", "10^-6", "10^-9", "10^-12"},
                {"10^-6", "10^-9", "10^-12", "10^-3"},
                {"10^3", "10^6", "10^9", "10^12"},
                {"1000 Hz", "1,000,000 Hz", "10,000 Hz", "1,000 Hz"},
                {"10^6 W", "10^9 W", "10^12 W", "10^3 W"},
                {"0.0001 F", "0.000001 F", "0.001 F", "1 F"},
                {"10^-6 s", "10^-9 s", "10^-12 s", "10^-3 s"},
                {"10^-6 F", "10^-9 F", "10^-12 F", "10^-3 F"},
                {"Confuse numbers", "Simplify large and small numbers", "Add zeros", "Remove decimals"},
                {"1", "2", "3", "4"},
                {"0.1 g", "10 g", "100 g", "1000 g"},
                {"10^0", "10^3", "10^6", "10^-3"},
                {"2,500", "25,000", "250", "250,000"},
                {"0.045", "0.0045", "0.00045", "0.000045"},
                {"Positive", "Negative", "Zero", "Undefined"},
                {"Positive", "Negative", "Both", "Neither"},
                {"5s", "10s", "12s", "20s"},
                {"They eliminate zeros", "They add digits", "They confuse units", "They only work in physics"}
        };

        correctAnswers = new String[]{
                "Ten", "3", "4", "6.25 × 10^18", "1.67 × 10^-24", "Between 1 and 10",
                "The number is large", "The number is small", "1,000", "0.001", "1000 m",
                "0.001 m", "10^-6", "10^-9", "10^9", "1,000,000 Hz", "10^9 W", "0.000001 F",
                "10^-9 s", "10^-12 F", "Simplify large and small numbers", "4", "1000 g",
                "10^0", "25,000", "0.0045", "Positive", "Negative", "10s", "They eliminate zeros"
        };
    }


    private void loadQuiz5() {
        questions = new String[]{
                "Passive components include:",
                "Active components can:",
                "A resistor is used to:",
                "The unit of resistance is:",
                "The Greek symbol for Ohm is:",
                "A capacitor stores:",
                "The insulator between capacitor plates is called:",
                "The SI unit of capacitance is:",
                "Capacitors connected in parallel:",
                "Capacitors connected in series:",
                "The SI unit of inductance is:",
                "The core of an inductor is often made of:",
                "Inductors resist changes in:",
                "A diode allows current flow in:",
                "The diode terminal with a line is the:",
                "A diode is used mainly to:",
                "A transistor has how many terminals?",
                "Transistors act as:",
                "IC stands for:",
                "ICs are made of:",
                "A DIPP refers to:",
                "A battery converts:",
                "The negative terminal of a battery is called:",
                "A capacitor can block:",
                "A capacitor can pass:",
                "A resistor’s color code shows its:",
                "Which of the following is an active component?",
                "A coil wound around a core is called:",
                "The smallest SMD IC type is:",
                "A vacuum tube was replaced by:"
        };

        choices = new String[][]{
                {"Resistor", "Diode", "Transistor", "IC"},
                {"Store energy only", "Control electric current", "Only resist current", "Block electricity"},
                {"Increase voltage", "Decrease current", "Store charge", "Generate light"},
                {"Ampere", "Ohm", "Volt", "Henry"},
                {"α", "β", "Ω", "µ"},
                {"Electricity", "Light", "Sound", "Heat"},
                {"Dielectric", "Conductor", "Coil", "Transistor"},
                {"Volt", "Henry", "Farad", "Ohm"},
                {"Add up", "Subtract", "Divide", "Multiply"},
                {"Add up", "Divide", "Multiply", "Cancel"},
                {"Henry", "Farad", "Ohm", "Tesla"},
                {"Plastic", "Iron", "Rubber", "Glass"},
                {"Current", "Voltage", "Power", "Frequency"},
                {"Both directions", "One direction only", "Reverse direction", "Random directions"},
                {"Anode", "Cathode", "Base", "Emitter"},
                {"Amplify current", "Convert AC to DC", "Block current", "Store energy"},
                {"Two", "Three", "Four", "Five"},
                {"Resistors", "Amplifiers or switches", "Power sources", "Capacitors"},
                {"Internal Circuit", "Integrated Circuit", "Input Circuit", "Inductive Component"},
                {"Transistors only", "Transistors, resistors, and capacitors", "Inductors only", "Only semiconductors"},
                {"Dual In-line Pin Package", "Double Inside Pin Package", "Digital Input Port", "Direct Integrated Pin"},
                {"Mechanical to electrical", "Chemical to electrical", "Electrical to thermal", "Thermal to chemical"},
                {"Cathode", "Anode", "Base", "Collector"},
                {"DC", "AC", "Both", "None"},
                {"DC", "AC", "Both", "Neither"},
                {"Voltage", "Resistance value", "Power rating", "Temperature"},
                {"Resistor", "Capacitor", "Diode", "Inductor"},
                {"Inductor", "Resistor", "Transformer", "Capacitor"},
                {"BGA", "DIPP", "SIPP", "LCC"},
                {"Capacitor", "Transistor", "Resistor", "Battery"}
        };

        correctAnswers = new String[]{
                "Resistor", "Control electric current", "Decrease current", "Ohm", "Ω", "Electricity", "Dielectric",
                "Farad", "Add up", "Divide", "Henry", "Iron", "Current", "One direction only", "Cathode",
                "Convert AC to DC", "Three", "Amplifiers or switches", "Integrated Circuit", "Transistors, resistors, and capacitors",
                "Dual In-line Pin Package", "Chemical to electrical", "Anode", "DC", "AC", "Resistance value",
                "Diode", "Inductor", "BGA", "Transistor"
        };
    }

    private void loadQuiz6() {
        questions = new String[]{
                "A semiconductor can act as:",
                "The most common semiconductor materials are:",
                "N-type semiconductors have:",
                "P-type semiconductors have:",
                "Doping means:",
                "The PN junction is formed when:",
                "In forward bias, current:",
                "In reverse bias, current:",
                "The PN junction acts like a:",
                "The main component of a diode is:",
                "Diodes convert:",
                "LED means:",
                "LEDs emit:",
                "LEDs are used in:",
                "Forward bias in LED allows:",
                "Reverse bias in LED causes:",
                "The region between P and N layers is called:",
                "N-type charge carriers are:",
                "P-type charge carriers are:",
                "Diodes allow current to flow in:",
                "The cathode side of a diode is usually:",
                "A PN junction diode in forward bias has:",
                "A PN junction diode in reverse bias has:",
                "Which semiconductor type forms the anode of a diode?",
                "Which semiconductor type forms the cathode of a diode?",
                "The basic function of semiconductors is to:",
                "Which component uses a PN junction to emit light?",
                "Semiconductors are used in:",
                "Pure semiconductors are called:",
                "Doped semiconductors are called:"
        };

        choices = new String[][]{
                {"Only a conductor", "Both conductor and insulator", "Only an insulator", "None"},
                {"Copper and aluminum", "Silicon and germanium", "Iron and nickel", "Carbon and silver"},
                {"Extra electrons", "Extra protons", "Holes", "No carriers"},
                {"Extra electrons", "Extra holes", "Equal electrons", "No charge"},
                {"Removing impurities", "Adding impurities", "Melting semiconductors", "Coating metals"},
                {"P and N materials join", "Two N materials join", "Two P materials join", "P and metal join"},
                {"Flows", "Stops", "Reverses", "Oscillates"},
                {"Flows", "Does not flow", "Increases", "Doubles"},
                {"Conductor", "Resistor", "One-way valve", "Battery"},
                {"PN junction", "Capacitor", "Resistor", "Battery"},
                {"DC to AC", "AC to DC", "AC to light", "Light to DC"},
                {"Light Electric Device", "Light Emitting Diode", "Low Energy Device", "Linear Electrical Diode"},
                {"Heat", "Light", "Sound", "Magnetism"},
                {"Lighting and displays", "Motors", "Switches", "Resistors"},
                {"Light emission", "Blocking", "No flow", "Heating"},
                {"Bright light", "No current", "Strong current", "Noise"},
                {"Active region", "Dead region", "Neutral region", "Negative region"},
                {"Holes", "Electrons", "Ions", "Neutrons"},
                {"Electrons", "Holes", "Protons", "Photons"},
                {"One direction", "Two directions", "Randomly", "Opposite ways"},
                {"Positive", "Negative", "Neutral", "None"},
                {"Low resistance", "High resistance", "Infinite resistance", "Zero voltage"},
                {"High resistance", "Low resistance", "Zero resistance", "None"},
                {"N-type", "P-type", "Neutral", "Both"},
                {"N-type", "P-type", "Both", "None"},
                {"Store energy", "Control current flow", "Block voltage", "Increase temperature"},
                {"Resistor", "LED", "Capacitor", "Inductor"},
                {"Computers", "Books", "Glass", "Wood"},
                {"Conductors", "Intrinsic semiconductors", "Extrinsic semiconductors", "Neutral metals"},
                {"Intrinsic", "Extrinsic", "Neutral", "Base"}
        };

        correctAnswers = new String[]{
                "Both conductor and insulator", "Silicon and germanium", "Extra electrons", "Extra holes", "Adding impurities",
                "P and N materials join", "Flows", "Does not flow", "One-way valve", "PN junction", "AC to DC",
                "Light Emitting Diode", "Light", "Lighting and displays", "Light emission", "No current", "Active region",
                "Electrons", "Holes", "One direction", "Negative", "Low resistance", "High resistance", "P-type", "N-type",
                "Control current flow", "LED", "Computers", "Intrinsic semiconductors", "Extrinsic"
        };
    }


    private void loadQuiz7() {
        questions = new String[]{
                "A transistor mainly functions as a:",
                "BJT stands for:",
                "FET stands for:",
                "A transistor is made of how many terminals?",
                "The three parts of a BJT are:",
                "The three parts of an FET are:",
                "The transistor acts as a switch when:",
                "The transistor acts as an amplifier when:",
                "The input signal of a BJT amplifier is applied at the:",
                "The output signal of a BJT amplifier is taken from the:",
                "When there is no base current, a transistor is:",
                "In a switch circuit, applying a small base current:",
                "In amplification, a transistor increases:",
                "The FET controls current using:",
                "Transistors are used in:",
                "The BJT uses both:",
                "The small input current controls:",
                "The transistor as a switch is used in:",
                "The transistor as an amplifier is used in:",
                "A transistor is often called the:",
                "A transistor is mainly a:",
                "The collector current depends on:",
                "The small base current causes:",
                "The FET’s input terminal is the:",
                "The output terminal of an FET is the:",
                "In switching mode, transistor current flows from:",
                "Transistors amplify:",
                "A transistor can replace:",
                "In audio systems, transistors are used to:",
                "Transistors are used in microcontrollers for:"
        };

        choices = new String[][]{
                {"Resistor", "Capacitor", "Switch or amplifier", "Diode"},
                {"Bipolar Junction Transistor", "Binary Junction Transistor", "Base Junction Transmitter", "Basic Junction Transistor"},
                {"Field Energy Transistor", "Field Effect Transistor", "Full Energy Transformer", "Frequency Emission Transistor"},
                {"Two", "Three", "Four", "Five"},
                {"Base, Source, Drain", "Collector, Base, Emitter", "Gate, Drain, Source", "Anode, Cathode, Base"},
                {"Base, Emitter, Collector", "Source, Gate, Drain", "Anode, Cathode, Gate", "Input, Output, Ground"},
                {"It amplifies a signal", "It turns current on/off", "It stores charge", "It rectifies current"},
                {"It blocks signals", "It reduces signal strength", "It increases signal strength", "It filters voltage"},
                {"Emitter", "Base", "Collector", "Drain"},
                {"Base", "Collector", "Emitter", "Source"},
                {"ON", "OFF", "Saturated", "Amplifying"},
                {"Turns it ON", "Turns it OFF", "Amplifies sound", "Filters current"},
                {"Frequency", "Signal strength", "Resistance", "Capacitance"},
                {"Magnetic fields", "Electric fields", "Base current", "Resistance"},
                {"Computers", "Radios", "Amplifiers", "All of the above"},
                {"Protons and neutrons", "Electrons and holes", "Electrons only", "Holes only"},
                {"A smaller output", "A larger output", "Equal output", "Reverse output"},
                {"Relays", "Capacitors", "Coils", "Transformers"},
                {"LED circuits", "Microphones", "Power cables", "Motors"},
                {"Brain of electronics", "Heart of circuits", "Muscle of systems", "Eye of machines"},
                {"Passive component", "Active component", "Neutral element", "Resistor"},
                {"Base current", "Voltage regulator", "Resistance", "Capacitance"},
                {"No flow", "Large collector current", "Reverse current", "Negative signal"},
                {"Drain", "Gate", "Source", "Collector"},
                {"Source", "Drain", "Gate", "Base"},
                {"Collector to emitter", "Emitter to base", "Gate to source", "Drain to gate"},
                {"Electric noise", "Input signals", "Resistance", "Voltage drops"},
                {"Capacitor", "Vacuum tube", "Fuse", "Battery"},
                {"Generate signals", "Amplify sound", "Block current", "Store energy"},
                {"Logic switching", "Light emission", "Heating", "Resistance control"}
        };

        correctAnswers = new String[]{
                "Switch or amplifier", "Bipolar Junction Transistor", "Field Effect Transistor", "Three",
                "Collector, Base, Emitter", "Source, Gate, Drain", "It turns current on/off", "It increases signal strength",
                "Base", "Collector", "OFF", "Turns it ON", "Signal strength", "Electric fields", "All of the above",
                "Electrons and holes", "A larger output", "Relays", "Microphones", "Brain of electronics",
                "Active component", "Base current", "Large collector current", "Gate", "Drain", "Collector to emitter",
                "Input signals", "Vacuum tube", "Amplify sound", "Logic switching"
        };
    }


    private void loadQuiz8() {
        questions = new String[]{
                "Voltage is the:",
                "Voltage is measured in:",
                "The unit 'volt' was named after:",
                "Voltage was historically called:",
                "The symbol commonly used for voltage in formulas is:",
                "Direct current (DC) means:",
                "Alternating current (AC) means:",
                "DC is ideal for:",
                "AC is mainly used in:",
                "AC can be transmitted efficiently because:",
                "Batteries produce:",
                "A primary battery is:",
                "Examples of primary batteries include:",
                "A secondary battery is:",
                "Examples of secondary batteries:",
                "The device that converts AC to DC is a:",
                "A filter’s function is to:",
                "A voltage regulator’s function is to:",
                "The 7805 voltage regulator provides:",
                "The main parts of a basic power supply include:",
                "A transformer is used to:",
                "The main job of a power supply is to:",
                "The result after rectification is:",
                "Filters use what component most often?",
                "A power supply converts:",
                "The device that maintains steady DC despite changes in input is:",
                "Voltage is also known as:",
                "The invention of the voltaic pile was by:",
                "The voltage symbol for DC circuits is often:",
                "In power supply design, the first stage is usually the:"
        };

        choices = new String[][]{
                {"Resistance in a circuit", "Driving force pushing electrons", "Speed of current", "Heat in a wire"},
                {"Amperes", "Volts", "Ohms", "Joules"},
                {"Michael Faraday", "Alessandro Volta", "Nikola Tesla", "Thomas Edison"},
                {"Electron motive force", "Electromotive force", "Electrostatic charge", "Magnetic force"},
                {"V", "I", "E", "R"},
                {"Electrons flow back and forth", "Electrons flow in one direction", "Voltage alternates", "Current stops"},
                {"Flow is one-way", "Flow periodically reverses", "Voltage is constant", "Current is DC"},
                {"Long-distance transmission", "Consistent voltage devices", "High voltage systems", "Transformers"},
                {"Batteries", "Home electricity", "Flashlights", "Cellphones"},
                {"It uses transformers", "It uses capacitors", "It does not lose power", "It moves slower"},
                {"AC", "DC", "Both", "None"},
                {"Rechargeable", "Non-rechargeable", "Reusable", "Secondary"},
                {"Lithium-ion", "Nickel-cadmium", "AA and AAA", "Car batteries"},
                {"Disposable", "Rechargeable", "Single-use", "Non-rechargeable"},
                {"Li-ion and Ni-Cd", "Alkaline and carbon-zinc", "Silver-oxide", "Mercury"},
                {"Transformer", "Rectifier", "Filter", "Regulator"},
                {"Block voltage", "Smooth ripples", "Reverse polarity", "Lower AC"},
                {"Store voltage", "Keep output constant", "Block current", "Reduce heat"},
                {"9V", "5V", "12V", "3V"},
                {"Transformer, rectifier, filter, regulator", "Battery, LED, diode, resistor", "Switch, fuse, bulb, wire", "Transistor, capacitor, coil, motor"},
                {"Convert AC to DC", "Step voltage up or down", "Store charge", "Filter current"},
                {"Provide correct voltage and current", "Generate AC only", "Block electrons", "Store energy"},
                {"Pure DC", "Pulsating DC", "Reverse AC", "No current"},
                {"Diode", "Capacitor", "Transformer", "Resistor"},
                {"AC to stable DC", "DC to AC", "DC to DC", "AC to AC"},
                {"Transformer", "Regulator", "Capacitor", "Battery"},
                {"Current", "Pressure", "Resistance", "Load"},
                {"Faraday", "Volta", "Ohm", "Ampere"},
                {"Wavy line", "Solid line with dashes", "Triangle", "Cross"},
                {"Regulator", "Transformer", "Rectifier", "Filter"}
        };

        correctAnswers = new String[]{
                "Driving force pushing electrons", "Volts", "Alessandro Volta", "Electromotive force", "E",
                "Electrons flow in one direction", "Flow periodically reverses", "Consistent voltage devices", "Home electricity",
                "It uses transformers", "DC", "Non-rechargeable", "AA and AAA", "Rechargeable", "Li-ion and Ni-Cd",
                "Rectifier", "Smooth ripples", "Keep output constant", "5V", "Transformer, rectifier, filter, regulator",
                "Step voltage up or down", "Provide correct voltage and current", "Pulsating DC", "Capacitor",
                "AC to stable DC", "Regulator", "Pressure", "Volta", "Solid line with dashes", "Transformer"
        };
    }
}


