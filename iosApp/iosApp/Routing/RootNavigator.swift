import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI

struct RootNavigator: View {
  @ObservedObject private var viewModel = DI().rootNavigatorViewModel()
  
  var body: some View {
    let appState = viewModel.state(\.appState)
    
    ZStack {
      Transition(showing: appState == .pending, direction: .right) {
        SplashScreen()
      }
      
      Transition(showing: appState == .authenticated, direction: .left) {
        ProtectedNavigator()
      }
      
      Transition(showing: appState == .needAuth, direction:  appState == .authenticated ? .right : .left) {
        SignInScreen()
      }
    }
    .background(Color(UIColor.systemGroupedBackground))
  }
}
