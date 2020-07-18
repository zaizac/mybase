//
//  UpdateProfileViewController.swift
//  PagohCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import UIKit
import AVKit
import Localize_Swift

class UpdateProfileViewController: UIViewController, Alert {
    
    @IBOutlet private weak var scrollView: UIScrollView!
    @IBOutlet private weak var contentHolder: UIView!
    @IBOutlet private weak var userIV: UIImageView!
    @IBOutlet private weak var userIVHolder: UIView!
    @IBOutlet private weak var cameraBtn: UIButton!
    
    @IBOutlet private weak var mealBtn: UIButton!
    @IBOutlet private weak var genderLbl: UILabel!
    @IBOutlet private weak var maritialStatusLbl: UILabel!
    @IBOutlet private weak var raceLbl: UILabel!
    @IBOutlet private weak var ageLbl: UILabel!
    @IBOutlet private weak var dobLbl: UILabel!
    @IBOutlet private weak var occupationLbl: UILabel!
    @IBOutlet private weak var placeOfBirthLbl: UILabel!
    @IBOutlet private weak var passportSV: UIStackView!
    
    @IBOutlet private weak var fullnameTF: UITextField!
    @IBOutlet private weak var passportTF: UITextField!
    @IBOutlet private weak var mobileTF: UITextField!
    @IBOutlet private weak var companyNameTF: UITextField!
    @IBOutlet private weak var companyMobileTF: UITextField!
    
    @IBOutlet private weak var addressTV: UITextView!
    @IBOutlet private weak var companyAddressTV: UITextView!
    
    //Title Label
    @IBOutlet private weak var nameTitleLbl: UILabel!
    @IBOutlet private weak var passportTitleLbl: UILabel!
    @IBOutlet private weak var mobileTitleLbl: UILabel!
    @IBOutlet private weak var genderTitleLbl: UILabel!
    @IBOutlet private weak var mealTitleLbl: UILabel!
    @IBOutlet private weak var companyNameLbl: UILabel!
    @IBOutlet private weak var companyMobileLbl: UILabel!
    @IBOutlet private weak var addressLbl: UILabel!
    @IBOutlet private weak var companyAddressLbl: UILabel!
    @IBOutlet private weak var maritialStatusTitleLbl: UILabel!
    @IBOutlet private weak var raceTitleLbl: UILabel!
    @IBOutlet private weak var ageTitleLbl: UILabel!
    @IBOutlet private weak var dobTitleLbl: UILabel!
    @IBOutlet private weak var occupationTitleLbl: UILabel!
    @IBOutlet private weak var placeOfBirthTitleLbl: UILabel!
    
    @IBOutlet private weak var updateBtn: CorneredButton!
    
    private var activeField: UITextField?
    private let imagePicker = UIImagePickerController()
    private var snappedPhoto: UIImage?
    private var keyboardHeight: CGFloat = 0.0
    
