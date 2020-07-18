//
//  HomeViewController.swift
//  MyCare
//
//  Created by Cookie on 4/15/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import UIKit
import SideMenu
import CryptoSwift

class HomeViewController: UIViewController, Alert {
    
    @IBOutlet private weak var welcomeLabel: UILabel!
    @IBOutlet private weak var usernameLabel: UILabel!
    @IBOutlet private weak var usernameSV: UIStackView!

    @IBOutlet private weak var receivedBtnHolder: CorneredShadowedView!
    @IBOutlet private weak var receivedBtn: UIButton!
    @IBOutlet private weak var verificationHolderView: UIView!
    @IBOutlet private weak var verificationLabel: UILabel!

    @IBOutlet private weak var buttonsHolderView: CorneredShadowedView!
    
    @IBOutlet private weak var profileLabel: UILabel!
    @IBOutlet private weak var updateLabel: UILabel!
    @IBOutlet private weak var notiLabel: UILabel!
    @IBOutlet private weak var languageLabel: UILabel!
    @IBOutlet private weak var logoutLabel: UILabel!
    @IBOutlet private weak var clickBtnLabel: UILabel!
    
    private var qrCodeViewController: ShowQRCodeViewController?
    private var currentWindow: UIWindow?
    
    private var foodTypeId: Int = 0
    private var foodTypeString: String = ""
    private var contactNumber: String = ""
    private var passportMykadNumber: String = ""
    private var username: String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialConfig()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.setText()
    }
    
    private func initialConfig() {
        self.username = AppData.fullname
        self.passportMykadNumber = AppData.passportNo
        self.contactNumber = AppData.contactNo
        
        self.usernameLabel.text = self.username
        
        self.buttonsHolderView.backgroundColor = .clear
        self.receivedBtnHolder.backgroundColor = .clear
        
        self.verificationHolderView.backgroundColor = UIColor(white: 1, alpha: 0.6)
        self.verificationHolderView.alpha = 0
        self.verificationHolderView.isHidden = true
        self.usernameSV.layoutIfNeeded()
    }
    
    private func setText() {
        self.welcomeLabel.text = Constants.welcome.localized()
        self.profileLabel.text = Constants.profile.localized()
        self.notiLabel.text = Constants.message.localized()
        self.languageLabel.text = Constants.language.localized()
        self.logoutLabel.text = Constants.logout.localized()
        self.clickBtnLabel.text = Constants.clickHere.localized()
        self.updateLabel.text = Constants.update.localized()
        self.verificationLabel.text = Constants.successfullyVerified.localized()
    }
    
    private func setupChildController() {
        guard let vc = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: String(describing: ShowQRCodeViewController.self)) as? ShowQRCodeViewController else { return }
        
        vc.qrCodeString = "\(AppData.fwProfId)"
        self.qrCodeViewController = vc
        
        self.qrCodeViewController?.onClosePopup = {
            self.removeQRCode()
        }
        
        self.showQRCode()
    }
    
    private func showQRCode() {
        guard let qrCodeVC = self.qrCodeViewController else { return }
        self.currentWindow = UIApplication.shared.keyWindow
        guard let cWindow = self.currentWindow else { return }
        
        cWindow.addSubview(qrCodeVC.view)
        qrCodeVC.view.translatesAutoresizingMaskIntoConstraints = false
        qrCodeVC.view.alpha = 0
        
        qrCodeVC.view.topAnchor.constraint(equalTo: cWindow.topAnchor).isActive = true
        qrCodeVC.view.bottomAnchor.constraint(equalTo: cWindow.bottomAnchor).isActive = true
        qrCodeVC.view.leadingAnchor.constraint(equalTo: cWindow.leadingAnchor).isActive = true
        qrCodeVC.view.trailingAnchor.constraint(equalTo: cWindow.trailingAnchor).isActive = true
        qrCodeVC.willMove(toParent: cWindow.rootViewController)
        cWindow.rootViewController?.addChild(qrCodeVC)
        qrCodeVC.didMove(toParent: cWindow.rootViewController)
        
        UIView.animate(withDuration: 0.3) {
            qrCodeVC.view.alpha = 1
        }
        
        print("ADD")
    }
    
    private func removeQRCode() {
        guard let qrCodeViewController = self.qrCodeViewController else { return }
        guard let cWindow = self.currentWindow else { return }
        
        UIView.animate(withDuration: 0.3, animations: {
            qrCodeViewController.view.alpha = 0
        }) { (_) in
            if qrCodeViewController.view.isDescendant(of: cWindow.rootViewController!.view) {
                qrCodeViewController.view.removeFromSuperview()
            }
        }
        
        print("REMOVE")
    }
    
    //MARK: - IBAction
    
    @IBAction
    private func moreAction(_ sender: UIButton) {
        let alertSheet = UIAlertController(title: nil, message: nil, preferredStyle: .actionSheet)
        
        let profileAction = UIAlertAction(title: Constants.profileInfo.localized(), style: .default) { (action) in
            guard let langVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: String(describing: ViewProfileViewController.self)) as? ViewProfileViewController else { return }
            
            let navController = UINavigationController(rootViewController: langVC)
            
            self.present(navController, animated: true)
        }
        
        let langAction = UIAlertAction(title: Constants.language.localized(), style: .default) { (action) in
            guard let langVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: String(describing: HomeLanguageViewController.self)) as? HomeLanguageViewController else { return }
            langVC.isFromHome = true
            langVC.onSelectLanguage = {
                self.setText()
            }
            
            let navController = UINavigationController(rootViewController: langVC)
            
            self.present(navController, animated: true)
        }
        
        let logoutAction = UIAlertAction(title: Constants.logout.localized(), style: .default) { [weak self] (action) in
            
            self?.confirmationAlert(withTitle: Constants.logout.localized(), message: Constants.sureWant2Logout.localized()) {
                self?.logout()
            }
        }
        
        let cancelAction = UIAlertAction(title: Constants.cancel.localized(), style: .cancel)
        
        alertSheet.addAction(profileAction)
        alertSheet.addAction(langAction)
        alertSheet.addAction(logoutAction)
        alertSheet.addAction(cancelAction)
        
        self.present(alertSheet, animated: true)
    }
    
