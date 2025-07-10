package com.example.langocoach.core.data

import org.junit.Assert.assertEquals
import org.junit.Test

class SessionContainersTest {

    @Test
    fun testSessionContainersCreation() {
        val category =
            Category(
                name = "Blocks",
                pct_of_collection = 0.05,
                definition = "Fixed multi-word phrases (2–4 words) bundling high-frequency function + content into utterances.",
                subcategories = listOf("fixed", "parametric")
            )

        val objective =
            Objective(
                id = "german_BB900",
                token = "Guten Morgen",
                category = "Blocks",
                subcategory = "fixed",
                frequency_rank = null
            )

        val sessionContainers =
            SessionContainers(
                collection_size = 500,
                hl_cornerstones = "| No. | Cornerstone frame (plug-in slots in **bold**) | Core job(s) it covers | Why it’s high-leverage | Register notes & gotchas |\n  |---|-----------------------------------------------|-----------------------|------------------------|--------------------------|\n  | 1 | **[pronoun] + verb-present** <br>+ future time word<br>e.g. *Ich **gehe** morgen …* | Present **and** near-future (“I go / I’m going tomorrow”) | Same single present endings serve both tenses once you add *morgen, nächste Woche*, etc.—no separate *Futur I* needed. | Neutral/standard. Works in speech & informal writing; formal writing prefers explicit future (*werde*). |\n  | 2 | **[pronoun] + haben/sein-present + Partizip II**<br>e.g. *Ich **habe gegessen*** | Conversational past (perfect) for **any** verb | You only learn six present-tense forms of *haben/sein* plus a memorised participle list; that covers most past narration. | Neutral & dominant in spoken German. In formal writing replace with Präteritum for some verbs (*sagte, ging*). |\n  | 3 | **[pronoun] + modal-present + Infinitiv**<br>e.g. *Ich **kann** Deutsch sprechen* | Ability, permission, obligation, desire, advice | One rule (conjugate 6 modal verbs) unlocks dozens of pragmatic meanings; infinitive always stays bare at the end. | Neutral. *Wollen* can sound pushy; soften with *möchte* or *würde gern*. |\n  | 4 | **[pronoun] + würde + Infinitiv**<br>(Konjunktiv II of *werden*)<br>e.g. *Ich **würde** gern reisen* | Hypotheticals, polite requests, conditionals | Sidesteps irregular Konjunktiv forms (*hätte*, *ginge*)—just learn *würde* table once. | Polite/standard. Over-use can feel repetitive in high-level writing; mix in real Konjunktiv later. |\n  | 5 | **um … zu + Infinitiv**<br>e.g. *… um Deutsch **zu lernen*** | Purpose clauses (“in order to …”) | A two-word wrapper expresses purpose without extra verb endings or conjunction syntax. | Neutral. Keep subject the same in both clauses; otherwise switch to *damit*. |\n  | 6 | **es gibt + Akk.-object**<br>e.g. *Es gibt viele Museen* | Existential *there is/are* (all numbers) | Fixed phrase—no subject/verb agreement headaches. Swap *gab* or *wird … geben* for past/future as needed. | Neutral & ubiquitous. Avoid in very formal academic prose (prefer *es existieren*). |\n  | 7 | **Lass(t) uns + Infinitiv!**<br>e.g. *Lasst uns anfangen!* | Inclusive “let’s …!” imperative | Learners make suggestions with zero imperative endings to memorise beyond *lassen*. | Spoken & friendly. In formal or written contexts, prefer *Lassen Sie uns …* (polite plural). |",
                categories = listOf(category),
                bootstrap_queue = listOf(objective)
            )

        assertEquals(500, sessionContainers.collection_size)
        assertEquals(1, sessionContainers.categories.size)
        assertEquals(1, sessionContainers.bootstrap_queue.size)
    }
}