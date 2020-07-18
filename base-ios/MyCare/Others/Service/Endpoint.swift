//
//  Endpoint.swift
//  PagohCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

struct Endpoint {
    private static let headerPath : String = {
        #if PagohCare
        return "https://mycaresgw1.bestinet.my/mycare-sgw/service/"
        #else
        return "https://mycaresgw1.bestinet.my/mycare-sgw/service/"
        #endif
    }()
    
    static var login: String {
        return headerPath + "login"
    }
    
    static var logout: String {
        return headerPath + "logout"
    }
    
    static var state: String {
        return headerPath + "refState"
    }
    
    static var district: String {
        return headerPath + "refDistrict"
    }
    
    static var mukim: String {
        return headerPath + "refMukim"
    }
    
    static var viewProfile: String {
        return headerPath + "viewProfile"
    }
    
    static var updateProfile: String {
        return headerPath + "updatProfile"
    }
    
    static var insertWorker: String {
        return headerPath + "insertWorker"
    }
    
    static var registerStatus: String {
        return headerPath + "registrationInquiry"
    }
    
    static var city: String {
        return headerPath + "refCity"
    }
    
    static var receiveFood: String {
        return headerPath + "receiveFood"
    }
    
    static var races: String {
        return headerPath + "getRefRace"
    }
    
    static var notifications: String {
        return headerPath + "listNotification"
    }
    
    static var listEvents: String {
        return headerPath + "deliveryEvents"
    }
    
    static var adminLogin: String {
        return headerPath + "adminLogin"
    }
}
