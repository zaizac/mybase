import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'ui-inbox-detail',
  templateUrl: './inbox-detail.component.html',
  styleUrls: ['./inbox-detail.component.scss']
})
export class InboxDetailComponent implements OnInit {

  mail = true

  @Output() replyMessage = new EventEmitter();

  constructor(private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    // this.route.params.switchMap((params: Params) => this.service.getMail(+params['id'])).subscribe((mail: Mail) => (this.mail = mail));
  }

  goToReply(mail): void {
    this.replyMessage.emit(mail);
  }

  trash(id) {
    // this.service.getMail(id).then(mail => {
    //   mail.trash = true;
    //   mail.sent = false;
    //   mail.draft = false;
    //   mail.starred = false;
    // });
    this.router.navigate(['inbox/inbox-list/inboxList']);
  }
}
