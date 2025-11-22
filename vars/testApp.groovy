def call(Map config = [:]) {
    def appName = config.appName ?: "unknown-app"
    def testCmd = config.testCmd ?: "npm test"

    echo "[TEST] Running tests for ${appName}"

    try {
        stage("Test") {
            sh testCmd
        }
        echo "[TEST] Test successful for ${appName}"
    } catch (err) {
        echo "[TEST] Test failed: ${err}"
        org.gamechanger.utils.SlackNotifier.notifyFail("Tests failed for ${appName}")
        error "Test step failed"
    }
}
