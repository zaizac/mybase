//
//  State.swift
//  MyCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

struct StateRequest: Encodable {
    var countryCode: String
    var deviceInfo: DeviceInfo
    
    enum CodingKeys: String, CodingKey {
        case countryCode = "cntryCd"
    }
}

struct StateListResponse : Decodable {
    let arrAllStates: [AllStates]
}

struct AllStates: Codable {
    var id: Int
    var code: String
    var description: String
    
    enum CodingKeys: String, CodingKey {
        case id = "stateId"
        case code = "stateCd"
        case description = "stateDesc"
    }
}
