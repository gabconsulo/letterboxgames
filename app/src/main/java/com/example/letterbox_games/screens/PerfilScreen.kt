package com.example.letterbox_games.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.letterbox_games.model.Jogo

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun PerfilScreenPreview() {
    PerfilScreen(emptyList(), "Gabriel", {})
}


data class Estatistica(val icone: ImageVector, val valor: String, val rotulo: String)
data class GeneroRanking(val posicao: Int, val nome: String)

@Composable
fun PerfilScreen(jogos: List<Jogo>, nome: String, onNomeAlterado: (String) -> Unit) {
    var mostrarEdicao by remember { mutableStateOf(false) }
    var nomeRascunho by remember(nome) { mutableStateOf(nome) }
    val media = if (jogos.isEmpty()) "—" else String.format("%.1f", jogos.map { it.nota }.average())
    val estatisticas = listOf(
        Estatistica(Icons.Filled.EmojiEvents, jogos.size.toString(), "Jogos"),
        Estatistica(Icons.Filled.HourglassEmpty, jogos.count { it.resenha.isNotBlank() }.toString(), "Resenhas"),
        Estatistica(Icons.Filled.Star, media, "Nota média")
    )
    val plataformas = jogos.groupingBy { it.plataforma }.eachCount().entries
        .sortedByDescending { it.value }
        .take(3)
        .mapIndexed { indice, entrada -> GeneroRanking(indice + 1, "${entrada.key} · ${entrada.value}") }

    androidx.compose.foundation.layout.Box(
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
            CabecalhoPerfil(nome = nome, membroDesde = "Perfil da biblioteca")

            Spacer(modifier = Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                estatisticas.forEach { estatistica ->
                    CardEstatistica(estatistica = estatistica, modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Plataformas mais jogadas",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (plataformas.isEmpty()) {
                Text("As plataformas aparecem aqui quando você adicionar jogos.", color = Color(0xFF9CA3AF), fontSize = 13.sp)
            } else {
                ListaGeneros(generos = plataformas)
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    nomeRascunho = nome
                    mostrarEdicao = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xB3171329)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x14FFFFFF))
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar perfil",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Editar perfil",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    if (mostrarEdicao) {
        AlertDialog(
            onDismissRequest = { mostrarEdicao = false },
            title = { Text("Editar perfil") },
            text = {
                OutlinedTextField(
                    value = nomeRascunho,
                    onValueChange = { nomeRascunho = it },
                    label = { Text("Nome") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val nomeValido = nomeRascunho.trim()
                    if (nomeValido.isNotEmpty()) {
                        onNomeAlterado(nomeValido)
                        mostrarEdicao = false
                    }
                }) { Text("Salvar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarEdicao = false }) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun CabecalhoPerfil(nome: String, membroDesde: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF8B5CF6), Color(0xFFEC4899))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nome.take(1),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = nome,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = membroDesde,
                color = Color(0xFF9CA3AF),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun CardEstatistica(estatistica: Estatistica, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xB3171329))
            .border(1.dp, Color(0x14FFFFFF), RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0x1400F3FF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = estatistica.icone,
                contentDescription = null,
                tint = Color(0xFF00F3FF),
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = estatistica.valor,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = estatistica.rotulo.uppercase(),
            color = Color(0xFF9CA3AF),
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ListaGeneros(generos: List<GeneroRanking>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xB3171329))
            .border(1.dp, Color(0x14FFFFFF), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        generos.forEachIndexed { indice, genero ->
            LinhaGenero(genero = genero)
            if (indice < generos.lastIndex) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0x14FFFFFF))
                )
            }
        }
    }
}

@Composable
private fun LinhaGenero(genero: GeneroRanking) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0x228B5CF6)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = genero.posicao.toString(),
                    color = Color(0xFF8B5CF6),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = genero.nome,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF9CA3AF),
            modifier = Modifier.size(16.dp)
        )
    }
}