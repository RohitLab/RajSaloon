import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { NgbDatepickerConfig, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;

  // form: any;
  // submitted: boolean = false;
  // powers: Array <string> =  [
  //   '09:00 AM - 10:00 AM',
  //   '10:00 AM - 11:00 AM',
  //   '11:00 AM - 12:00 PM',
  //   '12:00 PM - 01:00 PM',
  //   '01:00 PM - 02:00 PM',
  //   '03:00 PM - 04:00 PM',
  //   '04:00 PM - 05:00 PM',
  //   '05:00 PM - 06:00 PM',
  //   '06:00 PM - 07:00 PM',
  // ];
  // form: any;
  // submitted: boolean = false;
  // powers: Array <string> =  [
  //   '09:00 AM - 10:00 AM',
  //   '10:00 AM - 11:00 AM',
  //   '11:00 AM - 12:00 PM',
  //   '12:00 PM - 01:00 PM',
  //   '01:00 PM - 02:00 PM',
  //   '03:00 PM - 04:00 PM',
  //   '04:00 PM - 05:00 PM',
  //   '05:00 PM - 06:00 PM',
  //   '06:00 PM - 07:00 PM',
  // ];
  form: any;
  powers: any;
  submitted = false;

  minDate: NgbDateStruct | undefined;
  maxDate: NgbDateStruct | undefined;

  constructor(private accountService: AccountService, private loginModalService: LoginModalService, private config: NgbDatepickerConfig) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.powers = [
      '09:00 AM - 10:00 AM',
      '10:00 AM - 11:00 AM',
      '11:00 AM - 12:00 PM',
      '12:00 PM - 01:00 PM',
      '01:00 PM - 02:00 PM',
      '03:00 PM - 04:00 PM',
      '04:00 PM - 05:00 PM',
      '05:00 PM - 06:00 PM',
      '06:00 PM - 07:00 PM',
    ];

    const current = new Date();
    this.minDate = {
      year: current.getFullYear(),
      month: current.getMonth() + 1,
      day: current.getDate(),
    };

    this.maxDate = {
      year: current.getFullYear(),
      month: current.getMonth() + 1,
      day: current.getDate() + 7,
    };
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  onSubmit(form: any): any {
    this.submitted = true;
    this.form = form;
  }
}
