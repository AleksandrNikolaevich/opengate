import SwiftUI

struct HomeScreen: View {
  var onOpenProfile: () -> Void
  
  var body: some View {
    VStack {
      Text("Home Screen")
    }
    .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .center)
    .toolbar {
      ToolbarItem(placement: .topBarTrailing) {
        Button {
          onOpenProfile()
        } label: {
          Image(systemName: "person.circle")
        }
      }
    }
    .navigationTitle("OpenGate")
  }
}

struct HomeScreen_Previews: PreviewProvider {
  static var previews: some View {
    HomeScreen(onOpenProfile: {})
  }
}
