import SwiftUI

struct ListItem<TrailingContent: View>: View {
  var overlineContent: String
  var headlineContent: String
  @ViewBuilder var trailingContent: () -> TrailingContent
  var onPress: () -> Void
  
  var body: some View {
    Button(action: onPress) {
      HStack(alignment: .center) {
        VStack {
          Text(overlineContent)
            .font(.caption)
            .foregroundColor(.secondary)
            .frame(maxWidth: .infinity, alignment: .leading)
          
          Text(headlineContent)
            .font(.title3)
            .foregroundColor(.primary)
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        
        trailingContent()
      }
      .padding(EdgeInsets(top: 8, leading: 16, bottom: 8, trailing: 16))
      .frame(maxWidth: .infinity, alignment: .leading)
    }
  }
}

#Preview {
  ListItem(
    overlineContent: "Logout",
    headlineContent: "You will need to login again",
    trailingContent: {
      Image(systemName: "rectangle.portrait.and.arrow.right")
        .resizable()
        .frame(width: 30, height: 30)
        .padding(.top, 6)
        .foregroundColor(.primary)
    },
    onPress: {}
  )
}
