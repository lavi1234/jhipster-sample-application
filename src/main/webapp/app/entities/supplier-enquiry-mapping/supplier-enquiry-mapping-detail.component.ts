import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplierEnquiryMapping } from 'app/shared/model/supplier-enquiry-mapping.model';

@Component({
  selector: 'jhi-supplier-enquiry-mapping-detail',
  templateUrl: './supplier-enquiry-mapping-detail.component.html'
})
export class SupplierEnquiryMappingDetailComponent implements OnInit {
  supplierEnquiryMapping: ISupplierEnquiryMapping | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supplierEnquiryMapping }) => (this.supplierEnquiryMapping = supplierEnquiryMapping));
  }

  previousState(): void {
    window.history.back();
  }
}
