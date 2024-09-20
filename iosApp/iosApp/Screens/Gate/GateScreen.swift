import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI

struct GateScreen: View {
  var gateId: String? = nil
  var onClose: () -> Void
  
  @State private var showingShortnameDialog = false
  @ObservedObject private var viewModel = DI().gateViewModel()
  
  private var state: GateViewModel.State { viewModel.state(\.state) }
  
  var body: some View {
    VStack{
      DataRow(
        title: state.shortName ?? "",
        subtitle: "Shortname",
        icon: "info.circle",
        onPress: {
          showingShortnameDialog.toggle()
        }
      )
      
      DataRow(
        title: state.gate?.isAvailable == true ? "Online" : "Offline",
        subtitle: "Status",
        icon: state.gate?.isAvailable == true ? "cloud" : "icloud.slash",
        onPress: {}
      )
      
      openGateButton
    }
    .frame(maxHeight: .infinity, alignment: .top)
    .toolbar {
      ToolbarItem(placement: .principal) {
        Text(state.gate?.name ?? "Gate")
      }
      
      ToolbarItem(placement: .topBarTrailing) {
        Button(action: onClose) {
          Image(systemName: "xmark.circle.fill")
            .foregroundColor(.secondary)
        }
      }
    }
    .onAppear {
      guard let id = gateId else { return }
      viewModel.run(gateId: id)
    }
    .alert("Change the shortname", isPresented: $showingShortnameDialog, actions: {
      ChangeShortnameDialog(
        shortName: viewModel.binding(\.shortNameFieldValue),
        onCancel: {
          showingShortnameDialog.toggle()
        },
        onSubmit: viewModel.commitShortName)
    }, message: {})
  }
  
  @ViewBuilder
  private var openGateButton: some View {
    let gateState = state.gate?.state
    
    if (gateState == .opening) {
      ProgressView()
    } else if (gateState == .opened) {
      Image(systemName: "checkmark.circle")
        .resizable()
        .frame(width: 30, height: 30)
        .foregroundColor(.accentColor)
    } else {
      Button("Open", action: viewModel.openGate)
    }
  }
}

#Preview {
  GateScreen(onClose: {})
}
