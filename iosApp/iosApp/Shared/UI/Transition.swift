import SwiftUI

struct Transition<Content>: View where Content: View {
  enum Direction : CGFloat {
    case left = 1
    case right = -1    
  }
  
  var showing: Bool
  var direction: Direction
  var content: () -> Content
  
  @State private var isShowing: Bool
  
  init(showing: Bool, direction: Direction,  content: @escaping () -> Content) {
    self.showing = showing
    self.content = content
    self.direction = direction
    self.isShowing = showing
  }
  
  var body: some View {
    content()
      .opacity(isShowing ? 1 : 0)
      .offset(x: isShowing ? 0.0 : 100.0 * direction.rawValue)
      .onChange(of: showing) { _ in
        withAnimation {
          isShowing = !showing
        }
      }
  }
}
