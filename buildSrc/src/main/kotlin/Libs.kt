object Libs {
    const val jvmTarget = "11"

    const val kotlinCoroutines = "1.3.4"

    const val tgBotApi = "org.telegram:telegrambots:4.3.1"

    private const val ktor = "1.3.2"
    const val ktorServer = "io.ktor:ktor-server-netty:$ktor"
    const val ktorServerJackson = "io.ktor:ktor-jackson:$ktor"
    const val ktorClient = "io.ktor:ktor-client-apache:$ktor"
    const val ktorClientJackson = "io.ktor:ktor-client-jackson:$ktor"

    private const val komodo = "0.0.1-dev-b40"
    const val komodoLogging = "io.heapy.komodo:komodo-logging:$komodo"
    const val komodoDotenv = "io.heapy.komodo:komodo-config-dotenv:$komodo"
    const val logback = "ch.qos.logback:logback-classic:1.3.0-alpha4"

    private const val micrometerVersion = "1.3.5"
    const val micrometer = "io.micrometer:micrometer-core:$micrometerVersion"
    const val micrometerPrometheus = "io.micrometer:micrometer-registry-prometheus:$micrometerVersion"

    private const val junit = "5.5.2"
    const val junitApi = "org.junit.jupiter:junit-jupiter-api:$junit"
    const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:$junit"

    const val mockk = "io.mockk:mockk:1.9.3"
}
