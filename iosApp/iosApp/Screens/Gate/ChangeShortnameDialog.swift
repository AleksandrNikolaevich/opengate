import SwiftUI

struct ChangeShortnameDialog: View {
  @Binding var shortName: String
  
  let onCancel: () -> Void
  let onSubmit: () -> Void
  
  @FocusState private var focused: Bool
  
  var body: some View {
    TextField("Shortname", text: $shortName)
      .focused($focused)
    
    
    Button("Save", action: onSubmit)
    Button("Cancel", role: .cancel, action: onCancel)
  }
}

#Preview {
  ChangeShortnameDialog(shortName: Binding(get: { "" }, set: { _ in }), onCancel: {}, onSubmit: {})
}
