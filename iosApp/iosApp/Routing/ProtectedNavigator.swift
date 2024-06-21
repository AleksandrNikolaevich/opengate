import SwiftUI

struct ProtectedNavigator: View {
  @State private var isProfileOpened = false
  
  var body: some View {
    NavigationView {
      HomeScreen(onOpenProfile: {
        isProfileOpened = true
      }).sheet(isPresented: $isProfileOpened) {
        NavigationView {
          ProfileScreen(onClose: { isProfileOpened = false })
        }
      }
    }
    .navigationViewStyle(.stack)
  }
}

#Preview {
  ProtectedNavigator()
}
