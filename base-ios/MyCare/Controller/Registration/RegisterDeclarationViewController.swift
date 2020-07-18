//
//  RegisterDeclarationViewController.swift
//  MyCare
//
//  Created by Cookie on 4/15/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import UIKit

class RegisterDeclarationViewController: UIViewController, Alert {
    
    @IBOutlet private weak var backButton: CorneredButton!
    @IBOutlet private weak var agreeBtn: CorneredButton!
    @IBOutlet private weak var checkBoxBtn: UIButton!
    @IBOutlet private weak var declareTitleLbl: UILabel!
    @IBOutlet private weak var declareLbl: UILabel!
    @IBOutlet private weak var contentHolder: UIView!
    
    private var isAgree: Bool = false
    var onAgree: ((Bool) -> Void)?
    var onDisagree: (() -> Void)?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialConfig()
        
        self.setText()
    }
    
    private func initialConfig() {
        
        //Change this backgroundColor to white if they want to make same as android.
        //And the text color need to change to black
        self.view.backgroundColor = UIColor(white: 0, alpha: 0.75)
        
        self.checkBoxBtn.backgroundColor = .clear
        self.checkBoxBtn.tintColor = .darkGray
        self.checkBoxBtn.addCornerRadius(3)
        self.checkBoxBtn.layer.borderColor = UIColor.darkGray.cgColor
        self.checkBoxBtn.layer.borderWidth = 1
        
        self.declareTitleLbl.textColor = UIColor.white
        self.backButton.setTitleColor(UIColor.white, for: .normal)
        self.agreeBtn.backgroundColor = UIColor.init(named: "standardBtnColor")
        
        self.contentHolder.layer.cornerRadius = 10
        self.contentHolder.layer.masksToBounds = true
        self.contentHolder.layer.borderWidth = 3
        self.contentHolder.layer.borderColor = UIColor.systemBlue.cgColor
        
        if UIApplication.TARGET_NAME == "PAGOH" {
            self.declareLbl.text = Constants.declarationText_PAGOH.localized()
        } else if UIApplication.TARGET_NAME == "BH" {
            self.declareLbl.text = Constants.declarationText_BH.localized()
        }
    }
    
    private func setText() {
        self.agreeBtn.setTitle(Constants.agree.localized().uppercased(), for: .normal)
        self.backButton.setTitle(Constants.back.localized().uppercased(), for: .normal)
        self.declareTitleLbl.text = Constants.declaration.localized()
    }
    
    //MARK: - IBAction
    @IBAction
    private func disagreeAction(_ sender: UIButton) {
        self.onDisagree?()
    }
    
    @IBAction
    private func agreeAction(_ sender: UIButton) {
        if self.isAgree {
            self.onAgree?(true)
        } else {
            self.onAgree?(false)
        }
    }
    
    @IBAction
    private func checkBoxButton(_ sender: UIButton) {
        self.isAgree = !self.isAgree
        self.checkBoxBtn.setImage(self.isAgree ? UIImage(named: "confirmCheckmark") : nil, for: .normal)
        
        self.checkBoxBtn.transform = CGAffineTransform.init(scaleX: 0.75, y: 0.75)
        UIView.animate(withDuration: 0.3) {
            self.checkBoxBtn.transform = .identity
        }
    }
}
