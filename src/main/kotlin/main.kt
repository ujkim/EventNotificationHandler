
import enh.configmanager.ConfigManager

fun main(args: Array<String>) {
//    val configPath = "D:\\Projects\\Kotlin\\EventNotificationHandler\\src\\main\\kotlin\\config.json"
//    val configInstance = ConfigManager().getInstanceOfConfig(configPath)
    val configInstance = ConfigManager("config.json").getInstanceOfConfig()
    println(configInstance?.kafkaServerIP)
    println(configInstance?.kafkaServerPort)
    println(configInstance?.errorLimitCount)
    println(configInstance?.warnLimitCount)
    configInstance?.accessTopicList?.forEach { topic -> println(topic)}
}