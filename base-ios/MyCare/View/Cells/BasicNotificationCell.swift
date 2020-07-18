//
//  BasicNotificationCell.swift
//  PagohCare
//
//  Created by Cookie on 4/27/20.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import UIKit

class BasicNotificationCell: UITableViewCell {

    @IBOutlet weak var holderView: UIView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var contentLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        self.initialSetup()
    }
    
    private func initialSetup() {
        self.holderView.bottomAnchor.constraint(equalTo: contentView.bottomAnchor).isActive = true
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func updateUI(_ notification: Notify) {
        titleLabel.text = notification.title
        dateLabel.text = notification.date
        contentLabel.text = notification.content
    }
 }
