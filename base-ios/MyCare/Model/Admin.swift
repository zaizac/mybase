//
//  Admin.swift
//  PagohCare
//
//  Created by Cookie on 5/8/20.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import Foundation

struct AdminEvent: Codable {
    var id: Int
    var time: String
    var name: String
    var date: String
    
    enum CodingKeys: String, CodingKey {
        case id = "deliveryEventId"
        case time = "eventTime"
        case date = "eventDate"
        case name = "eventName"
    }
}

//MARK: - Login
struct AdminLoginRequest: Encodable {
    var userId: String
    var password: String
    var deviceInfo: DeviceInfo
}

struct AdminLoginResponse: Decodable {
    var firstName: String
    var lastName: String
    var userType: String?
    var userRoleGroupCode: String
    var userId: String
    var status: String
    var token : AdminLoginToken
}

struct AdminLoginToken: Codable {
    var accessToken : String
    var refreshToken : String
    var tokenType : String?
}
