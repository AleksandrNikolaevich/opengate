import SwiftUI

class VCHookViewController: UIViewController {
  var onViewWillAppear: ((UIViewController) -> Void)?
  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(animated)
    onViewWillAppear?(self)
  }
}

struct VCHookView: UIViewControllerRepresentable {
  typealias UIViewControllerType = VCHookViewController
  let onViewWillAppear: ((UIViewController) -> Void)
  func makeUIViewController(context: Context) -> VCHookViewController {
    let vc = VCHookViewController()
    vc.onViewWillAppear = onViewWillAppear
    return vc
  }
  func updateUIViewController(_ uiViewController: VCHookViewController, context: Context) {
  }
}

struct VCHookViewModifier<V>: ViewModifier where V : View {
  let customView: V
  
  @State private var lastHostingView: UIView!
  
  func body(content: Content) -> some View {
    content
      .background {
        VCHookView { vc in
          guard let bar =  vc.navigationController?.navigationBar else { return }
          let hosting = UIHostingController(rootView: customView)
          
          guard
            let hostingView = hosting.view,
            let barLargeTitle = bar.subviews.first(where: { String(describing: type(of: $0)) == "_UINavigationBarLargeTitleView" }),
            let label = barLargeTitle.subviews.first(where: { $0 is UILabel }) as? UILabel
          else { return }
          
          label.isHidden = true
          
          barLargeTitle.addSubview(hostingView)
          hostingView.backgroundColor = .clear
          
          lastHostingView?.removeFromSuperview()
          lastHostingView = hostingView
          
          hostingView.translatesAutoresizingMaskIntoConstraints = false
          NSLayoutConstraint.activate([
            hostingView.leadingAnchor.constraint(equalTo: barLargeTitle.leadingAnchor, constant: 16),
            hostingView.bottomAnchor.constraint(equalTo: barLargeTitle.bottomAnchor, constant: -4)
          ])
        }
      }
  }
}

extension View {
  func navigationTitle<V>(_ customView: V) -> some View where V : View {
    modifier(VCHookViewModifier(customView: customView))
  }
}
