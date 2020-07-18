//
//  RegisterProfileViewController.swift
//  MyCare
//
//  Created by Cookie on 4/15/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import UIKit
import AVKit

class RegisterProfileViewController: UIViewController, Alert {
    
    @IBOutlet private weak var scrollView: UIScrollView!
    @IBOutlet private weak var contentHolder: UIView!
    @IBOutlet private weak var userIV: UIImageView!
    @IBOutlet private weak var cameraBtn: UIButton!
    @IBOutlet private weak var fullnameTF: UITextField!
    @IBOutlet private weak var passportTF: UITextField!
    @IBOutlet private weak var retypePassportTF: UITextField!
    @IBOutlet private weak var mobileTF: UITextField!
    @IBOutlet private weak var retypeMobileTF: UITextField!
    @IBOutlet private weak var genderTF: UITextField!
    @IBOutlet private weak var dobTF: UITextField!
    @IBOutlet private weak var mealTF: UITextField!
    
    // New
    @IBOutlet private weak var ageTF: UITextField!
    @IBOutlet private weak var maritalStatusTF: UITextField!
    @IBOutlet private weak var raceTF: UITextField!
    @IBOutlet private weak var placeOfBirthTF: UITextField!
    @IBOutlet private weak var occupationTF: UITextField!
    
    @IBOutlet private weak var requiredInfoLbl: UILabel!
    @IBOutlet private weak var optionalInfoLbl: UILabel!
    
    @IBOutlet private weak var nextBtn: CorneredButton!
    
    private var activeField: UITextField?
    private let imagePicker = UIImagePickerController()
    private var snappedPhoto: UIImage?
    
    var isPhotoAdded = false
    
    var customPickerRef         : PickerViewController!
    
    var dictOccupation : Dictionary<Int, String>?
    
    private var arrAllRacesModel : [AllRacesResponse] = []
    var arrAllRacesDesc: [String]  = []
    
    // MARK: - View Lifecycle
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialConfig()
        
