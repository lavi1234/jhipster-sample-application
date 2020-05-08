import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnquiryDetails } from 'app/shared/model/enquiry-details.model';

@Component({
  selector: 'jhi-enquiry-details-detail',
  templateUrl: './enquiry-details-detail.component.html'
})
export class EnquiryDetailsDetailComponent implements OnInit {
  enquiryDetails: IEnquiryDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enquiryDetails }) => (this.enquiryDetails = enquiryDetails));
  }

  previousState(): void {
    window.history.back();
  }
}
