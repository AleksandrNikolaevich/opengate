import SwiftUI
import MultiPlatformLibrary 

@main
struct iOSApp: App {
  init() {
    KoinHelper().doInit()
  }
  
	var body: some Scene {
		WindowGroup {
			SignInScreen()
		}
	}
}
