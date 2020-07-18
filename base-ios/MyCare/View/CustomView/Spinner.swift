//
//  LoadingViewHandler.swift
//  BSTBaseFramework
//
//  Created by xamarin developer on 17/12/2019.
//  Copyright © Mohd Nasir Selamat. All rights reserved.
//

import UIKit

public class Spinner: NSObject {
    
    var loadingViewObj : SpinnerView!
    
    public class var shared : Spinner {
        struct Singleton {
            static let instance = Spinner()
        }
        return Singleton .instance
    }
    
    public func show(onView: UIView, text: String, blurEffect: UIBlurEffect.Style = .light, frame: CGRect = CGRect(x: 0, y: 0, width: 120, height: 120)) {
        
        DispatchQueue.main.async {
            self.loadingViewObj = SpinnerView(blurEffect, frame: frame)
            self.loadingViewObj.set(title: text)
            self.loadingViewObj.center = onView.center
            onView.addSubview(self.loadingViewObj)
        }
    }
    
    public func hide() {
        DispatchQueue.main.async {
            if self.loadingViewObj != nil{
                self.loadingViewObj.removeSelf()
            }
        }
    }
}
