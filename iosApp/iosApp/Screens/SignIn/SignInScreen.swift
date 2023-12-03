import SwiftUI

struct SignInScreen: View {
  @State private var login: String = ""
  @State private var password: String = ""
  
  private var isSubmitting = false
  private var isSubmitAvailable = false
  
  var body: some View {
    VStack {
      Text("OpenGate").font(.largeTitle)
      
      VStack(spacing: 0) {
        TextField("Phone", text: $login)
          .keyboardType(.phonePad)
          .padding(.vertical, 8)
          .padding(.horizontal, 16)
        
        Divider()
          .frame(height: 1)
          .overlay(Color.secondary)
        
        SecureField("Password", text: $password)
          .padding(.vertical, 8)
          .padding(.horizontal, 16)
      }
      .addBorder(Color.secondary, width: 1, cornerRadius: 12)
      
      submitButton
        .frame(height: 60)
        .disabled(!isSubmitAvailable)
      
    }
      .padding(48)
  }
  
  @ViewBuilder
  private var submitButton: some View {
    if (isSubmitting) {
      ProgressView()
    } else {
      Button("Sign In", action: {})
    }
  }
}

struct SignInScreen_Previews: PreviewProvider {
  static var previews: some View {
    SignInScreen()
  }
}
