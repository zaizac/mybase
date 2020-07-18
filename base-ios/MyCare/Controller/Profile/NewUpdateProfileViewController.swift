//
//  NewUpdateProfileViewController.swift
//  PagohCare
//
//  Created by Cookie on 4/28/20.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import UIKit
import AVKit

class NewUpdateProfileViewController: UIViewController, Alert {

    @IBOutlet private weak var userIV: CircledImageView!
    @IBOutlet private weak var userNameLabel: UILabel!
    @IBOutlet private weak var mealTypeLabel: UILabel!
    @IBOutlet private weak var userIVHolder: CircledShadowedView!
    @IBOutlet private weak var cameraBtn: UIButton!
    @IBOutlet private weak var mealTypeBtn: UIButton!
    @IBOutlet private weak var updateBtn: CorneredButton!
    
    private let imagePicker = UIImagePickerController()
    
    private var profileResponse: ProfileResponse!
    private var profileRequest: UpdateProfileRequest!
    private var mealId: Int = 348
    private var photoBase64: String?
    private var initialPhoto: String!
    private var initialMealType: Int!
    private var isPhotoChanged: Bool = false
    private var isMealTypeChanged: Bool = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.intialConfig()
        self.fetchProfile()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.setText()
    }
    
    private func intialConfig() {
        
        title = Constants.updatePhotoMealType.localized()
        
        self.cameraBtn.addCornerRadius(self.cameraBtn.bounds.width / 2)
        self.userIVHolder.backgroundColor = .clear
        self.updateBtn.backgroundColor = UIColor.init(named: "standardBtnColor")
        
        self.mealTypeBtn.addCornerRadius(5)
        self.mealTypeBtn.layer.borderColor = Colors.defaultBorder.cgColor
        self.mealTypeBtn.layer.borderWidth = 1
        
        self.imagePicker.delegate = self
        
        guard let _ = self.navigationController else { return }
        
        let backBtn = UIBarButtonItem(title: Constants.close.localized(), style: .plain, target: self, action: #selector(backBtnHandler(_:)))
        navigationItem.leftBarButtonItem = backBtn
    }
    
    private func updateUI(withProfile profile: ProfileResponse) {
        
        self.profileResponse = profile
        self.userNameLabel.text = profile.name.uppercased()
        
        self.initialMealType = profile.mealTypeId
        self.mealId = profile.mealTypeId ?? 348
        self.mealTypeBtn.setTitle(profile.mealTypeId == 348 ? Constants.veggie.localized().uppercased() : Constants.nonVeggie.localized().uppercased(), for: .normal)
                
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
    
    private func setText() {
        self.mealTypeLabel.text = Constants.selectMealTypeAlertTitle.localized()
        self.updateBtn.setTitle(Constants.update.localized().uppercased(), for: .normal)
    }
    
    //MARK: - IBAction and Handlers
    
    @IBAction
    private func dropdownAction(_ sender: UIButton) {
        self.showMealList(forBtn: sender)
    }
    
    @IBAction
    private func cameraAction(_ sender: UIButton) {
        self.showPhotoPickingOption()
    }
    
    @IBAction
    private func updateAction(_ sender: UIButton) {
        
        self.isMealTypeChanged = !(self.initialMealType == self.mealId)
        
        if !self.isPhotoChanged && !self.isMealTypeChanged {
            self.popBasicAlert(withTitle: nil, message: Constants.nothingToUpdate.localized())
            return
        }
        
        self.profileRequest = UpdateProfileRequest(fwProfId: AppData.fwProfId, name: self.profileResponse.name, address: self.profileResponse.address, stateCode: profileResponse.stateCode, districtCode: profileResponse.districtCode, countryCode: profileResponse.countryCode, mukimCode: profileResponse.mukimCode, zipCode: profileResponse.zipCode, photoBase64: self.photoBase64, mealTypeId: self.mealId, deviceInfo: Utilities.createDeviceInfo())
        
        self.confirmationAlert(withTitle: Constants.updateProfile.localized(), message: Constants.sureToUpdateProfile.localized()) {
            self.updateProfile()
        }
    }
    
    @objc
    private func backBtnHandler(_ sender: UIBarButtonItem) {
        self.dismiss(animated: true, completion: nil)
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
                                self?.isPhotoChanged = true
                                self?.userIV.image = faces[0]
                            }
                    }
                    case .notFound:
                        DispatchQueue.main.async {
                            print("faces Not found")
                            self?.isPhotoChanged = false
                            self?.popBasicAlert(withTitle: nil, message: Constants.noFaceDetect.localized())
                    }
                    case .failure(let error):
                        DispatchQueue.main.async {
                            self?.isPhotoChanged = false
                            print("CROP FACE ERROR: ++++++++++++ ", error)
                            self?.popBasicAlert(withTitle: Constants.failed.localized(), message: Constants.captureFaceAgain.localized())
                    }
                }
                
                Spinner.shared.hide()
            })
        }
    }
}

// MARK: - Camera delegates
extension NewUpdateProfileViewController : UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        
        if let pickedImage = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
            guard let capturedImage = pickedImage.fixedOrientation() else {
                self.popBasicAlert(withTitle: Constants.failed.localized(), message: Constants.captureFaceAgain.localized())
                return
            }
            // Image to Base64
            print("YES IMAGE")
            let pickedImageBase64 = capturedImage.toBase64(quality: 0.8)
            self.readingFaceFromPhoto(capturedImage: capturedImage)
            
            DispatchQueue.main.async {
                self.isPhotoChanged = true
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

extension NewUpdateProfileViewController {
    
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
                        AppData.resetDefaults()
                        self.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
                    } else {
                        self.dismiss(animated: true, completion: nil)
                    }
                }
            }
        }
    }
    
    private func updateProfile() {
        Spinner.shared.show(onView: self.view, text: Constants.loading.localized(), blurEffect: .dark)
        
        let service = Services.shared
        service.updateProfileService(requestObj: self.profileRequest, Success: { [weak self] (serverObj) in
            DispatchQueue.main.async {
                print("SUCCESS", serverObj.status!)
                // TODO: Later add server response in message
                var succesMessage: String!
                if self?.isPhotoChanged == true && self?.isMealTypeChanged == true {
                    succesMessage = Constants.updateProfileSuccess.localized()
                } else if self?.isPhotoChanged == true {
                    succesMessage = Constants.profilePictureUpdated.localized()
                } else if self?.isMealTypeChanged == true {
                    succesMessage = Constants.mealTypeUpdated.localized()
                } else {
                    print("NOTHINGNESS")
                }
                
                self?.popBasicAlert(withTitle: succesMessage, message: nil, completion: {
                    self?.dismiss(animated: true, completion: nil)
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
