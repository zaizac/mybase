//
//  Extension.swift
//  MyCare
//
//  Created by Cookie on 4/15/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation
import UIKit

extension String {
    func strFromBase64() -> String? {
        guard let data = Data(base64Encoded: self, options: Data.Base64DecodingOptions(rawValue: 0)) else {
            return nil
        }
        
        return String(data: data as Data, encoding: String.Encoding.utf8)
    }
    
    func strToBase64() -> String? {
        guard let data = self.data(using: String.Encoding.utf8) else {
            return nil
        }
        
        return data.base64EncodedString(options: Data.Base64EncodingOptions(rawValue: 0))
    }
    
    func substring(from: Int, to: Int) -> String {
        let start = index(startIndex, offsetBy: from)
        let end = index(start, offsetBy: to - from)
        return String(self[start ..< end])
    }
    
    func substring(range: NSRange) -> String {
        return substring(from: range.lowerBound, to: range.upperBound)
    }
    
    var isInt: Bool {
        return Int(self) != nil
    }
    
    var isPhoneNumber: Bool {
        do {
            let detector = try NSDataDetector(types: NSTextCheckingResult.CheckingType.phoneNumber.rawValue)
            let matches = detector.matches(in: self, options: [], range: NSRange(location: 0, length: self.count))
            if let res = matches.first {
                return res.resultType == .phoneNumber && res.range.location == 0 && res.range.length == self.count
            } else {
                return false
            }
        } catch {
            return false
        }
    }
    
    // Show a html code in uilabel or uitextview.
    var htmlToAttributedString: NSAttributedString? {
           guard let data = data(using: .utf8) else { return NSAttributedString() }
           do {
               return try NSAttributedString(data: data, options: [.documentType: NSAttributedString.DocumentType.html, .characterEncoding:String.Encoding.utf8.rawValue], documentAttributes: nil)
           } catch {
               return NSAttributedString()
           }
       }
       var htmlToString: String {
           return htmlToAttributedString?.string ?? ""
       }
}

extension Dictionary where Value : Equatable {
    func allKeysForValue(val : Value) -> [Key] {
        return self.filter { $1 == val }.map { $0.0 }
    }
}

extension UITextField {
    func addDoneCancelToolbar(_ isBoth: Bool = true, onDone: (target: Any, action: Selector)? = nil, onCancel: (target: Any, action: Selector)? = nil) {
        let onCancel = onCancel ?? (target: self, action: #selector(cancelButtonTapped))
        let onDone = onDone ?? (target: self, action: #selector(doneButtonTapped))
        
        let toolbar: UIToolbar = UIToolbar()
        toolbar.barStyle = .default
        toolbar.items = isBoth ? [
            UIBarButtonItem(title: Constants.cancel.localized(), style: .plain, target: onCancel.target, action: onCancel.action),
            UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: self, action: nil),
            UIBarButtonItem(title: Constants.done.localized(), style: .done, target: onDone.target, action: onDone.action)
            ] : [
            UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: self, action: nil),
            UIBarButtonItem(title: Constants.done.localized(), style: .done, target: onDone.target, action: onDone.action)
            ]
        toolbar.sizeToFit()
        
        self.inputAccessoryView = toolbar
    }
    
    @objc private func doneButtonTapped() { self.resignFirstResponder() }
    @objc private func cancelButtonTapped() { self.resignFirstResponder() }
    
    //Add asterisk icon
    func addAsterisk() {
        self.rightViewMode = UITextField.ViewMode.always
        
        let hView = UIView(frame: CGRect(x: 0, y: 0, width: 25, height: 25))
        hView.backgroundColor = .clear
        
        let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: 20, height: 20))
        let image = UIImage(named: "asteriskIcon")
        imageView.image = image
        imageView.tintColor = .systemRed
        imageView.center = hView.center
        
        hView.addSubview(imageView)
        self.rightView = hView
    }
    
    func addDropDown() {
        self.rightViewMode = UITextField.ViewMode.always
        
        let hView = UIView(frame: CGRect(x: 0, y: 0, width: 25, height: 25))
        hView.backgroundColor = .clear
        
        let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: 20, height: 20))
        let image = UIImage(named: "dropdownIcon")
        imageView.image = image
        imageView.tintColor = .black
        imageView.center = hView.center
        
        hView.addSubview(imageView)
        self.rightView = hView
    }
    
    func addAsteriskDropdown() {
        self.rightViewMode = UITextField.ViewMode.always
        
        let hView = UIView(frame: CGRect(x: 0, y: 0, width: 43, height: 20))
        hView.backgroundColor = .clear
        
        let dropdownIV = UIImageView()
        dropdownIV.translatesAutoresizingMaskIntoConstraints = false
        dropdownIV.image = UIImage(named: "dropdownIcon")
        dropdownIV.tintColor = .darkGray
        
        let asteriskIV = UIImageView()
        asteriskIV.translatesAutoresizingMaskIntoConstraints = false
        asteriskIV.image = UIImage(named: "asteriskIcon")
        asteriskIV.tintColor = .systemRed
        
        hView.addSubview(dropdownIV)
        hView.addSubview(asteriskIV)
        
        dropdownIV.leadingAnchor.constraint(equalTo: hView.leadingAnchor, constant: 0).isActive = true
        dropdownIV.centerYAnchor.constraint(equalTo: hView.centerYAnchor).isActive = true
        dropdownIV.heightAnchor.constraint(equalToConstant: 24).isActive = true
        dropdownIV.widthAnchor.constraint(equalToConstant: 24).isActive = true
        
        asteriskIV.leadingAnchor.constraint(equalTo: dropdownIV.trailingAnchor, constant: -4).isActive = true
        asteriskIV.centerYAnchor.constraint(equalTo: hView.centerYAnchor).isActive = true
        asteriskIV.heightAnchor.constraint(equalToConstant: 20).isActive = true
        asteriskIV.widthAnchor.constraint(equalToConstant: 20).isActive = true
        
        self.rightView = hView
    }
    
    func togglePasswordVisibility() {
        isSecureTextEntry = !isSecureTextEntry
        
        if let existingText = text, isSecureTextEntry {
            /* When toggling to secure text, all text will be purged if the user
             continues typing unless we intervene. This is prevented by first
             deleting the existing text and then recovering the original text. */
            deleteBackward()
            
            if let textRange = textRange(from: beginningOfDocument, to: endOfDocument) {
                replace(textRange, withText: existingText)
            }
        }
        
        /* Reset the selected text range since the cursor can end up in the wrong
         position after a toggle because the text might vary in width */
        if let existingSelectedTextRange = selectedTextRange {
            selectedTextRange = nil
            selectedTextRange = existingSelectedTextRange
        }
    }
}

