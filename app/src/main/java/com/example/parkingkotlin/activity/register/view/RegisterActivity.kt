package com.example.parkingkotlin.activity.register.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import android.app.DatePickerDialog
import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import butterknife.OnClick
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.parkingkotlin.R
import com.example.parkingkotlin.activity.register.presenter.RegisterPresenterImpl
import com.example.parkingkotlin.database.entity.ClientEntity
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jaredrummler.materialspinner.MaterialSpinner
import dmax.dialog.SpotsDialog
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity(), RegisterActivityView, CompoundButton.OnCheckedChangeListener {


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

    @BindView(R.id.register_chip_payment)
    lateinit var chipPay: Chip

    @BindView(R.id.register_chip_pending)
    lateinit var chipPending: Chip

    @BindView(R.id.main_toolbar)
    lateinit var registerToolbar: Toolbar

    var clientRate: Float = 0F
    lateinit var registerPresenterImpl: RegisterPresenterImpl

    lateinit var dialog: android.app.AlertDialog

    var clientStatus: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initComponents()
        setOnClicks()
    }

    private fun initComponents(){
        ButterKnife.bind(this)

        setSupportActionBar(registerToolbar)
        title = resources.getString(R.string.title_register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        dialog = SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build()


        val calendar: Calendar = Calendar.getInstance()
        registerPresenterImpl = RegisterPresenterImpl(application, this)

        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        day = calendar.get(Calendar.DAY_OF_MONTH)

        clientStartDate.setOnClickListener{

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, myear, mmonth, mday ->
                clientStartDate.setText("""${mday.twoDigits()}-${(mmonth + 1).twoDigits()}-$myear""")
            }, year, month, day)
            dpd.show()
        }

        spinerPrices.setItems("Tarifa", "$80.000", "$60.000", "$40.000")

        spinerPrices.setOnItemSelectedListener { view, position, id, item ->
            clientRate = convertToFloat(item.toString())
        }


    }

    private fun setOnClicks(){
        chipPay.setOnCheckedChangeListener(this)
        chipPending.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView?.id){
            R.id.register_chip_payment -> {
                this.clientStatus = 1
                changeBackgroundChipSelected(chipPay, R.color.green, R.color.very_light_gray, isChecked)
                enableDisableChips(chipPending, isChecked)
            }
            R.id.register_chip_pending -> {
                this.clientStatus = 0
                changeBackgroundChipSelected(chipPending, R.color.brown_shadow, R.color.very_light_gray, isChecked)
                enableDisableChips(chipPay, isChecked)
            }
        }
    }

    private fun changeBackgroundChipSelected(
        chip: Chip,
        colorChecked: Int,
        colorNotChecked: Int,
        checked: Boolean
    ) {
        if(checked){
            chip.setChipBackgroundColorResource(colorChecked)
            chip.setTextColor(ContextCompat.getColor(this, R.color.white))
        }else{
            chip.setChipBackgroundColorResource(colorNotChecked)
            chip.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
    }

    private fun enableDisableChips(option: Chip, checked: Boolean) {
        if (checked) {
            option.isEnabled = false
            option.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
        }else{
            option.isEnabled = true
            option.setTextColor(ContextCompat.getColor(this, R.color.black))
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

        if(validateFields( listOf(this.clientName, this.clientPlaque), listOf(this.clientNameTextLayout, this.clientPlaqueTextLayout))){
            val clientEntity = ClientEntity(
                clientName = this.clientName.text.toString(),
                clientIdentification = this.clientIdentification.text.toString(),
                clientActive = this.clientStatus,
                clientPlaque = this.clientPlaque.text.toString(),
                clientRate = this.clientRate,
                clientPhone = this.clientPhone.text.toString(),
                startDate = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(this.clientStartDate.text.toString()),
                dueDate = Date()
            )

            registerPresenterImpl.registerClient(clientEntity)
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
}
