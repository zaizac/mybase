//
//  RegisterSummaryViewController.swift
//  MyCare
//
//  Created by Cookie on 4/15/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import UIKit

class RegisterSummaryViewController: UIViewController, Alert {
    
    @IBOutlet private weak var confirmBtn: CorneredButton!
    @IBOutlet private weak var featuredIV: UIImageView!
    
    
    @IBOutlet private weak var nameLbl: UILabel!
    @IBOutlet private weak var passportLbl: UILabel!
    @IBOutlet private weak var mobileLbl: UILabel!
    @IBOutlet private weak var genderLbl: UILabel!
    @IBOutlet private weak var mealTypeLbl: UILabel!
    
    //This one used for title only, do not assign any data.
    @IBOutlet private weak var nameTitleLbl: UILabel!
    @IBOutlet private weak var passportTitleLbl: UILabel!
    @IBOutlet private weak var mobileTitleLbl: UILabel!
    @IBOutlet private weak var genderTitleLbl: UILabel!
    @IBOutlet private weak var mealsTitleLbl: UILabel!
    
    @IBOutlet private weak var addressTitleLbl: UILabel!
    @IBOutlet private weak var addressContentLbl: UILabel!
    
    //~~~~~~~~~~~~~
    
    @IBOutlet private weak var featuredIVHolder: UIView!
    
    @IBOutlet private weak var mealsLbl: UILabel!
    @IBOutlet private weak var maritialStatusLbl: UILabel!
    @IBOutlet private weak var raceLbl: UILabel!
    @IBOutlet private weak var ageLbl: UILabel!
    @IBOutlet private weak var dobLbl: UILabel!
    @IBOutlet private weak var occupationLbl: UILabel!
    @IBOutlet private weak var placeOfBirthLbl: UILabel!
    @IBOutlet private weak var passportSV: UIStackView!
    @IBOutlet private weak var districtLbl: UILabel!
    @IBOutlet private weak var mukimLbl: UILabel!
    @IBOutlet private weak var postcodeLbl: UILabel!
    @IBOutlet private weak var stateLbl: UILabel!
    
    @IBOutlet private weak var maritialStatusTitleLbl: UILabel!
    @IBOutlet private weak var raceTitleLbl: UILabel!
    @IBOutlet private weak var ageTitleLbl: UILabel!
    @IBOutlet private weak var dobTitleLbl: UILabel!
    @IBOutlet private weak var occupationTitleLbl: UILabel!
    @IBOutlet private weak var placeOfBirthTitleLbl: UILabel!
    @IBOutlet private weak var districtTitleLbl: UILabel!
    @IBOutlet private weak var mukimTitleLbl: UILabel!
    @IBOutlet private weak var postcodeTitleLbl: UILabel!
    @IBOutlet private weak var stateTitleLbl: UILabel!
    
    @IBOutlet private weak var employerTitleLbl: UILabel!
    @IBOutlet private weak var employerContactTitleLbl: UILabel!
    @IBOutlet private weak var employerAddressTitleLbl: UILabel!
    
    @IBOutlet private weak var employerContentLbl: UILabel!
    @IBOutlet private weak var employerContactLbl: UILabel!
    @IBOutlet private weak var employerAddressLbl: UILabel!
    
    private var declarationAlert: RegisterDeclarationViewController?
    private var currentWindow: UIWindow?
    
    var userGenderID = 8
    
    var profileMaritalCode : Int?
    var profileRaceCode : String?
    var profileOccupationCode : Int?
    
    var profileAddress : String?
    var profileStateCode : String?
    var profileDistrictCode : String?
    var profileTownCode : String?
    var profileMukimCode : String?
    var profileZipCode : String?
    var profileMealTypeID : Int = 348
    //        348 = VEGETARIAN
    //        349 = NON VEGETARIAN
    
    var empName : String?
    var empContactNo : String?
    var empAddress : String?
    var empStateCode : String?
    var empTownCode : String?
    var empDistrictCode : String?
    var empMukimCode : String?
    var empZipCode  : String?
    
