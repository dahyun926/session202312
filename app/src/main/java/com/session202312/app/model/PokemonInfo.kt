/*
 * Designed and developed by 2022 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.session202312.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.session202312.app.BaseApplication
import com.session202312.app.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream
import java.nio.charset.StandardCharsets

@Entity(tableName = "pokemons")
data class PokemonInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
) {
    val koName: String
        get() {
            val jsonString = readJsonFile(R.raw.ko)
            jsonString?.let {
                try {
                    val jsonObject = JSONObject(it)
                    return jsonObject.getJSONObject(id.toString()).getString("ko")
                } catch (e: JSONException) {
                    e.printStackTrace()
                    return name
                }
            }
            return name
        }

    val num: String
        get() {
            return if (id > 99)
                id.toString()
            else if (id > 9)
                "0$id"
            else
                "00$id"
        }

    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/official-artwork/$id.png"
    }

    private fun readJsonFile(resId: Int): String? {
        return try {
            // Resources를 통해 JSON 파일을 문자열로 읽어오기
            val inputStream: InputStream =
                BaseApplication.applicationContext().resources.openRawResource(resId)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()

            // 읽어온 데이터를 문자열로 변환하여 반환
            String(buffer, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
