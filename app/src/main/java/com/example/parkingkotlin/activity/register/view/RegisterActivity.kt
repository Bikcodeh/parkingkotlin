package com.example.parkingkotlin.activity.register.view

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import butterknife.*
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.parkingkotlin.R
import com.example.parkingkotlin.activity.register.presenter.RegisterPresenterImpl
import com.example.parkingkotlin.database.entity.ClientEntity
import com.example.parkingkotlin.model.ClientModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dmax.dialog.SpotsDialog
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class RegisterActivity : AppCompatActivity(), RegisterActivityView {

    lateinit var calendar: Calendar

    private var month: Int = 0
    private var year: Int = 0
    private var day: Int = 0

    @BindView(R.id.register_tiinputlayout_client_name)
    lateinit var clientNameTextLayout: TextInputLayout

    @BindView(R.id.register_edtext_client_name)
    lateinit var clientName: TextInputEditText

    @Nullable
    @JvmField
    @BindView(R.id.register_edtext_client_phone)
    var clientPhone: TextInputEditText? = null

    @BindView(R.id.register_tiinputlayout_client_plaque)
    lateinit var clientPlaqueTextLayout: TextInputLayout

    @BindView(R.id.register_edtext_client_plaque)
    lateinit var clientPlaque: TextInputEditText

    @Nullable
    @JvmField
    @BindView(R.id.register_edtext_client_id)
    var clientIdentification: TextInputEditText? = null

    @BindView(R.id.register_tiinputlayout_client_date)
    lateinit var clientStartDateTextLayout: TextInputLayout

    @BindView(R.id.register_edtext_client_date)
    lateinit var clientStartDate: TextInputEditText

    @BindView(R.id.register_spinner_prices)
    lateinit var spinerPrices: Spinner

    @BindView(R.id.progress_bar)
    lateinit var progressBar: ProgressBar

    @BindView(R.id.main_toolbar)
    lateinit var registerToolbar: Toolbar

    @BindString(R.string.register)
    lateinit var textTitleRegister : String

    @BindString(R.string.edit)
    lateinit var textTitleEdit : String

    @BindString(R.string.update)
    lateinit var textUpdate : String

    @BindView(R.id.register_txt_register)
    lateinit var titleHead : TextView

    @BindView(R.id.register_btn_register)
    lateinit var btnRegister : MaterialButton

    @BindView(R.id.register_llayout_radioGroup)
    lateinit var radioGroup: RadioGroup

    @BindView(R.id.register_radio_paid)
    lateinit var radioPaid: RadioButton

    @BindView(R.id.register_radio_pending)
    lateinit var radioPending: RadioButton

    private var clientRate: Double = 90.000
    private lateinit var registerPresenterImpl: RegisterPresenterImpl

    private lateinit var dialog: android.app.AlertDialog

    private var clientModel: ClientModel? = null

    var isEditing = false

    @BindArray(R.array.prices)
    lateinit var prices: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        isEditing = intent.getBooleanExtra("edit", false)
        initComponents(isEditing)

        if(isEditing){
            this.clientModel = intent.getSerializableExtra("client") as? ClientModel
            this.setInputs()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initComponents(isEditing: Boolean){
        ButterKnife.bind(this)

        setSupportActionBar(registerToolbar)
        title = if(!isEditing) resources.getString(R.string.title_register) else resources.getString(R.string.title_edit)
        titleHead.text = if (!isEditing) textTitleRegister else textTitleEdit
        btnRegister.text = if (!isEditing) textTitleRegister else textUpdate
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        dialog = SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build()

        registerPresenterImpl = RegisterPresenterImpl(application, this)

        calendar = Calendar.getInstance()
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        day = calendar.get(Calendar.DAY_OF_MONTH)

        clientStartDate.setOnClickListener{

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, myear, mmonth, mday ->
                clientStartDate.setText("${mday.twoDigits()}-${(mmonth + 1).twoDigits()}-$myear")
            }, year, month, day)
            dpd.show()
        }

        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, prices)
        spinerPrices.adapter = adapter

        spinerPrices.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                clientRate =  prices[position].replace("$", "").toDouble()
            }
        }
    }

    private fun convertToFloat(value: String): Float{
        val valueFinal: String = value.replace("$", "")
        return valueFinal.toFloat()
    }

    private fun Int.twoDigits() =
        if (this <= 9) "0$this" else this.toString()

    private fun stringToDate(value: String): Date {
        return SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(value)
    }

    @OnClick(R.id.register_btn_register)
    fun registerClient(){

        if(validateFields( listOf(this.clientName, this.clientPlaque, this.clientStartDate), listOf(this.clientNameTextLayout, this.clientPlaqueTextLayout, clientStartDateTextLayout))){

            var date = stringToDate(this.clientStartDate.text.toString())
            calendar.time = date
            calendar.add(Calendar.MONTH, 1)
            date = calendar.time

            var status = 1

            if(radioGroup.checkedRadioButtonId == R.id.register_radio_paid)
                status = 1
            if(radioGroup.checkedRadioButtonId == R.id.register_radio_pending)
                status = 0


            if(this.isEditing) {

                val client = ClientModel(this.clientModel?.clientId, this.clientName.text.toString(),
                    this.clientIdentification?.text.toString(), this.clientPhone?.text.toString(),
                    this.clientPlaque.text.toString(), this.clientRate,
                    stringToDate(this.clientStartDate.text.toString()), status, 1)

                registerPresenterImpl.updateClient(client)
            } else {
                val clientEntity = ClientEntity(
                    clientName = this.clientName.text.toString(),
                    clientIdentification = this.clientIdentification?.text.toString(),
                    clientActive = 1,
                    clientPlaque = this.clientPlaque.text.toString(),
                    clientRate = this.clientRate,
                    clientPhone = this.clientPhone?.text.toString(),
                    startDate = stringToDate(this.clientStartDate.text.toString()),
                    statusPayment = status,
                    dueDate = date
                )

                registerPresenterImpl.registerClient(clientEntity)
            }
        }else{
            Toasty.warning(this, "Campos obligatorios", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateFields(listTextInputEditText: List<TextInputEditText>, listTextInputLayout: List<TextInputLayout>): Boolean {
        var counter = 0
        for((index, editText) in listTextInputEditText.withIndex()){
           if(TextUtils.isEmpty(editText.text.toString())){
                listTextInputLayout[index].error = "Requerido"
                counter++
           }else{
               listTextInputLayout[index].error = ""
           }
       }
        return counter == 0
    }

    override fun showProgress() {
        dialog.show()
    }

    override fun hideProgress() {
        dialog.dismiss()
    }

    override fun showErrorMessage(throwable: Throwable) {
        Toasty.error(this, throwable.message.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessMesage(message: String) {
        Toasty.success(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Animatoo.animateSlideLeft(this)
    }

    override fun clearInputs(){

        this.clientIdentification?.setText("")
        this.clientStartDate.setText("")
        this.clientName.setText("")
        this.clientPlaque.setText("")
        this.clientPhone?.setText("")
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun setInputs() {
        this.clientIdentification?.setText(this.clientModel?.clientIdentification)
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.format(this.clientModel?.startDate)
        this.clientStartDate.setText(date)
        this.clientName.setText(this.clientModel?.clientName)
        this.clientPlaque.setText(this.clientModel?.clientPlaque)
        this.clientPhone?.setText(this.clientModel?.clientPhone)
        if(this.clientModel?.statusPayment == 1){
            this.radioPaid.isChecked = true
        } else {
            this.radioPending.isChecked = true
        }
        this.spinerPrices.setSelection(this.prices.indexOf("$${this.clientModel?.clientRate}00"))
    }

    override fun closeActivity() {
        val result = Intent()
        result.putExtra("update", true)
        setResult(Activity.RESULT_OK, result)
        this.finish()
    }
}
