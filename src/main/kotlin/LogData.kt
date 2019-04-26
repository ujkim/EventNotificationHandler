package enh.logdata

import java.util.*

//enum class logType (
//    val ErrorLogType:String,
//    val WarningLogType:String,
//    val NormalLogType:String,
//    val NoneType:String
//)

class LogData {
    val managerName: String = "" // Manager person's name
    val managerEmail: String = "" // Manager's Email Address, this is made for notification
    val logType: String = "" // Error, Warning, Normal
    val logMessage: String = ""
}

// Singletone of LogQueue
object LogQueue { // static definition
    var queueOfErrors: Queue<String> = ArrayDeque<String>()
    var queueOfWarnings: Queue<String> = ArrayDeque<String>()
    var queueOfNormals: Queue<String> = ArrayDeque<String>()
}