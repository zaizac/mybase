//
//  RegisterStatusModel.swift
//  MyCare
//
//  Created by Sadham on 19/04/2020.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation


struct RegisterStatusRequest: Encodable {
    var contactNo: String
    var passportNo: String
    var deviceInfo: DeviceInfo
}

struct RegisterStatusResponse: Decodable {
    var appStatus: String
    var reason: String?
}
