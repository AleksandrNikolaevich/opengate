import mokoMvvmFlowSwiftUI
import MultiPlatformLibrary

extension ObservableObject where Self: ViewModel {
  func state<R>(_ flowKey: KeyPath<Self, CStateFlow<R>>) -> R where R : KotlinBase {
      return state(
          flowKey,
          equals: { $0 == $1 },
          mapper: { $0 }
      )
  }
}
