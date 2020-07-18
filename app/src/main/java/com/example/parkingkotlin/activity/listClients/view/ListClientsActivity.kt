package com.example.parkingkotlin.activity.listClients.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.parkingkotlin.R
import com.example.parkingkotlin.activity.listClients.presenter.ListClientsPresenterImpl
import com.example.parkingkotlin.adapter.ClientAdapter
import com.example.parkingkotlin.database.entity.ClientEntity
import es.dmoral.toasty.Toasty
import java.util.*


class ListClientsActivity : AppCompatActivity(), ListClientsView {

    @BindView(R.id.list_clients_recycler_clients)
    lateinit var recyclerClients: RecyclerView

    @BindView(R.id.progress_bar)
    lateinit var progressBar: ProgressBar

    @BindView(R.id.main_toolbar)
    lateinit var listClientsToolbar: Toolbar

    @BindView(R.id.list_clients_llyout_empty_clients)
    lateinit var layoutEmptyClients: LinearLayout

    @BindView(R.id.list_clients_eddtext_search)
    lateinit var searchClientEditText: EditText

    @BindView(R.id.list_clients_txt_not_result)
    lateinit var notResultsTxt: TextView

    @BindView(R.id.list_clients_edtext_search)
    lateinit var searchLayout: LinearLayout

    lateinit var presenter: ListClientsPresenterImpl

    lateinit var listClients: List<ClientEntity>

    lateinit var adapterClients: ClientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_clients)
        initComponents()
    }

    private fun initComponents() {
        ButterKnife.bind(this)

        setSupportActionBar(listClientsToolbar)
        title = resources.getString(R.string.title_list_clients)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        presenter = ListClientsPresenterImpl(this, application, this)

        recyclerClients.setHasFixedSize(true)
        recyclerClients.layoutManager = LinearLayoutManager(this)
        this.presenter.getClients()

        searchClientEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

        })
    }

    override fun showProgress() {
        this.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        this.progressBar.visibility = View.INVISIBLE
    }

    override fun setDataToRecycler(list: List<ClientEntity>) {
        this.listClients = list
        this.adapterClients = ClientAdapter(this, list)
        recyclerClients.adapter = this.adapterClients
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
        //this.recyclerClients.invalidate()
        //this.recyclerClients.adapter?.notifyDataSetChanged()
    }

    override fun hideEmptyClients() {
        layoutEmptyClients.visibility = View.GONE
        recyclerClients.visibility = View.VISIBLE
        searchLayout.visibility = View.VISIBLE
    }

    override fun showEmptyClients() {
        layoutEmptyClients.visibility = View.VISIBLE
        recyclerClients.visibility = View.GONE
        searchLayout.visibility = View.GONE
    }

    override fun onStart() {
        presenter.registerEventBus()
        super.onStart()
    }

    override fun onStop() {
        presenter.unRegisterEvent()
        super.onStop()
    }

    @SuppressLint("DefaultLocale")
    private fun filter(text: String) {
        val filteredList = ArrayList<ClientEntity>()

        for (item in listClients) {
            if (item.clientName.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        adapterClients.filterList(filteredList)

        if (filteredList.size == 0) {
            notResultsTxt.visibility = View.VISIBLE
            recyclerClients.visibility = View.GONE
        }else{
            notResultsTxt.visibility = View.GONE
            recyclerClients.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toasty.success(this, resources.getString(R.string.update_success), Toast.LENGTH_SHORT).show()
    }
}
