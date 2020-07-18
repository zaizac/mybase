//
//  CircledImageView.swift
//  PagohCare
//
//  Created by Cookie on 4/28/20.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import Foundation
import UIKit

class CircledImageView: UIImageView {
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.initialConfig()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.initialConfig()
    }
    
    private func initialConfig() {
        contentMode = .scaleAspectFill
        layer.masksToBounds = true
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        layer.cornerRadius = bounds.width / 2
    }
}
