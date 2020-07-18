//
//  Mukim.swift
//  MyCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

struct MukimRequest: Encodable {
    var stateCode: String
    var districtCode: String
    var deviceInfo: DeviceInfo
    
    enum CodingKeys: String, CodingKey {
        case stateCode = "stateCd"
        case districtCode = "districtCd"
    }
}

struct AllMukim: Decodable {
    var id: Int
    var code: String
    var description: String
    var stateCode: String
    var districtCode: String
    
    enum CodingKeys: String, CodingKey {
        case id = "mukimId"
        case code = "mukimCd"
        case description = "mukimDesc"
        case stateCode = "stateCd"
        case districtCode = "districtCd"
    }
}
