package com.example.parkingkotlin.events

import java.util.*

data class ClientUpdateEvent(val clientId: Int?, val clientStatus: Int?, val dueDate: Date?)