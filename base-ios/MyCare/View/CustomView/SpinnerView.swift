//
//  SpinnerView.swift
//  BSTBaseFramework
//
//  Created by Cookie on 4/6/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation
import UIKit

public class SpinnerView: UIView {
    
    @IBOutlet private weak var contentView: UIView!
    private var spinnerLabel: UILabel = {
        let lbl = UILabel()
        lbl.translatesAutoresizingMaskIntoConstraints = false
        lbl.textColor = .darkGray
        lbl.textAlignment = .center
        lbl.adjustsFontSizeToFitWidth = true
        lbl.minimumScaleFactor = 0.4
        lbl.text = "Loading"
        return lbl
    }()
    
    private var blurStyle: UIBlurEffect.Style = .light
    private var spinner = UIActivityIndicatorView(style: .whiteLarge)
    
    init(_ blurStyle: UIBlurEffect.Style, frame: CGRect) {
        self.blurStyle = blurStyle
        super.init(frame: frame)
        self.initialSetup()
    }
    
    // MARK: - Setup View
    override init(frame: CGRect) {
        // For use in code
        super.init(frame: frame)
        self.initialSetup()
    }
    
    required init?(coder aDecoder: NSCoder) {
        // For use in Interface Builder
        super.init(coder: aDecoder)
        self.initialSetup()
    }
    
    private func initialSetup() {
        
        let bundle = Bundle(for: type(of: self))
        let nib = UINib(nibName: "SpinnerView", bundle: bundle)
        self.contentView = nib.instantiate(withOwner: self, options: nil).first as? UIView
        
        contentView.fixInView(self)
        contentView.backgroundColor = .clear
        
        let blurEffect = UIBlurEffect(style: self.blurStyle)
        let blurredEffectView = UIVisualEffectView(effect: blurEffect)
        blurredEffectView.frame = contentView.bounds
        contentView.addSubview(blurredEffectView)
        
        spinner.translatesAutoresizingMaskIntoConstraints = false
        spinner.color = self.blurStyle == .light ? .darkGray : .white
        spinner.startAnimating()
        self.spinnerLabel.textColor = self.blurStyle == .light ? .darkGray : .white
        
        let vibrancyEffect = UIVibrancyEffect(blurEffect: blurEffect)
        let vibrancyEffectView = UIVisualEffectView(effect: vibrancyEffect)
        vibrancyEffectView.frame = contentView.bounds
        
        blurredEffectView.contentView.addSubview(vibrancyEffectView)
        blurredEffectView.contentView.addSubview(spinner)
        blurredEffectView.contentView.addSubview(spinnerLabel)
        
        spinner.centerXAnchor.constraint(equalTo: contentView.centerXAnchor).isActive = true
        spinner.centerYAnchor.constraint(equalTo: contentView.centerYAnchor).isActive = true
        
        spinnerLabel.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 4).isActive = true
        spinnerLabel.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -4).isActive = true
        spinnerLabel.centerXAnchor.constraint(equalTo: contentView.centerXAnchor).isActive = true
        spinnerLabel.bottomAnchor.constraint(equalTo: contentView.bottomAnchor, constant: -10).isActive = true
    }
    
    // Allow view to control itself
    public override func layoutSubviews() {
        // Rounded corners
        self.layoutIfNeeded()
        self.contentView.layer.masksToBounds = true
        self.contentView.clipsToBounds = true
        self.contentView.layer.cornerRadius = 10
    }
    
    // MARK: - Remove View
    public override func didMoveToSuperview() {
        // Fade in when added to superview
        // Then add a timer to remove the view
        self.contentView.transform = CGAffineTransform(scaleX: 0.5, y: 0.5)
        UIView.animate(withDuration: 0.15, animations: {
            self.contentView.alpha = 1.0
            self.contentView.transform = CGAffineTransform.identity
        }) { _ in }
    }
    
    func set(title text: String) {
        self.spinnerLabel.text = text
    }
    
    public func removeSelf() {
        // Animate removal of view
        UIView.animate(
            withDuration: 0.15,
            animations: {
                self.contentView.transform = CGAffineTransform(scaleX: 0.5, y: 0.5)
                self.contentView.alpha = 0.0
        }) { _ in
            self.removeFromSuperview()
        }
    }
}
