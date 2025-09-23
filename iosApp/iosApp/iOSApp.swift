import SwiftUI
import composeApp

@main
struct iOSApp: App {
    init() {
        KoinIosHelper().doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}