    var profile: ProfileResponse!
    private var profileRequest: UpdateProfileRequest!
    private var mealId: Int = 348
    private var genderId: Int?
    private var photoBase64: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialConfig()
        self.updateUI()
    }
    
    override func viewWillAppear(_ animated:Bool) {
        super.viewWillAppear(animated)
        
        self.setText()
                
        let notificationCenter = NotificationCenter.default
        notificationCenter.addObserver(self, selector: #selector(adjustForKeyboard), name: UIResponder.keyboardWillHideNotification, object: nil)
        notificationCenter.addObserver(self, selector: #selector(adjustForKeyboard), name: UIResponder.keyboardWillChangeFrameNotification, object: nil)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        //2  Remove the observers added for keyboard from your ViewController
        let notificationCenter = NotificationCenter.default
        notificationCenter.removeObserver(self, name: UIResponder.keyboardWillHideNotification, object: nil)
        notificationCenter.removeObserver(self, name: UIResponder.keyboardWillChangeFrameNotification, object: nil)
    }
    
    private func initialConfig() {
        
        title = Constants.updateProfile.localized()
        
        self.userIVHolder.backgroundColor = .clear
        self.userIVHolder.addShadow(withOpacity: 0.25, shadowRadius: 3, offset: CGSize(width: 0, height: 2.0))
        self.userIV.addCornerRadius(self.userIV.frame.height / 2)
        self.userIV.contentMode = .scaleAspectFill
        
        self.cameraBtn.addCornerRadius(self.cameraBtn.frame.height / 2)
        self.updateBtn.backgroundColor = UIColor.init(named: "standardBtnColor")
        
//        self.genderBtn.titleEdgeInsets = UIEdgeInsets(top: 0.0, left: 0.0, bottom: 0.0, right: 0.0)
//        self.genderBtn.setTitleColor(UIColor.black, for: .normal)
        
        self.fullnameTF.delegate = self
        self.passportTF.delegate = self
        self.mobileTF.delegate = self
        self.companyNameTF.delegate = self
        self.companyMobileTF.delegate = self
        
        self.mobileTF.keyboardType = .numberPad
        self.companyMobileTF.keyboardType = .numberPad
        
        self.addressTV.addCornerRadius(5)
        self.companyAddressTV.addCornerRadius(5)
        
        self.addressTV.delegate = self
        self.companyAddressTV.delegate = self
        self.addressTV.isUserInteractionEnabled = false
        self.companyAddressTV.isUserInteractionEnabled = false
        
        //Disable interaction for the button
//        self.dobBtn.isUserInteractionEnabled = false
//        self.ageBtn.isUserInteractionEnabled = false
//        self.raceBtn.isUserInteractionEnabled = false
//        self.maritialStatusBtn.isUserInteractionEnabled = false
//        self.occupationBtn.isUserInteractionEnabled = false
//        self.placeOfBirthBtn.isUserInteractionEnabled = false
        
        self.addressTV.textContainerInset = UIEdgeInsets(top: 4, left: -2, bottom: 4, right: 0)
        self.companyAddressTV.textContainerInset = UIEdgeInsets(top: 4, left: -2, bottom: 4, right: 0)
        
        self.addressTV.sizeToFit()
        self.companyAddressTV.sizeToFit()
        
        // No need to hide for any target app
        self.passportSV.isHidden = false // AppData.nationalityId == "MYS"
        
        self.imagePicker.delegate = self
        
//        self.genderBtn.addCornerRadius(5)
        self.mealBtn.addCornerRadius(5)
//
//        self.genderBtn.layer.borderColor = Colors.defaultBorder.cgColor
//        self.genderBtn.layer.borderWidth = 0.0 // 1.0
        
        self.mealBtn.layer.borderColor = Colors.defaultBorder.cgColor
        self.mealBtn.layer.borderWidth = 1.0
        
        let dismissGesture = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))
        self.view.addGestureRecognizer(dismissGesture)
    }
    
    private func setText() {
        self.nameTitleLbl.text = Constants.fullname.localized()
        self.mobileTitleLbl.text = Constants.mobileNo.localized()
        self.genderTitleLbl.text = Constants.gender.localized()
        self.mealTitleLbl.text = Constants.mealType.localized()
        self.companyNameLbl.text = Constants.companyName.localized()
        self.companyMobileLbl.text = Constants.companyContactNo.localized()
        self.addressLbl.text = Constants.address.localized()
        self.companyAddressLbl.text = Constants.companyAddress.localized()
        
        maritialStatusTitleLbl.text = Constants.maritalStatus.localized()
        raceTitleLbl.text = Constants.race.localized()
        ageTitleLbl.text = Constants.age.localized()
        dobTitleLbl.text = Constants.dob.localized()
        occupationTitleLbl.text = Constants.occupation.localized()
        placeOfBirthTitleLbl.text = Constants.placeOfBirth.localized()
        
        self.passportTitleLbl.text = UIApplication.TARGET_NAME == "PAGOH" ? Constants.mykad.localized() : Constants.passportNo.localized()
        self.passportTF.placeholder = UIApplication.TARGET_NAME == "PAGOH" ? Constants.mykad.localized() : Constants.passportNo.localized()
        
        self.updateBtn.setTitle(Constants.update.localized().uppercased(), for: .normal)
    }
    
    private func updateUI() {
        self.fullnameTF.text = profile.name.uppercased()
        self.passportTF.text = profile.passportNo?.uppercased()
        self.mobileTF.text = profile.contactNo.uppercased()
        
        if let genderId = profile.genderId {
            self.genderLbl.text = Int(genderId)! == 8 ? Constants.male.localized().uppercased() : Constants.female.localized().uppercased()
        }
        
        self.mealBtn.setTitle(profile.mealTypeId == 348 ? Constants.veggie.localized().uppercased() : Constants.nonVeggie.localized().uppercased(), for: .normal)
        
        if let maritialId = profile.maritialStatus {
            var tempMaritialStatus: String!
            switch maritialId {
            case 324:
                tempMaritialStatus = Constants.single.localized()
            case 325:
                tempMaritialStatus = Constants.married.localized()
            case 326:
                tempMaritialStatus = Constants.widowed.localized()
            case 328:
                tempMaritialStatus = Constants.divorced.localized()
            default:
                tempMaritialStatus = "N/A"
            }
            self.maritialStatusLbl.text = tempMaritialStatus.uppercased()
        }
        
        if let jobId = profile.occupationId {
            var tempJob: String!
            switch jobId {
            case 350:
                tempJob = Constants.governSector.localized()
            case 351:
                tempJob = Constants.privateSector.localized()
            case 352:
                tempJob = Constants.selfEmployed.localized()
            case 353:
                tempJob = Constants.governPensioner.localized()
            case 354:
                tempJob = Constants.notWorking.localized()
            default:
                tempJob = "N/A"
            }
            self.occupationLbl.text = tempJob.uppercased()
        }
        
        if let placeBirth = profile.placeOfBirth {
            self.placeOfBirthLbl.text = placeBirth == "" ? "N/A" : placeBirth.uppercased()
        }
        
        if let age = profile.age {
            self.ageLbl.text = "\(age)"
        }
        
        if let raceCode = profile.raceCode {
            var localeRaceDesc = ""
            switch raceCode {
            case "01":
                localeRaceDesc = Constants.bumiputera.localized()
            case "02":
                localeRaceDesc = Constants.chinese.localized()
            case "03":
                localeRaceDesc = Constants.indians.localized()
            case "04":
                localeRaceDesc = Constants.otherRaces.localized()
            default:
                localeRaceDesc = "N/A"
            }
            self.raceLbl.text = localeRaceDesc
        }
        
        if let dob = profile.dob {
            self.dobLbl.text = dob
        }
        
        self.companyNameTF.text = profile.empName.uppercased()
        self.companyMobileTF.text = profile.empContactNo.uppercased()
        
        self.addressTV.text = profile.address.uppercased()
        self.companyAddressTV.text = profile.empAddress.uppercased()
        
        if let photo64 = profile.photoBase64 {
            if photo64 == "" {
                self.userIV.image = UIImage.init(named: Constants.defaultImageName.localized())
                return
            }
            
            let base64ToImage = Utilities.base64ToImage(strBase64: profile.photoBase64)
            self.userIV.image = base64ToImage
            self.photoBase64 = photo64
        } else {
            self.userIV.image = UIImage.init(named: Constants.defaultImageName.localized())
        }
    }
    
    //MARK: - IBAction and Handlers
    
    @IBAction
    private func dropdownAction(_ sender: UIButton) {
        
        switch sender.tag {
        case 1:
            return // Dont show it
            //self.showGenderList(forBtn: sender)
        case 2:
            self.showMealList(forBtn: sender)
        default:
            print("Nothingness")
        }
    }
    
    @IBAction
    private func cameraAction(_ sender: UIButton) {
        self.showPhotoPickingOption()
    }
    
    @IBAction
    private func updateAction(_ sender: UIButton) {
        //Call api services.
        let validated = self.checkForValidation()
        
        if !validated.isSuccess {
            self.popBasicAlert(withTitle: nil, message: validated.message)
        } else {
            self.updateProfile()
        }
    }
    
    @objc
    private func dismissKeyboard(_ sender: UITapGestureRecognizer) {
        self.view.endEditing(true)
    }
    
