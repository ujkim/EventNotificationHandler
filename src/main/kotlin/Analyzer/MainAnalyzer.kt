package analyzer

import enh.configmanager.ConfigManager
import enh.logdata.*
import java.util.*

interface Analyzer {
    fun calculateWithQueueContents(logQueue: Queue<String>)
}

open class MainAnalyzer: Thread() {
    val configManager = ConfigManager("config.json").getInstanceOfConfig()
    fun checkErrorCountOfQueue(queueOfLog: Queue<String>) {
        // Norminal Implementation? (Check)

    }
    // TODO Analysis of result, To Implement of functions

    override fun run() { // Thread Override
        // Error & Warning & Normal Queue Count Checking
        while(true) {
            if(LogQueue.queueOfErrors.size < configManager?.errorLimitCount!!) {
                // ErrorAnalyzer Call
                val errorAnalyzer = ErrorAnalyzer()
                errorAnalyzer.calculateWithQueueContents(LogQueue.queueOfErrors)
                // Notification Email
            }
            else if(LogQueue.queueOfWarnings.size < configManager?.warnLimitCount!!) {
                // WarningAnalyzer Call
                val warningAnalyzer = WarnAnalyzer()
                warningAnalyzer.calculateWithQueueContents((LogQueue.queueOfWarnings))
                // Notification Email
            }
        }
    }
}