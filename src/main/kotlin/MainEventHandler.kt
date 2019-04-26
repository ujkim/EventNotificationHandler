// Main Event Handler
// Topic Consumering, Logging Information Filetering
// Queue Sharing (Error/Warning/Normal)
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import enh.configmanager.ConfigManager
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

import enh.logdata.*

class MainEventHandler {
    val configManager = ConfigManager("config.json").getInstanceOfConfig()

    private fun createConsumer(): Consumer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = configManager?.kafkaServerIP + ":" + configManager?.kafkaServerPort
        props["group.id"] = configManager?.kafkaGroupId
        props["key.deserializer"] = StringDeserializer::class.java
        props["value.deserializer"] = StringDeserializer::class.java
        return KafkaConsumer<String, String>(props)
    }

    // Serialize Data to Json
    val jsonMapper = ObjectMapper().apply {
        registerKotlinModule()
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        setDateFormat(StdDateFormat())
    }

    // get LogData class's instance
    fun pollingTopics() {
        val consumer = createConsumer()
        // if there is only topic in list, only get log information.
        consumer.subscribe(configManager?.accessTopicList)
        while(true) {
            val records = consumer.poll(Duration.ofSeconds(1))
            records.iterator().forEach {
                val logsString = it.value()
                val logMessage = jsonMapper.readValue(logsString, LogData::class.java)
                println(logMessage.managerName + ", " + logMessage.managerEmail + ", "
                 + logMessage.logType + ", " + logMessage.logMessage)
                divideLogMessage(logMessage)
            }
        }
    }

    private fun divideLogMessage(logMessage: LogData?) {
        when (logMessage?.logType) {
            "ErrorLogType" -> LogQueue.queueOfErrors.add(logMessage.logMessage)
            "WarningLogType" -> LogQueue.queueOfWarnings.add(logMessage.logMessage)
            "logMessage" -> LogQueue.queueOfNormals.add(logMessage.logMessage)
        }
    }

    // polling routine is coroutine or thread ( + Filtering)
//    fun polling {
//        runBlocking {
//            val job = launch{
//                println("${Thread.currentThread()} has run")
//            }
//        }
//    }
}

