import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI

struct SignInScreen: View {
  @ObservedObject private var viewModel = DI().signInViewModel()
  
  private var state: AuthAuthStoreState { viewModel.state(\.state) }
  
  @FocusState private var focused: Bool
  
  var body: some View {
    let hasError = !(state.error ?? "").isEmpty
    let borderColor: Color = hasError ? .red : Color(UIColor.separator)
    
    VStack {
      Text("OpenGate").font(.largeTitle)
      
      VStack(spacing: 0) {
        TextField("Phone", text: viewModel.binding(\.login))
          .keyboardType(.phonePad)
          .padding(.vertical, 8)
          .padding(.horizontal, 16)
          .focused($focused)
        
        Divider()
          .frame(height: 1)
          .overlay(borderColor)
        
        SecureField("Password", text: viewModel.binding(\.password))
          .padding(.vertical, 8)
          .padding(.horizontal, 16)
      }
      .background(Color(UIColor.systemBackground))
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
      .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .center)
      .background(Color(UIColor.systemGroupedBackground))
      .onAppear {
        focused = true
      }
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
