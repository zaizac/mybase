//
//  HomeLanguageViewController.swift
//  MyCare
//
//  Created by Cookie on 4/15/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import UIKit
import Localize_Swift

class HomeLanguageViewController: UIViewController {
    
    @IBOutlet private weak var chooseLanguageLbl: UILabel!
    @IBOutlet private weak var malayBtn: CorneredButton!
    @IBOutlet private weak var englishBtn: CorneredButton!
    @IBOutlet private weak var bengaliBtn: CorneredButton!
    @IBOutlet private weak var nepaliBtn: CorneredButton!
    
    var isFromHome: Bool = false
    var onSelectLanguage: (() -> Void)?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialConfig()
    }
    
    private func initialConfig() {
        
        title = Constants.language.localized()
        chooseLanguageLbl.text = Constants.chooseLanguage.localized()
        
        if UIApplication.TARGET_NAME == "PAGOH" {
            self.malayBtn.isHidden = false
            self.bengaliBtn.isHidden = true
        } else {
            self.malayBtn.isHidden = true
            self.bengaliBtn.isHidden = false
        }
        
        self.malayBtn.backgroundColor = UIColor.init(named: "standardBtnColor")
        self.bengaliBtn.backgroundColor = UIColor.init(named: "standardBtnColor")
        self.englishBtn.backgroundColor = UIColor.init(named: "standardBtnColor")
        
        guard let _ = self.navigationController else { return }
        
        let backBtn = UIBarButtonItem(title: Constants.close.localized(), style: .plain, target: self, action: #selector(backBtnHandler(_:)))
        navigationItem.leftBarButtonItem = backBtn
    }
    
    //MARK: - Handlers
    
    @objc
    private func backBtnHandler(_ sender: UIBarButtonItem) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction
    private func languageAction(_ sender: UIButton) {
        
        switch sender {
        case malayBtn:
            Localize.setCurrentLanguage("ms")
        case englishBtn:
            Localize.setCurrentLanguage("en")
        case bengaliBtn:
            Localize.setCurrentLanguage("bn")
        default:
            print("Nothingness")
        }
        
        self.onSelectLanguage?()
        self.dismiss(animated: true, completion: nil)
    }
}
