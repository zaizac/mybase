//
//  Alert.swift
//  RadioButtonTest
//
//  Created by Hainizam on 16/08/2018.
//  Copyright © 2018 Potkins. All rights reserved.
//

import Foundation
import UIKit

protocol Alert { }

extension Alert where Self: UIViewController {
    func postMediaAlert(with vc: UIViewController, completion: @escaping (() -> ())) {
        
        let alert = UIAlertController(title: "Ping!", message: "Adding attachment to the post?", preferredStyle: .actionSheet)
        let addMedia = UIAlertAction(title: "Gallery", style: .default) { _ in
            completion()
        }
        
        let cancel = UIAlertAction(title: "Nah, just the text.", style: .cancel)
        
        alert.addAction(addMedia)
        alert.addAction(cancel)
        
        vc.present(alert, animated: true)
    }
    
    func validationError(with vc: UIViewController) {
        let alert = UIAlertController(title: "Warning!", message: "Ops! There is empty field..", preferredStyle: .alert)
        let okay = UIAlertAction(title: "OK", style: .destructive, handler: nil)
        
        alert.addAction(okay)
        vc.present(alert, animated: true)
    }
    
    func popBasicAlert(withTitle title: String?, message: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let okayButton = UIAlertAction(title: "OK", style: .default, handler: nil)
        alert.addAction(okayButton)
        present(alert, animated: true)
    }
    
    func popBasicAlert(withTitle title: String?, message: String?, completion: @escaping (() -> ())) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let okayButton = UIAlertAction(title: "OK", style: .default) { (action) in
            completion()
        }
        alert.addAction(okayButton)
        present(alert, animated: true)
    }
    
    func confirmationAlert(withTitle title: String, message: String, completion: @escaping (() -> ())) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let yesButton = UIAlertAction(title: Constants.yes.localized(), style: .destructive) { (action) in
            completion()
        }
        
        let noButton = UIAlertAction(title: Constants.no.localized(), style: .default)
        
        alert.addAction(noButton)
        alert.addAction(yesButton)
        
        present(alert, animated: true)
    }
    
    func confirmationAlertMessage(withTitle title: String, message: String, completion: @escaping (() -> ())) {
        let alert = UIAlertController(title: title, message: "", preferredStyle: .alert)
        let yesButton = UIAlertAction(title: "OK", style: .default) { (action) in
            completion()
        }
        
        let noButton = UIAlertAction(title: Constants.cancel.localized(), style: .destructive)
        
        alert.addAction(noButton)
        alert.addAction(yesButton)
        
        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.alignment = .left
        let messageText = NSAttributedString(
            string: "\n\(message)",
            attributes: [
                NSAttributedString.Key.paragraphStyle: paragraphStyle,
                NSAttributedString.Key.foregroundColor : UIColor.darkGray,
                NSAttributedString.Key.font: UIFont.systemFont(ofSize: 15.0, weight: .regular)
            ]
        )

        alert.setValue(messageText, forKey: "attributedMessage")
        
        present(alert, animated: true)
    }
    
    func confirmationAlertAttributedMessage(withTitle title: String, attrMessage: NSAttributedString, completion: @escaping (() -> ())) {
        let alert = UIAlertController(title: title, message: "", preferredStyle: .alert)
        let yesButton = UIAlertAction(title: "OK", style: .default) { (action) in
            completion()
        }
        
        let noButton = UIAlertAction(title: Constants.cancel.localized(), style: .destructive)
        
        alert.addAction(noButton)
        alert.addAction(yesButton)
        
        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.alignment = .left

        alert.setValue(attrMessage, forKey: "attributedMessage")
        
        present(alert, animated: true)
    }
    
    func settingsAlert(withTitle title: String, message: String, completion: @escaping (() -> ())) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let yesButton = UIAlertAction(title: "Settings", style: .destructive) { (action) in
            completion()
        }
        
        let noButton = UIAlertAction(title: Constants.cancel.localized(), style: .cancel)
        
        alert.addAction(noButton)
        alert.addAction(yesButton)
        
        present(alert, animated: true)
    }
    
    func basicActionSheet(toViewController vc: UIViewController, actions: [UIAlertAction], title: String? = nil, message: String? = nil, sender: UIButton) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .actionSheet)
        
        for action in actions {
            alert.addAction(action)
        }
        
        let cancel = UIAlertAction(title: Constants.cancel.localized(), style: .cancel)
        alert.addAction(cancel)
        
        if let popoverController = alert.popoverPresentationController {
            popoverController.sourceView = sender
            popoverController.sourceRect = sender.bounds
        }
        
        vc.present(alert, animated: true)
    }
}












