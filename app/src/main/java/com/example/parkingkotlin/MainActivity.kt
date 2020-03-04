package com.example.parkingkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import butterknife.ButterKnife
import butterknife.OnClick
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.parkingkotlin.activity.listClients.view.ListClientsActivity
import com.example.parkingkotlin.activity.register.view.RegisterActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
    }

    private fun initComponents(){
        ButterKnife.bind(this)
    }

    @OnClick(R.id.main_img_register)
    fun openRegister(){
        startActivity(Intent(this, RegisterActivity::class.java))
        Animatoo.animateSlideRight(this)
    }

    @OnClick(R.id.main_img_list)
    fun openList(){
        startActivity(Intent(this, ListClientsActivity::class.java))
        Animatoo.animateSlideRight(this)
    }
}
