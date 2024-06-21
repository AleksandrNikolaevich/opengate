import SwiftUI

struct Transition<Content: View>: View {
  enum Direction : CGFloat {
    case left = 1
    case right = -1    
  }
  
  var showing: Bool
  var direction: Direction
  var content: () -> Content
  
  @State private var isShowing: Bool
  @State private var mount: Bool
  
  init(showing: Bool, direction: Direction, @ViewBuilder content: @escaping () -> Content) {
    self.showing = showing
    self.content = content
    self.direction = direction
    self.isShowing = showing
    self.mount = showing
  }
  
  var body: some View {
    VStack{
      if (mount) {
        content()
      }
    }
      .opacity(isShowing ? 1 : 0)
      .offset(x: isShowing ? 0.0 : 100.0 * direction.rawValue)
      .onChange(of: showing) { newValue in
        if (newValue) {
          mount = true
        }
        withAnimation(.linear(duration: 0.2)) {
          isShowing = newValue
          
          if (!newValue) {
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
              mount = false
            }
          }
        }
      }
  }
}
