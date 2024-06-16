import SwiftUI

struct DataRow: View {
  var title: String
  var subtitle: String
  var icon: String
  var onPress: () -> Void
  
  var body: some View {
    Button(action: onPress) {
      HStack(alignment: .top) {
        Image(systemName: icon)
          .resizable()
          .frame(width: 30, height: 30)
          .padding(.top, 6)
          .foregroundColor(.primary)
        
        VStack {
          Text(title)
            .font(.title3)
            .foregroundColor(.primary)
            .frame(maxWidth: .infinity, alignment: .leading)
          
          Text(subtitle)
            .font(.caption)
            .foregroundColor(.secondary)
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        
      }
      .padding(EdgeInsets(top: 8, leading: 16, bottom: 8, trailing: 16))
      .frame(maxWidth: .infinity, alignment: .leading)
    }
  }
}

#Preview {
  DataRow(
    title: "Logout",
    subtitle: "You will need to login again",
    icon: "rectangle.portrait.and.arrow.right",
    onPress: {}
  )
}
