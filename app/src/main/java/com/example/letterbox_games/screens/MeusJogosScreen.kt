package com.example.letterbox_games.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.letterbox_games.model.Jogo

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun MeusJogosScreenPreview() {
    MeusJogosScreen(emptyList(), {}, {}, {})
}

@Composable
fun MeusJogosScreen(
    jogos: List<Jogo>,
    onNavigateToAdicionarJogo: () -> Unit,
    onEditarJogo: (Jogo) -> Unit,
    onExcluirJogo: (Jogo) -> Unit
) {
    var jogoSelecionado by remember { mutableStateOf<Jogo?>(null) }
    var jogoParaExcluir by remember { mutableStateOf<Jogo?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF070510), Color(0xFF140E28))))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column {
                    Text("ACOMPANHAMENTO", color = Color(0xFF00F3FF), fontSize = 12.sp)
                    Spacer(Modifier.height(4.dp))
                    Text("Meus jogos", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
                    Text("${jogos.size} ${if (jogos.size == 1) "jogo" else "jogos"} na biblioteca", color = Color(0xFF9CA3AF), fontSize = 13.sp)
                }
            }

            if (jogos.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 36.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Sua biblioteca está vazia", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(6.dp))
                        Text("Adicione um jogo para começar a registrar suas avaliações.", color = Color(0xFF9CA3AF), fontSize = 13.sp)
                    }
                }
            } else {
                items(jogos, key = { it.id }) { jogo ->
                    GameCard(jogo = jogo, onClick = { jogoSelecionado = jogo })
                }
            }

            item {
                Button(
                    onClick = onNavigateToAdicionarJogo,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().background(
                            Brush.horizontalGradient(listOf(Color(0xFF8B5CF6), Color(0xFF00F3FF))),
                            RoundedCornerShape(12.dp)
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null, tint = Color(0xFF0D0D26))
                        Spacer(Modifier.width(8.dp))
                        Text("Adicionar jogo", color = Color(0xFF0D0D26), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }

    jogoSelecionado?.let { jogo ->
        AlertDialog(
            onDismissRequest = { jogoSelecionado = null },
            title = { Text(jogo.nome) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("${jogo.plataforma}  ·  ${jogo.nota}/5 estrelas")
                    Text(jogo.resenha.ifBlank { "Nenhuma resenha adicionada." })
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    jogoSelecionado = null
                    onEditarJogo(jogo)
                }) { Text("Editar") }
            },
            dismissButton = {
                TextButton(onClick = {
                    jogoParaExcluir = jogo
                    jogoSelecionado = null
                }) { Text("Excluir", color = Color(0xFFB3261E)) }
            }
        )
    }

    jogoParaExcluir?.let { jogo ->
        AlertDialog(
            onDismissRequest = { jogoParaExcluir = null },
            title = { Text("Excluir jogo?") },
            text = { Text("${jogo.nome} será removido da sua biblioteca.") },
            confirmButton = {
                TextButton(onClick = {
                    onExcluirJogo(jogo)
                    jogoParaExcluir = null
                }) { Text("Excluir", color = Color(0xFFB3261E)) }
            },
            dismissButton = { TextButton(onClick = { jogoParaExcluir = null }) { Text("Cancelar") } }
        )
    }
}

@Composable
private fun GameCard(jogo: Jogo, onClick: () -> Unit) {
    val paleta = listOf(Color(0xFF00B8A9), Color(0xFF3B82F6), Color(0xFFE76F51), Color(0xFF8B5CF6))
    val cor = paleta[(jogo.nome.hashCode().ushr(1)) % paleta.size]

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xB3171329))
            .border(1.dp, Color(0x14FFFFFF), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(10.dp))
                .background(Brush.horizontalGradient(listOf(cor, cor.copy(alpha = 0.65f)))),
            contentAlignment = Alignment.Center
        ) {
            Text(
                jogo.nome.split(Regex("\\s+")).take(3).mapNotNull { it.firstOrNull() }.joinToString("").uppercase(),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(jogo.nome, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(jogo.plataforma, color = Color(0xFF00F3FF), fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            for (indice in 1..5) {
                Icon(
                    imageVector = if (indice <= jogo.nota) Icons.Filled.Star else Icons.Filled.StarBorder,
                    contentDescription = null,
                    tint = if (indice <= jogo.nota) Color(0xFFFBBF24) else Color(0xFF4B5563),
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}