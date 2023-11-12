package miu.edu.quizapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private val correctRadio = "All of the above";
    private val correctBoxes = listOf<String>("text", "textSize")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val checkBoxes = listOf<CheckBox>(
            findViewById<CheckBox>(R.id.checkBox1),
            findViewById<CheckBox>(R.id.checkBox2),
            findViewById<CheckBox>(R.id.checkBox3)
        )

        val submit = findViewById<Button>(R.id.submit)
        val reset = findViewById<Button>(R.id.reset)

        submit.setOnClickListener {
            val selectedRadio = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            val checkedBoxes = checkBoxes.filter { it.isChecked }
            if (selectedRadio != null && checkedBoxes.isNotEmpty()) {
                val answer = matchAnswers(selectedRadio, checkedBoxes)
                val date = getCurrentDate()
                showDialog("Congratulations! You submitted on the current $date, you achieved $answer%")
            } else {
                Toast.makeText(this, "Select the correct answers and click submit!", Toast.LENGTH_SHORT).show()
            }
        }

        reset.setOnClickListener {
            radioGroup.clearCheck()
            checkBoxes.forEach {
                it.isChecked = false
            }
        }
    }

    private fun getCurrentDate(): String {
        val pattern = "yyyy-MM-dd HH:mm"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(Date())
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Answer")
        builder.setMessage(message)
        val dialog = builder.create()
        dialog.show()
    }

    private fun matchAnswers(radio: RadioButton, checkBoxes: List<CheckBox>): Int {
        var answer = 0
        if (radio.text.equals(correctRadio))
            answer += 50
        if (correctBoxes.joinToString() == checkBoxes.joinToString { it.text })
            answer += 50
        return answer
    }
}