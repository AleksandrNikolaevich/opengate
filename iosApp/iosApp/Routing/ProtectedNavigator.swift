import SwiftUI

struct SelectedGate: Identifiable {
  var id: String
}

struct ProtectedNavigator: View {
  @State private var isProfileOpened = false
  @State private var selectedGate: SelectedGate?
  
  var body: some View {
    NavigationView {
      HomeScreen(
        onOpenProfile: {
          isProfileOpened = true
        },
        onOpenDetails: { gateId in
          selectedGate = SelectedGate(id: gateId)
        }
      )
        .sheet(isPresented: $isProfileOpened) {
          NavigationView {
            ProfileScreen(onClose: { isProfileOpened = false })
          }
        }
        .sheet(
          item: $selectedGate,
          onDismiss: {
            selectedGate = nil
          }
        ) { item in
          NavigationView {
            GateScreen(
              gateId: item.id,
              onClose: {
                selectedGate = nil
              }
            )
          }
        }
    }
    .navigationViewStyle(.stack)
  }
}

#Preview {
  ProtectedNavigator()
}
