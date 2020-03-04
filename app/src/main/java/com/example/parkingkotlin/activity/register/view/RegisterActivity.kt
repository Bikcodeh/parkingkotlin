package com.example.parkingkotlin.activity.register.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.OnClick
import com.example.parkingkotlin.R
import com.example.parkingkotlin.activity.register.presenter.RegisterPresenterImpl
import com.example.parkingkotlin.database.entity.ClientEntity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jaredrummler.materialspinner.MaterialSpinner
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class RegisterActivity : AppCompatActivity(), RegisterActivityView {

    var month: Int = 0
    var year: Int = 0
    var day: Int = 0

    @BindView(R.id.register_tiinputlayout_client_name)
    lateinit var clientNameTextLayout: TextInputLayout

    @BindView(R.id.register_edtext_client_name)
    lateinit var clientName: TextInputEditText

    @BindView(R.id.register_edtext_client_phone)
    lateinit var clientPhone: TextInputEditText

    @BindView(R.id.register_tiinputlayout_client_plaque)
    lateinit var clientPlaqueTextLayout: TextInputLayout

    @BindView(R.id.register_edtext_client_plaque)
    lateinit var clientPlaque: TextInputEditText

    @BindView(R.id.register_edtext_client_id)
    lateinit var clientIdentification: TextInputEditText

    @BindView(R.id.register_edtext_client_date)
    lateinit var clientStartDate: TextInputEditText

    @BindView(R.id.register_spinner_prices)
    lateinit var spinerPrices: MaterialSpinner

    @BindView(R.id.progress_bar)
    lateinit var progressBar: ProgressBar

    var clientRate: Float = 0F
    lateinit var registerPresenterImpl: RegisterPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initComponents()
    }

    private fun initComponents(){
        ButterKnife.bind(this)

        val calendar: Calendar = Calendar.getInstance()
        registerPresenterImpl = RegisterPresenterImpl(application, this)

        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        day = calendar.get(Calendar.DAY_OF_MONTH)

        clientStartDate.setOnClickListener{

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, myear, mmonth, mday ->
                clientStartDate.setText("""${mday.twoDigits()}/${(mmonth + 1).twoDigits()}/$myear""")
            }, year, month, day)
            dpd.show()
        }

        spinerPrices.setItems("Tarifa", "$80.000", "$60.000", "$40.000")

        spinerPrices.setOnItemSelectedListener { view, position, id, item ->
            clientRate = convertToFloat(item.toString())
        }
    }

    private fun convertToFloat(value: String): Float{
        val valueFinal: String = value.replace("$", "")
        return valueFinal.toFloat()
    }

    private fun Int.twoDigits() =
        if (this <= 9) "0$this" else this.toString()

    @OnClick(R.id.register_btn_register)
    fun registerClient(){


        if(validateFields(this.clientName, this.clientNameTextLayout) && validateFields(this.clientPlaque, this.clientPlaqueTextLayout)){
            val clientEntity = ClientEntity(
                clientName = this.clientName.text.toString(),
                clientIdentification = this.clientIdentification.text.toString(),
                clientActive = 1,
                clientPlaque = this.clientPlaque.text.toString(),
                clientRate = this.clientRate,
                clientPhone = this.clientPhone.text.toString(),
                startDate = Date(),
                dueDate = Date()
            )

            registerPresenterImpl.registerClient(clientEntity)
        }else{
            Toasty.warning(this, "Campos obligatorios", Toast.LENGTH_SHORT).show()
        }

    }

    private fun validateFields(textInputEditText: TextInputEditText, textInputLayout: TextInputLayout): Boolean {
        return if(textInputEditText.text.toString() == ""){
            textInputLayout.error = "Requerido"
            false
        } else{
            true
        }
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showErrorMessage(throwable: Throwable) {
        Toasty.error(this, throwable.message.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessMesage(message: String) {
        Toasty.success(this, message, Toast.LENGTH_SHORT).show()
    }
}
