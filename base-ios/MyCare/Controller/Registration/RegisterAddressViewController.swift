//
//  RegisterAddressViewController.swift
//  MyCare
//
//  Created by Cookie on 4/15/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces

class RegisterAddressViewController: UIViewController, Alert {
    
    @IBOutlet private weak var scrollView: UIScrollView!
    @IBOutlet private weak var contentHolder: UIView!
    @IBOutlet private weak var mapHolder: GMSMapView!
    @IBOutlet private weak var nextBtn: CorneredButton!
    
    @IBOutlet private weak var addressTV: UITextView!
    @IBOutlet private weak var postalCodeTF: UITextField!
    @IBOutlet private weak var stateTF: UITextField!
    @IBOutlet private weak var townTF: UITextField!
    @IBOutlet private weak var districtTF: UITextField!
    @IBOutlet private weak var mukimTF: UITextField!
    
    @IBOutlet private weak var requiredInfoLbl: UILabel!
    
    private var resultsViewController: GMSAutocompleteResultsViewController?
    private var searchController: UISearchController?
    private var activeField: UITextField?
    
    var customPickerRef         : PickerViewController!
    
    private var arrAllStatesModels: [AllStates] = []
    var arrAllStatesDesc: [String]  = []
    var selectedStateCode           = ""
    
    private var arrAllTownsModels: [AllCities] = []
    var arrAllTownsDesc: [String]  = []
    var selectedTownsCode          = ""
    
    private var arrAllDistrictsModels: [AllDistricts] = []
    var arrAllDistrictsDesc: [String]  = []
    var selectedDistrictCode           = ""
    
    private var arrAllMukimsModels: [AllMukim] = []
    var arrAllMukimsDesc: [String]  = []
    var selectedMukimCode           = ""
    
    private var keyboardHeight: CGFloat = 0.0
    
    // MARK: - View Lifecycle
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialConfig()
        self.setupSearchController()
    }
    
    override func viewWillAppear(_ animated:Bool) {
        super.viewWillAppear(animated)
        
        self.setText()
        
        //1  Add this observers to observe keyboard shown and hidden events
        let notificationCenter = NotificationCenter.default
        notificationCenter.addObserver(self, selector: #selector(adjustForKeyboard), name: UIResponder.keyboardWillHideNotification, object: nil)
        notificationCenter.addObserver(self, selector: #selector(adjustForKeyboard), name: UIResponder.keyboardWillChangeFrameNotification, object: nil)
        
        if addressTV.text == "" {
            UserLocationManager.shared.startLocationManager()
        }
        
        requestListOfStates()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        //2  Remove the observers added for keyboard from your ViewController
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillHideNotification, object: nil)
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillShowNotification, object: nil)
        
        // For safety
        UserLocationManager.shared.stopLocationManager()
    }
    
    private func initialConfig() {
        print("Saved Reg Profile Info: ", AppData.registerProfileInfo)
        
        title = Constants.address.localized()
        
        let dismissGesture = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))
        self.view.addGestureRecognizer(dismissGesture)
        
        // Location
        UserLocationManager.shared.delegate = self
        self.nextBtn.backgroundColor = UIColor.init(named: "standardBtnColor")
        
        self.addressTV.delegate = self
        self.stateTF.delegate = self
        self.districtTF.delegate = self
        self.townTF.delegate = self
        self.mukimTF.delegate = self
        self.postalCodeTF.delegate = self
        
        self.postalCodeTF.keyboardType = .numberPad
        
