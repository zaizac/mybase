//
//  InitialViewController.swift
//  MyCare
//
//  Created by Cookie on 4/15/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import UIKit
import Localize_Swift

class InitialViewController: UIViewController {
    
    let loginSegue = "directToLogin"
    let homeSegue = "directHome"
    let langugageSegue = "directToLanguage"
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.25, execute: {
            if !AppData.isLogin {
                guard let vc = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: String(describing: LoginViewController.self)) as? LoginViewController else {
                    return
                }
                vc.modalPresentationStyle = .fullScreen
                self.present(vc, animated: true, completion: nil)
            } else {
                if AppData.isAdmin {
                    guard let vc = UIStoryboard(name: "Admin", bundle: nil).instantiateViewController(withIdentifier: String(describing: AdminHomeViewController.self)) as? AdminHomeViewController else {
                        return
                    }
                    vc.modalPresentationStyle = .fullScreen
                    self.present(vc, animated: true, completion: nil)
                } else {
                    guard let vc = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: String(describing: HomeViewController.self)) as? HomeViewController else {
                        return
                    }
                    vc.modalPresentationStyle = .fullScreen
                    self.present(vc, animated: true, completion: nil)
                }
            }
        })
    }
}
