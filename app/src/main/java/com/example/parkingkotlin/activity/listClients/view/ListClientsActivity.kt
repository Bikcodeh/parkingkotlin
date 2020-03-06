package com.example.parkingkotlin.activity.listClients.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.parkingkotlin.R
import com.example.parkingkotlin.activity.listClients.presenter.ListClientsPresenter
import com.example.parkingkotlin.activity.listClients.presenter.ListClientsPresenterImpl
import com.example.parkingkotlin.adapter.ClientAdapter
import com.example.parkingkotlin.database.entity.ClientEntity
import es.dmoral.toasty.Toasty

class ListClientsActivity : AppCompatActivity(), ListClientsView {

    @BindView(R.id.list_clients_recycler_clients)
    lateinit var recyclerClients: RecyclerView

    @BindView(R.id.progress_bar)
    lateinit var progressBar: ProgressBar

    @BindView(R.id.main_toolbar)
    lateinit var listClientsToolbar: Toolbar

    lateinit var presenter: ListClientsPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_clients)
        initComponents()
    }

    private fun initComponents(){
        ButterKnife.bind(this)

        setSupportActionBar(listClientsToolbar)
        title = resources.getString(R.string.title_list_clients)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        presenter = ListClientsPresenterImpl(this, application, this)

        recyclerClients.setHasFixedSize(true)
        recyclerClients.layoutManager = LinearLayoutManager(this)
        this.presenter.getClients()
    }

    override fun showProgress() {
        this.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        this.progressBar.visibility = View.INVISIBLE
    }

    override fun setDataToRecycler(list: List<ClientEntity>) {
        recyclerClients.adapter = ClientAdapter(this, list)
    }
    override fun showMessageError(throwable: Throwable) {
        Toasty.error(this, throwable.message.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Animatoo.animateSlideLeft(this)
    }

    override fun updateRecycler() {
        this.recyclerClients.invalidate()
        this.recyclerClients.adapter?.notifyDataSetChanged()
    }

    override fun onStart() {
        presenter.registerEventBus()
        super.onStart()
    }

    override fun onStop() {
        presenter.unRegisterEvent()
        super.onStop()
    }
}
