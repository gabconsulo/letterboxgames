package com.example.letterbox_games.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// ---------------------------------------------------------
// PREVIEW
// ---------------------------------------------------------

@Preview(
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun MeusJogosScreenPreview() {
    MeusJogosScreen(
        onNavigateToAdicionarJogo = {}
    )
}


// ---------------------------------------------------------
// MODELO DO JOGO
// ---------------------------------------------------------

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


// ---------------------------------------------------------
// JOGOS MOCKADOS
// ---------------------------------------------------------

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
    ),

    JogoJogado(
        iniciais = "RDR",
        nome = "Red Dead Redemption 2",
        plataforma = "PC",
        notaEstrelas = 5,
        corInicio = Color(0xFFEF4444),
        corFim = Color(0xFFF97316),
        corTagFundo = Color(0x14EF4444),
        corTagBorda = Color(0x40EF4444),
        corTagTexto = Color(0xFFEF4444)
    ),

    JogoJogado(
        iniciais = "GOW",
        nome = "God of War",
        plataforma = "PS5",
        notaEstrelas = 5,
        corInicio = Color(0xFF06B6D4),
        corFim = Color(0xFF2563EB),
        corTagFundo = Color(0x143B82F6),
        corTagBorda = Color(0x403B82F6),
        corTagTexto = Color(0xFF60A5FA)
    ),

    JogoJogado(
        iniciais = "CP",
        nome = "Cyberpunk 2077",
        plataforma = "PC",
        notaEstrelas = 4,
        corInicio = Color(0xFFFACC15),
        corFim = Color(0xFFEF4444),
        corTagFundo = Color(0x14FACC15),
        corTagBorda = Color(0x40FACC15),
        corTagTexto = Color(0xFFFACC15)
    ),

    JogoJogado(
        iniciais = "TW3",
        nome = "The Witcher 3",
        plataforma = "PC",
        notaEstrelas = 5,
        corInicio = Color(0xFFDC2626),
        corFim = Color(0xFF7F1D1D),
        corTagFundo = Color(0x14EF4444),
        corTagBorda = Color(0x40EF4444),
        corTagTexto = Color(0xFFF87171)
    ),

    JogoJogado(
        iniciais = "DS3",
        nome = "Dark Souls III",
        plataforma = "PC",
        notaEstrelas = 5,
        corInicio = Color(0xFF64748B),
        corFim = Color(0xFF1E293B),
        corTagFundo = Color(0x1464748B),
        corTagBorda = Color(0x4064748B),
        corTagTexto = Color(0xFF94A3B8)
    )
)


// ---------------------------------------------------------
// TELA PRINCIPAL
// ---------------------------------------------------------

@Composable
fun MeusJogosScreen(
    onNavigateToAdicionarJogo: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF070510),
                        Color(0xFF140E28)
                    )
                )
            )
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp,
                bottom = 90.dp
            ),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {


            // -------------------------------------------------
            // CABEÇALHO
            // -------------------------------------------------

            item {

                Column {

                    Text(
                        text = "ACOMPANHAMENTO",
                        color = Color(0xFF00F3FF),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Meus jogos",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }


            // -------------------------------------------------
            // LISTA DE JOGOS
            // -------------------------------------------------

            items(
                items = jogosMock,
                key = { jogo -> jogo.nome }
            ) { jogo ->

                GameCard(
                    jogo = jogo
                )
            }


            // -------------------------------------------------
            // ESPAÇO ANTES DO BOTÃO
            // -------------------------------------------------

            item {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }


            // -------------------------------------------------
            // BOTÃO ADICIONAR JOGO
            // -------------------------------------------------

            item {

                Button(
                    onClick = onNavigateToAdicionarJogo,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),

                    shape = RoundedCornerShape(16.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),

                    contentPadding = PaddingValues(0.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF8B5CF6),
                                        Color(0xFF00F3FF)
                                    )
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ),

                        contentAlignment = Alignment.Center
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Adicionar jogo",
                                tint = Color(0xFF0D0D26)
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

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
}


// ---------------------------------------------------------
// CARD DE CADA JOGO
// ---------------------------------------------------------

@Composable
private fun GameCard(
    jogo: JogoJogado
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(
                Color(0xB3171329)
            )
            .border(
                width = 1.dp,
                color = Color(0x14FFFFFF),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {


        // -------------------------------------------------
        // ÍCONE / INICIAIS DO JOGO
        // -------------------------------------------------

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(
                    RoundedCornerShape(14.dp)
                )
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            jogo.corInicio,
                            jogo.corFim
                        )
                    )
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


        Spacer(
            modifier = Modifier.width(16.dp)
        )


        // -------------------------------------------------
        // NOME + PLATAFORMA
        // -------------------------------------------------

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = jogo.nome,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )


            // TAG PC / PS5 / ETC
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(6.dp)
                    )
                    .background(
                        jogo.corTagFundo
                    )
                    .border(
                        width = 1.dp,
                        color = jogo.corTagBorda,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(
                        horizontal = 8.dp,
                        vertical = 3.dp
                    )
            ) {

                Text(
                    text = jogo.plataforma,
                    color = jogo.corTagTexto,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        // -------------------------------------------------
        // ESTRELAS
        // -------------------------------------------------

        StarRating(
            notaAtual = jogo.notaEstrelas,
            totalEstrelas = 5
        )
    }
}


// ---------------------------------------------------------
// SISTEMA DE ESTRELAS
// ---------------------------------------------------------

@Composable
private fun StarRating(
    notaAtual: Int,
    totalEstrelas: Int
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        for (i in 1..totalEstrelas) {

            Icon(
                imageVector =
                    if (i <= notaAtual) {
                        Icons.Filled.Star
                    } else {
                        Icons.Filled.StarBorder
                    },

                contentDescription = null,

                tint =
                    if (i <= notaAtual) {
                        Color(0xFFFBBF24)
                    } else {
                        Color(0xFF4B5563)
                    },

                modifier = Modifier.size(16.dp)
            )
        }
    }
}