//
//  Login.swift
//  MyCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

struct LoginRequest: Encodable {
    var userId: String
    var passportNo: String
    var deviceInfo: DeviceInfo
}

struct LoginResponse: Decodable {
    var fwProfId: Int
    var name: String
    var passportNo: String?
    var nationalityCode: String
    var token : LoginToken
    
    enum CodingKeys: String, CodingKey {
        case fwProfId, name, passportNo, token
        case nationalityCode = "ntnltyCd"
    }
}

struct LoginToken: Codable {
    var accessToken : String
    var refreshToken : String?
    var scope : String?
}

struct LogoutRequest: Encodable {
    var userId: String?
    var passportNo: String?
    var deviceInfo: DeviceInfo
}
