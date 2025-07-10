package com.example.langocoach.core.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class AssetJsonSourceTest {

    @Test
    fun testOpenStream() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val assetJsonSource = AssetJsonSource(context, "bootstrap_queue.json")

        val inputStream = assetJsonSource.openStream()

        assertNotNull(inputStream)
    }

    @Test
    fun testReadBootstrapQueueJsonContent() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val assetJsonSource = AssetJsonSource(context, "bootstrap_queue.json")

        val inputStream = assetJsonSource.openStream()
        val content = inputStream.bufferedReader().use { it.readText() }

        val expectedContent = """
{
  "bootstrap_queue": [
    {"id":"german_BB900","token":"Guten Morgen","category":"Blocks","subcategory":"fixed"},
    {"id":"german_BB901","token":"Hallo","category":"Blocks","subcategory":"fixed"},
    {"id":"german_BB902","token":"Wie geht’s?","category":"Blocks","subcategory":"fixed"},
    {"id":"german_AA900","token":"Gut","category":"Adjectives/Adverbs","subcategory":"adjective"},
    {"id":"german_BB903","token":"Bitte schön","category":"Blocks","subcategory":"fixed"},
    {"id":"german_BB904","token":"Danke schön","category":"Blocks","subcategory":"fixed"},
    {"id":"german_BB905","token":"Kein Problem","category":"Blocks","subcategory":"fixed"},
    {"id":"german_BB906","token":"Es tut mir leid","category":"Blocks","subcategory":"fixed"},
    {"id":"german_BB907","token":"Alles klar","category":"Blocks","subcategory":"fixed"},
    {"id":"german_BB908","token":"Ich weiß nicht","category":"Blocks","subcategory":"fixed"},
    {"id":"german_FW900","token":"Ja","category":"Function Words","subcategory":"particle"},
    {"id":"german_FW901","token":"Nein","category":"Function Words","subcategory":"particle"},
	{"id":"german_FW902","token":"Ich","category":"Function Words","subcategory":"pronoun"},
	{ "id": "german_CS001", "token": "Ich ___ morgen ___.", "frequency_rank": 1, "category": "Core Stems" },
    { "id": "german_FW001", "token": "der", "frequency_rank": 1, "category": "Function Words", "subcategory": "article" },
    { "id": "german_FW002", "token": "die", "frequency_rank": 2, "category": "Function Words", "subcategory": "article" },
    { "id": "german_AA001", "token": "gut", "frequency_rank": 1, "category": "Adjectives/Adverbs", "subcategory": "adjective" },
    { "id": "german_BB001", "token": "Ich weiß nicht", "frequency_rank": 1, "category": "Blocks", "subcategory": "fixed" },
    { "id": "german_CS002", "token": "Wir ___ nächste Woche ___.", "frequency_rank": 2, "category": "Core Stems" },
    { "id": "german_FW003", "token": "und", "frequency_rank": 3, "category": "Function Words", "subcategory": "conjunction" },
    { "id": "german_FW004", "token": "in", "frequency_rank": 4, "category": "Function Words", "subcategory": "preposition" },
    { "id": "german_AA002", "token": "sehr", "frequency_rank": 2, "category": "Adjectives/Adverbs", "subcategory": "adverb" },
    { "id": "german_BB002", "token": "Zum Beispiel", "frequency_rank": 2, "category": "Blocks", "subcategory": "fixed" },
    { "id": "german_CS003", "token": "Du ___ heute Abend ___?", "frequency_rank": 3, "category": "Core Stems" },
    { "id": "german_FW005", "token": "den", "frequency_rank": 5, "category": "Function Words", "subcategory": "article" },
    { "id": "german_FW006", "token": "von", "frequency_rank": 6, "category": "Function Words", "subcategory": "preposition" },
    { "id": "german_AA003", "token": "viel", "frequency_rank": 3, "category": "Adjectives/Adverbs", "subcategory": "adjective" },
    { "id": "german_BB003", "token": "Auf jeden Fall", "frequency_rank": 3, "category": "Blocks", "subcategory": "fixed" },
    { "id": "german_CS004", "token": "Sie ___ bald ___.", "frequency_rank": 4, "category": "Core Stems" },
    { "id": "german_FW007", "token": "zu", "frequency_rank": 7, "category": "Function Words", "subcategory": "preposition" },
    { "id": "german_FW008", "token": "das", "frequency_rank": 8, "category": "Function Words", "subcategory": "article" },
    { "id": "german_AA004", "token": "groß", "frequency_rank": 4, "category": "Adjectives/Adverbs", "subcategory": "adjective" },
    { "id": "german_BB004", "token": "Danke schön", "frequency_rank": 4, "category": "Blocks", "subcategory": "fixed" },
    { "id": "german_FW009", "token": "mit", "frequency_rank": 9, "category": "Function Words", "subcategory": "preposition" },
    { "id": "german_FW010", "token": "sich", "frequency_rank": 10, "category": "Function Words", "subcategory": "pronoun" },
    { "id": "german_AA005", "token": "klein", "frequency_rank": 5, "category": "Adjectives/Adverbs", "subcategory": "adjective" },
    { "id": "german_BB005", "token": "Bitte schön", "frequency_rank": 5, "category": "Blocks", "subcategory": "fixed" },
    { "id": "german_FW011", "token": "des", "frequency_rank": 11, "category": "Function Words", "subcategory": "article" },
    { "id": "german_FW012", "token": "dem", "frequency_rank": 12, "category": "Function Words", "subcategory": "article" },
    { "id": "german_AA006", "token": "alt", "frequency_rank": 6, "category": "Adjectives/Adverbs", "subcategory": "adjective" },
    { "id": "german_FW013", "token": "auf", "frequency_rank": 13, "category": "Function Words", "subcategory": "preposition" },
    { "id": "german_FW014", "token": "nicht", "frequency_rank": 14, "category": "Function Words", "subcategory": "particle" },
    { "id": "german_AA007", "token": "neu", "frequency_rank": 7, "category": "Adjectives/Adverbs", "subcategory": "adjective" },
    { "id": "german_FW015", "token": "ein", "frequency_rank": 15, "category": "Function Words", "subcategory": "article" },
    { "id": "german_FW016", "token": "für", "frequency_rank": 16, "category": "Function Words", "subcategory": "preposition" },
    { "id": "german_V001", "token": "sein", "frequency_rank": 5, "category": "Verbs", "subcategory": "auxiliary" },
    { "id": "german_FW017", "token": "wir", "frequency_rank": 27, "category": "Function Words", "subcategory": "pronoun" },
    { "id": "german_V002", "token": "haben", "frequency_rank": 9, "category": "Verbs", "subcategory": "auxiliary" },
    { "id": "german_V003", "token": "werden", "frequency_rank": 12, "category": "Verbs", "subcategory": "auxiliary" },
    { "id": "german_V004", "token": "können", "frequency_rank": 14, "category": "Verbs", "subcategory": "auxiliary" },
    { "id": "german_V005", "token": "müssen", "frequency_rank": 15, "category": "Verbs", "subcategory": "auxiliary" },
    { "id": "german_V006", "token": "sagen", "frequency_rank": 18, "category": "Verbs", "subcategory": "action" },
    { "id": "german_V007", "token": "machen", "frequency_rank": 20, "category": "Verbs", "subcategory": "action" },
    { "id": "german_V008", "token": "geben", "frequency_rank": 22, "category": "Verbs", "subcategory": "action" },
    { "id": "german_N001", "token": "Mann", "frequency_rank": 101, "category": "Nouns", "subcategory": "masculine" },
    { "id": "german_N002", "token": "Frau", "frequency_rank": 102, "category": "Nouns", "subcategory": "feminine" },
    { "id": "german_N003", "token": "Kind", "frequency_rank": 103, "category": "Nouns", "subcategory": "neuter" },
    { "id": "german_N004", "token": "Haus", "frequency_rank": 104, "category": "Nouns", "subcategory": "neuter" },
    { "id": "german_N005", "token": "Stadt", "frequency_rank": 105, "category": "Nouns", "subcategory": "feminine" },
    { "id": "german_N006", "token": "Land", "frequency_rank": 106, "category": "Nouns", "subcategory": "neuter" },
    { "id": "german_N007", "token": "Auto", "frequency_rank": 107, "category": "Nouns", "subcategory": "neuter" },
    { "id": "german_N008", "token": "Hund", "frequency_rank": 108, "category": "Nouns", "subcategory": "masculine" },
    { "id": "german_N009", "token": "Katze", "frequency_rank": 109, "category": "Nouns", "subcategory": "feminine" }
  ]
}
"""

        assertEquals(expectedContent.trim(), content.trim())
    }
}
