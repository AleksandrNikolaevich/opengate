import SwiftUI
import MultiPlatformLibrary

struct HomeScreen: View {
  @ObservedObject private var viewModel = DI().homeViewModel()
  
  private var state: GatesGatesStoreState { viewModel.state(\.state) }
  
  @State var isRefreshing = false
  
  var onOpenProfile: () -> Void
  
  var body: some View {
    let gatesCount: Int = state.gates?.count ?? 0
    let showLoader = gatesCount == 0 && state.isLoading && !isRefreshing
    
    VStack {
      if (showLoader) {
        ProgressView()
          .controlSize(.large)
      } else {
        List(0..<gatesCount, id: \.self) { index in
          renderListItem(gate: state.gates![index])
            .listRowInsets(EdgeInsets(top: 6, leading: .zero, bottom: 6, trailing: .zero))
        }
        .refreshable {
          isRefreshing = true
          viewModel.forceReload()
          await refreshing()
          isRefreshing = false
        }
      }
     
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
    .background(Color(UIColor.systemGroupedBackground))
    .onAppear {
      viewModel.run()
    }
  }
  
  @ViewBuilder
  private func renderListItem(gate: GatesGate) -> some View {
    let gateState = state.openStates.first{ $0.id == gate.id }?.state ?? .pending
    
    ListItem(
      overlineContent: gate.isAvailable ? "ONLINE" : "OFFLINE",
      headlineContent: gate.name,
      trailingContent: {
        if (gateState == .opening) {
          ProgressView()
            .controlSize(.regular)
        }
        
        if (gateState == .opened) {
          Image(systemName: "checkmark.circle")
            .resizable()
            .frame(width: 30, height: 30)
            .padding(.top, 6)
            .foregroundColor(.accentColor)
        }
      }
    ) {
      viewModel.openGate(gate: gate)
    }
  }
  
  private func refreshing() async {
    // Delay of 1 seconds (1 second = 1_000_000_000 nanoseconds)
    try? await Task.sleep(nanoseconds: 1_000_000_000)
    
    if (state.isLoading) {
      return await refreshing()
    }
  }
}

struct HomeScreen_Previews: PreviewProvider {
  static var previews: some View {
    HomeScreen(onOpenProfile: {})
  }
}
