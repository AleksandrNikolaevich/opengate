import SwiftUI
import MultiPlatformLibrary

struct ProfileScreen: View {
  @ObservedObject private var viewModel = DI().profileViewModel()
  
  @State var logoutAlertShowing = false
  
  var onClose: () -> Void
  
  var body: some View {
    VStack{
      DataRow(
        title: "Logout",
        subtitle: "You will need to login again",
        icon: "rectangle.portrait.and.arrow.right",
        onPress: {
          logoutAlertShowing = true
          //          onClose()
          //          viewModel.logout()
        }
      )
    }
    .frame(maxHeight: .infinity, alignment: .top)
    .toolbar {
      ToolbarItem(placement: .principal) {
        Text("Profile")
      }
      
      ToolbarItem(placement: .topBarTrailing) {
        Button(action: onClose) {
          Image(systemName: "xmark.circle.fill")
            .foregroundColor(.secondary)
        }
      }
    }
    .alert(isPresented: $logoutAlertShowing) {
      Alert(
        title: Text("Are you shure?"),
        primaryButton: .cancel(
          Text("Cancel"),
          action: { }
        ),
        secondaryButton: .destructive(
          Text("Logout"),
          action: {
            onClose()
            viewModel.logout()
          }
        )
      )
    }
  }
}

#Preview {
  ProfileScreen(onClose: {})
}