    // MARK: -
    override func viewDidLoad() {
        super.viewDidLoad()
        self.setupChildController()
        self.initialConfig()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.setText()
        
        self.readUserValuesFromDefaults()
    }
    
    private func initialConfig() {
        print("Saved Employer Info: ", AppData.registerEmployerInfo)
        
        title = Constants.profileInfo.localized()
        
        self.featuredIVHolder.backgroundColor = .clear
        self.featuredIVHolder.addShadow(withOpacity: 0.25, shadowRadius: 3, offset: CGSize(width: 0, height: 2.0))
        self.featuredIV.layer.cornerRadius = self.featuredIV.bounds.width / 2
        //self.featuredIV.layer.masksToBounds = true
        
        self.confirmBtn.backgroundColor = UIColor.init(named: "standardBtnColor")
        
        let cancelBtn = UIBarButtonItem(title: Constants.back.localized(), style: .plain, target: self, action: #selector(cancelHandler(_:)))
        navigationItem.leftBarButtonItem = cancelBtn
    }
    
    private func setupChildController() {
        guard let vc = UIStoryboard(name: REGISTRATION_ID, bundle: nil).instantiateViewController(withIdentifier: String(describing: RegisterDeclarationViewController.self)) as? RegisterDeclarationViewController else { return }
        self.declarationAlert = vc
        self.declarationAlert?.onDisagree = {
            self.removeDeclarationAlert()
        }
        
        self.declarationAlert?.onAgree = { [weak self] isAgree in
            if !isAgree {
                self?.popBasicAlert(withTitle: nil, message: Constants.pleaseReadAccept.localized())
            } else {
                //Do something..
                self?.removeDeclarationAlert(true)
                
                // TODO: Call Insert worker API
                self?.requestInsertWokerService()
            }
        }
    }
    
    private func setText() {
        nameTitleLbl.text = Constants.fullname.localized()
        mobileTitleLbl.text = Constants.mobileNo.localized()
        genderTitleLbl.text = Constants.gender.localized()
        mealsTitleLbl.text = Constants.mealType.localized()
        addressTitleLbl.text = Constants.address.localized()
        employerTitleLbl.text = Constants.companyName.localized()
        employerAddressTitleLbl.text = Constants.companyAddress.localized()
        employerContactTitleLbl.text = Constants.companyContactNo.localized()
        districtTitleLbl.text = Constants.district.localized()
        mukimTitleLbl.text = "Mukim"
        postcodeTitleLbl.text = Constants.postalCode.localized()
        stateTitleLbl.text = Constants.state.localized()
        
        ageTitleLbl.text = Constants.age.localized()
        raceTitleLbl.text = Constants.race.localized()
        occupationTitleLbl.text = Constants.occupation.localized()
        maritialStatusTitleLbl.text = Constants.maritalStatus.localized()
        dobTitleLbl.text = Constants.dob.localized()
        placeOfBirthTitleLbl.text = Constants.placeOfBirth.localized()
        
        if UIApplication.TARGET_NAME == "PAGOH" {
            passportTitleLbl.text = Constants.mykad.localized()
        } else if UIApplication.TARGET_NAME == "BH" {
            passportTitleLbl.text = Constants.passportNo.localized()
        }
        
        self.confirmBtn.setTitle(Constants.next.localized(), for: .normal)
    }
    
    private func readUserValuesFromDefaults() {
        // Screen 1st data
        // Read profile avatar image from Doc. directory
        let newFaceFileStatus = Utilities.checkPhotoExistsInDirectory(folderName: .ALL_FILES, fileName: .CROPPED_FACE)
        if newFaceFileStatus.isFileExist {
            DispatchQueue.global(qos: .background).async { [] in
                if let image = UIImage(contentsOfFile: newFaceFileStatus.urlString) {
                    DispatchQueue.main.async {
                        self.featuredIV.image = image
                        self.featuredIV.contentMode = .scaleAspectFill
                    }
                } else {
                    DispatchQueue.main.async {
                        self.featuredIV.image = nil
                        self.featuredIV.contentMode = .scaleAspectFill
                    }
                }
            }
        }
        
        /**
         let dictRegProfileInfo = [
         "name"              : fullnameTF.text ?? "",
         "passportNo"        : passportTF.text ?? "",
         "contactNo"         : mobileTF.text ?? "",
         "nationalityCode"   : nationalityCode,
         "genderId"          : genderId,
         "mealTypeId"        : mealTypeId,
         "dob"               : self.dobTF.text ?? "",
         "age"               : self.ageTF.text ?? "",
         "maritalStatus"     : selectedMaritalId[0],
         "raceCode"          : selectedRaceCode,
         "placeOfBirth"      : self.placeOfBirthTF.text ?? "",
         "occupationId"      : selectedOccupationId
         ] as [String : Any]
         
         */
        
        // Read all values from user defaults & show it in this screen
        var dictUserData        = AppData.registerProfileInfo
        let name                = dictUserData["name"] as? String ?? "N/A"
        let passport            = dictUserData["passportNo"] as? String ?? "N/A"
        let contactNo           = dictUserData["contactNo"] as? String ?? "N/A"
        nameLbl.text            = name.uppercased()
        passportLbl.text        = passport.uppercased()
        mobileLbl.text          = contactNo.uppercased()
        if let genderId = dictUserData["genderId"] as? Int {
            userGenderID    = genderId
            if genderId == 8 {
                genderLbl.text = Constants.male.localized()
            } else {
                genderLbl.text = Constants.female.localized()
            }
        }
        
        if let mealId = dictUserData["mealTypeId"] as? Int {
            self.profileMealTypeID = mealId
            self.mealTypeLbl.text = mealId == 348 ? Constants.veggie.localized() : Constants.nonVeggie.localized()
        }
        
        self.dobLbl.text            = dictUserData["dob"] as? String ?? "N/A"
        self.ageLbl.text            = dictUserData["age"] as? String ?? "N/A"
        self.maritialStatusLbl.text = dictUserData["maritalDesc"] as? String ?? "N/A"
        self.raceLbl.text           = dictUserData["raceDesc"] as? String ?? "N/A"
        
        let occu                    = dictUserData["occupationDesc"] as? String ?? ""
        self.occupationLbl.text     = occu == "" ? "N/A" : occu.uppercased()
        
        let placeOfBirth            = dictUserData["placeOfBirth"] as? String ?? ""
        self.placeOfBirthLbl.text   = placeOfBirth == "" ? "N/A" : placeOfBirth.uppercased()
        
        profileOccupationCode       = dictUserData["occupationId"]  as? Int ?? 0
        profileMaritalCode          = dictUserData["maritalCode"] as? Int ?? 0
        profileRaceCode             = dictUserData["raceCode"] as? String ?? ""
        
        /**
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
         */
        // Screen 2nd data:
        dictUserData            = AppData.registerAddressInfo
        profileAddress          = dictUserData["address"] as? String ?? ""
        profileStateCode        = dictUserData["stateCode"] as? String ?? ""
        profileDistrictCode     = dictUserData["districtCode"] as? String ?? ""
        profileTownCode         = dictUserData["townCode"] as? String ?? ""
        profileMukimCode        = dictUserData["mukimCode"] as? String ?? ""
        profileZipCode          = dictUserData["zipCode"] as? String ?? ""
        let tempState = dictUserData["stateDesc"] as? String ?? ""
        let tempMukim = dictUserData["districtDesc"] as? String ?? ""
        let tempDistrict = dictUserData["mukimDesc"] as? String ?? ""
        
        self.addressContentLbl.text = profileAddress == "" ? "N/A" : profileAddress?.uppercased()
        self.districtLbl.text = tempDistrict
        self.mukimLbl.text = tempMukim
        self.postcodeLbl.text = profileZipCode
        self.stateLbl.text = tempState
        
        //var fullAddress = ""
        //var stateDesc           = dictUserData["stateDesc"] as? String ?? ""
        //var districtDesc        = dictUserData["districtDesc"] as? String ?? ""
        //var townDesc            = dictUserData["townDesc"] as? String ?? ""
        //var mukimDesc           = dictUserData["mukimDesc"] as? String ?? ""
        
        /*if let address = profileAddress, address != "" {
         fullAddress = address
         }
         
         if stateDesc != "" {
         fullAddress = fullAddress + ", " + stateDesc
         }
         
         if districtDesc != "" {
         fullAddress = fullAddress + ", " + districtDesc
         }
         profileAddress              = fullAddress
         
         if let zipcode = profileZipCode, zipcode != "" {
         fullAddress = fullAddress + ", " + zipcode
         }
         self.addressContentLbl.text = profileAddress?.capitalized*/
        
        /**
         let dictEmployerInfo = [
         "empName"           : companyNameTF.text ?? "",
         "empAddr"           : address,
         "empCityCd"         : selectedTownsCode,
         "empStateCd"        : selectedStateCode,
         "empMukimCd"        : selectedMukimCode,
         "empDistrictCd"     : selectedDistrictCode,
         "empStateDesc"      : stateTF.text ?? "",
         "empTownDesc"       : townTF.text ?? "",
         "empDistrictDesc"   : districtTF.text ?? "",
         "empMukimDesc"      : mukimTF.text ?? "",
         "zipCode"           : postalCodeTF.text ?? "",
         "empContactNo"      : companyContactTF.text ?? ""
         ] as [String : Any]
         */
        
        if let occupationId = self.profileOccupationCode {
            if occupationId == 353 || occupationId == 354 {
                AppData.resetEmployerInfo()
            }
        }
        
        // Screen 3rd data: Employer
        dictUserData            = AppData.registerEmployerInfo
        let employer            = dictUserData["empName"] as? String ?? ""
        let empContact          = dictUserData["empContactNo"] as? String ?? ""
        empName                 = employer == "" ? nil : employer.uppercased()
        empContactNo            = empContact == "" ? nil : empContact.uppercased()
        empAddress              = dictUserData["empAddr"] as? String
        empStateCode            = dictUserData["empStateCd"] as? String
        empTownCode             = dictUserData["empCityCd"] as? String
        empDistrictCode         = dictUserData["empDistrictCd"] as? String
        empMukimCode            = dictUserData["empMukimCd"] as? String
        empZipCode              = dictUserData["zipCode"] as? String
        
        /**
         fullAddress = ""
         stateDesc               = dictUserData["empStateDesc"] as? String ?? ""
         districtDesc            = dictUserData["empDistrictDesc"] as? String ?? ""
         if let address = empAddress, address != "" {
         fullAddress = address
         }
         
         if stateDesc != "" {
         fullAddress = fullAddress + ", " + stateDesc
         }
         
         if districtDesc != "" {
         fullAddress = fullAddress + ", " + districtDesc
         }
         empAddress      = fullAddress.capitalized
         
         if let zipcode = empZipCode, zipcode != "" {
         fullAddress = fullAddress + ", " + zipcode
         }*/
        
        self.employerContentLbl.text = "N/A"
        self.employerContactLbl.text = "N/A"
        self.employerAddressLbl.text = "N/A"
        
        if let employerName = empName, employerName != "" {
            self.employerContentLbl.text = employerName
        }
        
        if let employerContact = empContactNo, employerContact != "" {
            self.employerContactLbl.text = employerContact
        }
        
        if let employerAddress = empAddress, employerAddress != "" {
            self.employerAddressLbl.text = employerAddress
        }
    }
    
    private func addDeclarationAlert() {
        guard let declarationAlert = self.declarationAlert else { return }
        self.currentWindow = UIApplication.shared.keyWindow
        guard let cWindow = self.currentWindow else { return }
        
        cWindow.addSubview(declarationAlert.view)
        declarationAlert.view.translatesAutoresizingMaskIntoConstraints = false
        declarationAlert.view.alpha = 0
        
        declarationAlert.view.topAnchor.constraint(equalTo: cWindow.topAnchor).isActive = true
        declarationAlert.view.bottomAnchor.constraint(equalTo: cWindow.bottomAnchor).isActive = true
        declarationAlert.view.leadingAnchor.constraint(equalTo: cWindow.leadingAnchor).isActive = true
        declarationAlert.view.trailingAnchor.constraint(equalTo: cWindow.trailingAnchor).isActive = true
        declarationAlert.willMove(toParent: cWindow.rootViewController)
        cWindow.rootViewController?.addChild(declarationAlert)
        declarationAlert.didMove(toParent: cWindow.rootViewController)
        
        UIView.animate(withDuration: 0.5) {
            declarationAlert.view.alpha = 1
        }
        
        print("ADD")
    }
    
    private func removeDeclarationAlert(_ fromRoot: Bool = false) {
        guard let declarationAlert = self.declarationAlert else { return }
        guard let cWindow = self.currentWindow else { return }
        
        UIView.animate(withDuration: 0.5, animations: {
            declarationAlert.view.alpha = 0
        }) { (_) in
            if declarationAlert.view.isDescendant(of: cWindow.rootViewController!.view) {
                declarationAlert.view.removeFromSuperview()
            }
            
            /*if fromRoot {
             self.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
             }*/
        }
        
        print("REMOVE")
    }
    
    @objc
    private func cancelHandler(_ sender: UIBarButtonItem) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction
    private func confirmAction(_ sender: UIButton) {
        self.addDeclarationAlert()
    }
}

// MARK: - API
extension RegisterSummaryViewController {
    func requestInsertWokerService() {
        let profileName     = self.nameLbl.text ?? ""
        let profilePassport = self.passportLbl.text ?? ""
        let mobileNo        = self.mobileLbl.text ?? ""
        let nationalityCode = UIApplication.TARGET_NAME == "PAGOH" ? "MYS" : "BGD"
        
        let image           = self.featuredIV.image
        
        var photoBase64 = ""
        if image != nil {
            photoBase64 = image?.toBase64(quality: 0.8) ?? ""
        }
        
        // TODO: - fwProfId
        // For just log without base64
        let insertWorkerRequest = InsertWorker(name: profileName, genderId: self.userGenderID, passportNo: profilePassport, contactNo: mobileNo, nationalityCode: nationalityCode, address: self.profileAddress!, stateCode: self.profileStateCode!, districtCode: self.profileDistrictCode!, countryCode: nationalityCode, mukimCode: self.profileMukimCode!, zipCode: self.profileZipCode!, salary: 0.0, empName: self.empName, empAddress: self.empAddress, empCityCode: self.empTownCode, empStateCode: self.empStateCode, empCountryCode: nationalityCode, empZipCode: self.empZipCode, empContactNo: self.empContactNo, mealTypeId: self.profileMealTypeID, photoBase64: photoBase64, cityCode: profileTownCode!, empDistrictCode: empDistrictCode, empMukimCode: empMukimCode, fwProfId: 0, maritalId: profileMaritalCode!, occupationId: profileOccupationCode!, placeOfBirth: placeOfBirthLbl.text ?? "", raceCode: profileRaceCode!, raceDesc: self.raceLbl.text!, age: self.ageLbl.text!, dob: self.dobLbl.text!, occupationDesc: self.occupationLbl.text!, deviceInfo: Utilities.createDeviceInfo())
        
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized(), blurEffect: .dark)
        
        let service = Services.shared
        service.insertWorkerService(requestObj: insertWorkerRequest, Success: { [weak self] (serverObj) in
            DispatchQueue.main.async {
                print("Insert worker status ", serverObj.status!)
                self?.popBasicAlert(withTitle: Constants.regSuccess.localized(), message: nil, completion: {
                    self?.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
                })
            }
            }, Failure: { (error) in
                DispatchQueue.main.async {
                    print("Error: ", error)
                    if error == Constants.userAlreadyLoginRelogin.localized() {
                        self.popBasicAlert(withTitle: nil, message: error) {
                            AppData.resetDefaults()
                            self.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
                        }
                    } else {
                        self.popBasicAlert(withTitle: nil, message: Utilities.isItCommonErrorMessage(errMsg: error) ? error : Constants.regFailed.localized())
                    }
                }
        })
    }
}
