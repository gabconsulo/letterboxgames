package com.example.letterbox_games.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.letterbox_games.model.Jogo

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun AdicionarJogoScreenPreview() {
    AdicionarJogoScreen(onBack = {}, onSave = {})
}

private val plataformas = listOf("PC", "PS5", "Switch")
private val labelsNota = mapOf(
    1 to "Ruim",
    2 to "Regular",
    3 to "Bom",
    4 to "Muito bom",
    5 to "Excelente"
)

@Composable
fun AdicionarJogoScreen(
    onBack: () -> Unit,
    onSave: (Jogo) -> Unit,
    jogoExistente: Jogo? = null
) {
    var nomeJogo by remember(jogoExistente?.id) { mutableStateOf(jogoExistente?.nome.orEmpty()) }
    var plataformaSelecionada by remember(jogoExistente?.id) {
        mutableStateOf(jogoExistente?.plataforma ?: "PC")
    }
    var notaSelecionada by remember(jogoExistente?.id) {
        mutableIntStateOf(jogoExistente?.nota ?: 5)
    }
    var resenha by remember(jogoExistente?.id) { mutableStateOf(jogoExistente?.resenha.orEmpty()) }
    var erroNome by remember { mutableStateOf(false) }

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
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = "BIBLIOTECA",
                color = Color(0xFF00F3FF),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (jogoExistente == null) "Novo jogo" else "Editar jogo",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(20.dp))

            CampoFormulario(label = "Nome do jogo") {
                TextoDeEntrada(
                    valor = nomeJogo,
                    placeholder = "Digite o nome do jogo...",
                    aoAlterar = {
                        nomeJogo = it
                        if (it.isNotBlank()) erroNome = false
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            CampoFormulario(label = "Plataforma") {
                SeletorPlataforma(
                    plataformaSelecionada = plataformaSelecionada,
                    aoSelecionar = { plataformaSelecionada = it }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            CampoFormulario(label = "Sua nota") {
                SeletorNota(
                    notaSelecionada = notaSelecionada,
                    aoSelecionar = { notaSelecionada = it }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            CampoFormulario(label = "Resenha curta (opcional)") {
                CampoTextoLongo(
                    valor = resenha,
                    placeholder = "O que achou do jogo?",
                    aoAlterar = { resenha = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (erroNome) {
                Text(
                    text = "Digite o nome do jogo para continuar.",
                    color = Color(0xFFFF8A80),
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.weight(1f).height(49.dp)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        val nome = nomeJogo.trim()
                        if (nome.isBlank()) {
                            erroNome = true
                        } else {
                            onSave(
                                Jogo(
                                    id = jogoExistente?.id ?: java.util.UUID.randomUUID().toString(),
                                    nome = nome,
                                    plataforma = plataformaSelecionada,
                                    nota = notaSelecionada,
                                    resenha = resenha.trim()
                                )
                            )
                        }
                    },
                    modifier = Modifier.weight(1f).height(49.dp),
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
                        Text(
                            text = "Salvar jogo",
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
private fun CampoFormulario(label: String, conteudo: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label.uppercase(),
            color = Color(0xFF9CA3AF),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        conteudo()
    }
}

@Composable
private fun TextoDeEntrada(valor: String, placeholder: String, aoAlterar: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xB3171329))
            .border(1.dp, Color(0x14FFFFFF), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        BasicTextField(
            value = valor,
            onValueChange = aoAlterar,
            textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 14.sp),
            cursorBrush = SolidColor(Color(0xFF00F3FF)),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (valor.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color(0xFF6B7280),
                        fontSize = 14.sp
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun CampoTextoLongo(valor: String, placeholder: String, aoAlterar: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xB3171329))
            .border(1.dp, Color(0x14FFFFFF), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        BasicTextField(
            value = valor,
            onValueChange = aoAlterar,
            textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 14.sp),
            cursorBrush = SolidColor(Color(0xFF00F3FF)),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (valor.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color(0xFF6B7280),
                        fontSize = 14.sp
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun SeletorPlataforma(plataformaSelecionada: String, aoSelecionar: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        plataformas.forEach { plataforma ->
            val selecionada = plataforma == plataformaSelecionada
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (selecionada) Color(0xA1221C3A) else Color(0xB3171329))
                    .border(
                        width = 1.dp,
                        color = if (selecionada) Color(0xFF00F3FF) else Color(0x14FFFFFF),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { aoSelecionar(plataforma) }
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = plataforma,
                    color = if (selecionada) Color(0xFF00F3FF) else Color(0xFF9CA3AF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun SeletorNota(notaSelecionada: Int, aoSelecionar: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xB3171329))
            .border(1.dp, Color(0x14FFFFFF), RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = labelsNota[notaSelecionada] ?: "",
            color = Color(0xFF00F3FF),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            for (i in 1..5) {
                Icon(
                    imageVector = if (i <= notaSelecionada) Icons.Filled.Star else Icons.Filled.StarBorder,
                    contentDescription = "Nota $i",
                    tint = if (i <= notaSelecionada) Color(0xFFFBBF24) else Color(0xFF4B5563),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { aoSelecionar(i) }
                )
            }
        }
    }
}