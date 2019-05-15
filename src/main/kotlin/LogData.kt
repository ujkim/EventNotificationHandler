package enh.logdata

import java.util.*

class LogData {
    val managerName: String = "" // Manager person's name
    val managerEmail: String = "" // Manager's Email Address, this is made for notification
    val logType: String = "" // Error, Warning, Normal
    val logMessage: String = ""
}

// Singletone of LogQueue
object LogQueue { // static definition
    var queueOfErrors: Queue<LogData> = ArrayDeque<LogData>()
    var queueOfWarnings: Queue<LogData> = ArrayDeque<LogData>()
    var queueOfNormals: Queue<LogData> = ArrayDeque<LogData>()
}