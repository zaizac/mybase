//
//  Food.swift
//  MyCare
//
//  Created by Cookie on 4/16/20.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

struct ReceivedFoodRequest: Encodable {
    var userId: String
    var eventId: Int
    var deviceInfo: DeviceInfo
    
    enum CodingKeys: String, CodingKey {
        case userId
        case eventId = "deliveryEventId"
    }
}
