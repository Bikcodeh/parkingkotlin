package com.example.parkingkotlin.model

import java.io.Serializable
import java.util.*

class ClientModel(var clientId: Int?, var clientName: String, var clientIdentification: String,
                  var clientPhone: String, var clientPlaque: String, var clientRate: Double,
                  var startDate: Date, var statusPayment: Int, var clientActive: Int?): Serializable