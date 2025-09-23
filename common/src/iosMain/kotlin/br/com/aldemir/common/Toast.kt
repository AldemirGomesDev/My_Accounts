package br.com.aldemir.common

import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.NSEC_PER_SEC
import platform.darwin.dispatch_after
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_time

actual fun showMessage(message: String) {
    val alert = UIAlertController.alertControllerWithTitle(
        title = null,
        message = message,
        preferredStyle = UIAlertControllerStyleAlert
    )
    val rootController = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootController?.presentViewController(alert, animated = true, completion = null)

    // Fechar automaticamente depois de 2 segundos
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 2 * NSEC_PER_SEC.toLong()), dispatch_get_main_queue()) {
        rootController?.dismissViewControllerAnimated(true, completion = null)
    }
}