// MARK: - UIImage
extension UIImage {
    func toBase64(quality: CGFloat) -> String? {
        guard let imageData = self.jpegData(compressionQuality: quality) else { return nil }
        return imageData.base64EncodedString(options: [])
    }
    
    func fixedOrientation() -> UIImage? {
        
        guard imageOrientation != UIImage.Orientation.up else {
            //This is default orientation, don't need to do anything
            return self.copy() as? UIImage
        }
        
        guard let cgImage = self.cgImage else {
            //CGImage is not available
            return nil
        }
        
        guard let colorSpace = cgImage.colorSpace, let ctx = CGContext(data: nil, width: Int(size.width), height: Int(size.height), bitsPerComponent: cgImage.bitsPerComponent, bytesPerRow: 0, space: colorSpace, bitmapInfo: CGImageAlphaInfo.premultipliedLast.rawValue) else {
            return nil //Not able to create CGContext
        }
        
        var transform: CGAffineTransform = CGAffineTransform.identity
        
        switch imageOrientation {
        case .down, .downMirrored:
            print("DOWN")
            transform = transform.translatedBy(x: size.width, y: size.height)
            transform = transform.rotated(by: CGFloat.pi)
            break
        case .left, .leftMirrored:
            print("LEFT")
            // DON'T UNCOMMENT THIS CODE FOR THIS PROJECT
            transform = transform.translatedBy(x: size.width, y: 0)
            transform = transform.rotated(by: CGFloat.pi / 2.0)
            break
        case .right, .rightMirrored:
            print("RIGHT")
            // DON'T UNCOMMENT THIS CODE FOR THIS PROJECT
            transform = transform.translatedBy(x: 0, y: size.height)
            transform = transform.rotated(by: CGFloat.pi / -2.0)
            break
        case .up, .upMirrored:
            print("UP")
            break
        @unknown default:
            fatalError()
        }
        
        //Flip image one more time if needed to, this is to prevent flipped image
        switch imageOrientation {
        case .upMirrored, .downMirrored:
            transform.translatedBy(x: size.width, y: 0)
            transform.scaledBy(x: -1, y: 1)
            break
        case .leftMirrored, .rightMirrored:
            transform.translatedBy(x: size.height, y: 0)
            transform.scaledBy(x: -1, y: 1)
        case .up, .down, .left, .right:
            break
        @unknown default:
            fatalError()
        }
        
        ctx.concatenate(transform)
        
        switch imageOrientation {
        case .left, .leftMirrored, .right, .rightMirrored:
            ctx.draw(self.cgImage!, in: CGRect(x: 0, y: 0, width: size.height, height: size.width))
        default:
            ctx.draw(self.cgImage!, in: CGRect(x: 0, y: 0, width: size.width, height: size.height))
            break
        }
        
        guard let newCGImage = ctx.makeImage() else { return nil }
        return UIImage.init(cgImage: newCGImage, scale: 1, orientation: .up)
    }
    
