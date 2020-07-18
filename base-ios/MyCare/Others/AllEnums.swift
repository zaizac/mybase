//
//  AllEnums.swift
//  MyCare
//
//  Created by Sadham on 16/04/2020.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import Foundation

enum STATIC_DATA_LIST: Int {
    case STATES, CITY, MUKIM
}

enum FILE_FOLDER_NAMES : CaseIterable {
    case ALL_FILES, REG_AVATAR, CROPPED_FACE, FOLDER2
    public var description: String {
        switch self {
            case .ALL_FILES: return "AllFiles"
            case .REG_AVATAR: return "RegProfileAvatar"
            case .CROPPED_FACE: return "CroppedFace"
            case .FOLDER2: return "Folder2"
        }
    }
}
