package com.example.letterbox_games.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun MeusJogosScreenPreview() {
    MeusJogosScreen()
}

/**
 * Tela "Meus jogos" — Acompanhamento/Progresso.
 * Mostra a lista de jogos já jogados/avaliados pelo usuário.
 *
 * Modelo simples em memória (sem persistência ainda, conforme escopo do Trabalho 1).
 */
data class JogoJogado(
    val iniciais: String,
    val nome: String,
    val plataforma: String,
    val notaEstrelas: Int,
    val corInicio: Color,
    val corFim: Color,
    val corTagFundo: Color,
    val corTagBorda: Color,
    val corTagTexto: Color
)

private val jogosMock = listOf(
    JogoJogado(
        iniciais = "HK",
        nome = "Hollow Knight",
        plataforma = "PC",
        notaEstrelas = 5,
        corInicio = Color(0xFF3B82F6),
        corFim = Color(0xFF00F3FF),
        corTagFundo = Color(0x1400F3FF),
        corTagBorda = Color(0x4000F3FF),
        corTagTexto = Color(0xFF00F3FF)
    ),
    JogoJogado(
        iniciais = "ER",
        nome = "Elden Ring",
        plataforma = "PS5",
        notaEstrelas = 5,
        corInicio = Color(0xFF8B5CF6),
        corFim = Color(0xFFEC4899),
        corTagFundo = Color(0x143B82F6),
        corTagBorda = Color(0x403B82F6),
        corTagTexto = Color(0xFF3B82F6)
    ),
    JogoJogado(
        iniciais = "V",
        nome = "Valorant",
        plataforma = "PC",
        notaEstrelas = 4,
        corInicio = Color(0xFFF59E0B),
        corFim = Color(0xFFEF4444),
        corTagFundo = Color(0x1400F3FF),
        corTagBorda = Color(0x4000F3FF),
        corTagTexto = Color(0xFF00F3FF)
    )
)

@Composable
fun MeusJogosScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF070510), Color(0xFF140E28))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "ACOMPANHAMENTO",
                color = Color(0xFF00F3FF),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Meus jogos",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                jogosMock.forEach { jogo ->
                    GameCard(jogo = jogo)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* navegação será feita em uma próxima aula */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF8B5CF6), Color(0xFF00F3FF))
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Adicionar jogo",
                            tint = Color(0xFF0D0D26)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Adicionar jogo",
                            color = Color(0xFF0D0D26),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GameCard(jogo: JogoJogado) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xB3171329))
            .border(1.dp, Color(0x14FFFFFF), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Brush.horizontalGradient(colors = listOf(jogo.corInicio, jogo.corFim))
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = jogo.iniciais,
                color = Color(0xFF0D0D26),
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = jogo.nome,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(jogo.corTagFundo)
                    .border(1.dp, jogo.corTagBorda, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = jogo.plataforma,
                    color = jogo.corTagTexto,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        StarRating(notaAtual = jogo.notaEstrelas, totalEstrelas = 5)
    }
}

@Composable
private fun StarRating(notaAtual: Int, totalEstrelas: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        for (i in 1..totalEstrelas) {
            Icon(
                imageVector = if (i <= notaAtual) Icons.Filled.Star else Icons.Filled.StarBorder,
                contentDescription = null,
                tint = if (i <= notaAtual) Color(0xFFFBBF24) else Color(0xFF4B5563),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
