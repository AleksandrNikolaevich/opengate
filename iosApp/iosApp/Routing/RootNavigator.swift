import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI

struct RootNavigator: View {
  
  @ObservedObject private var viewModel = DI().rootNavigatorViewModel()
  
  private var state: AuthStoreState { viewModel.state(\.auth) }
  
  var body: some View {
    ZStack {
      Transition(showing: state.isLoggedIn, direction: .left) {
        HomeScreen(logout: viewModel.logout)
      }
      
      Transition(showing: !state.isLoggedIn, direction: .right) {
        SignInScreen()
      }
    }
  }
}
