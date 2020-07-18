//
//  AppData.swift
//  PagohCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

struct AppData {
    
    private static let isLoginKey = "isLogin"
    private static let fullnameKey = "fullname"
    private static let fwProfIdKey = "fwProfId"
    private static let passportNoKey = "passportNo"
    private static let userIdKey = "userIdKey"
    private static let nationalityIdKey = "nationalityIdKey"
    private static let firstTimerKey = "firstTimerKey"
    private static let fcmTokenKey = "fcmTokenKey"
    private static let contactNoKey = "contactNoKey"
    private static let accessTokenKey = "accessTokenKey"
    private static let deliveryEventTokenKey = "deliveryEventTokenKey"
    private static let eventNameKey = "eventNameKey"
    private static let eventDateKey = "eventDateKey"
    private static let adminRoleGroupKey = "adminRoleGroupKey"
    private static let isAdminLoginKey = "isAdminLoginKey"
    
    // Dev2
    private static let registerProfileInfoKey       = "registerProfile"
    private static let registerAddressInfoKey       = "registerProfileAddress"
    private static let registerEmployerInfoKey      = "registerEmployerInfo"
    private static let regProfileLocationInfoKey    = "regProfileLocation"
    
    static var isLogin: Bool {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.bool(forKey:isLoginKey)
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: isLoginKey)
        }
    }
    
    static var isAdmin: Bool {
        get {
            return UserDefaults.standard.bool(forKey:isAdminLoginKey)
        }
        set {
            UserDefaults.standard.set(newValue, forKey: isAdminLoginKey)
        }
    }
    
    static var isFirstTimer: Bool {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.bool(forKey:firstTimerKey)
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: firstTimerKey)
        }
    }
    
    static func resetDefaults() {
        let defaults = UserDefaults.standard
        defaults.removeObject(forKey: isLoginKey)
        defaults.removeObject(forKey: fullnameKey)
        defaults.removeObject(forKey: fwProfIdKey)
        defaults.removeObject(forKey: passportNoKey)
        defaults.removeObject(forKey: userIdKey)
        defaults.removeObject(forKey: nationalityIdKey)
        defaults.removeObject(forKey: contactNoKey)
        defaults.removeObject(forKey: registerProfileInfoKey)
        defaults.removeObject(forKey: registerAddressInfoKey)
        defaults.removeObject(forKey: registerEmployerInfoKey)
        defaults.removeObject(forKey: regProfileLocationInfoKey)
        defaults.removeObject(forKey: isAdminLoginKey)

//        let dictionary = defaults.dictionaryRepresentation()
//        dictionary.keys.forEach { key in
//            defaults.removeObject(forKey: key)
//        }
    }
    
    static var fullname: String {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.string(forKey: fullnameKey) ?? ""
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: fullnameKey)
        }
    }
    
    static var fwProfId: Int {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.integer(forKey: fwProfIdKey)
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: fwProfIdKey)
        }
    }
    
    static var userId: String {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.string(forKey: userIdKey) ?? ""
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: userIdKey)
        }
    }
    
    static var passportNo: String {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.string(forKey: passportNoKey) ?? ""
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: passportNoKey)
        }
    }
    
    static var contactNo: String {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.string(forKey: contactNoKey) ?? ""
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: contactNoKey)
        }
    }
    
    static var deliveryEventId: String {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.string(forKey: deliveryEventTokenKey) ?? ""
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: deliveryEventTokenKey)
        }
    }
    
    static var eventName: String {
        get {
            return UserDefaults.standard.string(forKey: eventNameKey) ?? ""
        }
        set {
            UserDefaults.standard.set(newValue, forKey: eventNameKey)
        }
    }
    
    static var eventDate: String {
        get {
            return UserDefaults.standard.string(forKey: eventDateKey) ?? ""
        }
        set {
            UserDefaults.standard.set(newValue, forKey: eventDateKey)
        }
    }
    
    static var adminRoleGroup: String {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.string(forKey: adminRoleGroupKey) ?? ""
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: adminRoleGroupKey)
        }
    }
    
    static var fcmToken: String {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.string(forKey:fcmTokenKey) ?? ""
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: fcmTokenKey)
        }
    }
    
    // Dev2
    static var registerProfileInfo: Dictionary<String, Any> {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.dictionary(forKey: registerProfileInfoKey) ?? [:]
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: registerProfileInfoKey)
        }
    }
    
    static var registerAddressInfo: Dictionary<String, Any> {
        get {
            return UserDefaults.standard.dictionary(forKey: registerAddressInfoKey) ?? [:]
        }
        set {
            UserDefaults.standard.set(newValue, forKey: registerAddressInfoKey)
        }
    }
    
    static func resetEmployerInfo() {
        let defaults = UserDefaults.standard
        defaults.removeObject(forKey: registerEmployerInfoKey)
    }
    
    static var registerEmployerInfo: Dictionary<String, Any> {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.dictionary(forKey: registerEmployerInfoKey) ?? [:]
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: registerEmployerInfoKey)
        }
    }
    
    static var regProfileLocationInfo: Dictionary<String, Any> {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.dictionary(forKey: regProfileLocationInfoKey) ?? [:]
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: regProfileLocationInfoKey)
        }
    }
            
    static var nationalityId: String {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.string(forKey: nationalityIdKey) ?? ""
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: nationalityIdKey)
        }
    }
    
    static var accessToken: String {
        get {
            // Read from UserDefaults
            return UserDefaults.standard.string(forKey: accessTokenKey) ?? ""
        }
        set {
            // Save to UserDefaults
            UserDefaults.standard.set(newValue, forKey: accessTokenKey)
        }
    }
}
