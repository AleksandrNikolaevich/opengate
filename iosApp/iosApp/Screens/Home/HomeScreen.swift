import SwiftUI

struct HomeScreen: View {
  var logout: () -> Void
  
  var body: some View {
    VStack {
      Text("Home Screen")
      
      Button("Logout", action: logout)
    }
  }
}

struct HomeScreen_Previews: PreviewProvider {
  static var previews: some View {
    HomeScreen(logout: {})
  }
}
