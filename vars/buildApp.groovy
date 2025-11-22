def call(Map config = [:]) {
    def appName = config.appName ?: "unknown-app"
    def buildCmd = config.buildCmd ?: "npm run build"

    echo "[BUILD] Starting build for ${appName}"

    try {
        stage("Build") {
            sh buildCmd
        }
        echo "[BUILD] Build completed for ${appName}"
    } catch (err) {
        echo "[BUILD] Build failed: ${err}"
        org.gamechanger.utils.SlackNotifier.notifyFail("Build failed for ${appName}")
        error "Build step failed"
    }
}
