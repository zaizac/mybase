//
//  AdminEventCell.swift
//  PagohCare
//
//  Created by Cookie on 5/7/20.
//  Copyright © 2020 Bestinet. All rights reserved.
//

import UIKit

class AdminEventCell: UITableViewCell {
    
    @IBOutlet private weak var holderView: UIView!
    @IBOutlet private weak var eventNameLabel: UILabel!
    @IBOutlet private weak var eventDateLabel: UILabel!
    @IBOutlet private weak var indicatorIV: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        self.initialSetup()
    }
    
    private func initialSetup() {
        
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
        self.holderView.backgroundColor = selected ? Colors.lightGreen : .white
        self.indicatorIV.image = selected ? UIImage(named: "ic_checked") : nil
    }
    
    func updateUI(event: AdminEvent) {
        self.eventNameLabel.text = event.name
        self.eventDateLabel.text = "\(event.date). \(event.time)"
    }
}
