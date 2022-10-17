package com.project.manishprajapat.emicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val principle = findViewById<EditText>(R.id.et_principle);
        val interest = findViewById<EditText>(R.id.et_interset);
        val month = findViewById<RadioButton>(R.id.checkBox_one);
        val yearly = findViewById<RadioButton>(R.id.checkBox_two);
        val period = findViewById<EditText>(R.id.et_period);
        val cal = findViewById<Button>(R.id.calculate);
        val reset = findViewById<Button>(R.id.reset);
        val mon_emi = findViewById<TextView>(R.id.monthly_emi);
        val total_interest = findViewById<TextView>(R.id.total_interest);
        val total_payment = findViewById<TextView>(R.id.total_payment);
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup);

        principle.requestFocus()

        reset.setOnClickListener {
            principle.setText("");
            interest.setText("")
            month.setChecked(false);
            yearly.setChecked(false);
            mon_emi.setText("")
            period.setText("")
            total_interest.setText("")
            total_payment.setText("")
        }

        cal?.setOnClickListener {
            val int_amt = interest.text.toString();
            val duartion = period.text.toString();
            val radioOne = month.findViewById<RadioButton>(R.id.checkBox_one);
            val princ_amt = principle.text.toString();
            val radioTwo = month.findViewById<RadioButton>(R.id.checkBox_two);


            try {
                var interest_amt = int_amt.toDouble();
                var tenure = duartion.toInt().toDouble();
                var principle_amt = princ_amt.toDouble();

                interest_amt = interest_amt / (12 * 100);

                val radioId = radioGroup.checkedRadioButtonId;

                if (radioId == R.id.checkBox_one) {
//                interest_amt = interest_amt / (12 * 100);
                } else if (radioId == R.id.checkBox_two) {
                    tenure = tenure * 12;
                }

                if (radioGroup.checkedRadioButtonId == -1) {
                    val snackbar = Snackbar.make(
                        findViewById(R.id.parent),
                        "Select Period(Month/Year)",
                        Snackbar.LENGTH_LONG
                    ).show();
                    return@setOnClickListener
                }

                val emi =
                    (principle_amt * interest_amt * Math.pow(1 + interest_amt, tenure)) / (Math.pow(
                        1 + interest_amt,
                        tenure
                    ) - 1);

                val tot_paymen = emi * (tenure);
                val tot_inter = tot_paymen - principle_amt;

                mon_emi.setText("Rs. " + String.format("%.2f", emi));
                total_payment.setText("Rs. " + String.format("%.2f", tot_paymen));
                total_interest.setText("Rs. " + String.format("%.2f", tot_inter));

            } catch (e: Exception) {
                val snackbar = Snackbar.make(
                    findViewById(R.id.parent),
                    "Please fill above details",
                    Snackbar.LENGTH_LONG
                ).show();
            }

        }
    }

    override fun onBackPressed() {
        val dialog = android.app.AlertDialog.Builder(this)
        dialog.setTitle("Exit ?")
        dialog.setMessage("Are you sure want to Exit")
        dialog.setIcon(R.drawable.ic_baseline_warning_24)
        dialog.setPositiveButton("Yes") { dialog, id ->
            exitProcess(0);
        }
        dialog.setNegativeButton("No") { dialog, id ->
            dialog.cancel()
        }
        dialog.create()
        dialog.show();
//        dialog.setCancelable(true)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.rateUs) {
            //Open Play Store
            Toast.makeText(this, "Open Play Store", Toast.LENGTH_LONG).show()
        }
        if (R.id.exit == item.itemId) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item)
    }
}