//    // Called when the UIKeyboardWillHide is sent
//    @objc
//    private func keyboardWillBeHidden(aNotification: NSNotification) {
//        let contentInsets: UIEdgeInsets = .zero
//        self.scrollView.contentInset = contentInsets
//        self.scrollView.scrollIndicatorInsets = contentInsets
//    }
//
//    // Called when the UIKeyboardWillShow is sent
//    @objc
//    private func keyboardWillShow(aNotification: NSNotification) {
//
//        let info = aNotification.userInfo!
//        let kbSize: CGSize = ((info["UIKeyboardFrameEndUserInfoKey"] as? CGRect)?.size)!
//
//        print("kbSize = \(kbSize)")
//
//        let contentInsets: UIEdgeInsets = UIEdgeInsets(top: 0.0, left: 0.0, bottom: kbSize.height, right: 0.0)
//
//        scrollView.contentInset = contentInsets
//        scrollView.scrollIndicatorInsets = contentInsets
//
//        var aRect: CGRect = self.view.frame
//        aRect.size.height -= kbSize.height
//
//        if let activeField = self.activeField {
//            if !aRect.contains(activeField.frame.origin) {
//                self.scrollView.scrollRectToVisible(activeField.frame, animated: true)
//            }
//        }
//
//        if let activeTextView = self.activeTextView {
//            if !aRect.contains(activeTextView.frame.origin) {
////                self.scrollView.scrollRectToVisible(activeTextView.frame, animated: true)
//
//            }
//
//        }
//    }
    
    @objc func adjustForKeyboard(notification: Notification) {
        guard let keyboardValue = notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue else { return }

        let keyboardScreenEndFrame = keyboardValue.cgRectValue
        let keyboardViewEndFrame = view.convert(keyboardScreenEndFrame, from: view.window)

        if notification.name == UIResponder.keyboardWillHideNotification {
            self.scrollView.contentInset = .zero
        } else {
            self.scrollView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: keyboardViewEndFrame.height - view.safeAreaInsets.bottom, right: 0)
            self.keyboardHeight = keyboardViewEndFrame.height - view.safeAreaInsets.bottom
        }

        self.scrollView.scrollIndicatorInsets = self.scrollView.contentInset
    }
    
    //MARK: - Custom
    
    private func checkForValidation() -> (message: String, isSuccess: Bool) {
        
        /*guard let fullname = self.fullnameTF.text, fullname != "" else {
            return (Constants.enterFullname.localized(), false)
        }
        
        guard let passport = self.passportTF.text, passport != "" else {
            return (Constants.enterPassportNo.localized(), false)
        }
        
        guard let mobileNumber = self.mobileTF.text, mobileNumber != "" else {
            return (Constants.enterMobileNo.localized(), false)
        }
        
        guard mobileNumber.isPhoneNumber else {
            return ("Mobile number is not in correct format.", false)
        }
        
        guard let address = self.addressTV.text, address != "" else {
            return (Constants.enterAddress.localized(), false)
        }
        
        guard let companyName = self.companyNameTF.text, companyName != "" else {
            return (Constants.enterCompanyName.localized(), false)
        }
        
        guard let companyNo = self.companyMobileTF.text, companyNo != "" else {
            return ("Company number cannot be empty.", false)
        }
        
        guard companyNo.isPhoneNumber else {
            return ("Company number is not in correct format.", false)
        }
        
        guard let addressCompany = self.companyAddressTV.text, addressCompany != "" else {
            return ("Company address cannot be empty.", false)
        }*/
        
        if let mealText = self.mealBtn.titleLabel?.text, mealText != "" {
            self.mealId = mealText == Constants.veggie.localized() ? 348 : 349
        }
        
        // Original
        //self.profileRequest = UpdateProfileRequest(fwProfId: AppData.fwProfId, name: fullname, address: address, stateCode: profile.stateCode, districtCode: profile.districtCode, countryCode: profile.countryCode, mukimCode: profile.mukimCode, zipCode: profile.zipCode, photoBase64: self.photoBase64, mealTypeId: self.mealId, deviceInfo: Utilities.createDeviceInfo())
        
        // Temp
        self.profileRequest = UpdateProfileRequest(fwProfId: AppData.fwProfId, name: self.fullnameTF.text ?? "", address: self.addressTV.text ?? "", stateCode: profile.stateCode, districtCode: profile.districtCode, countryCode: profile.countryCode, mukimCode: profile.mukimCode, zipCode: profile.zipCode, photoBase64: self.photoBase64, mealTypeId: self.mealId, deviceInfo: Utilities.createDeviceInfo())
        
        return ("", true)
    }
    
    private func showGenderList(forBtn btn: UIButton) {
        let alertSheet = UIAlertController(title: Constants.gender.localized(), message: Constants.selectOption.localized(), preferredStyle: .actionSheet)
        
        let maleAction = UIAlertAction(title: Constants.male.localized(), style: .default) { (action) in
            //Do something..
            btn.setTitle(Constants.male.localized(), for: .normal)
            self.genderId = 8
            self.view.endEditing(true)
        }
        
        let femaleAction = UIAlertAction(title: Constants.female.localized(), style: .default) { (action) in
            //Do something..
            btn.setTitle(Constants.female.localized(), for: .normal)
            self.genderId = 9
            self.view.endEditing(true)
        }
        
        let cancelAction = UIAlertAction(title: Constants.cancel.localized(), style: .cancel)
        
        alertSheet.addAction(maleAction)
        alertSheet.addAction(femaleAction)
        alertSheet.addAction(cancelAction)
        
        self.present(alertSheet, animated: true)
    }
    
    private func showMealList(forBtn btn: UIButton) {
        
        let alertSheet = UIAlertController(title: Constants.mealType.localized(), message: Constants.selectOption.localized(), preferredStyle: .actionSheet)
        
        let veggieAction = UIAlertAction(title: Constants.veggie.localized(), style: .default) { (action) in
            //Do something..
            btn.setTitle(Constants.veggie.localized(), for: .normal)
            self.mealId = 348
        }
        
        let nonVeggieAction = UIAlertAction(title: Constants.nonVeggie.localized(), style: .default) { (action) in
            //Do something..
            btn.setTitle(Constants.nonVeggie.localized(), for: .normal)
            self.mealId = 349
        }
        
        let cancelAction = UIAlertAction(title: Constants.cancel.localized(), style: .cancel)
        
        alertSheet.addAction(veggieAction)
        alertSheet.addAction(nonVeggieAction)
        alertSheet.addAction(cancelAction)
        
        self.present(alertSheet, animated: true)
    }
    
    // MARK: - Camera
    private func showPhotoPickingOption() {
        let alert = UIAlertController(title: Constants.workerPhoto.localized(), message: Constants.selectOption.localized(), preferredStyle: .actionSheet)
        alert.addAction(UIAlertAction(title: Constants.camera.localized(), style: .default, handler: { (_) in
            self.checkCameraPermission()
        }))
        
        alert.addAction(UIAlertAction(title: Constants.gallery.localized(), style: .default, handler: { (_) in
            self.imagePicker.sourceType = .photoLibrary
            self.imagePicker.allowsEditing = false
            self.present(self.imagePicker, animated: true)
        }))
        
        alert.addAction(UIAlertAction(title: Constants.cancel.localized(), style: .cancel, handler: { (_) in
            print("User click Dismiss button")
        }))
        
        self.present(alert, animated: true, completion: {
            print("completion block")
        })
    }
    
    private func checkCameraPermission() {
        if AVCaptureDevice.authorizationStatus(for: .video) ==  .authorized {
            // already authorized
            self.openCamera()
        } else if AVCaptureDevice.authorizationStatus(for: .video) ==  .notDetermined {
            AVCaptureDevice.requestAccess(for: .video, completionHandler: { (granted: Bool) in
                DispatchQueue.main.async {
                    if granted {
                        // access allowed
                        self.openCamera()
                    } else {
                        // access denied
                        print("Access denied. Dont ask user again this time.")
                    }
                }
            })
        } else {
            // ask user to go settings
            self.showAlertCameraPermission()
        }
    }
    
    private func openCamera() {
        imagePicker.sourceType = .camera
        imagePicker.allowsEditing = false
        imagePicker.cameraFlashMode = .off
        self.present(imagePicker, animated: true, completion: {
        })
    }
    
    private func showAlertCameraPermission() {
        DispatchQueue.main.async {
            let message = Constants.capturePhotoPhoneSettings.localized()
            print(message)
        }
    }
    
    // MARK: - Document Directory
    func readingFaceFromPhoto(capturedImage: UIImage) {
        
        Spinner.shared.show(onView: self.view, text: Constants.readingFace.localized(), blurEffect: .dark)
        
        DispatchQueue.global(qos: .background).async { [weak self] in
            // Pass image to face cropper
            capturedImage.face.crop({ (result) in
                switch result {
                    case .success(let faces):
                        DispatchQueue.main.async {
                            if faces.count >= 1 {
                                self?.userIV.image = faces[0]
                            }
                    }
                    case .notFound:
                        DispatchQueue.main.async {
                            print("faces Not found")
                            self?.popBasicAlert(withTitle: nil, message: Constants.noFaceDetect.localized())
                    }
                    case .failure(let error):
                        DispatchQueue.main.async {
                            print("CROP FACE ERROR: ++++++++++++ ", error)
                            self?.popBasicAlert(withTitle: Constants.failed.localized(), message: Constants.captureFaceAgain.localized())
                    }
                }
                
                Spinner.shared.hide()
            })
        }
    }
}

