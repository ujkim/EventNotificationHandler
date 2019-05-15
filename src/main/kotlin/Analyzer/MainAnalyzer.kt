package analyzer

import enh.configmanager.ConfigManager
import enh.logdata.*
import java.util.*
import kotlin.collections.HashMap
import enh.common.io.IOHandler


interface Analyzer {
    fun calculateWithQueueContents(targetLogList: List<LogData>): HashMap<String, Double> {
        val hash_map_statistics: HashMap<String, Double> = HashMap<String, Double>()
        for (logData in targetLogList) {
            if (hash_map_statistics.keys.contains(logData.logMessage)) {
                var x = hash_map_statistics[logData.logMessage]
                hash_map_statistics[logData.logMessage] = 1.0 + x!!
            } else {
                hash_map_statistics[logData.logMessage] = 1.0
            }
        }
        return hash_map_statistics
    }
}

open class MainAnalyzer: Thread() {
    val configManager = ConfigManager("config.json").getInstanceOfConfig()
    // TODO Analysis of result, To Implement of functions
    val ioHandler = IOHandler()

    override fun run() { // Thread Override
        // Error & Warning & Normal Queue Count Checking
        while(true) {
            // Map<String, List<LogData>
            for ((email, valueOfLogList) in LogQueue.queueOfErrors.groupBy { it.managerEmail }.entries) {
                if (valueOfLogList.size > configManager?.errorLimitCount!!) {
                    val errorAnalyzer = ErrorAnalyzer()
                    val error_stats_map = errorAnalyzer.calculateWithQueueContents(valueOfLogList)
                    ioHandler.convertMapToCSV(configManager.errorCsvFileName!!, error_stats_map, valueOfLogList.size)
                    // Notification Sending Email(Outlook), send email and valueOfLogList (Log Instance)
                    // To implement Notification Email
                }
                Thread.sleep(100)
            }

            for ((email, valueOfLogList) in LogQueue.queueOfWarnings.groupBy { it.managerEmail }.entries) {
                if (valueOfLogList.size > configManager?.warnLimitCount!!) {
                    val warningAnalyzer = WarnAnalyzer()
                    val warn_stats_map = warningAnalyzer.calculateWithQueueContents(valueOfLogList)
                    ioHandler.convertMapToCSV(configManager.warnCsvFileName!!, warn_stats_map, valueOfLogList.size)
                    // Notification Sending Email(Outlook), send email and valueOfLogList (Log Instance)
                    // To Implement Notification Email
                }
                Thread.sleep(100)
            }
        }
    }
}