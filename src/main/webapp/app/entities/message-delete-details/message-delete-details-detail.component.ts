import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMessageDeleteDetails } from 'app/shared/model/message-delete-details.model';

@Component({
  selector: 'jhi-message-delete-details-detail',
  templateUrl: './message-delete-details-detail.component.html'
})
export class MessageDeleteDetailsDetailComponent implements OnInit {
  messageDeleteDetails: IMessageDeleteDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ messageDeleteDetails }) => (this.messageDeleteDetails = messageDeleteDetails));
  }

  previousState(): void {
    window.history.back();
  }
}
