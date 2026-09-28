package com.example.letterbox_games

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.letterbox_games.screens.AdicionarJogoScreen
import com.example.letterbox_games.screens.MeusJogosScreen
import com.example.letterbox_games.screens.PerfilScreen
import com.example.letterbox_games.data.RepositorioDeJogos
import com.example.letterbox_games.model.Jogo
import com.example.letterbox_games.ui.theme.LetterboxgamesTheme

/**
 * Enum simples representando qual das 3 telas está sendo mostrada.
 * Não é navegação de verdade (ainda não vimos Navigation Component em sala) —
 * é só um estado (remember + mutableStateOf) que decide qual Composable desenhar.
 */
enum class TelaAtual {
    MEUS_JOGOS, ADICIONAR_JOGO, PERFIL
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LetterboxgamesTheme {
                AppComTrocaDeTelas()
            }
        }
    }
}

@Composable
fun AppComTrocaDeTelas() {
    val context = LocalContext.current
    val repositorio = remember(context) { RepositorioDeJogos(context) }
    val jogos = remember(repositorio) {
        mutableStateListOf<Jogo>().apply { addAll(repositorio.listar()) }
    }
    var nomePerfil by remember(repositorio) { mutableStateOf(repositorio.lerNomePerfil()) }
    var telaAtual by remember { mutableStateOf(TelaAtual.MEUS_JOGOS) }
    var jogoEmEdicao by remember { mutableStateOf<Jogo?>(null) }

    BackHandler(enabled = telaAtual == TelaAtual.ADICIONAR_JOGO) {
        jogoEmEdicao = null
        telaAtual = TelaAtual.MEUS_JOGOS
    }

    Column(modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()) {
        Box(modifier = Modifier.weight(1f)) {
            when (telaAtual) {
                TelaAtual.MEUS_JOGOS -> MeusJogosScreen(
                    jogos = jogos,
                    onNavigateToAdicionarJogo = {
                        jogoEmEdicao = null
                        telaAtual = TelaAtual.ADICIONAR_JOGO
                    },
                    onEditarJogo = { jogo ->
                        jogoEmEdicao = jogo
                        telaAtual = TelaAtual.ADICIONAR_JOGO
                    },
                    onExcluirJogo = { jogo ->
                        jogos.removeAll { it.id == jogo.id }
                        repositorio.salvar(jogos)
                    }
                )
                TelaAtual.ADICIONAR_JOGO -> AdicionarJogoScreen(
                    onBack = { telaAtual = TelaAtual.MEUS_JOGOS },
                    jogoExistente = jogoEmEdicao,
                    onSave = { jogo ->
                        val indice = jogos.indexOfFirst { it.id == jogo.id }
                        if (indice >= 0) jogos[indice] = jogo else jogos.add(0, jogo)
                        repositorio.salvar(jogos)
                        jogoEmEdicao = null
                        telaAtual = TelaAtual.MEUS_JOGOS
                    }
                )
                TelaAtual.PERFIL -> PerfilScreen(
                    jogos = jogos,
                    nome = nomePerfil,
                    onNomeAlterado = { novoNome ->
                        nomePerfil = novoNome
                        repositorio.salvarNomePerfil(novoNome)
                    }
                )
            }
        }

        // Barra de troca de telas, fixa embaixo
        SeletorDeTelas(
            telaAtual = telaAtual,
            aoSelecionar = {
                if (it == TelaAtual.ADICIONAR_JOGO) jogoEmEdicao = null
                telaAtual = it
            }
        )
    }
}

@Composable
private fun SeletorDeTelas(telaAtual: TelaAtual, aoSelecionar: (TelaAtual) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0D0B1A))
            .padding(vertical = 10.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BotaoDeTela(
            texto = "Jogos",
            selecionado = telaAtual == TelaAtual.MEUS_JOGOS,
            onClick = { aoSelecionar(TelaAtual.MEUS_JOGOS) }
        )
        BotaoDeTela(
            texto = "Adicionar",
            selecionado = telaAtual == TelaAtual.ADICIONAR_JOGO,
            onClick = { aoSelecionar(TelaAtual.ADICIONAR_JOGO) }
        )
        BotaoDeTela(
            texto = "Perfil",
            selecionado = telaAtual == TelaAtual.PERFIL,
            onClick = { aoSelecionar(TelaAtual.PERFIL) }
        )
    }
}

@Composable
private fun BotaoDeTela(texto: String, selecionado: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selecionado) Color(0xFF00F3FF) else Color(0xFF171329)
        )
    ) {
        Text(
            text = texto,
            color = if (selecionado) Color(0xFF0D0D26) else Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}