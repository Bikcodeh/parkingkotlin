package com.example.parkingkotlin.events

import com.example.parkingkotlin.database.entity.ClientEntity

data class ClientEvent(val clientEntity: ClientEntity)