//        self.addCompulsoryMark(forView: self.addressTV)
//        self.addCompulsoryMark(forView: self.stateTF)
//        self.addCompulsoryMark(forView: self.townTF)
//        self.addCompulsoryMark(forView: self.districtTF)
//        self.addCompulsoryMark(forView: self.mukimTF)
//        self.addCompulsoryMark(forView: self.postalCodeTF)
        
        //self.addAsterisk(forTextView: self.addressTV)
        self.stateTF.addDropDown()
        self.townTF.addDropDown()
        self.districtTF.addDropDown()
        self.mukimTF.addDropDown()
    }
    
    private func setupSearchController() {
        //Settings for searchbar.
        
        let filter = GMSAutocompleteFilter()
        filter.type = .establishment
        filter.country = "MY"
        
        resultsViewController = GMSAutocompleteResultsViewController()
        resultsViewController?.autocompleteFilter = filter
        resultsViewController?.autocompleteBoundsMode = .bias
        resultsViewController?.delegate = self

        searchController = UISearchController(searchResultsController: resultsViewController)
        searchController?.searchResultsUpdater = resultsViewController
        searchController?.obscuresBackgroundDuringPresentation = false
        searchController?.searchBar.placeholder = Constants.searchAddress.localized()
        navigationItem.searchController = searchController
        definesPresentationContext = true
    }
    
    private func loadGoogleMap() {
        
        // Load / Refresh Map
        let locCoordinate = UserLocationManager.shared.currentLocation
        if let coordinate = locCoordinate {
            let currLocCoordinates = CLLocationCoordinate2D(latitude: coordinate.coordinate.latitude, longitude: coordinate.coordinate.longitude)

            let camera = GMSCameraPosition.camera(withLatitude: currLocCoordinates.latitude, longitude: currLocCoordinates.longitude, zoom: 16.0)
            mapHolder.camera = camera
            mapHolder.isMyLocationEnabled = false

            // Load Marker
            let marker = GMSMarker()
            marker.position = CLLocationCoordinate2D(latitude: currLocCoordinates.latitude, longitude: currLocCoordinates.longitude)
            marker.title = "Current Position"
            marker.map = mapHolder
        }
        
        // One time reading device location from willAppear - is enough
        // After map will update depends on google place autocomplete
        UserLocationManager.shared.stopLocationManager()
    }
    
    private func setText() {
        self.stateTF.placeholder = Constants.selectState.localized()
        self.townTF.placeholder = Constants.town.localized()
        self.districtTF.placeholder = Constants.selectDistrict.localized()
        self.mukimTF.placeholder = Constants.selectMukim.localized()
        self.postalCodeTF.placeholder = Constants.postalCode.localized()
        
        self.nextBtn.setTitle(Constants.next.localized(), for: .normal)
        
        self.requiredInfoLbl.text   = Constants.reqInfo.localized()
    }
        
    //MARK: - IBAction & Handlers
    @IBAction
    private func nextAction(_ sender: UIButton) {
        
        /*
        // Temp
        guard let employerVC = UIStoryboard(name: REGISTRATION_ID, bundle: nil).instantiateViewController(withIdentifier: String(describing: RegisterEmployerInfoViewController.self)) as? RegisterEmployerInfoViewController else { return }

        self.navigationController?.pushViewController(employerVC, animated: true)
        return
        // end
        */
        
        
        let validated = self.checkForValidation()
        
        if !validated.isSuccess {
            self.popBasicAlert(withTitle: nil, message: validated.message)
        } else {
            // Save values in user defaults in dict type
            let address = addressTV.text ?? ""
            
            let nationalityCode = UIApplication.TARGET_NAME == "PAGOH" ? "MYS" : "BGD"
            
            let dictRegAddress = [
                "address"           : address,
                "stateCode"         : selectedStateCode,
                "districtCode"      : selectedDistrictCode,
                "townCode"          : selectedTownsCode,
                "mukimCode"         : selectedMukimCode,
                "stateDesc"         : stateTF.text ?? "",
                "townDesc"          : townTF.text ?? "",
                "districtDesc"      : districtTF.text ?? "",
                "mukimDesc"         : mukimTF.text ?? "",
                "countryCode"       : nationalityCode,
                "zipCode"           : postalCodeTF.text ?? ""
                ] as [String : Any]
            AppData.registerAddressInfo = dictRegAddress as Dictionary<String, Any>
            
            let dictUserData = AppData.registerProfileInfo
            let occupationId = dictUserData["occupationId"] as? Int ?? 0
            
            //Check if the user if unemployed, retired or self employed. skip employer info.
            if occupationId == 353 || occupationId == 354 {
                guard let summaryVC = UIStoryboard(name: REGISTRATION_ID, bundle: nil).instantiateViewController(withIdentifier: String(describing: RegisterSummaryViewController.self)) as? RegisterSummaryViewController else { return }
                
                self.navigationController?.pushViewController(summaryVC, animated: true)
            } else {
                guard let employerVC = UIStoryboard(name: REGISTRATION_ID, bundle: nil).instantiateViewController(withIdentifier: String(describing: RegisterEmployerInfoViewController.self)) as? RegisterEmployerInfoViewController else { return }
                
                self.navigationController?.pushViewController(employerVC, animated: true)
            }
        }
    }
    
    @objc
    private func dismissKeyboard(_ sender: UITapGestureRecognizer) {
        self.view.endEditing(true)
    }
    
    /**
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
    */
    
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
    }
    
    //MARK: - Custom Method
    
    private func addCompulsoryMark(forView cView: UIView) {
        let lbl = UILabel()
        lbl.translatesAutoresizingMaskIntoConstraints = false
        lbl.textColor = .systemRed
        lbl.text = "*"
        lbl.font = UIFont.systemFont(ofSize: 18.0, weight: .semibold)
        
        self.view.addSubview(lbl)
        
        lbl.centerYAnchor.constraint(equalTo: cView.topAnchor).isActive = true
        lbl.centerXAnchor.constraint(equalTo: cView.leadingAnchor).isActive = true
    }
    
    //This one add the asterisk icon in the uitextview
    private func addAsterisk(forTextView textView: UITextView) {
        textView.textContainerInset = UIEdgeInsets(top: 8, left: 4, bottom: 6, right: 20)
    
        let imageView = UIImageView()
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.image = UIImage(named: "asteriskIcon")
        imageView.contentMode = .scaleAspectFit
        imageView.tintColor = .systemRed
        
        self.contentHolder.addSubview(imageView)
        imageView.topAnchor.constraint(equalTo: textView.topAnchor, constant: 8).isActive = true
        imageView.trailingAnchor.constraint(equalTo: textView.trailingAnchor, constant: -2).isActive = true
        imageView.heightAnchor.constraint(equalToConstant: 20).isActive = true
        imageView.widthAnchor.constraint(equalToConstant: 20).isActive = true
    }
    
    private func checkForValidation() -> (message: String, isSuccess: Bool) {
        
        guard let street = self.addressTV.text, street != "" else {
            return (Constants.enterAddress.localized(), false)
        }
        
        guard let state = self.stateTF.text, state != "" else {
            return (Constants.enterState.localized(), false)
        }
        
       /* guard let town = self.townTF.text, town != "" else {
            return (Constants.enterTown.localized(), false)
        }*/
        
        guard let district = self.districtTF.text, district != "" else {
            return (Constants.enterDistrict.localized(), false)
        }
        
        guard let mukim = self.mukimTF.text, mukim != "" else {
            return (Constants.pleaseSelectMukim.localized(), false)
        }
        
        guard let postal = self.postalCodeTF.text, postal != "" else {
            return (Constants.enterPostcode.localized(), false)
        }
        
        return ("", true)
    }
    
    //Getting the place details: - Country, postal code, locality and etc.
    //need to pass the location e.g: - CLLocation(latitude: -33.86, longitude: 151.20)
    private func getPlacemark(forLocation location: CLLocation, completionHandler: @escaping (CLPlacemark?, String?) -> ()) {
        let geocoder = CLGeocoder()

        geocoder.reverseGeocodeLocation(location, completionHandler: {
            placemarks, error in

            if let err = error {
                completionHandler(nil, err.localizedDescription)
            } else if let placemarkArray = placemarks {
                if let placemark = placemarkArray.first {
                    completionHandler(placemark, nil)
                } else {
                    completionHandler(nil, "Placemark was nil")
                }
            } else {
                completionHandler(nil, "Unknown error")
            }
        })

    }
    
    func scrollToBottom(){
        DispatchQueue.main.async {
            self.scrollView.contentInset = .zero
        }
    }
}

