//
//  ServerModel.swift
//  MyCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

struct ServerModel: Codable {
    let timestamp   : String?
    let status      : Int?
    let code        : String?
    let message     : String?
    let path        : String?
    
    enum CodingKeys : String, CodingKey {
        case timestamp, status, code, message, path
    }
}

struct DeviceInfo : Codable {
    let machineID, sdkVersion, model, brand: String
    let manufacturer, geoLocation, fcmToken: String
    
    enum CodingKeys: String, CodingKey {
        case machineID = "machineId"
        case sdkVersion, model, brand, manufacturer, geoLocation, fcmToken
    }
}
