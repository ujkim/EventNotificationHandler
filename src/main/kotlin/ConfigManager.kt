// All of Configuration Management
// Configuration Factor
package enh.configmanager

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.nio.file.Paths

class ConfigManager(configName: String) {
    val configJsonPath = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\kotlin\\$configName"
    var kafkaServerIP: String? = ""
    var kafkaServerPort: Int? = 0
    var kafkaGroupId: String? = ""
    var accessTopicList: List<String>? = null
    var errorLimitCount: Int? = 0
    var warnLimitCount: Int? = 0
    var errorCsvFileName: String? = ""
    var warnCsvFileName: String? = ""

    private fun readJson() : String {
        val bufferedReader: BufferedReader = File(configJsonPath).bufferedReader()
        return bufferedReader.use { it.readText() }
    }

    fun getInstanceOfConfig() : ConfigManager? {
        return Gson().fromJson(readJson(), ConfigManager::class.java)
    }
}

