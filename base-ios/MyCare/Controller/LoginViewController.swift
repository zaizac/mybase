//
//  LoginViewController.swift
//  MyCare
//
//  Created by Cookie on 4/15/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import UIKit
import Localize_Swift
import SideMenu

let REGISTRATION_ID = "Registration"

class LoginViewController: UIViewController, Alert {
    
    @IBOutlet private weak var scrollView: UIScrollView!
    @IBOutlet private weak var loginTextField: UITextField!
    @IBOutlet private weak var passwordTextField: UITextField!
    @IBOutlet private weak var loginBtn: CorneredButton!
    @IBOutlet private weak var registerStatusBtn: CorneredButton!
    @IBOutlet private weak var signUpBtn: UIButton!
    @IBOutlet private weak var firstLangBtn: UIButton!
    @IBOutlet private weak var secondLangBtn: UIButton!
    
    private var activeField: UITextField?
    
    private var holderView: UIView!
    private var isHidePassword: Bool = true
    
    // MARK: - View Lifecycle
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialConfig()
        
        print("CURRENT LANG:", Localize.currentLanguage())
        print("FCM TOKEN:", AppData.fcmToken)

    }
    
    override func viewWillAppear(_ animated:Bool) {
        super.viewWillAppear(animated)
        
        self.setText()
        
        //1  Add this observers to observe keyboard shown and hidden events
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillBeHidden(aNotification:)), name: UIResponder.keyboardWillHideNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillShow(aNotification:)), name: UIResponder.keyboardWillShowNotification, object: nil)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        //2  Remove the observers added for keyboard from your ViewController
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillHideNotification, object: nil)
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillShowNotification, object: nil)
    }
    
    private func initialConfig() {
        
        self.loginTextField.delegate = self
        self.loginTextField.keyboardType = .numberPad
        
        self.passwordTextField.delegate = self
        self.passwordTextField.keyboardType = .numberPad
        self.passwordTextField.isHidden = false
        self.passwordTextField.isSecureTextEntry = true
        
        self.loginBtn.backgroundColor = UIColor.init(named: "standardBtnColor")
        
        self.passwordTextField.isHidden = UIApplication.TARGET_NAME == "PAGOH" ? false : true
        
        let dismissGesture = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))
        self.view.addGestureRecognizer(dismissGesture)
        
        self.addShowButton(toTextField: passwordTextField)
    }
    
    private func setText() {
        
        if UIApplication.TARGET_NAME == "PAGOH" {
            self.loginTextField.placeholder = Constants.mykad.localized()
            self.loginBtn.setTitle(Constants.login.localized(), for: .normal)
        } else if UIApplication.TARGET_NAME == "BH" {
            self.loginTextField.placeholder = Constants.mobilePassport.localized()
            self.loginBtn.setTitle(Constants.login.localized(), for: .normal)
        }
        self.passwordTextField.placeholder = Constants.mobileNo.localized()
        
        self.registerStatusBtn.setTitle(Constants.checkRegStatus.localized(), for: .normal)
        
        let italicFontAttr : [NSAttributedString.Key: Any] = [
        NSAttributedString.Key.font : UIFont.italicSystemFont(ofSize: 14.0),
        NSAttributedString.Key.foregroundColor : UIColor.darkGray]
        
        let underlineAttr : [NSAttributedString.Key: Any] = [
            NSAttributedString.Key.font : UIFont.systemFont(ofSize: 14),
            NSAttributedString.Key.foregroundColor : UIColor.red,
            NSAttributedString.Key.underlineStyle : NSUnderlineStyle.single.rawValue]
                
        let attributeString = NSMutableAttributedString(string: Constants.notRegisterYet.localized() + " ", attributes: italicFontAttr)
        attributeString.append(NSAttributedString(string: Constants.signUpNow.localized(), attributes: underlineAttr))
        attributeString.append(NSAttributedString(string: Constants.nowOnly.localized(), attributes: italicFontAttr))
        signUpBtn.setAttributedTitle(attributeString, for: .normal)
        
        self.toogleButton()
    }
    
    private func toogleButton() {
        let bahasaUnderlineAttr : [NSAttributedString.Key: Any] = [
        NSAttributedString.Key.font : UIFont.systemFont(ofSize: 13),
        NSAttributedString.Key.foregroundColor : UIColor.blue,
        NSAttributedString.Key.underlineStyle : NSUnderlineStyle.single.rawValue]
        
        if UIApplication.TARGET_NAME == "PAGOH" {
            if Localize.currentLanguage() == "en" {
                let firstAttrString = NSMutableAttributedString(string: "English", attributes: bahasaUnderlineAttr)
                let secondAttrString = NSMutableAttributedString(string: "Bahasa Malaysia", attributes: [NSAttributedString.Key.foregroundColor : UIColor.darkGray])
                firstLangBtn.setAttributedTitle(firstAttrString, for: .normal)
                secondLangBtn.setAttributedTitle(secondAttrString, for: .normal)
            } else {
                let firstAttrString = NSMutableAttributedString(string: "English", attributes: [NSAttributedString.Key.foregroundColor : UIColor.darkGray])
                let secondAttrString = NSMutableAttributedString(string: "Bahasa Malaysia", attributes: bahasaUnderlineAttr)
                firstLangBtn.setAttributedTitle(firstAttrString, for: .normal)
                secondLangBtn.setAttributedTitle(secondAttrString, for: .normal)
            }
        } else if UIApplication.TARGET_NAME == "BH" {
            if Localize.currentLanguage() == "en" {
                let firstAttrString = NSMutableAttributedString(string: "English", attributes: bahasaUnderlineAttr)
                let secondAttrString = NSMutableAttributedString(string: "বাংলা", attributes: [NSAttributedString.Key.foregroundColor : UIColor.darkGray])
                firstLangBtn.setAttributedTitle(firstAttrString, for: .normal)
                secondLangBtn.setAttributedTitle(secondAttrString, for: .normal)
            } else {
                let firstAttrString = NSMutableAttributedString(string: "English", attributes: [NSAttributedString.Key.foregroundColor : UIColor.darkGray])
                let secondAttrString = NSMutableAttributedString(string: "বাংলা", attributes: bahasaUnderlineAttr)
                firstLangBtn.setAttributedTitle(firstAttrString, for: .normal)
                secondLangBtn.setAttributedTitle(secondAttrString, for: .normal)
            }
        }
    }
    
    //Add showButton to password textfield.
    private func addShowButton(toTextField textField: UITextField) {
        textField.rightViewMode = .always
        
        let hView = UIView(frame: CGRect(x: 0, y: 0, width: 32, height: 24))
        hView.backgroundColor = .clear
        
        let showBtn = UIButton(frame: CGRect(x: 0, y: 0, width: 22, height: 22))
        let image = UIImage(named: "showPassword")
        showBtn.setImage(image, for: .normal)
        showBtn.tintColor = .darkGray
        showBtn.addTarget(self, action: #selector(LoginViewController.showPasswordHandler(_:)), for: .touchUpInside)
        
        hView.addSubview(showBtn)
        textField.rightView = hView
    }
    
    //View for viewing a registration status.
    private func addRegistrationView(withStatus status: String, reason: String?) {
        
        holderView = UIView()
        holderView.frame = self.view.frame
        holderView.backgroundColor = .black
        holderView.alpha = 0.0
        
        let registrationTitleLbl = UILabel()
        registrationTitleLbl.textColor = .white
        registrationTitleLbl.font = UIFont.systemFont(ofSize: 24.0, weight: .semibold)
        registrationTitleLbl.text = "\(Constants.registerStatus.localized()):"
        registrationTitleLbl.textAlignment = .center
        
        let statusLbl = UILabel()
        statusLbl.textColor = .white
        statusLbl.font = UIFont.systemFont(ofSize: 24.0, weight: .semibold)
        statusLbl.text = status.uppercased()
        statusLbl.textAlignment = .center
        
        let reasonLbl = UILabel()
        reasonLbl.textColor = .white
        reasonLbl.font = UIFont.systemFont(ofSize: 17.0, weight: .regular)
        reasonLbl.text = ""
        reasonLbl.textAlignment = .center
        reasonLbl.isHidden = reason == nil ? true : false
        
        if let rsn = reason {
            reasonLbl.text = rsn.uppercased()
        }
        
        let statusSV = UIStackView()
        statusSV.alignment = .fill
        statusSV.axis = .vertical
        statusSV.spacing = 0.0
        
        statusSV.addArrangedSubview(registrationTitleLbl)
        statusSV.addArrangedSubview(statusLbl)
        statusSV.addArrangedSubview(reasonLbl)
        
        let okBtn = UIButton()
        okBtn.backgroundColor = Colors.bhCareGreen
        okBtn.setTitle("OK", for: .normal)
        okBtn.addCornerRadius(10)
        okBtn.translatesAutoresizingMaskIntoConstraints = false
        okBtn.addTarget(self, action: #selector(LoginViewController.okStatusHandler(_:)), for: .touchUpInside)
        
        let contentSV = UIStackView()
        contentSV.translatesAutoresizingMaskIntoConstraints = false
        contentSV.alignment = .fill
        contentSV.distribution = .fillProportionally
        contentSV.axis = .vertical
        contentSV.spacing = 40.0
        
        contentSV.addArrangedSubview(statusSV)
        contentSV.addArrangedSubview(okBtn)
        self.holderView.addSubview(contentSV)
        self.view.addSubview(self.holderView)
        self.view.bringSubviewToFront(self.holderView)
        
        contentSV.centerYAnchor.constraint(equalTo: holderView.centerYAnchor).isActive = true
        contentSV.centerXAnchor.constraint(equalTo: holderView.centerXAnchor).isActive = true
        contentSV.leadingAnchor.constraint(equalTo: holderView.leadingAnchor, constant: 24).isActive = true
        contentSV.trailingAnchor.constraint(equalTo: holderView.trailingAnchor, constant: -24).isActive = true
        
        okBtn.heightAnchor.constraint(equalToConstant: 44.0).isActive = true
        
        
        UIView.animate(withDuration: 0.5) {
            self.holderView.alpha = 1
        }
    }
    
    //MARK: - IBAction & Handlers
    
    @objc
    private func okStatusHandler(_ sender: UIButton) {
        UIView.animate(withDuration: 0.5, animations: {
            self.holderView.alpha = 0
        }) { (_) in
            if self.holderView != nil {
                if self.holderView.isDescendant(of: self.view) {
                    self.holderView.removeFromSuperview()
                }
            }
        }
    }
    
    @IBAction
    private func bahasaAction(_ sender: UIButton) {
        switch sender.tag {
        case 1:
            Localize.setCurrentLanguage("en")
        case 2:
            if UIApplication.TARGET_NAME == "PAGOH" {
                Localize.setCurrentLanguage("ms")
            } else {
                Localize.setCurrentLanguage("bn")
            }
        default:
            print("NOTHINGLESS")
        }
        
        self.setText()
    }
    
    @IBAction
    private func loginAction(_ sender: UIButton) {
        /*
        // TEMP
        guard let homeVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: String(describing: HomeViewController.self)) as? HomeViewController else { return }
        //
        self.present(homeVC, animated: true)
        return
        // -end
        */
        
        guard let loginText = self.loginTextField.text, loginText != "" else {
            var alertMsg = ""
            if UIApplication.TARGET_NAME == "PAGOH" {
                alertMsg = Constants.enterMykadNo.localized()
            } else if UIApplication.TARGET_NAME == "BH" {
                alertMsg = Constants.enterPassportNo.localized()
            }
            self.popBasicAlert(withTitle: nil, message: alertMsg)
            return
        }

        guard let pwdText = self.passwordTextField.text, pwdText != "" else {
            self.popBasicAlert(withTitle: nil, message: Constants.enterMobileNo.localized())
            return
        }

////         TODO: Pass two values
        self.login(myKadOrPassport: loginText, mobileNo: pwdText)
    }
    
    //Used for testing only.
    private func byPassLogin() {
        AppData.fullname = "ADUDUDU" //responses.name
        AppData.fwProfId = 1 //responses.fwProfId
        AppData.passportNo = "90909090909"
        AppData.contactNo = "0123323232"
        AppData.nationalityId = "MYS" //responses.nationalityCode
        AppData.isLogin = true
        
        guard let homeVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: String(describing: HomeViewController.self)) as? HomeViewController else { return }
        
        self.present(homeVC, animated: true)
    }
    
    @IBAction
    private func notificationAction(_ sender: UIButton) {
        guard let adminLoginVC = UIStoryboard(name: "Admin", bundle: nil).instantiateInitialViewController() as? AdminLoginViewController else {
            return
        }
        
        self.present(adminLoginVC, animated: true, completion: nil)
    }
    
    @IBAction
    private func regsiterStatusAction(_ sender: UIButton) {
        guard let loginText = self.loginTextField.text, loginText != "" else {
            var alertMsg = ""
            if UIApplication.TARGET_NAME == "PAGOH" {
                alertMsg = Constants.enterMykadNo.localized()
            } else if UIApplication.TARGET_NAME == "BH" {
                alertMsg = Constants.enterPassportNo.localized()
            }
            self.popBasicAlert(withTitle: nil, message: alertMsg)
            return
        }
        
        guard let pwdText = self.passwordTextField.text, pwdText != "" else {
            self.popBasicAlert(withTitle: nil, message: Constants.enterMobileNo.localized())
            return
        }
        
        self.registerStatusCheck(myKadOrPassport: loginText, mobileNo: pwdText)
    }
    
    @IBAction
    private func signUpAction(_ sender: UIButton) {
        guard let registerVC = UIStoryboard(name: REGISTRATION_ID, bundle: nil).instantiateViewController(withIdentifier: String(describing: RegisterProfileViewController.self)) as? RegisterProfileViewController else { return }
        
        let navController = UINavigationController(rootViewController: registerVC)
        navController.modalPresentationStyle = .fullScreen
        navController.navigationBar.transparentNavigationBar()
        self.present(navController, animated: true)
    }
    
    @objc
    private func showPasswordHandler(_ sender: UIButton) {
        /*self.isHidePassword = !self.isHidePassword
        sender.setImage(self.isHidePassword ? UIImage(named: "showPassword") : UIImage(named: "hidePassword"), for: .normal)
        self.passwordTextField.isSecureTextEntry = self.isHidePassword*/
        
        sender.setImage(passwordTextField.isSecureTextEntry ? UIImage.init(named: "hidePassword") : UIImage.init(named: "showPassword"), for: .normal)
        passwordTextField.togglePasswordVisibility()
    }
    
    @objc
    private func dismissKeyboard(_ sender: UITapGestureRecognizer) {
        self.view.endEditing(true)
    }
    
    // Called when the UIKeyboardWillHide is sent
    @objc
    private func keyboardWillBeHidden(aNotification: NSNotification) {
        let contentInsets: UIEdgeInsets = .zero
        self.scrollView.contentInset = contentInsets
        self.scrollView.scrollIndicatorInsets = contentInsets
    }
    
    // Called when the UIKeyboardWillShow is sent
    @objc
    private func keyboardWillShow(aNotification: NSNotification) {
        
        let info = aNotification.userInfo!
        let kbSize: CGSize = ((info["UIKeyboardFrameEndUserInfoKey"] as? CGRect)?.size)!
        
        print("kbSize = \(kbSize)")
        
        let contentInsets: UIEdgeInsets = UIEdgeInsets(top: 0.0, left: 0.0, bottom: kbSize.height, right: 0.0)
        
        scrollView.contentInset = contentInsets
        scrollView.scrollIndicatorInsets = contentInsets
        
        var aRect: CGRect = self.view.frame
        aRect.size.height -= kbSize.height
        
        if let activeField = self.activeField {
            if !aRect.contains(activeField.frame.origin) {
                self.scrollView.scrollRectToVisible(activeField.frame, animated: true)
            }
        }
    }
}