//MARK: - UITextField Delegate
extension RegisterAddressViewController: UITextFieldDelegate {
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        
        if textField == self.stateTF || textField == self.districtTF || textField == self.townTF || textField == self.mukimTF {
            self.view.endEditing(true)
        }
        return true
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        
        if (textField == districtTF || textField == townTF || textField == mukimTF) && stateTF.text == "" {
            self.popBasicAlert(withTitle: nil, message: Constants.pleaseFirstEnterState.localized())
            return
        }
        else if textField == stateTF {
            textField.resignFirstResponder()
            
            // Config String picker to list out States
            if self.arrAllStatesDesc.count > 0 {
                self.addCustomPicker()
                self.customPickerRef?.currentPickerType  = .e_PickerType_String
                self.customPickerRef?.arrayComponent = self.arrAllStatesDesc
                self.customPickerRef?.loadCustomPicker(pickerType: CustomPickerType.e_PickerType_String)
            } else {
                requestListOfStates()
            }
        } else if textField == townTF {
            textField.resignFirstResponder()
            
            // Call district service
            requestListOfTowns()
        } else if textField == districtTF {
            textField.resignFirstResponder()
            
            // Call district service
            if self.arrAllDistrictsDesc.count > 0 {
                // Load Picker
                self.addCustomPicker()
                self.customPickerRef?.currentPickerType  = .e_PickerType_String
                self.customPickerRef?.arrayComponent = self.arrAllDistrictsDesc
                self.customPickerRef?.loadCustomPicker(pickerType: CustomPickerType.e_PickerType_String)
            } else {
                self.requestListOfDistricts()
            }
        } else if textField == mukimTF && districtTF.text == "" {
            self.popBasicAlert(withTitle: nil, message: Constants.pleaseFirstEnterDistrict.localized())
            return
        } else if textField == mukimTF {
            textField.resignFirstResponder()
            
            if self.arrAllMukimsDesc.count > 0 {
                // Load Picker
                self.addCustomPicker()
                self.customPickerRef?.currentPickerType  = .e_PickerType_String
                self.customPickerRef?.arrayComponent = self.arrAllMukimsDesc
                self.customPickerRef?.loadCustomPicker(pickerType: CustomPickerType.e_PickerType_String)
            } else {
                self.requestListOfMukim()
            }
        }
        
