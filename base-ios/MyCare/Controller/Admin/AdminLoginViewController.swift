//
//  AdminLoginViewController.swift
//  PagohCare
//
//  Created by Cookie on 5/7/20.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import UIKit

class AdminLoginViewController: UIViewController, Alert {

    @IBOutlet private weak var scrollView: UIScrollView!
    @IBOutlet private weak var backBtn: UIButton!
    @IBOutlet private weak var loginBtn: CorneredButton!
    @IBOutlet private weak var usernameTF: UITextField!
    @IBOutlet private weak var passwordTF: UITextField!
    
    private var activeField: UITextField?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialSetup()
    }
    
    override func viewWillAppear(_ animated: Bool) {
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
    
    private func initialSetup() {
        backBtn.tag = 1
        loginBtn.tag = 2
        self.usernameTF.delegate = self
        self.passwordTF.delegate = self
        
        let dismissGesture = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))
        self.view.addGestureRecognizer(dismissGesture)
        
        self.passwordTF.isSecureTextEntry = true
        self.addShowButton(toTextField: self.passwordTF)
    }
    
    private func setText() {
        self.usernameTF.placeholder = "Username"
        self.passwordTF.placeholder = "Password"
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
        showBtn.addTarget(self, action: #selector(showPasswordHandler(_:)), for: .touchUpInside)
        
        hView.addSubview(showBtn)
        textField.rightView = hView
    }
    
    //MARK: - Action and Handlers
    @IBAction
    private func buttonAction(_ sender: UIButton) {
        switch sender.tag {
        case 1:
            self.dismiss(animated: true, completion: nil)
        case 2:
            
            guard let loginText = self.usernameTF.text, loginText != "" else {
                self.popBasicAlert(withTitle: nil, message: "Please enter username.")
                return
            }

            guard let pwdText = self.passwordTF.text, pwdText != "" else {
                self.popBasicAlert(withTitle: nil, message: "Please enter password.")
                return
            }
            
            self.login(username: loginText, password: pwdText)
        default:
            print("NOTHINGNESS")
        }
    }
    
    @objc
    private func showPasswordHandler(_ sender: UIButton) {
        sender.setImage(passwordTF.isSecureTextEntry ? UIImage.init(named: "hidePassword") : UIImage.init(named: "showPassword"), for: .normal)
        passwordTF.togglePasswordVisibility()
    }
    
    //MARK: - Keyboard
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
extension AdminLoginViewController {
    private func login(username: String, password: String) {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized())
        
        let loginReq = AdminLoginRequest(userId: username , password: password, deviceInfo: Utilities.createDeviceInfo())
        let service = Services.shared
        service.adminLoginService(requestObj: loginReq, Success: { [weak self] (responses, serverObj) in
            
            DispatchQueue.main.async {
                
                //Response data will be save to userdefaults.
                AppData.fullname = responses.firstName + " " + responses.lastName
                AppData.userId = username
                AppData.isLogin = true
                AppData.adminRoleGroup = responses.userRoleGroupCode
                AppData.isAdmin = true
                
                // Read AccessToken
                let tokenInfo = responses.token
                AppData.accessToken = tokenInfo.accessToken
                print("ACCESS TOKEN: ", tokenInfo.accessToken)
                
                guard let vc = UIStoryboard(name: "Admin", bundle: nil).instantiateViewController(withIdentifier: String(describing: AdminHomeViewController.self)) as? AdminHomeViewController else {
                    return
                }
                vc.modalPresentationStyle = .fullScreen
                self?.present(vc, animated: true, completion: nil)
            }
        }) { (error) in
            DispatchQueue.main.async {
                self.popBasicAlert(withTitle: nil, message: error)
            }
        }
    }
}

extension AdminLoginViewController: UITextFieldDelegate {
    func textFieldDidBeginEditing(_ textField: UITextField) {
        activeField = textField
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        activeField = nil
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        
        switch textField {
        case usernameTF:
            passwordTF.becomeFirstResponder()
        case passwordTF:
            textField.resignFirstResponder()
        default:
            textField.resignFirstResponder()
        }
        
        // Do not add a line break
        return false
    }
}
