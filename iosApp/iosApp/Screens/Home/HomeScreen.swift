import SwiftUI

struct HomeScreen: View {
  var logout: () -> Void
  
  var body: some View {
    VStack {
      Text("Home Screen")
      
      Button("Logout", action: logout)
    }
    .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .center)
    .background(Color.white)
  }
}

struct HomeScreen_Previews: PreviewProvider {
  static var previews: some View {
    HomeScreen(logout: {})
  }
}
