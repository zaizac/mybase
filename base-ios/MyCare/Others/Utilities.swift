//
//  Utilities.swift
//  MyCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation
import UIKit

class Utilities: NSObject {
    
    // MARK: - App Info
    class func readProductNameFromPlist() -> String {
        return Bundle.main.infoDictionary![kCFBundleNameKey as String] as! String
    }
    
    // MARK: - Picker
    public class func getCustomPickerInstance() -> PickerViewController
    {
        let storyboard = UIStoryboard(name: "Registration", bundle: Bundle(for:self))
        let customPickerObj = (storyboard.instantiateViewController(withIdentifier: "PickerVc")) as! PickerViewController
        return customPickerObj
    }
    
    // MARK: - GPS Info
    class func readLocationName() -> String {
        let locCoordinate = UserLocationManager.shared.currentLocation
        if let coordinate = locCoordinate {
            let geoLocationAsString = String(coordinate.coordinate.latitude) + ", " + String(coordinate.coordinate.longitude)
            return geoLocationAsString
        } else {
            let locationName = UserLocationManager.shared.dictUpdatedLocDetails?[GEO_DETAILS.NAME] as? String ?? ""
            let locality = UserLocationManager.shared.dictUpdatedLocDetails?[GEO_DETAILS.LOCALITY] as? String ?? ""
            
            var strFullLocName = ""
            if locality != "" && strFullLocName != "" {
                strFullLocName = strFullLocName + ", " + locality
            } else if locationName != "" {
                strFullLocName = locationName
            }
            else if locality != "" {
                strFullLocName = locality
            }
            return strFullLocName
        }
    }
    
    class func createDeviceInfo() -> DeviceInfo {
        return DeviceInfo(machineID: UIDevice.current.vendorUuid,
                          sdkVersion: UIDevice.current.deviceOsVersion,
                          model: UIDevice.current.modelName,
                          brand: "Apple",
                          manufacturer: "Apple",
                          geoLocation: Utilities.readLocationName(),
                          fcmToken: AppData.fcmToken)
    }
    
    // MARK: ~ Base64 to Image
    // FYI, ImageToBase64 available in UIImage extension (toBase64())
    class func base64ToImage(strBase64: String?) -> UIImage? {
        if let imgBase64Str = strBase64, imgBase64Str != "" {
            guard let dataDecoded : Data = Data(base64Encoded: imgBase64Str, options: []) else {
                return nil
            }
            
            let decodedimage = UIImage(data: dataDecoded)
            return decodedimage
        }
        return nil
    }
    
    // MARK: - Date
    class func changeGivenDateAsString(inputDate : Date, OutputFormat: String) -> String {
        let dateformatter = DateFormatter()
        dateformatter.dateFormat = OutputFormat
        return dateformatter.string(from: inputDate)
    }
    
    class func changeGivenStringDateAsDate(inputDateString : String, OutputFormat: String) -> Date {
        let dateformatter = DateFormatter()
        dateformatter.dateFormat = OutputFormat
        return dateformatter.date(from: inputDateString)!
    }
    
    // MARK: - Files
    class func appDocumentDirectory() -> URL {
        let urls = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        return urls[urls.count-1]
    }
    
    class func getDirectoryPath(withFolderName: String) -> URL {
        let path = (NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)[0] as NSString).appendingPathComponent(withFolderName)
        let url = URL(string: path)
        return url!
    }
    
    class func checkPhotoExistsInDirectory(folderName: FILE_FOLDER_NAMES, fileName: FILE_FOLDER_NAMES) -> (isFileExist: Bool, urlString: String) {
        // read image from document directory
        let fileManager     = FileManager.default
        var imagePath       = Utilities.getDirectoryPath(withFolderName: folderName.description)
        imagePath.appendPathComponent(fileName.description)
        let urlString       = imagePath.absoluteString
        
        return (fileManager.fileExists(atPath: urlString), urlString)
    }
    
    class func saveImageFileInDirectory(directoryName: FILE_FOLDER_NAMES, fileName: FILE_FOLDER_NAMES, capturedImage : UIImage, imgOutputQuality: CGFloat) -> (Bool) {
        let fileManager = FileManager.default
        let directoryPathURL = Utilities.getDirectoryPath(withFolderName: directoryName.description)
        if !fileManager.fileExists(atPath: directoryPathURL.absoluteString) {
            do {
                try fileManager.createDirectory(atPath: directoryPathURL.absoluteString, withIntermediateDirectories: true, attributes: nil)
            } catch let error {
                print("ERROR WHEN WRITE FILE: ", error.localizedDescription)
                return false
            }
        }
        
        let url = URL(string: directoryPathURL.absoluteString)
        let imagePath = url!.appendingPathComponent(fileName.description)
        let urlString: String = imagePath.absoluteString
        let imageData = capturedImage.jpegData(compressionQuality: imgOutputQuality)
        return fileManager.createFile(atPath: urlString as String, contents: imageData, attributes: nil)
    }
    
    class func deleteImageFileInDirectory(directoryName: FILE_FOLDER_NAMES, fileName: FILE_FOLDER_NAMES) {
        let checkFileStatus = Utilities.checkPhotoExistsInDirectory(folderName: directoryName, fileName: fileName)
        if checkFileStatus.isFileExist {
            let fileManager     = FileManager.default
            do {
                try fileManager.removeItem(atPath: checkFileStatus.urlString)
            }
            catch let error {
                print("ERROR WHEN WRITE FILE: ", error.localizedDescription)
            }
        }
    }
    
    class func isItCommonErrorMessage(errMsg: String) -> Bool {
        
        if errMsg == Constants.noInternet.localized() || errMsg == Constants.unableToProceed.localized() || errMsg == Constants.enconterServerError.localized() {
            return true
        }
        
        return false
    }
    
    // MARK: - Color
    public static func colorHEXString(hex:String) -> UIColor {
        var cString:String = hex.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()
        
        if (cString.hasPrefix("#")) {
            cString.remove(at: cString.startIndex)
        }
        
        if ((cString.count) != 6) {
            return UIColor.gray
        }
        
        var rgbValue:UInt32 = 0
        Scanner(string: cString).scanHexInt32(&rgbValue)
        
        return UIColor(
            red: CGFloat((rgbValue & 0xFF0000) >> 16) / 255.0,
            green: CGFloat((rgbValue & 0x00FF00) >> 8) / 255.0,
            blue: CGFloat(rgbValue & 0x0000FF) / 255.0,
            alpha: CGFloat(1.0)
        )
    }
}
