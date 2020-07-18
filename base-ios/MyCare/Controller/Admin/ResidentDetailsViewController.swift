//
//  ResidentDetailsViewController.swift
//  PagohCare
//
//  Created by Cookie on 5/7/20.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import UIKit

class ResidentDetailsViewController: UIViewController, Alert {
    
    @IBOutlet private weak var contentHolderView: UIView!
    @IBOutlet private weak var shadowContentHolderView: CorneredShadowedView!
    @IBOutlet private weak var shadowResidentImageHolder: CircledShadowedView!
    @IBOutlet private weak var residentIV: UIImageView!
    @IBOutlet private weak var residentDetailHolder: UIView!
    
    @IBOutlet private weak var eventNameLbl: UILabel!
    @IBOutlet private weak var eventDateLbl: UILabel!
    @IBOutlet private weak var residentDetailsLbl: UILabel!
    @IBOutlet private weak var nameLbl: UILabel!
    @IBOutlet private weak var nameContentLbl: UILabel!
    
    @IBOutlet private weak var homeBtn: CorneredButton!
    @IBOutlet private weak var scanNextBtn: CorneredButton!
    
    var fwProfId: String!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialSetup()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.setText()
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized(), blurEffect: .dark)
        self.fetchProfile(forId: fwProfId)
    }
    
    private func initialSetup() {
        self.shadowContentHolderView.backgroundColor = .clear
        self.shadowResidentImageHolder.backgroundColor = .clear
        self.residentIV.addCornerRadius(self.residentIV.frame.height / 2)
        self.contentHolderView.addCornerRadius(6)
        
        self.residentDetailHolder.backgroundColor = Colors.blueBlack
        self.scanNextBtn.backgroundColor = Colors.blueBlack
        self.eventNameLbl.textColor = Colors.blueBlack
        self.eventDateLbl.textColor = Colors.blueBlack
        
        self.residentIV.contentMode = .scaleAspectFill
        self.nameContentLbl.text = "N/A"
        self.homeBtn.tag = 1
        self.scanNextBtn.tag = 2
        
        self.eventNameLbl.text = AppData.eventName
        self.eventDateLbl.text = AppData.eventDate
    }
    
    private func setText() {
        self.residentDetailsLbl.text = Constants.residentDetails.localized()
        self.nameLbl.text = Constants.name.localized()
    }
    
    private func updateUI(withProfile profile: ProfileResponse) {
        
        self.nameContentLbl.text = profile.name
        
        if let photo64 = profile.photoBase64 {
            let base64ToImage = Utilities.base64ToImage(strBase64: photo64)
            self.residentIV.image = base64ToImage
        }
        
        self.view.layoutIfNeeded()
    }
    
    //MARK: - Action && Handlers
    @IBAction
    private func buttonsAction(_ sender: UIButton) {
        switch sender.tag {
        case 1:
            //Quit to the main page.
            self.presentingViewController?.presentingViewController?.dismiss(animated: true, completion: nil)
        case 2:
            self.dismiss(animated: true, completion: nil)
        default:
            print("NOTHINGNESS")
        }
    }
}

//MARK: - Http Request
extension ResidentDetailsViewController {
    private func fetchProfile(forId id: String) {
        
        let fwdProfId = Int(id) ?? 0
        
        let profileReq = ProfileRequest(fwProfId: fwdProfId, deviceInfo: Utilities.createDeviceInfo())
        let service = Services.shared
        service.viewProfile(requestObj: profileReq, Success: { [weak self] (responses, serverObj) in
            DispatchQueue.main.async {
                self?.updateUI(withProfile: responses)
                self?.receivedFood(AppData.deliveryEventId, residentId: responses.contactNo)
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
    
    private func receivedFood(_ eventId: String, residentId: String) {
        
        let tempEventId = Int(eventId) ?? 0
        
        let foodRequest = ReceivedFoodRequest(userId: residentId, eventId: tempEventId, deviceInfo: Utilities.createDeviceInfo())
        
        let service = Services.shared
        service.receivedFood(requestObj: foodRequest, Success: { [weak self] (serverObj) in
            DispatchQueue.main.async {
                self?.popBasicAlert(withTitle: Constants.success.localized(), message: serverObj.message ?? "")
            }
        }) { (error) in
            DispatchQueue.main.async {
                if error == Constants.userAlreadyLoginRelogin.localized() {
                    self.popBasicAlert(withTitle: nil, message: error) {
                        AppData.resetDefaults()
                        self.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
                    }
                } else {
                    self.popBasicAlert(withTitle: nil, message: error)
                }
            }
        }
    }
}