        self.initialValues()
    }
    
    override func viewWillAppear(_ animated:Bool) {
        super.viewWillAppear(animated)
        
        self.setText()
        
        // Add this observers to observe keyboard shown and hidden events
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillBeHidden(aNotification:)), name: UIResponder.keyboardWillHideNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillShow(aNotification:)), name: UIResponder.keyboardWillShowNotification, object: nil)
        
        requestAllRaces()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        //2  Remove the observers added for keyboard from your ViewController
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillHideNotification, object: nil)
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillShowNotification, object: nil)
    }
    
    private func initialConfig() {
        
        title = Constants.profileInfo.localized()
        
        self.userIV.contentMode = .scaleAspectFill
        self.userIV.layer.cornerRadius = self.userIV.bounds.width / 2
        self.userIV.layer.masksToBounds = true
        self.userIV.layer.borderWidth = 2
        self.userIV.layer.borderColor = UIColor.blue.cgColor
        self.userIV.image             = UIImage.init(named: Constants.defaultImageName.localized())
        
        self.nextBtn.backgroundColor = UIColor.init(named: "standardBtnColor")
        self.cameraBtn.addCornerRadius(self.cameraBtn.frame.height / 2)
        
        self.fullnameTF.delegate = self
        self.passportTF.delegate = self
        self.retypePassportTF.delegate = self
        self.mobileTF.delegate = self
        self.retypeMobileTF.delegate = self
        self.genderTF.delegate = self
        self.dobTF.delegate = self
        self.mealTF.delegate = self
        
        self.ageTF.delegate = self
        self.maritalStatusTF.delegate = self
        self.raceTF.delegate = self
        self.placeOfBirthTF.delegate = self
        self.occupationTF.delegate = self
        
        self.passportTF.keyboardType = .numberPad
        self.retypePassportTF.keyboardType = .numberPad
        self.mobileTF.keyboardType = .numberPad
        self.retypeMobileTF.keyboardType = .numberPad
        self.ageTF.keyboardType = .numberPad
        
        self.imagePicker.delegate = self
        
        //Settings for datepicker.
        initialDatePickerConfig()
        
//        self.addCompulsoryMark(forView: self.fullnameTF)
//        self.addCompulsoryMark(forView: self.passportTF)
//        self.addCompulsoryMark(forView: self.retypePassportTF)
//        self.addCompulsoryMark(forView: self.mobileTF)
//        self.addCompulsoryMark(forView: self.retypeMobileTF)
//        self.addCompulsoryMark(forView: self.genderTF)
//        self.addCompulsoryMark(forView: self.mealTF)
//        self.addCompulsoryMark(forView: self.ageTF)
//        self.addCompulsoryMark(forView: self.dobTF)
//        self.addCompulsoryMark(forView: self.raceTF)
//        self.addCompulsoryMark(forView: self.maritalStatusTF)
        
        /**
        self.fullnameTF.addAsterisk()
        self.passportTF.addAsterisk()
        self.retypePassportTF.addAsterisk()
        self.mobileTF.addAsterisk()
        self.retypeMobileTF.addAsterisk()
        self.genderTF.addAsteriskDropdown()
        self.mealTF.addAsteriskDropdown()
        self.ageTF.addAsterisk()
        self.dobTF.addAsteriskDropdown()
        self.raceTF.addAsteriskDropdown()
        self.maritalStatusTF.addAsteriskDropdown()
        */
        
        self.genderTF.addDropDown()
        self.mealTF.addDropDown()
        self.dobTF.addDropDown()
        self.raceTF.addDropDown()
        self.maritalStatusTF.addDropDown()
        self.occupationTF.addDropDown()
        
        let dismissGesture = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))
        self.view.addGestureRecognizer(dismissGesture)
        
        guard let _ = self.navigationController else { return }
        
        let cancelBtn = UIBarButtonItem(title: Constants.cancel.localized(), style: .plain, target: self, action: #selector(cancelHandler(_:)))
        navigationItem.leftBarButtonItem = cancelBtn
    }
    
    func initialDatePickerConfig() {
        self.genderTF.inputView = UIView()
        self.mealTF.inputView = UIView()
        self.maritalStatusTF.inputView = UIView()
        self.raceTF.inputView = UIView()
        self.occupationTF.inputView = UIView()
    }
    
    private func initialValues() {
        dictOccupation = [
            350 : "GOVERNMENT SECTOR",
            351 : "PRIVATE SECTOR",
            352 : "SELF EMPLOYED",
            353 : "GOVERNMENT PENSIONER",
            354 : "NOT WORKING"
        ]
    }
    
    private func setText() {
        self.fullnameTF.placeholder = Constants.fullname.localized()
        self.mobileTF.placeholder = Constants.mobileNo.localized()
        self.retypeMobileTF.placeholder = Constants.retypePhoneNo.localized()
        self.genderTF.placeholder = Constants.selectGender.localized()
        self.dobTF.placeholder = Constants.dob.localized()
        self.mealTF.placeholder = Constants.selectMealType.localized()
        
        self.maritalStatusTF.placeholder = Constants.selectMarital.localized()
        self.raceTF.placeholder = Constants.selectRace.localized()
        self.occupationTF.placeholder = Constants.selectOccupation.localized()
        
        self.ageTF.placeholder = Constants.age.localized()
        self.placeOfBirthTF.placeholder = Constants.placeOfBirth.localized()
        
        self.nextBtn.setTitle(Constants.next.localized(), for: .normal)
        
        self.requiredInfoLbl.text   = Constants.reqInfo.localized()
        self.optionalInfoLbl.text   = Constants.optInfo.localized()
        
        if UIApplication.TARGET_NAME == "PAGOH" {
            self.passportTF.placeholder = Constants.mykad.localized()
            self.retypePassportTF.placeholder = Constants.reenterMykadNo.localized()
        } else if UIApplication.TARGET_NAME == "BH" {
            self.passportTF.placeholder = Constants.passportNo.localized()
        }
    }
    
    //MARK: - IBAction & Handler
    @IBAction
    private func choosePhotoAction(_ sender: UIButton) {
        self.showPhotoPickingOption()
    }
    
    @objc
    private func cancelHandler(_ sender: UIBarButtonItem) {
        self.confirmationAlert(withTitle: "PagohCare", message: Constants.exitRegistration.localized()) {
            self.dismiss(animated: true, completion: nil)
        }
    }
    
    @IBAction
    private func nextAction(_ sender: UIButton) {
        
        /*
        // Temp
        guard let addressVC = UIStoryboard(name: REGISTRATION_ID, bundle: nil).instantiateViewController(withIdentifier: String(describing: RegisterAddressViewController.self)) as? RegisterAddressViewController else { return }
        self.navigationController?.pushViewController(addressVC, animated: true)
        return
        // end
        */
        
        let validated = self.checkForValidation()
        
        if !validated.isSuccess {
            self.popBasicAlert(withTitle: nil, message: validated.message)
        } else {
            // Save all user info in dict & write it in user defaults
            
            var genderId = 8 // male - default
            if genderTF.text?.uppercased() == "FEMALE" || genderTF.text?.uppercased() == "PEREMPUAN" {
                genderId = 9
            }
            
            var mealTypeId = 0
            if mealTF.text?.lowercased() == "vegetarian" {
                mealTypeId = 348
            } else if mealTF.text?.lowercased() == "non-vegetarian" {
                mealTypeId = 349
            }
            
            // Marital Status
            var selectedMaritalStatusId = 0
            if let selectedMaritalText = self.maritalStatusTF.text {
                switch selectedMaritalText {
                    case Constants.single.localized().uppercased():
                        selectedMaritalStatusId = 324
                    case Constants.married.localized().uppercased():
                        selectedMaritalStatusId = 325
                    case Constants.divorced.localized().uppercased():
                        selectedMaritalStatusId = 328
                    case Constants.widowed.localized().uppercased():
                        selectedMaritalStatusId = 327
                    default:
                        break
                }
            }
        
            var selectOccupationID = 0
            if let selectedOccupation = self.occupationTF.text {
                switch selectedOccupation {
                    case Constants.governSector.localized():
                        selectOccupationID = 350
                    case Constants.privateSector.localized():
                        selectOccupationID = 351
                    case Constants.selfEmployed.localized():
                        selectOccupationID = 352
                    case Constants.governPensioner.localized():
                        selectOccupationID = 353
                    case Constants.notWorking.localized():
                        selectOccupationID = 354
                    default:
                        break
                }
            }
            
            // Read district code for selected State
            var selectedRaceCode = ""
            
            switch self.raceTF.text {
                case Constants.bumiputera.localized():
                    selectedRaceCode = "01"
                case Constants.chinese.localized():
                    selectedRaceCode = "02"
                case Constants.indians.localized():
                    selectedRaceCode = "03"
                case Constants.otherRaces.localized():
                    selectedRaceCode = "04"
                default:
                    selectedRaceCode = ""
            }
            print("Selected district code: ", selectedRaceCode)
                        
            var dictRegProfileInfo = [
                "name"              : fullnameTF.text ?? "",
                "passportNo"        : passportTF.text ?? "",
                "contactNo"         : mobileTF.text ?? "",
                "genderId"          : genderId,
                "mealTypeId"        : mealTypeId,
                "dob"               : self.dobTF.text ?? "",
                "age"               : self.ageTF.text ?? "",
                ] as [String : Any]
            
            dictRegProfileInfo["maritalCode"]       = selectedMaritalStatusId
            dictRegProfileInfo["raceCode"]          = selectedRaceCode
            dictRegProfileInfo["occupationId"]      = selectOccupationID
            dictRegProfileInfo["maritalDesc"]       = self.maritalStatusTF.text ?? ""
            dictRegProfileInfo["raceDesc"]          = self.raceTF.text ?? ""
            dictRegProfileInfo["placeOfBirth"]      = self.placeOfBirthTF.text ?? ""
            dictRegProfileInfo["occupationDesc"]    = self.occupationTF.text ?? ""
            
            AppData.registerProfileInfo = dictRegProfileInfo as Dictionary<String, Any>
            
            guard let addressVC = UIStoryboard(name: REGISTRATION_ID, bundle: nil).instantiateViewController(withIdentifier: String(describing: RegisterAddressViewController.self)) as? RegisterAddressViewController else { return }
            
            self.navigationController?.pushViewController(addressVC, animated: true)
        }
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
    
    @objc
    private func findAge() {
        let now = Date()
        let birthday: Date = Utilities.changeGivenStringDateAsDate(inputDateString: self.dobTF.text!, OutputFormat: "dd/MM/yyyy")
        let calendar = Calendar.current
        let ageComponents = calendar.dateComponents([.year], from: birthday, to: now)
        let intUserAge = ageComponents.year!
        self.ageTF.text = String(intUserAge)
    }
    
    //MARK: - Custom Method
    
    private func addCompulsoryMark(forView cView: UIView) {
        let lbl = UILabel()
        lbl.translatesAutoresizingMaskIntoConstraints = false
        lbl.textColor = .systemRed
        lbl.text = "*"
        lbl.font = UIFont.systemFont(ofSize: 18.0, weight: .semibold)
        
        self.contentHolder.addSubview(lbl)
        
        lbl.centerYAnchor.constraint(equalTo: cView.topAnchor).isActive = true
        lbl.centerXAnchor.constraint(equalTo: cView.leadingAnchor).isActive = true
    }
    
    private func checkForValidation() -> (message: String, isSuccess: Bool) {
        
        if !isPhotoAdded {
            return (Constants.uploadPhotoIdentifyPurpose.localized(), false)
        }
        
        guard let fullname = self.fullnameTF.text, fullname != "" else {
            return (Constants.enterFullname.localized(), false)
        }
        
        guard let passport = self.passportTF.text, passport != "" else {
            var alertMsg = ""
            if UIApplication.TARGET_NAME == "PAGOH" {
                alertMsg = Constants.enterMykadNo.localized()
            } else if UIApplication.TARGET_NAME == "BH" {
                alertMsg = Constants.enterPassportNo.localized()
            }
            return (alertMsg, false)
        }
        
        if passport.count != 12 {
            return (Constants.myKadNumberLimitation.localized(), false)
        }
        
        guard let reTypePassport = self.retypePassportTF.text, reTypePassport != "" else {
            return (Constants.reenterMykadNo.localized(), false)
        }
        
        // TODO: Localized
        guard passport == reTypePassport else {
            return (Constants.myKadCharLimit.localized(), false)
        }
        
        guard let mobileNumber = self.mobileTF.text, mobileNumber != "" else {
            return (Constants.enterMobileNo.localized(), false)
        }
        
        guard mobileNumber.isPhoneNumber else {
            return (Constants.enterMobileNo.localized(), false)
        }
        
        // TODO: Localized
        if mobileNumber.count < 10 {
            return (Constants.mobileNoLimit.localized(), false)
        }
        
        guard let retypeMobileNumber = self.retypeMobileTF.text, retypeMobileNumber != "" else {
            return (Constants.retypeMobileNo.localized(), false)
        }
        
        guard mobileNumber == retypeMobileNumber else {
            return (Constants.notSameReenterMobileNo.localized(), false)
        }
        
        guard let meal = self.mealTF.text, meal != "" else {
            return (Constants.pleaseSelectMealType.localized(), false)
        }
        
        guard let gender = self.genderTF.text, gender != "" else {
            return (Constants.pleaseSelectGender.localized(), false)
        }
        
        guard let dob = self.dobTF.text, dob != "" else {
            return (Constants.enterDOB.localized(), false)
        }
        
        guard let age = self.ageTF.text, age != "" else {
            return (Constants.pleaseEnterAge.localized(), false)
        }
        
        guard let marital = self.maritalStatusTF.text, marital != "" else {
            return (Constants.pleaseSelectMarital.localized(), false)
        }
        
        guard let race = self.raceTF.text, race != "" else {
            return (Constants.pleaseSelectRace.localized(), false)
        }
        
        guard let occupation = self.occupationTF.text, occupation != "" else {
            return (Constants.pleaseSelectOccupation.localized(), false)
        }
        
        return ("", true)
    }
    
    private func showGenderList(forTextField textField: UITextField) {
        
        // Constants.gender.localized()
        let alertSheet = UIAlertController(title: nil, message: Constants.selectOption.localized(), preferredStyle: .actionSheet)
        
        let maleAction = UIAlertAction(title: Constants.male.localized(), style: .default) { (action) in
            //Do something..
            textField.text = Constants.male.localized()
            textField.endEditing(true)
        }
        
        let femaleAction = UIAlertAction(title: Constants.female.localized(), style: .default) { (action) in
            //Do something..
            textField.text = Constants.female.localized()
            textField.endEditing(true)
        }
        
        /**let maleFemaleAction = UIAlertAction(title: "Male & Female", style: .default) { (action) in
            //Do something..
            textField.text = "Male & Female"
        }*/
        
        let cancelAction = UIAlertAction(title: Constants.cancel.localized(), style: .cancel) { (action) in
            textField.endEditing(true)
        }
        
        alertSheet.addAction(maleAction)
        alertSheet.addAction(femaleAction)
        //alertSheet.addAction(maleFemaleAction)
        alertSheet.addAction(cancelAction)
        
        self.present(alertSheet, animated: true)
    }
    
    private func showMealList(forTextField textField: UITextField) {
        
        let alertSheet = UIAlertController(title: nil, message: Constants.selectMealTypeAlertTitle.localized(), preferredStyle: .actionSheet)
        
        let veggieAction = UIAlertAction(title: Constants.veggie.localized(), style: .default) { (action) in
            //Do something..
            textField.text = Constants.veggie.localized()
            textField .endEditing(true)
        }
        
        let nonVeggieAction = UIAlertAction(title: Constants.nonVeggie.localized(), style: .default) { (action) in
            //Do something..
            textField.text = Constants.nonVeggie.localized()
            textField .endEditing(true)
        }
        
        let cancelAction = UIAlertAction(title: Constants.cancel.localized(), style: .cancel) { (action) in
            //Do something..
            textField .endEditing(true)
        }
        
        alertSheet.addAction(veggieAction)
        alertSheet.addAction(nonVeggieAction)
        alertSheet.addAction(cancelAction)
        
        self.present(alertSheet, animated: true)
    }
    
    // MARK: - Camera
    private func showPhotoPickingOption() {
        let alert = UIAlertController(title: Constants.workerPhotoTitle.localized(), message: Constants.selectOption.localized(), preferredStyle: .actionSheet)
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
    func readImageFromDocumentDirectory(urlString: String) {
        
        Spinner.shared.show(onView: self.view, text: Constants.readingFace.localized(), blurEffect: .dark)
        
        DispatchQueue.global(qos: .background).async { [weak self] in
            if let capturedImage = UIImage(contentsOfFile: urlString) {
                // Pass image to face cropper
                capturedImage.face.crop({ (result) in
                    switch result {
                    case .success(let faces):
                        DispatchQueue.main.async {
                            if faces.count >= 1 {
                                // Save this image in document directory
                                if Utilities.saveImageFileInDirectory(directoryName: .ALL_FILES, fileName: .CROPPED_FACE, capturedImage: faces[0], imgOutputQuality: 0.7) {
                                    print("WRITE PHOTO IN DOC DIRECTORY - SUCCESS")
                                    self?.userIV.image = faces[0]
                                    self?.isPhotoAdded = true
                                } else {
                                    print("USER FACE PHOTO NOT WRITTEN")
                                    self?.popBasicAlert(withTitle: Constants.failed.localized(), message: Constants.captureFaceAgain.localized())
                                }
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
            } else {
                Spinner.shared.hide()
            }
        }
    }
 }

// MARK: - Textfield delegate
extension RegisterProfileViewController: UITextFieldDelegate {
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        
        if textField == self.genderTF || textField == self.mealTF || textField == self.maritalStatusTF
        || textField == self.raceTF || textField == self.occupationTF || textField == self.dobTF {
            activeField = textField
            
            switch textField {
                case self.dobTF:
                    self.addCustomPicker()
                    self.customPickerRef?.currentPickerType  = .e_PickerType_Date
                    self.customPickerRef?.loadCustomPicker(pickerType: CustomPickerType.e_PickerType_Date)
                    
                    let calendar = Calendar(identifier: .gregorian)
                    let currentDate = Date()
                    var components = DateComponents()
                    components.calendar = calendar

                    components.year = -19 // if age is min 18
                    components.month = 12
                    let maxDate = calendar.date(byAdding: components, to: currentDate)!

                    components.year = -150
                    let minDate = calendar.date(byAdding: components, to: currentDate)!
                    
                    self.customPickerRef.customDatePicker.datePickerMode = .date
                    self.customPickerRef.customDatePicker.minimumDate    = minDate
                    self.customPickerRef.customDatePicker.maximumDate    = maxDate
                    self.customPickerRef.customDatePicker.date           = maxDate
                    if let dateString = self.dobTF.text, dateString != "" {
                        let lastSelectedDate = Utilities.changeGivenStringDateAsDate(inputDateString: dateString, OutputFormat: "dd/MM/yyyy")
                        self.customPickerRef.customDatePicker.date           = lastSelectedDate
                    }
                case self.genderTF:
                    //activeField = textField
                    self.showGenderList(forTextField: textField)
                    //return // important
                case self.mealTF:
                    //activeField = textField
                    self.showMealList(forTextField: textField)
                    //return // important
                case self.maritalStatusTF:
                    //textField .resignFirstResponder()
                    
                    let arrAllMaritalStatus = [
                        Constants.single.localized().uppercased(),
                        Constants.married.localized().uppercased(),
                        Constants.widowed.localized().uppercased(),
                        Constants.divorced.localized().uppercased()
                    ]
                    
                    if arrAllMaritalStatus.count > 0 {
                        self.addCustomPicker()
                        self.customPickerRef?.currentPickerType  = .e_PickerType_String
                        self.customPickerRef?.arrayComponent = arrAllMaritalStatus
                        self.customPickerRef?.loadCustomPicker(pickerType: CustomPickerType.e_PickerType_String)
                    }
                case self.raceTF:
                    //textField .resignFirstResponder()
                    if self.arrAllRacesDesc.count > 0 {
                        // Sort array
                        self.arrAllRacesDesc.sort(by: { (value1, value2) -> Bool in
                            return value1 < value2
                        })
                        
                        self.addCustomPicker()
                        self.customPickerRef?.currentPickerType  = .e_PickerType_String
                        self.customPickerRef?.arrayComponent = self.arrAllRacesDesc
                        self.customPickerRef?.loadCustomPicker(pickerType: CustomPickerType.e_PickerType_String)
                    } else {
                        requestAllRaces()
                    }
                case self.occupationTF:
                    //textField .resignFirstResponder()
                    
                    let arrAllOccupation = [
                        Constants.governSector.localized(),
                        Constants.privateSector.localized(),
                        Constants.selfEmployed.localized(),
                        Constants.governPensioner.localized(),
                        Constants.notWorking.localized()
                    ]
                    
                    if arrAllOccupation.count > 0 {
                        self.addCustomPicker()
                        self.customPickerRef?.currentPickerType  = .e_PickerType_String
                        self.customPickerRef?.arrayComponent = arrAllOccupation
                        self.customPickerRef?.loadCustomPicker(pickerType: CustomPickerType.e_PickerType_String)
                    }
                default:
                    break
            }
            return false
        }
        
        return true
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        activeField = textField // always update at end
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
            retypeMobileTF.becomeFirstResponder()
        case retypeMobileTF:
            textField.resignFirstResponder()
        default:
            textField.resignFirstResponder()
        }
        
        // Do not add a line break
        return false
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
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
        
        // 2
        if ((textField == passportTF || textField == retypePassportTF) && textField.text?.count == 12) {
            return false
        } else if ((textField == mobileTF || textField == retypeMobileTF) && textField.text?.count == 11) {
            return false
        } else if textField == ageTF && textField.text?.count == 3 {
            return false
        }
        
        // 3 Only Capitalized
        let firstLowercaseCharRange = string.rangeOfCharacter(from: NSCharacterSet.lowercaseLetters)
        if let _ = firstLowercaseCharRange {
            if let text = textField.text, text.isEmpty {
                textField.text = string.uppercased()
            }
            else {
                let beginning = textField.beginningOfDocument
                if let start = textField.position(from: beginning, offset: range.location),
                    let end = textField.position(from: start, offset: range.length),
                    let replaceRange = textField.textRange(from: start, to: end) {
                    textField.replace(replaceRange, withText: string.uppercased())
                }
            }
            return false
        }
        return true
    }

}

// MARK: - Services
extension RegisterProfileViewController {
    func requestAllRaces() {
        // No need
        //Spinner.shared.show(onView: self.view, text: Constants.loading.localized())
        
        let service = Services.shared
        service.listOfRacesService(Success: { [weak self] (responses, serverObj) in
            DispatchQueue.main.async {
                self?.arrAllRacesModel = responses
                
                self?.arrAllRacesDesc.removeAll()
                for race in responses {
                    let raceCode = race.raceCode
                    switch raceCode {
                    case "01":
                        let raceName = Constants.malay.localized()
                        self?.arrAllRacesDesc.append(raceName)
                    case "02":
                        let raceName = Constants.chinese.localized()
                        self?.arrAllRacesDesc.append(raceName)
                    case "03":
                        let raceName = Constants.indians.localized()
                        self?.arrAllRacesDesc.append(raceName)
                    case "99":
                        let raceName = Constants.otherRaces.localized()
                        self?.arrAllRacesDesc.append(raceName)
                    default:
                        let raceName = race.raceDesc
                        self?.arrAllRacesDesc.append(raceName)
                    }
                }
            }
        }, Failure: { (error) in
            DispatchQueue.main.async {
                //self.popBasicAlert(withTitle: nil, message: error)
            }
        })
    }
}

// MARK: - Camera delegates
extension RegisterProfileViewController : UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        
        if let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
            guard let capturedImage = pickedImage.fixedOrientation() else {
                self.popBasicAlert(withTitle: Constants.failed.localized(), message: Constants.captureFaceAgain.localized())
                return
            }
            
            if Utilities.saveImageFileInDirectory(directoryName: .ALL_FILES, fileName: .REG_AVATAR, capturedImage: capturedImage, imgOutputQuality: 0.7) {
                print("WRITE PHOTO IN DOC DIRECTORY - SUCCESS")
            } else {
                print("REG PROFILE AVATAR PHOTO WRITTEN")
            }
            
            // Read image from document directory
            let directory = Utilities.checkPhotoExistsInDirectory(folderName: .ALL_FILES, fileName: .REG_AVATAR)
            if directory.isFileExist {
                self.readImageFromDocumentDirectory(urlString: directory.urlString)
            } else {
                print("+++++++++ SNAP PHOTO IS NOT EXIST ++++++++++++++")
                self.popBasicAlert(withTitle: Constants.failed.localized(), message: Constants.captureFaceAgain.localized())
            }
        }
        
        dismiss(animated: true, completion: nil)
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        dismiss(animated: true, completion: nil)
    }
}

extension RegisterProfileViewController : CustomPickerDelegate {
    // Delegates
    func itemPicked(item: AnyObject) {
        removeCustomPicker()
        
        if let pickedItem = item as? String, pickedItem != "" {
            print("Picked value: ", pickedItem)
            switch activeField {
                case maritalStatusTF:
                    self.maritalStatusTF.text = pickedItem
                case raceTF:
                    self.raceTF.text = pickedItem
                case occupationTF:
                    self.occupationTF.text = pickedItem
                default:
                    break
            }
        } else {
            let pickerDateValue     = item as! Date
            let strPickedDate       = Utilities.changeGivenDateAsString(inputDate: pickerDateValue, OutputFormat: "dd/MM/yyyy")
            self.dobTF.text         = strPickedDate
            
            self.findAge()
        }
    }
    
    func pickerCancelled() {
        removeCustomPicker()
    }
    
    // CustomPicker Local Methods
    func createCustomPickerInstance()
    {
        customPickerRef                 = Utilities.getCustomPickerInstance()
        customPickerRef.delegate        = self
        customPickerRef.totalComponents = 1
    }
    
    func addCustomPicker() {
        createCustomPickerInstance()
        self.view.addSubview(customPickerRef.view)
        
        // disable keyboard
        self.view.endEditing(true)
    }
    
    func removeCustomPicker() {
        if let picker = customPickerRef {
            picker.view.removeFromSuperview()
            customPickerRef  = nil
        }
    }
}
