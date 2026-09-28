package com.example.letterbox_games.data

import android.content.Context
import com.example.letterbox_games.model.Jogo
import org.json.JSONArray
import org.json.JSONObject

class RepositorioDeJogos(context: Context) {
	private val preferencias = context.getSharedPreferences("letterbox_games", Context.MODE_PRIVATE)

	fun listar(): List<Jogo> {
		val dados = preferencias.getString(CHAVE_JOGOS, null) ?: return emptyList()
		return runCatching {
			val json = JSONArray(dados)
			List(json.length()) { indice ->
				val item = json.getJSONObject(indice)
				Jogo(
					id = item.getString("id"),
					nome = item.getString("nome"),
					plataforma = item.getString("plataforma"),
					nota = item.getInt("nota"),
					resenha = item.optString("resenha")
				)
			}
		}.getOrDefault(emptyList())
	}

	fun salvar(jogos: List<Jogo>) {
		val json = JSONArray()
		jogos.forEach { jogo ->
			json.put(
				JSONObject()
					.put("id", jogo.id)
					.put("nome", jogo.nome)
					.put("plataforma", jogo.plataforma)
					.put("nota", jogo.nota)
					.put("resenha", jogo.resenha)
			)
		}
		preferencias.edit().putString(CHAVE_JOGOS, json.toString()).apply()
	}

	fun lerNomePerfil(): String = preferencias.getString(CHAVE_NOME, "Gabriel") ?: "Gabriel"

	fun salvarNomePerfil(nome: String) {
		preferencias.edit().putString(CHAVE_NOME, nome).apply()
	}

	private companion object {
		const val CHAVE_JOGOS = "jogos"
		const val CHAVE_NOME = "nome_perfil"
	}
}
