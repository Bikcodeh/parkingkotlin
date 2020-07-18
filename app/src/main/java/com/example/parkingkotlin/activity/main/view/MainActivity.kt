package com.example.parkingkotlin.activity.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.parkingkotlin.R
import com.example.parkingkotlin.activity.listClients.view.ListClientsActivity
import com.example.parkingkotlin.activity.main.presenter.MainPresenter
import com.example.parkingkotlin.activity.main.presenter.MainPresenterImpl
import com.example.parkingkotlin.activity.register.view.RegisterActivity
import es.dmoral.toasty.Toasty

class MainActivity : AppCompatActivity(), MainView {

    @BindView(R.id.main_txt_total_clients)
    lateinit var totalClientsTxt: TextView

    @BindView(R.id.main_txt_paid_clients)
    lateinit var totalPaidClientsTxt: TextView

    @BindView(R.id.main_txt_pending_clients)
    lateinit var totalPendingClientsTxt: TextView

    lateinit var presenter: MainPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
    }

    private fun initComponents(){
        ButterKnife.bind(this)

        setSupportActionBar(findViewById(R.id.main_toolbar))
        title = getString(R.string.title_main)

        presenter = MainPresenterImpl(this ,application, this)
        //presenter.getPendingClients()
        presenter.getClients()
        presenter.updateClients()
        presenter.getStatusPaymentList()
    }

    override fun onResume() {
        super.onResume()
    }

    @OnClick(R.id.main_llyout_register)
    fun openRegister(){
        startActivity(Intent(this, RegisterActivity::class.java))
        Animatoo.animateSlideRight(this)
    }

    @OnClick(R.id.main_llyout_list_clients)
    fun openList(){
        startActivity(Intent(this, ListClientsActivity::class.java))
        Animatoo.animateSlideRight(this)
    }

    override fun setTotalClients(value: Int?) {
        this.totalClientsTxt.text = "$value"
    }

    override fun setTotalPendingClients(value: Int?) {
        this.totalPendingClientsTxt.text = "$value"
    }

    override fun setTotalPaidClients(value: Int?) {
        this.totalPaidClientsTxt.text = "$value"
    }

    override fun showErrorMessage(throwable: Throwable) {
        Toasty.error(this, throwable.message.toString(), Toast.LENGTH_LONG).show()
    }
}
