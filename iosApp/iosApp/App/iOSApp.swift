import SwiftUI
import MultiPlatformLibrary 

@main
struct iOSApp: App {
  init() {
    KoinHelper().doInit { _ in }
  }
  
	var body: some Scene {
		WindowGroup {
			RootNavigator()
		}
	}
}
