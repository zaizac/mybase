//
//  ShowQRCodeViewController.swift
//  PagohCare
//
//  Created by Cookie on 5/7/20.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import UIKit
import CryptoSwift

class ShowQRCodeViewController: UIViewController {

    @IBOutlet private weak var contentHolder: CorneredShadowedView!
    @IBOutlet private weak var descOneLabel: UILabel!
    @IBOutlet private weak var descOneTwo: UILabel!
    @IBOutlet private weak var closeBtn: UIButton!
    @IBOutlet private weak var qrCodeIV: UIImageView!
    
    var onClosePopup: (() -> ())?
    var qrCodeString: String!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialSetup()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.setText()
    }
    
    private func initialSetup() {
        self.view.backgroundColor = UIColor(white: 0, alpha: 0.65)
        self.contentHolder.backgroundColor = .clear
        self.closeBtn.addCornerRadius(self.closeBtn.frame.height / 2)
        self.qrCodeIV.contentMode = .scaleAspectFit
        
        let qrImage = generateQRCode(from: qrCodeString)
        self.updateQRImage(qrImage)
    }
    
    private func setText() {
        self.descOneLabel.text = Constants.bringYourPhoneAndFlash.localized()
        self.descOneTwo.text = Constants.approachTheEventOfficer.localized()
    }
    
    func updateQRImage(_ qrImage: UIImage?) {
        self.qrCodeIV.image = qrImage
    }
    
    @IBAction
    private func closeAction(_ sender: UIButton) {
        self.onClosePopup?()
    }
    
    private func generateQRCode(from string: String) -> UIImage? {
        let data = string.data(using: String.Encoding.ascii)

        if let filter = CIFilter(name: "CIQRCodeGenerator") {
            filter.setValue(data, forKey: "inputMessage")
            let transform = CGAffineTransform(scaleX: 5, y: 5)

            if let output = filter.outputImage?.transformed(by: transform) {
                return UIImage(ciImage: output)
            }
        }

        return nil
    }
}

//TODO: - Encryption.
let KEY = "mw62SJ96!^54GKW)=@*HtQbK"
let IV = "swapswap"
extension String {
    func aesEncrypt() throws -> String {
        let encrypted = try AES(key: KEY, iv: IV, padding: .pkcs7).encrypt([UInt8](self.data(using: .utf8)!))
        return Data(encrypted).base64EncodedString()
    }

    func aesDecrypt() throws -> String {
        guard let data = Data(base64Encoded: self) else { return "" }
        let decrypted = try AES(key: KEY, iv: IV, padding: .pkcs7).decrypt([UInt8](data))
        return String(bytes: decrypted, encoding: .utf8) ?? self
    }
}
