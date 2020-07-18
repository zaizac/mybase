//
//  CorneredButton.swift
//  MyCare
//
//  Created by Cookie on 4/15/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation
import UIKit

class CorneredButton: UIButton {
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        translatesAutoresizingMaskIntoConstraints = false
        self.initialConfig()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.initialConfig()
    }
    
    private func initialConfig() {
        layer.cornerRadius = 5
        layer.masksToBounds = true
    }
}

class CircledButton: UIButton {
    override func layoutSubviews() {
        super.layoutSubviews()
        layer.cornerRadius = bounds.height / 2
    }
}
