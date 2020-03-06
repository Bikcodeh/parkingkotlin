package com.example.parkingkotlin.activity.listClients.presenter

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingkotlin.activity.listClients.repository.ListClientsRepository
import com.example.parkingkotlin.activity.listClients.repository.ListClientsRepositoryImpl
import com.example.parkingkotlin.activity.listClients.view.ListClientsView
import com.example.parkingkotlin.database.entity.ClientEntity
import com.example.parkingkotlin.events.ClientStatusEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ListClientsPresenterImpl(appCompatActivity: AppCompatActivity, application: Application, private val view: ListClientsView): ListClientsPresenter {

    private val repository: ListClientsRepository = ListClientsRepositoryImpl(appCompatActivity, application, this)

    override fun getClients() {
        view.showProgress()
        repository.getAllClients()
    }

    override fun updateClientStatus(status: Int) {
        view.showProgress()
        repository.updateClientStatus(status)
    }

    override fun onSuccessGetClients(list: List<ClientEntity>) {
        view.hideProgress()
        if(list.isNotEmpty()){
            view.hideEmptyClients()
            view.setDataToRecycler(list)
        }else{
            view.showEmptyClients()
        }
    }

    override fun onErrorGetClients(throwable: Throwable) {
        view.hideProgress()
        view.showMessageError(throwable)
    }

    override fun onSuccessUpdate(updated: Int) {
        view.hideProgress()
        view.updateRecycler()
    }

    override fun onErrorUpdate(throwable: Throwable) {
        view.hideProgress()
        view.showMessageError(throwable)
    }

    override fun registerEventBus() {
        EventBus.getDefault().register(this)
    }

    override fun unRegisterEvent() {
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(sticky = true)
    fun onEvent(clientStatusEvent: ClientStatusEvent){
        repository.updateClientStatus(clientStatusEvent.status)
    }
}