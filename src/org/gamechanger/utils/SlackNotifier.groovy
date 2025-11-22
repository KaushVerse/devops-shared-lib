package org.gamechanger.utils

import groovy.json.JsonOutput

class SlackNotifier {

    static def notifySuccess(String message) {
        sendSlack(message, "good")
    }

    static def notifyFail(String message) {
        sendSlack(message, "danger")
    }

    private static def sendSlack(String message, String color) {
        def webhook = getWebhook()

        if (!webhook) {
            println "[SLACK] No webhook configured"
            return
        }

        def payload = JsonOutput.toJson([
            attachments: [[
                color: color,
                text : message
            ]]
        ])

        try {
            println "[SLACK] Sending message..."
            def url = new URL(webhook)
            def conn = url.openConnection()
            conn.setRequestMethod("POST")
            conn.doOutput = true
            conn.outputStream.write(payload.getBytes("UTF-8"))
            println "[SLACK] Message sent"
        } catch (e) {
            println "[SLACK] Failed to send: ${e}"
        }
    }

    private static def getWebhook() {
        return System.getenv("SLACK_WEBHOOK_URL")
    }
}