        activeField = textField
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        activeField = nil
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        
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
        if textField == postalCodeTF && textField.text?.count == 5 {
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

extension RegisterAddressViewController: UITextViewDelegate {
    func textViewShouldBeginEditing(_ textView: UITextView) -> Bool {
        let pointInTable:CGPoint = textView.superview!.convert(textView.frame.origin, to: self.scrollView)
        var contentOffset:CGPoint = self.scrollView.contentOffset
        contentOffset.y  = pointInTable.y
        if let accessoryView = textView.inputAccessoryView {
            contentOffset.y -= accessoryView.frame.size.height
        }
        self.scrollView.contentOffset = contentOffset
        return true
    }
    
    func textViewDidEndEditing(_ textView: UITextView) {
        self.scrollView.setContentOffset(.zero, animated: true)
    }
    
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        let firstLowercaseCharRange = text.rangeOfCharacter(from: NSCharacterSet.lowercaseLetters)
        if let _ = firstLowercaseCharRange {
            if let existText = textView.text, existText.isEmpty {
                textView.text = text.uppercased()
            }
            else {
                let beginning = textView.beginningOfDocument
                if let start = textView.position(from: beginning, offset: range.location),
                    let end = textView.position(from: start, offset: range.length),
                    let replaceRange = textView.textRange(from: start, to: end) {
                    textView.replace(replaceRange, withText: text.uppercased())
                }
            }
            return false
        }
        return true
    }
}

extension RegisterAddressViewController: UISearchResultsUpdating {
  func updateSearchResults(for searchController: UISearchController) {
    // TODO
  }
}

// MARK: - API methods
extension RegisterAddressViewController {
    func requestListOfStates() {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized().uppercased(), blurEffect: .dark)
        
