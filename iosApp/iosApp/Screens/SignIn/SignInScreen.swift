import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI

struct SignInScreen: View {
  @ObservedObject private var viewModel = DI().signInViewModel()
  
  private var state: AuthStoreState { viewModel.state(\.state) }
  
  var body: some View {
    let hasError = !(state.error ?? "").isEmpty
    let borderColor: Color = hasError ? .red : .secondary
    
    VStack {
      Text("OpenGate").font(.largeTitle)
      
      VStack(spacing: 0) {
        TextField("Phone", text: viewModel.binding(\.login))
          .keyboardType(.phonePad)
          .padding(.vertical, 8)
          .padding(.horizontal, 16)
        
        Divider()
          .frame(height: 1)
          .overlay(borderColor)
        
        SecureField("Password", text: viewModel.binding(\.password))
          .padding(.vertical, 8)
          .padding(.horizontal, 16)
      }
      .addBorder(borderColor, width: 1, cornerRadius: 12)
      
      if (hasError) {
        Text(state.error ?? "")
          .foregroundColor(.red)
          .frame(maxWidth: .infinity, alignment: .leading)
      }
      
      submitButton
        .frame(height: 60)
        .disabled(false)
      
    }
      .padding(48)
  }
  
  @ViewBuilder
  private var submitButton: some View {
    if (state.isLoading) {
      ProgressView()
    } else {
      Button("Sign In", action: viewModel.signIn)
    }
  }
}

struct SignInScreen_Previews: PreviewProvider {
  static var previews: some View {
    SignInScreen()
  }
}