//    @IBAction
//    private func foodTypeAction(_ sender: UIButton) {
//        self.showFoodList(forBtn: sender)
//    }
    
    @IBAction
    private func receivedAction(_ sender: UIButton) {
        self.setupChildController()
        return
    }
    
    @IBAction
    private func buttonsAction(_ sender: UIButton) {
        
        switch sender.tag {
        case 1:
            guard let profileVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: String(describing: ViewProfileViewController.self)) as? ViewProfileViewController else { return }
            
            let navController = UINavigationController(rootViewController: profileVC)
            
            self.present(navController, animated: true)
        case 2:
            guard let updateVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: String(describing: NewUpdateProfileViewController.self)) as? NewUpdateProfileViewController else { return }
            
            let navController = UINavigationController(rootViewController: updateVC)
            
            self.present(navController, animated: true)
        case 3:
            
            guard let sideMenuNavController = UIStoryboard(name: "SideMenu", bundle: nil).instantiateInitialViewController() as? SideMenuNavigationController else {
                return
            }
            
            self.present(sideMenuNavController, animated: true, completion: nil)
            
        case 4:
            guard let langVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: String(describing: HomeLanguageViewController.self)) as? HomeLanguageViewController else { return }
            langVC.isFromHome = true
            langVC.onSelectLanguage = {
                self.setText()
            }
            
            let navController = UINavigationController(rootViewController: langVC)
            
            self.present(navController, animated: true)
        case 5:
            self.confirmationAlert(withTitle: Constants.logout.localized(), message: Constants.sureWant2Logout.localized()) {
                self.logout()
            }
        default:
            print("NOTHINGNESS")
        }
    }
    
    //MARK: - Custom
    
    private func showFoodList(forBtn btn: UIButton) {
        
        let alertSheet = UIAlertController(title: Constants.food.localized().uppercased(), message: Constants.selectOption.localized(), preferredStyle: .actionSheet)
        
        let mreAction = UIAlertAction(title: Constants.mealReadyToEat.localized().uppercased(), style: .default) { (action) in
            //Do something..
            btn.setTitle(Constants.mealReadyToEat.localized().uppercased(), for: .normal)
            self.foodTypeId = 346
        }
        
        let rawFoodAction = UIAlertAction(title: Constants.rawFood.localized().uppercased(), style: .default) { (action) in
            //Do something..
            btn.setTitle(Constants.rawFood.localized().uppercased(), for: .normal)
            self.foodTypeId = 347
        }
        
        let cancelAction = UIAlertAction(title: Constants.cancel.localized(), style: .cancel)
        
        alertSheet.addAction(mreAction)
        alertSheet.addAction(rawFoodAction)
        alertSheet.addAction(cancelAction)
        
        self.present(alertSheet, animated: true)
    }
}

extension HomeViewController {
    
//    private func receivedFood() {
//
//        Spinner.shared.show(onView: self.view, text: Constants.loading.localized().uppercased())
//
//        let foodRequest = ReceivedFoodRequest(userId: AppData.fwProfId, eventId: 10, deviceInfo: Utilities.createDeviceInfo())
//
//        let service = Services.shared
//        service.receivedFood(requestObj: foodRequest, Success: { [weak self] (serverObj) in
//            DispatchQueue.main.async {
//                self?.verificationHolderView.isHidden = false
//
//                UIView.animate(withDuration: 0.3, animations: {
//                    self?.verificationHolderView.alpha = 1
//                }) { (_) in
//                    UIView.animate(withDuration: 0.5, delay: 2.0, options: .curveEaseOut, animations: {
//                        self?.verificationHolderView.alpha = 0
//                    }) { (_) in
//                        self?.verificationHolderView.isHidden = true
//                    }
//                }
//            }
//        }) { (error) in
//            DispatchQueue.main.async {
//                if error == Constants.userAlreadyLoginRelogin.localized() {
//                    self.popBasicAlert(withTitle: nil, message: error) {
//                        AppData.resetDefaults()
//                        self.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
//                    }
//                } else {
//                    self.popBasicAlert(withTitle: nil, message: Utilities.isItCommonErrorMessage(errMsg: error) ? error : Constants.failed.localized())
//                }
//            }
//        }
//    }
    
    private func logout() {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized().uppercased())

        let logoutReq = LogoutRequest(userId: AppData.userId, passportNo: AppData.passportNo, deviceInfo: Utilities.createDeviceInfo())
        let service = Services.shared
        service.logoutService(reqModelObj: logoutReq, Success: { [weak self] (serverObj) in
            DispatchQueue.main.async {
                print("SUCCESS LOGOUT", serverObj.status!)
                AppData.resetDefaults()
                self?.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
            }
        }) { (error) in
            DispatchQueue.main.async {
                // Even error, allow user to logout
                AppData.resetDefaults()
                self.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
            }
        }
    }
}
