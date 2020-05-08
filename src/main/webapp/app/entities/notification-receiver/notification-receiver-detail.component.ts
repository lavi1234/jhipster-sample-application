import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INotificationReceiver } from 'app/shared/model/notification-receiver.model';

@Component({
  selector: 'jhi-notification-receiver-detail',
  templateUrl: './notification-receiver-detail.component.html'
})
export class NotificationReceiverDetailComponent implements OnInit {
  notificationReceiver: INotificationReceiver | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notificationReceiver }) => (this.notificationReceiver = notificationReceiver));
  }

  previousState(): void {
    window.history.back();
  }
}
