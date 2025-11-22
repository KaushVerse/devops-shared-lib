def call(Map config = [:]) {
    def appName = config.appName ?: "unknown-app"
    def deployCmd = config.deployCmd ?: "echo 'Deploy placeholder'"

    echo "[DEPLOY] Deploying ${appName}"

    try {
        stage("Deploy") {
            sh deployCmd
        }
        echo "[DEPLOY] Deployment successful for ${appName}"
        org.gamechanger.utils.SlackNotifier.notifySuccess("${appName} deployed successfully")
    } catch (err) {
        echo "[DEPLOY] Deployment failed: ${err}"
        org.gamechanger.utils.SlackNotifier.notifyFail("Deployment failed for ${appName}")
        error "Deployment step failed"
    }
}
