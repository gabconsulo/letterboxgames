package com.example.letterbox_games.model

import java.util.UUID

data class Jogo(
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val plataforma: String,
    val nota: Int,
    val resenha: String = ""
)