//MARK: - Http Request
extension LoginViewController {
    private func login(myKadOrPassport: String, mobileNo: String) {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized())
        
        let loginReq = LoginRequest(userId:mobileNo , passportNo: myKadOrPassport, deviceInfo: Utilities.createDeviceInfo())
        let service = Services.shared
        service.loginService(requestObj: loginReq, Success: { [weak self] (responses, serverObj) in
            
            DispatchQueue.main.async {
                
                //Response data will be save to userdefaults.
                AppData.fullname = responses.name
                AppData.fwProfId = responses.fwProfId
                AppData.passportNo = responses.passportNo ?? ""
                AppData.nationalityId = responses.nationalityCode
                AppData.userId = mobileNo
                AppData.contactNo = mobileNo
                AppData.isLogin = true
                AppData.isAdmin = false
                
                // Read AccessToken
                let tokenInfo = responses.token
                AppData.accessToken = tokenInfo.accessToken
                print("ACCESS TOKEN: ", tokenInfo.accessToken)
                
                guard let homeVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: String(describing: HomeViewController.self)) as? HomeViewController else { return }
                
                self?.present(homeVC, animated: true)
            }
        }) { (error) in
            DispatchQueue.main.async {
                self.popBasicAlert(withTitle: nil, message: Utilities.isItCommonErrorMessage(errMsg: error) ? error : error)
            }
        }
    }
    
    private func registerStatusCheck(myKadOrPassport: String, mobileNo: String) {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized())
        
        let registerStatusRequest = RegisterStatusRequest(contactNo: mobileNo, passportNo: myKadOrPassport, deviceInfo: Utilities.createDeviceInfo())
        let service = Services.shared
        service.registerStatusService(requestObj: registerStatusRequest, Success: { [weak self] (responses, serverObj) in
            DispatchQueue.main.async {
                print(responses.appStatus)
                self?.view.endEditing(true)
                switch responses.appStatus {
                    case "APR":
                        self?.addRegistrationView(withStatus: Constants.approved.localized(), reason: responses.reason)
                    case "SUB":
                        self?.addRegistrationView(withStatus: Constants.pendingApproval.localized(), reason: responses.reason)
                    case "REJ":
                        self?.addRegistrationView(withStatus: Constants.reject.localized(), reason: responses.reason)
                    default:
                        self?.addRegistrationView(withStatus: "Unknown", reason: nil)
                }
            }
            }, Failure: { (error) in
                DispatchQueue.main.async {
                    self.popBasicAlert(withTitle: nil, message:  Utilities.isItCommonErrorMessage(errMsg: error) ? error : error)
                }
        })
    }
}

// MARK: - Textfield delegate
extension LoginViewController: UITextFieldDelegate {
    func textFieldDidBeginEditing(_ textField: UITextField) {
        activeField = textField
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        activeField = nil
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if textField.isFirstResponder {
            let validString = CharacterSet(charactersIn: " !@#$%^&*()_+{}[]|\"<>,.~`/:;?-=\\¥'£•¢")

            if (textField.textInputMode?.primaryLanguage == "emoji") || textField.textInputMode?.primaryLanguage == nil {
                return false
            }
            if let range = string.rangeOfCharacter(from: validString)
            {
                print(range)
                return false
            }
        }
        
        // 1
        if textField.text == "" && string == " " {
            // Avoid space as first character
            return false
        } else if textField.text!.count >= 1 && String(textField.text!.last!) == " " && string == " " {
            // Avoid More than one spacing continuously
            return false
        } else if string == "" {
            return true // Backspace always allow
        }
        
        if ((textField == loginTextField) && textField.text?.count == 12) {
            return false
        } else if ((textField == passwordTextField) && textField.text?.count == 11) {
            return false
        }
        
        return true
    }
}
