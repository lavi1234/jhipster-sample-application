import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnquiryMaterial } from 'app/shared/model/enquiry-material.model';

@Component({
  selector: 'jhi-enquiry-material-detail',
  templateUrl: './enquiry-material-detail.component.html'
})
export class EnquiryMaterialDetailComponent implements OnInit {
  enquiryMaterial: IEnquiryMaterial | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enquiryMaterial }) => (this.enquiryMaterial = enquiryMaterial));
  }

  previousState(): void {
    window.history.back();
  }
}
