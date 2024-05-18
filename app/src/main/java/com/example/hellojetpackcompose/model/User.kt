package com.example.hellojetpackcompose.model

data class User(
    var name: String? = "",
    var licenseNumber: String? = "",
    var dateEntering: String? = "",
    var tonnageWeight: String? = "",
    var netWeight: String? = "",
    var photoUrl: String? = ""
)

val mData = listOf(
    User(
        name = "Bams",
        licenseNumber = "B 5413 MCU",
        dateEntering = "13 Mei 2024",
        tonnageWeight = "120 kg",
        netWeight = "80 kg",
        photoUrl = ""
    ),
    User(
        name = "Bams",
        licenseNumber = "B 5413 MCU",
        dateEntering = "13 Mei 2024",
        tonnageWeight = "120 kg",
        netWeight = "80 kg",
        photoUrl = ""
    )
)

val data = User(
    name = "Bams",
    licenseNumber = "B 5413 MCU",
    dateEntering = "13 Mei 2024",
    tonnageWeight = "120 kg",
    netWeight = "80 kg",
    photoUrl = ""
)
