//
//  District.swift
//  MyCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

struct DistrictRequest: Encodable {
    var stateCode: String
    var deviceInfo: DeviceInfo
    
    enum CodingKeys: String, CodingKey {
        case stateCode = "stateCd"
    }
}

struct AllDistricts: Decodable {
    var id: Int
    var code: String
    var description: String
    var stateCode: String
    
    enum CodingKeys: String, CodingKey {
        case id = "districtId"
        case code = "districtCd"
        case description = "districtDesc"
        case stateCode = "stateCd"
    }
}