extension UpdateProfileViewController: UITextFieldDelegate {
    func textFieldDidBeginEditing(_ textField: UITextField) {
        textField .resignFirstResponder()
        
        activeField = textField
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        activeField = nil
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        
        switch textField {
        case fullnameTF:
            passportTF.becomeFirstResponder()
        case passportTF:
            mobileTF.becomeFirstResponder()
        case mobileTF:
            companyNameTF.becomeFirstResponder()
        case companyNameTF:
            companyMobileTF.resignFirstResponder()
        case companyMobileTF:
            textField.resignFirstResponder()
        default:
            textField.resignFirstResponder()
        }
        
        // Do not add a line break
        return false
    }
}

extension UpdateProfileViewController: UITextViewDelegate {
    func textViewDidBeginEditing(_ textView: UITextView) {
        textView.resignFirstResponder()
        self.view .endEditing(true)
        return
    }
    
    func textFieldDidEndEditing(_ textField: UITextField, reason: UITextField.DidEndEditingReason) {
        self.scrollView.setContentOffset(.zero, animated: true)
    }
}

// MARK: - Camera delegates
extension UpdateProfileViewController : UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        
        if let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
            // Image to Base64
            print("YES IMAGE")
            let pickedImageBase64 = pickedImage.toBase64(quality: 0.8)
            self.readingFaceFromPhoto(capturedImage: pickedImage)
            
            DispatchQueue.main.async {
                self.photoBase64 = pickedImageBase64
            }
        } else {
            print("NO IMAGE")
        }
        
        dismiss(animated: true, completion: nil)
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        dismiss(animated: true, completion: nil)
    }
}

//MARK: - Http Request
extension UpdateProfileViewController {
    private func updateProfile() {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized(), blurEffect: .dark)
        
        let service = Services.shared
        service.updateProfileService(requestObj: self.profileRequest, Success: { [weak self] (serverObj) in
            DispatchQueue.main.async {
                print("SUCCESS", serverObj.status!)
                // TODO: Later add server response in message
                self?.popBasicAlert(withTitle: Constants.updateProfileSuccess.localized(), message: nil, completion: {
                    self?.navigationController?.popViewController(animated: true)
                })
            }
        }) { (error) in
            DispatchQueue.main.async {
                if error == Constants.userAlreadyLoginRelogin.localized() {
                    self.popBasicAlert(withTitle: nil, message: error) {
                        AppData.resetDefaults()
                        self.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
                    }
                } else {
                    self.popBasicAlert(withTitle: nil, message: Utilities.isItCommonErrorMessage(errMsg: error) ? error : Constants.updateProfileFailed.localized())
                }
            }
        }
    }
}