        let countryCode = "MYS" // MYS for all target. Dont change.
        let stateRequest = StateRequest(countryCode: countryCode, deviceInfo: Utilities.createDeviceInfo())
        let service = Services.shared
        service.listOfStatesService(requestObj: stateRequest, Success: { [weak self] (responses, serverObj) in
            DispatchQueue.main.async {
                
                if responses.count > 0 {
                    self?.arrAllStatesModels    = responses
                    
                    self?.arrAllStatesDesc.removeAll()
                    for state in responses {
                        let stateName = state.description
                        self?.arrAllStatesDesc.append(stateName)
                    }
                    
                    self?.arrAllStatesDesc.sort(by: { (value1: String, value2: String) -> Bool in
                        return value1 < value2
                    })
                }
                print("All States: ", self?.arrAllStatesDesc ?? [])
            }
        }, Failure: { (error) in
            DispatchQueue.main.async {
                self.popBasicAlert(withTitle: nil, message: error)
            }
        })
    }
    
    func requestListOfTowns() {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized().uppercased(), blurEffect: .dark)
        
        let townRequest = CityRequest(stateCode: selectedStateCode, deviceInfo: Utilities.createDeviceInfo())
        let service = Services.shared
        service.listOfCityService(requestObj: townRequest, Success: { [weak self] (responses, serverObj) in
            DispatchQueue.main.async {
                
                if responses.count > 0 {
                    self?.arrAllTownsModels    = responses
                    
                    self?.arrAllTownsDesc.removeAll()
                    for town in responses {
                        let townName = town.description
                        self?.arrAllTownsDesc.append(townName)
                    }
                    
                    self?.arrAllTownsDesc.sort(by: { (value1: String, value2: String) -> Bool in
                        return value1 < value2
                    })
                    
                    // Load Picker
                    if let allTowns = self?.arrAllTownsDesc, allTowns.count > 0 {
                        self?.addCustomPicker()
                        self?.customPickerRef?.currentPickerType  = .e_PickerType_String
                        self?.customPickerRef?.arrayComponent = allTowns
                        self?.customPickerRef?.loadCustomPicker(pickerType: CustomPickerType.e_PickerType_String)
                    }
                }
                print("All Towns: ", self?.arrAllTownsDesc ?? [])
            }
            }, Failure: { (error) in
                DispatchQueue.main.async {
                    self.popBasicAlert(withTitle: "", message: error)
                }
        })
    }
    
    func requestListOfDistricts() {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized().uppercased(), blurEffect: .dark)
        
        let districtRequest = DistrictRequest(stateCode: selectedStateCode, deviceInfo: Utilities.createDeviceInfo())
        let service = Services.shared
        service.listOfDistrictService(requestObj: districtRequest, Success: { [weak self] (responses, serverObj) in
            DispatchQueue.main.async {
                if responses.count > 0 {
                    self?.arrAllDistrictsModels    = responses
                    
                    self?.arrAllDistrictsDesc.removeAll()
                    for district in responses {
                        let districtName = district.description
                        self?.arrAllDistrictsDesc.append(districtName)
                    }
                    
                    self?.arrAllDistrictsDesc.sort(by: { (value1: String, value2: String) -> Bool in
                        return value1 < value2
                    })
                }
                print("All Districts: ", self?.arrAllDistrictsDesc ?? [])
            }
            }, Failure: { (error) in
                DispatchQueue.main.async {
                    self.popBasicAlert(withTitle: nil, message: error)
                }
        })
    }
    
    func requestListOfMukim() {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized().uppercased(), blurEffect: .dark)
        
        let townRequest = MukimRequest(stateCode: selectedStateCode, districtCode: selectedDistrictCode, deviceInfo: Utilities.createDeviceInfo())
        let service = Services.shared
        service.listOfMukimService(requestObj: townRequest, Success: { [weak self] (responses, serverObj) in
            DispatchQueue.main.async {
                if responses.count > 0 {
                    self?.arrAllMukimsModels    = responses
                    
                    self?.arrAllMukimsDesc.removeAll()
                    for mukim in responses {
                        let mukimName = mukim.description
                        self?.arrAllMukimsDesc.append(mukimName)
                    }
                    
                    self?.arrAllMukimsDesc.sort(by: { (value1: String, value2: String) -> Bool in
                        return value1 < value2
                    })
                }
                print("All Mukim: ", self?.arrAllMukimsDesc ?? [])
            }
            }, Failure: { (error) in
                DispatchQueue.main.async {
                    self.popBasicAlert(withTitle: "", message: error)
                }
        })
    }
}

