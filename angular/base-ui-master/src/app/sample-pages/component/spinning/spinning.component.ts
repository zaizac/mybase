import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-spinning',
  templateUrl: './spinning.component.html',
  styleUrls: ['./spinning.component.scss']
})
export class SpinningComponent implements OnInit {

  timeLeft: number = 10;
  interval;
  subscribeTimer: any;
  val: boolean = false;
  constructor() { }

  ngOnInit() {
  }

  public show(){
    this.val = true;

    this.interval = setInterval(() => {
      if(this.timeLeft > 0) {
        this.timeLeft--;
      } else {
        this.timeLeft = 10;
        this.val = false;
      }
    },1000)
  }
}
