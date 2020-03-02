package com.example.parkingkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import android.app.DatePickerDialog
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.jaredrummler.materialspinner.MaterialSpinner
import java.util.*


class RegisterActivity : AppCompatActivity() {

    var month: Int = 0
    var year: Int = 0
    var day: Int = 0

    @BindView(R.id.register_edtext_client_date)
    lateinit var register_edtext_client_date: TextInputEditText

    @BindView(R.id.register_spinner_prices)
    lateinit var spinerPrices: MaterialSpinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initComponents()
    }

    fun initComponents(){
        ButterKnife.bind(this)

        val calendar: Calendar = Calendar.getInstance()

        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        day = calendar.get(Calendar.DAY_OF_MONTH)

        register_edtext_client_date.setOnClickListener{

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, myear, mmonth, mday ->
                register_edtext_client_date.setText("""${mday.twoDigits()}/${(mmonth + 1).twoDigits()}/$myear""")
            }, year, month, day)
            dpd.show()
        }

        spinerPrices.setItems("Tarifa", "$80.000", "$60.000", "$40.000")

        spinerPrices.setOnItemSelectedListener { view, position, id, item ->
            Toast.makeText(view.context, ""+item, Toast.LENGTH_SHORT).show()
        }
    }

    fun Int.twoDigits() =
        if (this <= 9) "0$this" else this.toString()

}