    func rotate(radians: Float) -> UIImage? {
        var newSize = CGRect(origin: CGPoint.zero, size: self.size).applying(CGAffineTransform(rotationAngle: CGFloat(radians))).size
        // Trim off the extremely small float value to prevent core graphics from rounding it up
        newSize.width = floor(newSize.width)
        newSize.height = floor(newSize.height)
        
        UIGraphicsBeginImageContextWithOptions(newSize, false, self.scale)
        let context = UIGraphicsGetCurrentContext()!
        
        // Move origin to middle
        context.translateBy(x: newSize.width/2, y: newSize.height/2)
        // Rotate around middle
        context.rotate(by: CGFloat(radians))
        // Draw the image at its center
        self.draw(in: CGRect(x: -self.size.width/2, y: -self.size.height/2, width: self.size.width, height: self.size.height))
        
        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        return newImage
    }
    
    struct RotationOptions: OptionSet {
        let rawValue: Int
        
        static let flipOnVerticalAxis = RotationOptions(rawValue: 1)
        static let flipOnHorizontalAxis = RotationOptions(rawValue: 2)
    }
    
    func rotated(by rotationAngle: Measurement<UnitAngle>, options: RotationOptions = []) -> UIImage? {
        guard let cgImage = self.cgImage else { return nil }
        
        let rotationInRadians = CGFloat(rotationAngle.converted(to: .radians).value)
        let transform = CGAffineTransform(rotationAngle: rotationInRadians)
        var rect = CGRect(origin: .zero, size: self.size).applying(transform)
        rect.origin = .zero
        
        let renderer = UIGraphicsImageRenderer(size: rect.size)
        return renderer.image { renderContext in
            renderContext.cgContext.translateBy(x: rect.midX, y: rect.midY)
            renderContext.cgContext.rotate(by: rotationInRadians)
            
            let x = options.contains(.flipOnVerticalAxis) ? -1.0 : 1.0
            let y = options.contains(.flipOnHorizontalAxis) ? 1.0 : -1.0
            renderContext.cgContext.scaleBy(x: CGFloat(x), y: CGFloat(y))
            
            let drawRect = CGRect(origin: CGPoint(x: -self.size.width/2, y: -self.size.height/2), size: self.size)
            renderContext.cgContext.draw(cgImage, in: drawRect)
        }
    }
}

//MARK: - UIView
extension UIView {
    func addShadow(withOpacity opacity: Float, shadowRadius: CGFloat, offset: CGSize = .zero, color: UIColor = .black) {
        layer.shadowColor = color.cgColor
        layer.shadowOpacity = opacity
        layer.shadowOffset = offset
        layer.shadowRadius = shadowRadius
        
        layer.shouldRasterize = true
        layer.rasterizationScale = UIScreen.main.scale
    }
    
    func addCornerRadius(_ radius: CGFloat) {
        layer.cornerRadius = radius
        layer.masksToBounds = true
    }
    
    func addBorder(_ bWidth: CGFloat, color: UIColor) {
        layer.borderWidth = bWidth
        layer.borderColor = color.cgColor
    }
    
    func fixInView(_ container: UIView!) -> Void{
        self.translatesAutoresizingMaskIntoConstraints = false;
        self.frame = container.frame;
        container.addSubview(self);
        NSLayoutConstraint(item: self, attribute: .leading, relatedBy: .equal, toItem: container, attribute: .leading, multiplier: 1.0, constant: 0).isActive = true
        NSLayoutConstraint(item: self, attribute: .trailing, relatedBy: .equal, toItem: container, attribute: .trailing, multiplier: 1.0, constant: 0).isActive = true
        NSLayoutConstraint(item: self, attribute: .top, relatedBy: .equal, toItem: container, attribute: .top, multiplier: 1.0, constant: 0).isActive = true
        NSLayoutConstraint(item: self, attribute: .bottom, relatedBy: .equal, toItem: container, attribute: .bottom, multiplier: 1.0, constant: 0).isActive = true
    }
}

extension UIColor {
    convenience init(hexString: String) {
        let hex = hexString.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int = UInt64()
        Scanner(string: hex).scanHexInt64(&int)
        let a, r, g, b: UInt64
        switch hex.count {
        case 3: // RGB (12-bit)
            (a, r, g, b) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
        case 6: // RGB (24-bit)
            (a, r, g, b) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
        case 8: // ARGB (32-bit)
            (a, r, g, b) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (a, r, g, b) = (255, 0, 0, 0)
        }
        self.init(red: CGFloat(r) / 255, green: CGFloat(g) / 255, blue: CGFloat(b) / 255, alpha: CGFloat(a) / 255)
    }
}

extension UINavigationController {
    func removeHairline() {
        navigationBar.setBackgroundImage(UIImage(), for:.default)
        navigationBar.shadowImage = UIImage()
        navigationBar.layoutIfNeeded()
    }
}

extension UINavigationBar {
    func changeBar(toColor color: UIColor, tintColor tColor: UIColor) {
        barTintColor = color
        tintColor = tColor
        titleTextAttributes = [NSAttributedString.Key.foregroundColor: tColor]
        isTranslucent = false
    }
    
    func transparentNavigationBar() {
        self.setBackgroundImage(UIImage(), for: .default)
        self.shadowImage = UIImage()
        self.isTranslucent = true
    }
}

extension UIApplication {
    static var TARGET_NAME: String? {
        return Bundle.main.object(forInfoDictionaryKey: "Target Define Name") as? String
    }
}