extension RegisterAddressViewController : CustomPickerDelegate {
    // Delegates
    func itemPicked(item: AnyObject) {
        removeCustomPicker()
        
        let pickedItem = item as? String ?? ""
        print("Picked value: ", pickedItem)

        if activeField == stateTF {
            stateTF.text = pickedItem
            
            // Read State code for selected State
            for state in self.arrAllStatesModels {
                let stateName = state.description
                
                if stateName == pickedItem {
                    selectedStateCode = state.code
                    
                    requestListOfDistricts()
                    
                    break
                }
            }
            print("Selected state code: ", selectedStateCode)
        } else if activeField == townTF {
            townTF.text = pickedItem
            
            // Read district code for selected State
            for town in self.arrAllTownsModels {
                let townName = town.description
                
                if townName == pickedItem {
                    selectedTownsCode = town.code
                    break
                }
            }
            print("Selected town code: ", selectedTownsCode)
        } else if activeField == districtTF {
            districtTF.text = pickedItem
            
            // Read district code for selected State
            for district in self.arrAllDistrictsModels {
                let districtName = district.description
                
                if districtName == pickedItem {
                    selectedDistrictCode = district.code
                    
                    requestListOfMukim()
                    
                    break
                }
            }
            print("Selected district code: ", selectedStateCode)
        } else if activeField == mukimTF {
            mukimTF.text = pickedItem
            
            // Read district code for selected State
            for mukim in self.arrAllMukimsModels {
                let mukimName = mukim.description
                
                if mukimName == pickedItem {
                    selectedMukimCode = mukim.code
                    break
                }
            }
            print("Selected district code: ", selectedStateCode)
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

extension RegisterAddressViewController : LocationUpdateProtocol {
    // MARK: ~ Core Location
    // Negative cases
    func didChangeAuthorizationInLocationSettings(status: CLAuthorizationStatus) {
        switch status {
            case .restricted:
                askPermissionToEnableLocation()
            case .notDetermined:
                print("=== Ever first time asking permission. ===")
            case .denied:
                print("=== Don't allow / Cancel - Ever first time asking permission. ===")
            default:
                print("Permission allowed")
        }
    }
    
    func didFailWithErrorsWhenReadLocations(errors: String) {
        // Set Malaysia zoom out view
        print("=== Fail when reading locations ===")
        
        let authorizationStatus = CLLocationManager.authorizationStatus()
        if authorizationStatus == .restricted || authorizationStatus == .denied {
            askPermissionToEnableLocation()
        }
    }
    
    func askPermissionToEnableLocation() {
        DispatchQueue.main.async {
            let changePrivacySetting = Utilities.readProductNameFromPlist() + Constants.doesntHavePermissionPrivacySettings.localized()
            
            self.settingsAlert(withTitle: "", message: changePrivacySetting) {
                UIApplication.shared.open(URL(string: UIApplication.openSettingsURLString)!, options: [:], completionHandler: { (dismiss) in
                })
            }
        }
    }
    
    // Postive cases
    func readUpdatedLocationInfo() {
        let locCoordinate = UserLocationManager.shared.currentLocation
        if let coordinate = locCoordinate {
            let currLocCoordinates = CLLocationCoordinate2D(latitude: coordinate.coordinate.latitude, longitude: coordinate.coordinate.longitude)
            
            let dictCoordinates = [
                "Lat"   : coordinate.coordinate.latitude,
                "Longi" : coordinate.coordinate.longitude
            ]
            AppData.regProfileLocationInfo = dictCoordinates
            
            let geocoder = GMSGeocoder()
            geocoder.reverseGeocodeCoordinate(currLocCoordinates, completionHandler: { respond, error in
                guard let address = respond?.firstResult() else { return }
                
                // Way 1: Lines
                var completeAddress = ""
                if let linesOfAddress = address.lines {
                    for addressData in linesOfAddress {
                        completeAddress += addressData + "\n"
                    }
                }
                self.addressTV.text = completeAddress.uppercased()
                                
                if let postalCode = address.postalCode {
                    self.postalCodeTF.text = postalCode
                }
                
                // clear and refresh the map
                self.mapHolder.clear()
                
                // Refresh map
                self.loadGoogleMap()
                
            })
        }
    }
}

//MARK: - Googles Delegate.
// Handle the user's selection.
extension RegisterAddressViewController: GMSAutocompleteResultsViewControllerDelegate {
    func resultsController(_ resultsController: GMSAutocompleteResultsViewController,
                           didAutocompleteWith place: GMSPlace) {
        searchController?.isActive = false
        // Do something with the selected place.
        print("Place name: \(String(describing: place.name))")
        print("Place address: \(String(describing: place.formattedAddress))")
        print("Place attributions: \(String(describing: place.attributions))")
        
        // Address components
        self.addressTV.text = place.formattedAddress?.uppercased()
        for component in place.addressComponents! {
            print(component.types)
            print(component.name)
            
            if component.types.contains("postal_code") {
                self.postalCodeTF.text = component.name
            }
            
            // Check google given state is exist in array of States
            if component.types.contains("administrative_area_level_1") || component.types.contains("political") || component.types.contains("locality") {
                let adminArea = component.name
                if adminArea != "" && self.arrAllStatesDesc.count > 0 {
                    if self.arrAllStatesDesc.contains(adminArea.uppercased()) {
                        print("STATE FOUND")
                        self.stateTF.text = adminArea.uppercased()
                        
                        // Read State code for selected State
                        for state in self.arrAllStatesModels {
                            let stateName = state.description
                            
                            if stateName == adminArea.uppercased() {
                                selectedStateCode = state.code
                                
                                requestListOfDistricts()
                                
                                break
                            }
                        }
                    }
                }
            }
        }
        
        // Refresh Map
        let coordinate = place.coordinate
        
        let camera = GMSCameraPosition.camera(withLatitude: coordinate.latitude, longitude: coordinate.longitude, zoom: 16.0)
        mapHolder.camera = camera
        mapHolder.isMyLocationEnabled = false
        
        let marker = GMSMarker()
        marker.position = CLLocationCoordinate2D(latitude: coordinate.latitude, longitude: coordinate.longitude)
        marker.title = "Current Position"
        marker.map = mapHolder
    }
    
    func resultsController(_ resultsController: GMSAutocompleteResultsViewController,
                           didFailAutocompleteWithError error: Error){
        // TODO: handle the error.
        print("Error: ", error.localizedDescription)
    }
    
    // Turn the network activity indicator on and off again.
    func didRequestAutocompletePredictions(_ viewController: GMSAutocompleteViewController) {
        UIApplication.shared.isNetworkActivityIndicatorVisible = true
    }
    
    func didUpdateAutocompletePredictions(_ viewController: GMSAutocompleteViewController) {
        UIApplication.shared.isNetworkActivityIndicatorVisible = false
    }
}
