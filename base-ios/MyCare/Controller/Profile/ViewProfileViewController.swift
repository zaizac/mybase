//
//  ViewProfileViewController.swift
//  PagohCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import UIKit

class ViewProfileViewController: UIViewController, Alert {

    @IBOutlet private weak var updateBtn: CorneredButton!
    @IBOutlet private weak var featuredIV: UIImageView!
    @IBOutlet private weak var featuredIVHolder: UIView!
    
    @IBOutlet private weak var nameLbl: UILabel!
    @IBOutlet private weak var passportLbl: UILabel!
    @IBOutlet private weak var mobileLbl: UILabel!
    @IBOutlet private weak var genderLbl: UILabel!
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
    
    //This one used for title only, do not assign any data.
    @IBOutlet private weak var nameTitleLbl: UILabel!
    @IBOutlet private weak var passportTitleLbl: UILabel!
    @IBOutlet private weak var mobileTitleLbl: UILabel!
    @IBOutlet private weak var genderTitleLbl: UILabel!
    @IBOutlet private weak var mealsTitleLbl: UILabel!
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
    
    @IBOutlet private weak var addressTitleLbl: UILabel!
    @IBOutlet private weak var addressContentLbl: UILabel!
    
    @IBOutlet private weak var employerTitleLbl: UILabel!
    @IBOutlet private weak var employerContactTitleLbl: UILabel!
    @IBOutlet private weak var employerAddressTitleLbl: UILabel!
    @IBOutlet private weak var employerContentLbl: UILabel!
    @IBOutlet private weak var employerContactLbl: UILabel!
    @IBOutlet private weak var employerAddressLbl: UILabel!
    
    private var profile: ProfileResponse!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialConfig()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        self.setText()
        
        self.fetchProfile()
    }
    
    private func initialConfig() {
        title = Constants.profileInfo.localized()
        
        self.featuredIVHolder.backgroundColor = .clear
        self.featuredIVHolder.addShadow(withOpacity: 0.25, shadowRadius: 3, offset: CGSize(width: 0, height: 2.0))
        self.featuredIV.addCornerRadius(self.featuredIV.frame.height / 2)
        
        self.updateBtn.isEnabled = false
        self.updateBtn.backgroundColor = UIColor.init(named: "standardBtnColor")
        
        guard let _ = self.navigationController else { return }
        
        let backBtn = UIBarButtonItem(title: Constants.close.localized().uppercased(), style: .plain, target: self, action: #selector(backBtnHandler(_:)))
        navigationItem.leftBarButtonItem = backBtn
    }
    
    private func setText() {
        nameTitleLbl.text = Constants.fullname.localized()
        mobileTitleLbl.text = Constants.mobileNo.localized()
        genderTitleLbl.text = Constants.gender.localized()
        mealsTitleLbl.text = Constants.mealType.localized()
        addressTitleLbl.text = Constants.address.localized()
        employerTitleLbl.text = Constants.companyName.localized()
        employerContactTitleLbl.text = Constants.companyContactNo.localized()
        employerAddressTitleLbl.text = Constants.companyAddress.localized()
        
        maritialStatusTitleLbl.text = Constants.maritalStatus.localized()
        raceTitleLbl.text = Constants.race.localized()
        ageTitleLbl.text = Constants.age.localized()
        dobTitleLbl.text = Constants.dob.localized()
        occupationTitleLbl.text = Constants.occupation.localized()
        placeOfBirthTitleLbl.text = Constants.placeOfBirth.localized()
        districtTitleLbl.text = Constants.district.localized()
        mukimTitleLbl.text = "Mukim"
        postcodeTitleLbl.text = Constants.postalCode.localized()
        stateTitleLbl.text = Constants.state.localized()
        
        passportTitleLbl.text = UIApplication.TARGET_NAME == "PAGOH" ? Constants.mykad.localized() : Constants.passportNo.localized()
        
        self.updateBtn.setTitle(Constants.updateProfile.localized().uppercased(), for: .normal)
        
        self.featuredIV.image = UIImage.init(named: Constants.defaultImageName.localized())
    }
    
    //MARK: - IBAction & HAndlers
    
    @objc
    private func backBtnHandler(_ sender: UIBarButtonItem) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction
    private func updateAction(_ sender: UIButton) {
        guard let addressVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: String(describing: UpdateProfileViewController.self)) as? UpdateProfileViewController else { return }
        
        addressVC.profile = self.profile
        
        self.navigationController?.pushViewController(addressVC, animated: true)
    }
    
    private func updateUI(withProfile profile: ProfileResponse) {
        
//        348 = VEGETARIAN
//        349 = NON VEGETARIAN
        
//        350 = GOVERNMENT SECTOR
//        351 = PRIVATE SECTOR
//        352 = SELF EMPLOYED
//        353 = GOVERNMENT PENSIONER
//        354 = NOT WORKING

//        Marital Status
//        324 = SINGLE
//        325 = MARRIED
//        326 = WIDOWED
//        328 = DIVORCED
        
        self.updateBtn.isEnabled = true
        self.profile = profile
        
        self.nameLbl.text = profile.name.uppercased()
        self.mobileLbl.text = profile.contactNo.uppercased()
        
        // No need to hide for any target app
        self.passportSV.isHidden = false // AppData.nationalityId == "MYS"
        if let passportNo = profile.passportNo {
            self.passportLbl.text = passportNo.uppercased()
        }
        
        if let genderId = profile.genderId {
            self.genderLbl.text = Int(genderId)! == 8 ? Constants.male.localized().uppercased() : Constants.female.localized().uppercased()
        }
        
        if let mealId = profile.mealTypeId {
            self.mealsLbl.text = mealId == 348 ? Constants.veggie.localized().uppercased() : Constants.nonVeggie.localized().uppercased()
        }
        
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
        
        self.addressContentLbl.text = profile.address.uppercased()
        self.districtLbl.text = profile.district.uppercased()
        self.mukimLbl.text = profile.mukim.uppercased()
        self.postcodeLbl.text = profile.zipCode
        self.stateLbl.text = profile.state.uppercased()
        
        self.employerContentLbl.text = profile.empName.uppercased()
        self.employerContactLbl.text = profile.empContactNo.uppercased()
        self.employerAddressLbl.text = profile.empAddress.uppercased()
        
        if let photo64 = profile.photoBase64 {
            let base64ToImage = Utilities.base64ToImage(strBase64: photo64)
            self.featuredIV.image = base64ToImage
        }
    }
}

extension ViewProfileViewController {
    private func fetchProfile() {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized(), blurEffect: .dark)
        
        let profileReq = ProfileRequest(fwProfId: AppData.fwProfId, deviceInfo: Utilities.createDeviceInfo())
        let service = Services.shared
        service.viewProfile(requestObj: profileReq, Success: { [weak self] (responses, serverObj) in
            DispatchQueue.main.async {
                self?.updateUI(withProfile: responses)
            }
        }) { (error) in
            DispatchQueue.main.async {
                self.popBasicAlert(withTitle: nil, message: error) {
                    if error == Constants.userAlreadyLoginRelogin.localized() {
                        self.popBasicAlert(withTitle: nil, message: error) {
                            AppData.resetDefaults()
                            self.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
                        }
                    } else {
                        self.dismiss(animated: true, completion: nil)
                    }
                }
            }
        }
    }
}
